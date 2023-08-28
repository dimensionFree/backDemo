package com.kkk.userManage.dao;

import com.kkk.userManage.entity.Role;
import com.kkk.userManage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

}
