package com.cnj.common.util;

import lombok.Builder;
import lombok.Data;

/**
 * Create by cnj on 2019-2-24
 */
@Data
@Builder
public class ResponseData {

    public static final String OK = "ok";

    public static final String ERROR = "error";

    private String status;

    private String message;

    private int code;

    private Object data;

}
