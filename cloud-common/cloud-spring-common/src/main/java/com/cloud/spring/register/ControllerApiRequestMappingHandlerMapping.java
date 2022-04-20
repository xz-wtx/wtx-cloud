package com.cloud.spring.register;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description TODO 修改请求路径（mapping路径+父类的mapping路径）不同版本可能有修改，当前版本springboot 2.6.6
 * @Date 2022/3/1 9:09
 * @Version 1.0
 * @author wtx
 */
@Log4j2
public class ControllerApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 是否打印mapping
     */
    private final boolean logMappingPath;


    public ControllerApiRequestMappingHandlerMapping(boolean logMappingPath) {
        this.logMappingPath = logMappingPath;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(@NonNull Method method,@NonNull Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        Class<?> superClass = handlerType.getSuperclass();
        mappingInfo = appendParentRequestMapping(superClass,mappingInfo);
        logMapping(mappingInfo);
        return mappingInfo;
    }

    /**
     * 添加父类的mapping
     * @param handlerType
     * @param mappingInfo
     * @return
     */
    protected RequestMappingInfo appendParentRequestMapping(Class<?> handlerType, RequestMappingInfo mappingInfo) {

        if(handlerType==null) {
            return mappingInfo;
        }
        if (mappingInfo==null){
            return null;
        }
        RequestMapping parentRequestMapping = handlerType.getAnnotation(RequestMapping.class);
        if(parentRequestMapping!=null&&parentRequestMapping.value().length>0) {
            //使用path工具向前追加父类的path
            //不同版本可能有修改，当前版本springboot 2.6.6
            RequestMappingInfo info = createRequestMappingInfo(handlerType);
            if (Objects.nonNull(info)){
                mappingInfo=info.combine(mappingInfo);
            }
        }
        return appendParentRequestMapping(handlerType.getSuperclass(),mappingInfo);
    }

    /**
     * 由于spring boot2不打印mapping了，不习惯，就自己打印一下，但是有些系统mapping也不打印，有空再研究怎么打印
     * @param info
     */
    private void logMapping(RequestMappingInfo info) {
        if(!logMappingPath||info==null) {
            return;
        }
        //此处等价Logger.info
        log.info("mapping path:{}",info.toString());

    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = (RequestMapping) AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = element instanceof Class ? this.getCustomTypeCondition((Class) element) : this.getCustomMethodCondition((Method) element);
        return requestMapping != null ? this.createRequestMappingInfo(requestMapping, condition) : null;
    }
}
