package com.cloud.spring.dataSource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 插入和更新 自动填充数据
 * @author wtx
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {



    @Override
    public void insertFill(MetaObject metaObject) {
        fill(metaObject,"delete_flag",0);
        fill(metaObject,"createTime",LocalDateTime.now());
        fill(metaObject,"updateTime",LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fill(metaObject,"updateTime",LocalDateTime.now());
    }

    /**
     * 填充数据
     * @param metaObject
     * @param fieldName
     * @param value
     */
        private void fill(MetaObject metaObject,String fieldName,Object value){
            if(metaObject.hasGetter(fieldName)){
                setFieldValByName(fieldName,  value, metaObject);

                //存在值需不需要更新
                /* Object updateTime = getFieldValByName(fieldName, metaObject);
                if (Objects.isNull(updateTime)) {
                    setFieldValByName(fieldName,  value, metaObject);
                }*/
            }
        }
}
