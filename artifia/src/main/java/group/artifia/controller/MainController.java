package group.artifia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/main")
    public String main(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        String uname = (String) session.getAttribute("user_name");
        model.addAttribute("user_name", uname);
        return "main" ;      
    }

}
