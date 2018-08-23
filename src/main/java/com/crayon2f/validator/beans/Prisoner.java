package com.crayon2f.validator.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by feifan.gou@gmail.com on 2018/8/23 10:54.
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@ApiModel("囚犯实体类")
public class Prisoner {

    @ApiModelProperty("名字")
    @NotEmpty(message = "name 不能为空", groups = First.class)
    private String name;
    @ApiModelProperty("囚龄")
    @NotNull(message = "囚龄不能为空")
    @Range(min = 20, max = 70, message = "囚龄 20~70", groups = {First.class, Second.class})
    private Integer age;
    @NotNull(message = "国籍不能为空", groups = Second.class)
    @ApiModelProperty("国籍")
    private String nationality;
    @ApiModelProperty("邮箱")
    @Email(message = "Email 格式错误")
    private String email;

    @NotNull(message = "狱友不能为空")
    @Size(min = 1, max = 10, message = "狱友 1~10")
    @ApiModelProperty("狱友")
    private List<Prisoner> inmateList;

    @Pattern(regexp = "[\\d]{2,6}", message = "编号错误格式")
    @ApiModelProperty("编号")
    private String code;
    @NotNull(message = "生日不能为空")
    @Past(message = "生日只能是过去的时间")
    private Date birthday;

    @ApiModelProperty("负数")
    @Negative
    private Integer negative;



    // 分组校验, 例如 新增和更新的校验规则不同
    public interface First {

    }

    public interface Second {

    }
}
