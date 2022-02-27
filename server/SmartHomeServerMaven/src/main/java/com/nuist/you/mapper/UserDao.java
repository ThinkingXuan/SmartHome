package com.nuist.you.mapper;


import com.nuist.you.bean.Users;

import java.util.List;

public interface UserDao {

    /**
     * 保存用户信息
     * @param users users
     * @return
     */
    int insertUser(Users users);

    /**
     * 查询用户信息
     * @param users
     * @return
     */
    List<Users> selectUser(Users users);


    /**
     * 根据手机号查询用户信息
     * @param userphone
     * @return
     */
    Users selectUserByPhone(String userphone);

    /**
     * 更新用户信息
     * @param users
     * @return
     */
    int updateUser(Users users);

}
