package demo.tcloud.triblewood.qcbm.user.persist.dao;

import demo.tcloud.triblewood.qcbm.user.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserInfoMapper {

    @Select("SELECT * FROM userinfo WHERE id = #{userId}")
    @Results({
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    UserInfo getById(@Param("userId") Long userId);

    @Select("SELECT * FROM userinfo WHERE name = #{userName}")
    @Results({
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<UserInfo> getByName(@Param("userName") String userName);

    @Select("SELECT id FROM userinfo WHERE name = #{userName} limit 1")
    Long checkUserByName(@Param("userName") String userName);

    @Insert("INSERT INTO userinfo(name, password, role) VALUES(#{name}, #{password}, #{role})")
    int insert(UserInfo userInfo);

    @Update("UPDATE userinfo SET name=#{name},role=#{role},password=#{password},update_time=#{updateTime} WHERE id =#{id}")
    int updateById(UserInfo userInfo);

    @Delete("DELETE FROM userinfo WHERE id = #{id}")
    int deleteByID(Long id);

}
