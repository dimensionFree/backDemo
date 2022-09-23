package com.kkk.userManage.dao;

import com.kkk.userManage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String s);
}
