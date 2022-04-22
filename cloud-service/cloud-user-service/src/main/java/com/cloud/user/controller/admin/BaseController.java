package com.cloud.user.controller.admin;

import com.cloud.common.util.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/18 15:45
 * @Version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/admin")
public class BaseController{

    /**
     * @InitBinder标注的方法,只针对当前Controller有效!
     * InitBinder属于Controller级别的SpringMVC属性编辑器,并不是全局级别(针对所有@Controller)的属性编辑器哦！
     * 如果没有该方法,则会产生400状态码!
     * MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.util.Date!
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                setValue(DateUtils.parseDate(value));
            }
        });
    }
}
