package com.shopping.vo;

import com.shopping.validator.IsPhone;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author wyq
 * @date 2021/8/4 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVO {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "手机号")
    @IsPhone
    private String phoneNum;
}
