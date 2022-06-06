package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.constant.BaseConstant;
import com.cloud.common.enums.CommonEnum;
import com.cloud.common.util.*;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.spring.app.ServletUtils;
import com.cloud.user.dto.LoginDTO;
import com.cloud.user.dto.SysUserDTO;
import com.cloud.user.entity.*;
import com.cloud.user.mapper.*;
import com.cloud.user.service.SysUserService;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.vo.SysUserDetailVO;
import com.cloud.wechat.dto.WechatSendDTO;
import com.cloud.wechat.feign.SendMessageFeign;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-11
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SendMessageFeign sendMessageFeign;
    @Autowired
    SysUserRoleMapper userRoleMapper;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysRoleMenuMapper roleMenuMapper;
    @Autowired
    SysMenuMapper menuMapper;

    @Override
    public R login(LoginDTO loginDTO) {
        SysUserEntity user=sysUserMapper.login(loginDTO);
        if (Objects.isNull(user)){
            return BR.genResult(CommonEnum.NO_USER);
        }
        if (user.getDeleteFlag()==1){
            return BR.genResult(CommonEnum.USER_DISABLE);
        }

        LoginUserVO userVo = new LoginUserVO() {{
            setUserName(user.getUserName());
            setId(user.getId());
            setSignStr(RandomStringUtils.randomNumeric(5));
        }};
        final String jwt = JwtUtils.createJWT(userVo);
        userVo.setToken(jwt);
        return BR.genSuccessResult(userVo);
    }

    @Override
    public R getUserMenu() {
        final LoginUserVO user = ServletUtils.getBase64User();
        final List<SysUserRoleEntity> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRoleEntity>().eq("user_id", user.getId()));
        List<Integer> roles = userRoleList.stream().map(SysUserRoleEntity::getRoleId).toList();

        HashMap<String,Object> map=new HashMap<>(3);

        if (CollectionUtils.isEmpty(roles)){
            return BR.genErrorResult("无权限");
        }
        final QueryWrapper<SysRoleMenuEntity> wrapper = WrapperUtils.getWrapper(SysRoleMenuEntity.class);
        final List<SysRoleMenuEntity> roleMenus = roleMenuMapper.selectList(wrapper.in("role_id", roles));

        if (CollectionUtils.isEmpty(roleMenus)){
            return BR.genErrorResult("无权限");
        }
        //获取角色集合
        final QueryWrapper<SysRoleEntity> wrapperRole = WrapperUtils.getWrapper(SysRoleEntity.class);
        List<Integer> roleIds = roleMenus.stream().map(SysRoleMenuEntity::getRoleId).toList();
        final List<SysRoleEntity> roleEntities = roleMapper.selectList(wrapperRole.in("id", roleIds));
        //封装角色
        final List<String> roleCodeList = roleEntities.stream().map(SysRoleEntity::getCode).toList();
        map.put("roleList",roleCodeList);

        //获取菜单集合
        final QueryWrapper<SysMenuEntity> wrapperMenu = WrapperUtils.getWrapper(SysMenuEntity.class);
        List<Integer> menus = roleMenus.stream().map(SysRoleMenuEntity::getMenuId).distinct().toList();
        final List<SysMenuEntity> menuEntities = menuMapper.selectList(wrapperMenu.in("id", menus).orderByAsc("sort","id"));

        //封装按钮
        List<String> buttonList = menuEntities.stream().filter(p -> p.getType().equals(2)).map(SysMenuEntity::getAuthCode).collect(Collectors.toList());
        map.put("buttonList",buttonList);
        //封装tree菜单
        final List<SysMenuEntity> menuList = menuEntities.stream().filter(p -> !p.getType().equals(2)).collect(Collectors.toList());
        map.put("menuList",getChildrenList(0,menuList));
        return BR.genSuccessResult(map);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public <T> R<T> editUser(SysUserEntity sysUser) {
        final List<Integer> roleList = sysUser.getRoleIds();
        if (sysUser.getId()==null){
            sysUser.setPassword(MD5Util.md5(BaseConstant.PASSWORD));
            sysUserMapper.insert(sysUser);
        }else{
            VerifyUtils.isEmpty(sysUserMapper.selectById(sysUser.getId()));
            sysUserMapper.updateById(sysUser);
            userRoleMapper.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id",sysUser.getId()));
        }
        if (!CollectionUtils.isEmpty(roleList)){
            roleList.forEach(r->{
                SysUserRoleEntity role=new SysUserRoleEntity(){{
                    setUserId(sysUser.getId());
                    setRoleId(r);
                }};
                userRoleMapper.insert(role);
            });
        }
        return BR.genSuccessResult();
    }

    @Override
    public <T> R<T> resetPassword(String ids) {
        final List<String> list = Arrays.asList(ids.split(","));
        SysUserEntity user=new SysUserEntity();
        user.setPassword(MD5Util.md5(BaseConstant.PASSWORD));
        sysUserMapper.update(user,new QueryWrapper<SysUserEntity>().in("id",list));
        return BR.genSuccessResult();
    }

    @Override
    public R getPageUserList(SysUserDTO sysUser) {
        Page<SysUserDetailVO> page = new Page<>(sysUser.getCurrentPage(),sysUser.getPageSize());
        final IPage<SysUserDetailVO> userList = sysUserMapper.getPageUserList(page, sysUser);
        return BR.genSuccessResult(userList);
    }

    @Override
    public R getUser(Integer id) {
        final SysUserEntity user = sysUserMapper.selectById(id);
        SysUserDetailVO userDetailVO=new SysUserDetailVO();
        BeanUtils.copyProperties(user,userDetailVO);
        final QueryWrapper<SysUserRoleEntity> wrapper = new QueryWrapper<SysUserRoleEntity>().eq("user_id", userDetailVO.getId());
        final List<SysUserRoleEntity> roleEntities = userRoleMapper.selectList(wrapper);
        List<Integer> list=new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleEntities)){
              list = roleEntities.stream().map(SysUserRoleEntity::getRoleId).toList();
        }
        userDetailVO.setRoleIds(list);
        return BR.genSuccessResult(userDetailVO) ;
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
}
