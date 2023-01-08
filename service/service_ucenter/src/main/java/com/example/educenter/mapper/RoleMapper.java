package com.example.educenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.educenter.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xzwnp
 * @description 针对表【role】的数据库操作Mapper
 * @createDate 2022-11-08 19:41:14
 * @Entity com.example.admin.entity.Role
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	List<String> findRolesByUserId(@Param("userId") String userId);
}




