package com.cloud.user.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/5/6 13:00
 * @Version 1.0
 */
@Log4j2
@Data
public class SortDTO {

    public Integer bId;

    public Integer aId;

    //before、inner、after
    public String type;

}
