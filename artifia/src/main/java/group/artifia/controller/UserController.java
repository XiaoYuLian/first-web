package group.artifia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Jdbc;
// import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Jdbc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import group.artifia.model.Userinfo;

@Controller
public class UserController {

    @Autowired
    JdbcTemplate Jdbc;

    @RequestMapping("/a_showuser.html")
    public String showuser(Model model) {
        //List<StockInfo> info = jdbc.query("select", new BeanPropertyRowMapper<StockInfo>(StockInfo.class));
        List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        model.addAttribute("users", users);
        return"a_showuser";
    }
    
    @RequestMapping("/p_showuser.html")
    public String p_showuser(Model model) {
        //List<StockInfo> info = jdbc.query("select", new BeanPropertyRowMapper<StockInfo>(StockInfo.class));
        List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        model.addAttribute("users", users);
        return"p_showuser";
    }

    @RequestMapping("/adduser")
    public String adduser(String user_name, String user_pass,String user_rank, Model model,HttpServletRequest request) {
        int i = Jdbc.update("insert into users (user_name,user_pass,user_rank) values(?,?,?) ",user_name,user_pass,user_rank);
        List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        model.addAttribute("users", users);

        HttpSession session = request.getSession();
        if(i==1){
            return "a_showuser";
        }
        return "fail";
    }

    @RequestMapping("/p_adduser")
    public String p_adduser(String user_name, String user_pass, Model model,HttpServletRequest request) {
        //int j = Jdbc.queryForObject("select user_rank from users where user_name=? and user_pass=? ",new Object[]{user_name,user_pass},Integer.class);
        int i = Jdbc.update("insert into users (user_name,user_pass,user_rank) values(?,?,2) ",user_name,user_pass);
        List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        model.addAttribute("users", users);

        HttpSession session = request.getSession();
        if(i==1){
            return "a_showuser";
        }
        return "fail";
    }

    @RequestMapping("/deluser")
    public String deluser(String user_id, Model model,HttpServletRequest request) {
        int i = Jdbc.update("delete from users where user_id=? ",user_id);
        List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        model.addAttribute("users", users);

        HttpSession session = request.getSession();
        if(i==1){
            return "a_showuser";
        }
        return "fail";
    }

    @RequestMapping("/updateuserp")
    public String updateuserp(String user_id,String user_pass0, String user_pass1, String user_pass2, Model model,HttpServletRequest request) {
        int i = Jdbc.queryForObject("select count(*) from users where user_id=? and user_pass=? ",new Object[]{user_id,user_pass0},Integer.class);

        HttpSession session = request.getSession();
        session.setAttribute("user_id",user_id);
        if(i==1){
            if(user_pass1.equals(user_pass2)){              //字符串型的比较不能直接用等号
                int g = Jdbc.update("update users set user_pass=?  where user_id=? and user_pass=? ",user_pass1,user_id,user_pass0);
                List<Userinfo> users = Jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
                model.addAttribute("users", users);
                
                HttpSession session1 = request.getSession();
                if(g==1){
                    return "a_showuser";
                }
            }else{
                return "err1";
            }
            
        }else{
            return "err0";
        }
        return "fail";
    }
}
