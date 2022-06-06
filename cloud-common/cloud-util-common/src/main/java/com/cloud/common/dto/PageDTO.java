package com.cloud.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/26 16:28
 * @Version 1.0
 */
@Data
@Log4j2
public class PageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    //只写，不返回
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer currentPage=1;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer pageSize=10;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseRequest [");
        if (currentPage != null) {
            builder.append("currentPage=");
            builder.append(currentPage);
            builder.append(", ");
        }
        if (pageSize != null) {
            builder.append("pageSize=");
            builder.append(pageSize);
        }
        builder.append("]");
        return builder.toString();
    }

}
