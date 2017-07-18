package edu.ssdut.controllers;

import edu.ssdut.entities.Question;
import edu.ssdut.entities.User;
import edu.ssdut.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Gaomj on 2017/7/13.
 */
@Controller
public class questionListItemController {
    private static Logger logger = LoggerFactory.getLogger(questionListItemController.class);

    @Autowired
    private QuestionRepository questionRepository;

    private User user;
    private List<Question> list;
    @RequestMapping("/questionListItem")
    public String questionListItem(HttpServletRequest request,
                                         HttpServletResponse response,Model model){
        user= (User) request.getSession().getAttribute("user");
        String number=request.getParameter("number");
        logger.info("number:"+number);
        int floor=Integer.parseInt(number);
        if(floor%3==0){
            list = questionRepository.findAllByInterest(
                    user.getProfession().getDescription()
            );
        }
        if(list!=null&&list.size()>floor%3)
            model.addAttribute("question",list.get(floor%3));
        else return null;
        /*model.addAttribute("topic",new Section("topic","top_des",new Tag("互联网")));
        model.addAttribute("user",new User("admin","123456",'男',"admin@example.com",
                "一片冰心在玉壶","我是一只小小鸟，怎么飞也飞不高",
                "png",8888L,null,null,null,null));*/
        /*ModelAndView mav = new ModelAndView("questionListItem");//实例化一个VIew的ModelAndView实例

        mav.addObject("message", "Hello World!");//添加一个带名的model对象
        return mav;*/
        return "questionListItem";
    }
}
