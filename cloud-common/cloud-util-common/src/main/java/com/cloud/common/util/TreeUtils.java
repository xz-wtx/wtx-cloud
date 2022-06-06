package com.cloud.common.util;

import com.cloud.common.vo.TreeVO;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/5/3 14:13
 * @Version 1.0
 */
@Log4j2
public class TreeUtils {
    /**
     * 递归查询子节点
     * @param id  根节点
     * @param all   所有节点
     * @return 根节点信息
     */
    public static List<TreeVO> getTree(Integer id, List<TreeVO> all) {
        return all.stream().filter(m -> Objects.equals(m.getParentId(), id)).peek(
                (m) -> m.setChildren(getTree(m.getId(), all))
        ).collect(Collectors.toList());
    }
}
