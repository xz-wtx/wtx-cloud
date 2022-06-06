package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.SnowflakeUtil;
import com.cloud.common.util.TreeUtils;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.common.vo.TreeVO;
import com.cloud.user.dto.SortDTO;
import com.cloud.user.entity.SysDeptEntity;
import com.cloud.user.entity.SysMenuEntity;
import com.cloud.user.mapper.SysMenuMapper;
import com.cloud.user.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysMenuServiceImpl  implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;

    @Override
    public <T> R<T> editMenu(SysMenuEntity sysMenuEntity) {
        if (sysMenuEntity.getId()==null){
            if (Objects.isNull(sysMenuEntity.getParentId())){
                sysMenuEntity.setParentId(0);
            }
            menuMapper.insert(sysMenuEntity);
        }else{
            final SysMenuEntity entity = menuMapper.selectById(sysMenuEntity.getId());
            menuMapper.updateById(sysMenuEntity);
                //是否改变状态，如果改变则统一修改子级状态
            if (!entity.getStatus().equals(sysMenuEntity.getStatus())
                    ||!entity.getDeleteFlag().equals(sysMenuEntity.getDeleteFlag())) {
                recursive(List.of(entity.getId()), sysMenuEntity);
            }
        }
        return BR.genSuccessResult();
    }

    @Override
    public <T> R<T> copyMenu(Integer id) {
        final SysMenuEntity menu = menuMapper.selectById(id);
        recursive(menu,menu.getParentId());
        return BR.genSuccessResult();
    }

    @Override
    public List<SysMenuEntity> getList(SysMenuEntity sysMenuEntity) {
        final QueryWrapper<SysMenuEntity> wrapper = WrapperUtils.getWrapper(SysMenuEntity.class);
        return menuMapper.selectList(wrapper);
    }

    @Override
    public <T> R<T> changeSort(SortDTO sortDTO) {
        final SysMenuEntity bEntity = menuMapper.selectById(sortDTO.getBId());
        final SysMenuEntity aEntity = menuMapper.selectById(sortDTO.getAId());
        if ("inner".equals(sortDTO.getType())){
            bEntity.setParentId(aEntity.getId());
        }else{
            bEntity.setParentId(aEntity.getParentId());
            Integer bSort=bEntity.getSort();
            bEntity.setSort(aEntity.getSort());
            aEntity.setSort(bSort);
        }
        menuMapper.updateById(bEntity);
        menuMapper.updateById(aEntity);
        return BR.genSuccessResult();
    }

    private void recursive(SysMenuEntity menu,Integer parentId) {
        if (Objects.isNull(menu)) return;
        final QueryWrapper<SysMenuEntity> wrapper = WrapperUtils.getWrapper(SysMenuEntity.class).eq("parent_id",menu.getId());
        List<SysMenuEntity> menuEntities = menuMapper.selectList(wrapper);
        String copy="_copy"+ (int)(Math.random()*1000);
        menu.setId(null);
        menu.setTitle(menu.getTitle()+copy);
        menu.setAuthCode(menu.getAuthCode()+copy);
        menu.setName(menu.getName()+copy);
        menu.setParentId(parentId);
        menuMapper.insert(menu);
        if (CollectionUtils.isEmpty(menuEntities))return;
        for (SysMenuEntity entity : menuEntities) {
            recursive(entity,menu.getId());
        }
    }

    @Override
    public R queryMenu(Integer  id) {
        return BR.genSuccessResult(menuMapper.selectById(id));
    }

    @Override
    public R getTreeList(SysMenuEntity menu) {
        final QueryWrapper<SysMenuEntity> wrapper = new QueryWrapper<SysMenuEntity>().eq("delete_flag", 0);
        wrapper.like(StringUtils.isNotBlank(menu.getTitle()),"title",menu.getTitle());
        wrapper.like(StringUtils.isNotBlank(menu.getPath()),"path",menu.getPath());
        wrapper.like(StringUtils.isNotBlank(menu.getAuthCode()),"auth_code",menu.getAuthCode());
        wrapper.orderByAsc("sort","id");
        final List<SysMenuEntity> entities = menuMapper.selectList(wrapper);
        if (!StringUtils.isAllBlank(menu.getAuthCode(),menu.getTitle(),menu.getPath())) {
            return BR.genSuccessResult(entities);
        }
        final List<SysMenuEntity> childrenList = getChildrenList(0,entities);
        return BR.genSuccessResult(childrenList);
    }

    @Override
    public R getTree(SysMenuEntity sysMenuEntity) {
        List<TreeVO> trees=menuMapper.getTreeList(sysMenuEntity);
       return BR.genSuccessResult(TreeUtils.getTree(0,trees));
    }

    /**
     * 递归查询子节点
     * @param id  根节点
     * @param all   所有节点
     * @return 根节点信息
     */
    private List<SysMenuEntity> getChildrenList(Integer id, List<SysMenuEntity> all) {
        return all.stream().filter(m -> Objects.equals(m.getParentId(), id)).peek(
                (m) -> m.setChildren(getChildrenList(m.getId(), all))
        ).collect(Collectors.toList());
    }

    //递归修改状态
    void recursive(List<Integer> ids, SysMenuEntity dept){
        final List<SysMenuEntity> list = menuMapper.selectList(new QueryWrapper<SysMenuEntity>().in("parent_id", ids));
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        final List<Integer> listIds = list.stream().map(SysMenuEntity::getId).toList();
        menuMapper.update(dept,new QueryWrapper<SysMenuEntity>().in("id",listIds));
        recursive(listIds,dept);
    }
}
