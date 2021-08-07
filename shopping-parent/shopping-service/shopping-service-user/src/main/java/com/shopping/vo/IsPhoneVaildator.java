package com.shopping.vo;

import com.shopping.util.PhoneFormatCheckUtils;
import com.shopping.validator.IsPhone;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wyq
 * @date 2021/8/5 10:29
 */
public class IsPhoneVaildator implements ConstraintValidator<IsPhone,String> {

    private boolean required = false;

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return PhoneFormatCheckUtils.isChinaPhoneLegal(s);
        }else {
            if (StringUtils.isEmpty(s)){
                return true;
            } else {
                return PhoneFormatCheckUtils.isChinaPhoneLegal(s);
            }
        }
    }
}
