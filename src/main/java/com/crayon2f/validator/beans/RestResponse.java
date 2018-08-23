package com.crayon2f.validator.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {

    private RestCode code;
    private String msg;

    public static RestResponse success() {

        return new RestResponse(RestCode.SUCCESS, StringUtils.EMPTY);
    }

    public static RestResponse fail(String message) {

        return new RestResponse(RestCode.FAIL, message);
    }
}
