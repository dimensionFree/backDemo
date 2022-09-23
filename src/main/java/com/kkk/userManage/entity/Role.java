package com.kkk.userManage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    final static List<Authority> AUTHORITIES_ADMIN=Arrays.asList(new Authority("write_all"),new Authority("read_all"));
    final static List<Authority> AUTHORITIES_USER=Arrays.asList(new Authority("write_self"),new Authority("read_self"));


    @Id
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Authority> allowedOperations=new ArrayList<>();

    public Role(String roleName) {
        if (roleName.equals("ROLE_admin")){
            allowedOperations.addAll(AUTHORITIES_ADMIN);
        }else if (roleName.equals("ROLE_user")){
            allowedOperations.addAll(AUTHORITIES_USER);
        }

        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}