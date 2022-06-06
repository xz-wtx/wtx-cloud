package com.cloud.common.vo;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @author wtx
 * @Description TODO 树形
 * @Date 2022/5/3 12:37
 * @Version 1.0
 */
@Log4j2
@Data
public class TreeVO {

    private String value;

    private String label;

    public Integer id;

    public Integer parentId;

    public List<TreeVO> children;
}
