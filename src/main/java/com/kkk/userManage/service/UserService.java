package com.kkk.userManage.service;

import com.kkk.userManage.dao.RoleRepository;
import com.kkk.userManage.dao.UserRepository;
import com.kkk.userManage.entity.Role;
import com.kkk.userManage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new  UsernameNotFoundException("");
        }
        return user;
    }

    
    @Transactional
    public User saveUser(User user){

        Role role_user = new Role("ROLE_user");
        roleRepository.save(role_user);
        user.setRoles(Arrays.asList(role_user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).get();
    }

    public List findAllUsers(){
        return userRepository.findAll();
    }
}