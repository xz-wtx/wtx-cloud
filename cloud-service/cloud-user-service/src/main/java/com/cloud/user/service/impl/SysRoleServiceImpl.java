package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.user.entity.SysMenuEntity;
import com.cloud.user.entity.SysRoleEntity;
import com.cloud.user.entity.SysRoleMenuEntity;
import com.cloud.user.mapper.SysMenuMapper;
import com.cloud.user.mapper.SysRoleMapper;
import com.cloud.user.mapper.SysRoleMenuMapper;
import com.cloud.user.service.SysMenuService;
import com.cloud.user.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysRoleMenuMapper roleMenuMapper;
    @Autowired
    SysMenuMapper menuMapper;
    @Autowired
    SysMenuService menuService;

    @Override
    public <T> R<T> editRole(SysRoleEntity sysRoleEntity) {
        if (sysRoleEntity.getId() == null) {
            roleMapper.insert(sysRoleEntity);
        } else {
            VerifyUtils.isEmpty(roleMapper.selectById(sysRoleEntity.getId()));
            roleMapper.updateById(sysRoleEntity);
        }
        return BR.genSuccessResult();
    }

    @Override
    public <T> R<T> allotMenu(SysRoleEntity sysRoleEntity) {
        roleMenuMapper.delete(new QueryWrapper<SysRoleMenuEntity>().eq("role_id", sysRoleEntity.getId()));
        if (CollectionUtils.isEmpty(sysRoleEntity.getMenuIds())) {
            return BR.genSuccessResult();
        }
        sysRoleEntity.getMenuIds().forEach(p -> {
            roleMenuMapper.insert(new SysRoleMenuEntity() {{
                setMenuId(p);
                setRoleId(sysRoleEntity.getId());
            }});
        });
        return BR.genSuccessResult();
    }

    @Override
    public R getList(SysRoleEntity sysRoleEntity) {
        final QueryWrapper<SysRoleEntity> wrapper = WrapperUtils.getWrapper(SysRoleEntity.class);
        List<SysRoleEntity> list = roleMapper.selectList(wrapper);
        return BR.genSuccessResult(list);
    }


    @Override
    public R getPageList(SysRoleEntity role) {


        final QueryWrapper<SysRoleEntity> roleWrapper = new QueryWrapper<SysRoleEntity>().eq("delete_flag", 0);
        roleWrapper.like(Objects.nonNull(role.getCode()),"code",role.getCode());
        roleWrapper.like(StringUtils.isNotBlank(role.getName()),"name",role.getName());
        final Page<SysRoleEntity> page = WrapperUtils.page(role);
        Page<SysRoleEntity> list = roleMapper.selectPage(page,roleWrapper);
        list.getRecords().forEach(p -> {
                    //获取tree最后一层数据
                    final List<SysRoleMenuEntity> roleMenus = roleMenuMapper.selectList(WrapperUtils.getWrapper(SysRoleMenuEntity.class).eq("role_id", p.getId()));
                    List<Map<String, Object>> maps = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(roleMenus)) {
                    final QueryWrapper<SysMenuEntity> wrapper = WrapperUtils.getWrapper(SysMenuEntity.class);
                    final List<SysMenuEntity> menuList = menuMapper.selectList(wrapper.in("id", roleMenus.stream().map(SysRoleMenuEntity::getMenuId).toList()));
                    final Map<Integer, List<SysMenuEntity>> listMap = menuList.stream().collect(Collectors.groupingBy(SysMenuEntity::getParentId));
                    lastTree(menuList, 0, listMap, maps);
                 }
            p.setListMap(maps);
        });
        return BR.genSuccessResult(list);
    }


    //获取tree最后一层数据
    public void  lastTree(List<SysMenuEntity> list, Integer parentId,Map<Integer, List<SysMenuEntity>> listMap, List<Map<String, Object>> maps){
        for (SysMenuEntity menu : list) {
            Integer id = menu.getId();
            Integer pid = menu.getParentId();
            if (parentId.equals(pid)) {
                if (CollectionUtils.isEmpty(listMap.get(id))){
                    maps.add(new HashMap<>(1){{put("id",id);}});
                   continue;
                }
                lastTree(list, id,listMap,maps);
            }
        }
    }
}
