package edu.ssdut.controllers;

import edu.ssdut.entities.Question;
import edu.ssdut.entities.User;
import edu.ssdut.repositories.AnswerRepository;
import edu.ssdut.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gaomj on 2017/7/13.
 */
@Controller
public class QuestionListItemController {
    private static Logger logger = LoggerFactory.getLogger(QuestionListItemController.class);

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private List<Question> list1;
    private List<Question> list2;
    private List<Question> list3;
    private List<Question> list4;

    @RequestMapping("/questionListItem")
    public String questionListItem(HttpServletRequest request,
                                   HttpSession session, Model model) {

        String number = request.getParameter("number");
        logger.info("number:" + number);
        int floor = Integer.parseInt(number);
        User user = (User)session.getAttribute("user");
        List<Question> resultList= (List<Question>) session.getAttribute("resultList");
        if (resultList!=null&&resultList.size()>0){
            model.addAttribute("question",resultList.get(floor));
            return "questionListItem";
        }
        list1 = (List<Question>) session.getAttribute("list1");
        list2 = (List<Question>) session.getAttribute("list2");
        list3 = (List<Question>) session.getAttribute("list3");
        list4 = (List<Question>) session.getAttribute("list4");
        if (floor == 0) {
            list1 = questionRepository.findQuestionByUserInterests(user.getId());
            if (list1 == null) list1 = new LinkedList<>();
            session.setAttribute("list1",list1);
            list2 = questionRepository.findQuestionByUserEquals(user.getId());
            if (list2 == null) list2 = new LinkedList<>();
            session.setAttribute("list2",list2);
            if (user.getProfession()!=null)
                list3 = questionRepository.findQuestionBySectionKeywords(user.getProfession().getDescription());
            if (list3 == null) list3 = new LinkedList<>();
            session.setAttribute("list3",list3);
        }
        if (getListSize() > 0) {
            logger.info("getListSize:"+getListSize());
            Question question;
            Question question1 = list1.isEmpty() ? null : list1.get(0);
            Question question2 = list2.isEmpty() ? null : list2.get(0);
            Question question3 = list3.isEmpty() ? null : list3.get(0);
            if (question1 != null) {
                if (question2 != null) {
                    if (question3 != null) {
                        question = question1.getCreateDate().after(question2.getCreateDate()) ? question1 : question2;
                        question = question.getCreateDate().after(question3.getCreateDate()) ? question : question3;
                    } else {
                        question = question1.getCreateDate().after(question2.getCreateDate()) ? question1 : question2;
                    }
                } else {
                    if (question3 != null) {
                        question = question1.getCreateDate().after(question3.getCreateDate()) ? question1 : question3;
                    } else question = question1;
                }
            } else {
                if (question2 != null) {
                    if (question3 != null) {
                        question = question2.getCreateDate().after(question3.getCreateDate()) ? question2 : question3;
                    } else {
                        question = question2;
                    }
                } else {
                    if (question3 != null) {
                        question = question3;
                    } else question = null;
                }
            }
            model.addAttribute("question", question);
            model.addAttribute("answerCount",answerRepository.countAllByQuestion_Id(question.getId()));
            if (question == question1) {
                list1.remove(0);
            } else if (question == question2) {
                list2.remove(0);
            } else {
                list3.remove(0);
            }
        }
        else{
            if (list4==null||list4.isEmpty()) {
                list4 = questionRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
                session.setAttribute("list4",list4);
            }
            if(list4!=null&&list4.size()>0) {
                model.addAttribute("question", list4.get(0));
                model.addAttribute("answerCount",answerRepository.countAllByQuestion_Id(list4.get(0).getId()));
                list4.remove(0);
            }
            else {
                logger.info("questionListItem return:null");
                return null;
            }
        }

        /*model.addAttribute("topic",new Section("topic","top_des",new Tag("互联网")));
        model.addAttribute("user",new User("admin","123456",'男',"admin@example.com",
                "一片冰心在玉壶","我是一只小小鸟，怎么飞也飞不高",
                "png",8888L,null,null,null,null));*/
        /*ModelAndView mav = new ModelAndView("questionListItem");//实例化一个VIew的ModelAndView实例

        mav.addObject("message", "Hello World!");//添加一个带名的model对象
        return mav;*/
        return "questionListItem";
    }

    public int getListSize() {
        return (list1 == null ? 0 : list1.size()) + (list2 == null ? 0 : list2.size()) + (list3 == null ? 0 : list3.size());
    }
}
