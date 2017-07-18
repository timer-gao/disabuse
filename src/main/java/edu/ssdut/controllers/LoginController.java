package edu.ssdut.controllers;

import edu.ssdut.entities.User;
import edu.ssdut.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gaomj on 2017/7/11.
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginHTML(){
        logger.info("loginHTML");
        return "login";
    }
    @RequestMapping(value = "/registForm",method = RequestMethod.POST)
    public String regist(HttpServletRequest request, HttpServletResponse response,Model model,
                        @RequestParam("fullname")String fullname,
                        @RequestParam("email")String email,
                        @RequestParam("password")String password){
        logger.info("registForm");
        if (userRepository.readByNickname(fullname)!=null){
            model.addAttribute("note","用户名已存在！");
            return "login";
        }
        if (userRepository.readByEmail(email)!=null){
            model.addAttribute("note","email已被注册！");
            return "login";
        }
        User user=new User(fullname,password,'无',email);
        userRepository.save(user);
        model.addAttribute("note","注册成功！");
        return "login";
    }
    @RequestMapping(value = "/loginForm",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response,Model model,
                        @RequestParam("email")String email,
                        @RequestParam("password")String password){
        logger.info("loginForm");
        User user = userRepository.readByEmail(email);
        if (user!=null){
            if(user.getPassword().equals(password)){
                request.getSession().setAttribute("username",user);
                return "redirect:index";
            }
            else{
                model.addAttribute("note","密码错误！");
            }
        }
        model.addAttribute("note","用户不存在！");
        return "login";
    }
}
