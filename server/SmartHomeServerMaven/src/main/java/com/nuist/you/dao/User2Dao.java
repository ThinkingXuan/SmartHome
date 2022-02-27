package com.nuist.you.dao;

import com.nuist.you.bean.Users;

import java.util.List;

public interface User2Dao {

    Users selectUsersByPhone(String phone);
    List<Users> selectAllUser();

}
