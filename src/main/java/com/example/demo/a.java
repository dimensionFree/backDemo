package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@Controller
public class a {
    @ResponseBody
    @RequestMapping("/getUser")
    public List index() {
        List<User> b=new ArrayList<>();
        User a=new User("asdf",12);
        b.add(new User("asdf",12));
        b.add(new User("eee",32));
        return b;
    }
//    @RequestBody User user
    @PostMapping("/addUser")
    public void addUser(HttpServletRequest request) {
        String inputEmail = request.getParameter("inputEmail");
        System.out.println(inputEmail);

    }
}
