package com.crayon2f.validator.beans;

import lombok.Getter;

/**
 * Created by feifan.gou@gmail.com on 2018/8/22 15:47.
 */
@Getter
public enum RestCode {

    SUCCESS,
    FAIL;

    private final String message;

    RestCode() {
        this.message = this.name();
    }
}
