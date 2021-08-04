package com.shopping.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyq
 * @date 2021/8/4 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVO {
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String passWord;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;
}
