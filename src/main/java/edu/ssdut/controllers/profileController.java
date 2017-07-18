package edu.ssdut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gaomj on 2017/7/14.
 */
@Controller
public class profileController {
    @RequestMapping("/profile")
    public String profile(HttpServletRequest request,Model model){
        model.addAttribute("user",request.getSession().getAttribute("user"));
        return "/profile";
    }
}
