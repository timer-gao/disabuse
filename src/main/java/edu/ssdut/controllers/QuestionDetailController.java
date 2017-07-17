package edu.ssdut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gaomj on 2017/7/16.
 */
@Controller
public class QuestionDetailController {
    @RequestMapping("/questionDetail")
    private String questionDetail(){
        return "questionDetail";
    }
}
