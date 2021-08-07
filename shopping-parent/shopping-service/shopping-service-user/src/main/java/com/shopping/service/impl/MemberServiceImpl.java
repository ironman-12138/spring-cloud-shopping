package com.shopping.service.impl;

import com.shopping.domain.Member;
import com.shopping.mapper.MemberMapper;
import com.shopping.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xqq
 * @since 2021-08-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public boolean findOne(String userName) {
        return false;
    }
}
