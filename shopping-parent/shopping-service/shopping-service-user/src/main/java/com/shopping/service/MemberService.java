package com.shopping.service;

import com.shopping.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xqq
 * @since 2021-08-04
 */
public interface MemberService extends IService<Member> {

    /**
     *
     * @param userName
     * @return boolean
     */
    boolean findOne(String userName);
}
