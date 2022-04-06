package com.example.educenter.mapper;

import com.example.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer countByGmtCreateDaily(String day);
}
