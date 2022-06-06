package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.TreeUtils;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.user.entity.SysDeptEntity;
import com.cloud.user.mapper.SysDeptMapper;
import com.cloud.user.service.SysDeptService;
import com.cloud.common.vo.TreeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    SysDeptMapper deptMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public <T> R<T> editDept(SysDeptEntity sysDeptEntity) {

        if (Objects.isNull(sysDeptEntity.getId())){
            if(Objects.isNull(sysDeptEntity.getParentId())){
                sysDeptEntity.setLevel(0);
                sysDeptEntity.setParentId(0);
            }
            deptMapper.insert(sysDeptEntity);
        }else{
            if (Objects.nonNull(sysDeptEntity.getParentId())&&sysDeptEntity.getParentId()!=0){
                final SysDeptEntity parent = deptMapper.selectOne(new QueryWrapper<SysDeptEntity>().eq("id", sysDeptEntity.getParentId()));
                sysDeptEntity.setLevel(parent.getLevel()+1);
            }
            final SysDeptEntity sysDept = deptMapper.selectById(sysDeptEntity.getId());
            deptMapper.updateById(sysDeptEntity);
            //是否改变状态，如果改变则统一修改子级状态
            if (!sysDept.getStatus().equals(sysDeptEntity.getStatus())
                    ||!sysDept.getDeleteFlag().equals(sysDeptEntity.getDeleteFlag())){
                recursive(List.of(sysDept.getId()),sysDeptEntity);
            }
        }
        return BR.genSuccessResult();
    }

    @Override
    public R getTree(SysDeptEntity sysDeptEntity) {
        List<TreeVO> trees = deptMapper.getTreeList(sysDeptEntity);

        if (Objects.isNull(sysDeptEntity.getId())&&Objects.isNull(sysDeptEntity.getLevel())){
            return BR.genSuccessResult(TreeUtils.getTree(0,trees));
        }
        List<TreeVO> treeAll = deptMapper.getTreeList(new SysDeptEntity());
        for (TreeVO tree : trees) {
            tree.setChildren(TreeUtils.getTree(tree.getId(),treeAll));
        }
        return BR.genSuccessResult(trees);
    }

    @Override
    public R getTreeList(SysDeptEntity dept) {
        final QueryWrapper<SysDeptEntity> wrapper = new QueryWrapper<SysDeptEntity>().eq("delete_flag", 0);
        wrapper.like(StringUtils.isNotBlank(dept.getName()),"name",dept.getName());
        wrapper.eq(Objects.nonNull(dept.getLevel()),"level",dept.getLevel());
        final List<SysDeptEntity> list = deptMapper.selectList(wrapper);
        if (StringUtils.isNotBlank(dept.getName())||Objects.nonNull(dept.getLevel())){
            return BR.genSuccessResult(list);
        }
        final List<SysDeptEntity> treeList = getTree(0, list);
        return BR.genSuccessResult(treeList);
    }

    @Override
    public R queryDept(SysDeptEntity sysDeptEntity) {
        final SysDeptEntity entity = deptMapper.selectById(sysDeptEntity.getId());
        VerifyUtils.isEmpty(entity);

        return BR.genSuccessResult(entity);
    }


    /**
     * 递归查询子节点
     * @param id  根节点
     * @param all   所有节点
     * @return 根节点信息
     */
    public static List<SysDeptEntity> getTree(Integer id, List<SysDeptEntity> all) {
        return all.stream().filter(m -> Objects.equals(m.getParentId(), id)).peek(
                (m) -> m.setChildren(getTree(m.getId(), all))
        ).collect(Collectors.toList());
    }
    //递归修改状态
    void recursive(List<Integer> ids, SysDeptEntity dept){
        final List<SysDeptEntity> list = deptMapper.selectList(new QueryWrapper<SysDeptEntity>().in("parent_id", ids));
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        final List<Integer> listIds = list.stream().map(SysDeptEntity::getId).toList();
        dept.setLevel(dept.getLevel()+1);
        deptMapper.update(dept,new QueryWrapper<SysDeptEntity>().in("id",listIds));
        recursive(listIds,dept);
    }
}
