package group.artifia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import group.artifia.model.Booksinfo;

@Controller
public class BooksController {

    @Autowired
    JdbcTemplate jdbc;

    @RequestMapping("/showbooks.html")
    public String showbooks(Model model) {
        //List<StockInfo> info = jdbc.query("select", new BeanPropertyRowMapper<StockInfo>(StockInfo.class));
        // List<Booksinfo> users = jdbc.query("select * from users", new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
        List<Booksinfo> books = jdbc.query("select * from books",new BeanPropertyRowMapper<Booksinfo>(Booksinfo.class));
        model.addAttribute("books", books);
        return"showbooks";
    }

    @RequestMapping("/addbook")
    public String addbook(String book_name, String book_writer,String book_type,String book_b,String book_r,String book_person, Model model,HttpServletRequest request) {
        int i = jdbc.update("insert into books (book_name,book_writer,book_type,book_b,book_r,book_person) values(?,?,?,?,?,?) ",book_name,book_writer,book_type,book_b,book_r,book_person);
        List<Booksinfo> books = jdbc.query("select * from books",new BeanPropertyRowMapper<Booksinfo>(Booksinfo.class));
        model.addAttribute("books", books);

        HttpSession session = request.getSession();
        // session.setAttribute("user_name",user_name);
        if(i==1){
            // model.addAttribute("user_name",user_name);
            return "showbooks";
        }
        return "fail";
    }

    @RequestMapping("/delbook")
    public String delbook(String book_name, Model model,HttpServletRequest request) {
        int i = jdbc.update("delete from books where book_name=? ",book_name);
        List<Booksinfo> books = jdbc.query("select * from books",new BeanPropertyRowMapper<Booksinfo>(Booksinfo.class));
        model.addAttribute("books", books);

        HttpSession session = request.getSession();
        // session.setAttribute("user_name",user_name);
        if(i==1){
            // model.addAttribute("user_name",user_name);
            return "showbooks";
        }
        return "fail";
    }
}
