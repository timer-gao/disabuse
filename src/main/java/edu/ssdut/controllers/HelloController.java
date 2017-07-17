package edu.ssdut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Gaomj on 2017/7/7.
 */
@Controller
public class HelloController {
    /**
     * 返回html模板.
     */

    @RequestMapping("/helloHtml")
    public String helloHtml(Map<String,Object> map){
        map.put("hello","from TemplateController.helloHtml");
        return "/helloHtml";
    }
}
