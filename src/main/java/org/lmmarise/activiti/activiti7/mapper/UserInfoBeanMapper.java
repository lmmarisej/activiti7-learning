package org.lmmarise.activiti.activiti7.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lmmarise.activiti.activiti7.pojo.UserInfoBean;
import org.springframework.stereotype.Component;

/**
 * @author lmmarise.j@gmail.com
 * @since 2022/9/23 01:37
 */
@Mapper
@Component
public interface UserInfoBeanMapper {

    /**
     * 从数据库中查询用户
     */
    @Select("select * from \"user\" where username = #{username}")
    UserInfoBean selectByUsername(@Param("username") String username);
    
}
