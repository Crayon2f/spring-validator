package com.crayon2f.validator.beans;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by feifan.gou@gmail.com on 2018/11/13 17:25.
 */
@Data
public class ValidatedBean {

    @Valid
    private List<Prisoner> prisonerList;
}
