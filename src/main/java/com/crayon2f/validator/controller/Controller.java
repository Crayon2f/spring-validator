package com.crayon2f.validator.controller;

import com.crayon2f.common.pojo.Data;
import com.crayon2f.validator.beans.Prisoner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by feifan.gou@gmail.com on 2018/8/22 15:29.
 */
@RestController
@RequestMapping("/")
@Api(description = "spring 参数校验")
@Validated
public class Controller {


    @ApiOperation(value = "单个参数【String】")
    @RequestMapping(value = "singletonString", method = RequestMethod.GET)
    @ApiImplicitParam(name = "param", value = "param", paramType = "query")
    public String singletonString(
            @NotEmpty(message = "param 不能为空")
            @Length(max = 10, min = 1, message = "length 1 ~10")
            @Pattern(regexp = "[\\d]+")
                    String param) {

        return param;
    }

    @ApiOperation(value = "单个参数【Integer】")
    @RequestMapping(value = "singletonInteger", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "query")
    })
    public Integer singletonInteger(
            @NotNull(message = "param 不能为空")
//            @Max(12)
//            @Min(4)
            @Range(min = 4, max = 12)
                    Integer param) {

        return param;
    }

    @ApiOperation(value = "单个参数【Double】")
    @RequestMapping(value = "singletonDouble", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "query"),
            @ApiImplicitParam(name = "param2", value = "param2", paramType = "query")
    })
    public Double singletonDouble(
            @NotNull(message = "param 不能为空")
            @DecimalMax("16.5")
            @DecimalMin("12.5")
                    Double param,
            @NotNull(message = "param 不能为空")
            @DecimalMax("16.5")
            @DecimalMin("12.5")
                    Double param2) {

        return param + param2;
    }

    @ApiOperation(value = "单个参数【Date】")
    @RequestMapping(value = "singletonDate", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "query")
    })
    public Date singletonDate(
            @NotNull(message = "date 不能为空")
                    Date param) {
        return param;
    }

    @ApiOperation(value = "单个参数【LocalDateTime】")
    @RequestMapping(value = "singletonLocalDateTime", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "query")
    })
    public LocalDateTime singletonLocalDateTime(
            @NotNull(message = "localDate 不能为空")
            //使用 LocalDateTime 时候需要增加这两个, 不然报错, spring 早点兼容吧
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(required = false)
                    LocalDateTime param) {
        return param;
    }

    @ApiOperation(value = "单个参数【List】")
    @RequestMapping(value = "singletonCollection", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "body")
    })
    public List<String> singletonCollection(
            @RequestBody  @Size(min = 1, max = 10) List<String> param) {
        if (CollectionUtils.isNotEmpty(param)) {
            return param;
        }
        return Data.STRING_LIST;
    }

    @ApiOperation(value = "单个参数【Array】") //与list类似
    @RequestMapping(value = "singletonArray", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "param", paramType = "body")
    })
    public String[] singletonArray(
            @RequestBody @Size(min = 1, max = 10) String[] param) {
        if (ArrayUtils.isNotEmpty(param)) {
            return param;
        }
        return new String[]{"123","333"};
    }

    /* 单个参数,并且以RequestParam传入时 必须有构造函数 不然会报错(No primary or default constructor found for XX) 例如 List,LocalDateTime 等类*/

    // ------------------------------------- bean -----------------------------------


    @ApiOperation(value = "以bean形式传参")
    @RequestMapping(value = "bean", method = RequestMethod.POST)
//    public void bean(@RequestBody @Validated({Prisoner.First.class, Prisoner.Second.class}) Prisoner prisoner) {
    public void bean(@RequestBody @Validated Prisoner prisoner) {

        System.out.println(prisoner);
    }
}
