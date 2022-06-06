package com.cloud.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.dto.PageDTO;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/26 19:01
 * @Version 1.0
 */
@Log4j2
public class WrapperUtils {

    public static  <T> QueryWrapper<T> getWrapper(Class<T> t){
        final Field[] fields = t.getDeclaredFields();
        final QueryWrapper<T> wrapper = new QueryWrapper<>();

        for (Field field : fields) {
            if ("status".equals(field.getName())){
                wrapper.eq("status", 0);
            }
            if ("deleteFlag".equals(field.getName())){
                wrapper.eq("delete_flag", 0);
            }
        }
        return wrapper;
    }


    public static <T> Page<T> page(PageDTO pageDTO){

       return new Page<>(pageDTO.getCurrentPage(), pageDTO.getPageSize());
    }

}
