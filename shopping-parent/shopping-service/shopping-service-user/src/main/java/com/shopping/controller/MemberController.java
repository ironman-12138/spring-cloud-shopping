package com.shopping.controller;


import com.shopping.domain.Member;
import com.shopping.mapper.MemberMapper;
import com.shopping.service.MemberService;
import com.shopping.util.MD5Util;
import com.shopping.util.PhoneFormatCheckUtils;
import com.shopping.vo.RegisterVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import utils.Result;
import utils.ResultCode;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xqq
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "注册会员")
    @PostMapping
    public Result register(@Valid @RequestBody RegisterVO registerVO){
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(registerVO.getPhoneNum())){
            return Result.errorToClient(ResultCode.PHONE_ERROR);
        }
        if (memberService.findOne(registerVO.getUserName()) && memberService.findOne(registerVO.getPhoneNum())){
            return Result.errorToClient(ResultCode.MEMBER_ERROR);
        }
        return Result.successToClient();
    }

}

