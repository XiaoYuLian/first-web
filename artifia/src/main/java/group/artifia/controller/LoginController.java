package group.artifia.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    JdbcTemplate jdbc;

    @RequestMapping("/login")
    public String checkuser(String user_name, String user_pass, Model model,HttpServletRequest request) {
        int i = jdbc.queryForObject("select count(*) from users where user_name=? and user_pass=? ",new Object[]{user_name,user_pass},Integer.class);

        HttpSession session = request.getSession();
        session.setAttribute("user_name",user_name);
        if(i==1){
            model.addAttribute("user_name",user_name);
            
            int j = jdbc.queryForObject("select user_rank from users where user_name=? and user_pass=? ",new Object[]{user_name,user_pass},Integer.class);
            if(j==2){
                return "p_suc";
            }else{
                return "a_suc";
            }
        }
        return "fail";
    }
   
}