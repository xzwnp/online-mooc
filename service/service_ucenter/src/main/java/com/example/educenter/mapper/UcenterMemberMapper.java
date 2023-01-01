package com.example.educenter.mapper;

import com.example.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *

 *
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer countByGmtCreateDaily(String day);
}
