package edu.ssdut.controllers;

import edu.ssdut.entities.*;
import edu.ssdut.entities.Collection;
import edu.ssdut.repositories.AnswerRepository;
import edu.ssdut.repositories.CollectionRepository;
import edu.ssdut.repositories.QuestionRepository;
import edu.ssdut.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Gaomj on 2017/7/16.
 */
@Controller
public class QuestionDetailController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    CollectionRepository collectionRepository;

    @RequestMapping(value = "/questionDetail.html", method=RequestMethod.GET)
    public String questionDetail( HttpServletRequest request, Model model){
        long id = Long.parseLong(request.getParameter("questionId"));
        //System.out.print(id);
        Question question = questionRepository.findOne(id);
        List<Answer> answerList = answerRepository.findAllByQuestion(question);
        List<PriAnswer> priAnswers = new ArrayList<>();
        for(Answer answer : answerList){
            PriAnswer priAnswer = new PriAnswer();
            priAnswer.createDate = answer.getCreateDate();
            priAnswer.description = answer.getDescription();
            priAnswer.id = answer.getId();
            priAnswer.user = answer.getUser();
            priAnswer.followAnswer = answerRepository.findAllByReAnswer(answer);
            priAnswers.add(priAnswer);
        }
        model.addAttribute("questionImg", "");
        model.addAttribute("question", question);
        model.addAttribute("tags", "");
        model.addAttribute("answerList", priAnswers);
        return "questionDetail";
    }

    @RequestMapping(value = "/questionDetail/collectionQuestion", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleCollection(@RequestParam("id") long id, HttpSession session){
        Map<String, String> map = new HashMap<>();
        User user = userRepository.readByNickname(((User)session.getAttribute("user")).getNickname());
        Question question = questionRepository.findOne(id);
        Collection collection = new Collection();
        collection.setCreateDate(new Date());
        collection.setQuestion(question);
        collection.setUser(user);
        collectionRepository.save(collection);
        map.put("success", "OK");
        return map;
    }

    @RequestMapping(value = "/questionDetail/answer", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> saveAnswer(@RequestParam("id") long id, @RequestParam("myanswer") String myswer,
                                                            HttpSession session){
        Map<String, String> map = new HashMap<>();
        User user = userRepository.readByNickname(((User)session.getAttribute("user")).getNickname());
        Answer answer = new Answer();
        Question question = questionRepository.findOne(id);
        answer.setCreateDate(new Date());
        answer.setDescription(myswer);
        answer.setQuestion(question);
        answer.setReAnswer(null);
        answer.setUser(user);
        answer.setVotes(new TreeSet<Vote>());
        answerRepository.save(answer);
        return map;
    }

    @RequestMapping(value = "/questionDetail/vote", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleVote(@RequestParam("id") long id, HttpSession session){
        Map<String, String> map = new HashMap<>();
        User user = userRepository.readByNickname(((User)session.getAttribute("user")).getNickname());
        Answer answer = answerRepository.findOne(id);
        Vote vote = new Vote(user, answer, new Date());
        Set<Vote> set = answer.getVotes();
        set.add(vote);
        answer.setVotes(set);
        answerRepository.save(answer);
        map.put("success", "OK");
        return map;
    }

    @RequestMapping(value = "/questionDetail/comment", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> saveReAnswer(@RequestParam("id") long id, @RequestParam("value") String value,
                                                        HttpSession session){
        Map<String, String> map = new HashMap<>();
        User user = userRepository.readByNickname(((User)session.getAttribute("user")).getNickname());
        Answer answer = new Answer(), reAnswer = answerRepository.findOne(id);
        answer.setCreateDate(new Date());
        answer.setDescription(value);
        answer.setQuestion(reAnswer.getQuestion());
        answer.setReAnswer(reAnswer);
        answer.setUser(user);
        answer.setVotes(new TreeSet<Vote>());
        answerRepository.save(answer);
        return map;
    }

    class PriAnswer{
        public Long id;
        public User user;
        public String description;
        public Date createDate;
        public List<Answer> followAnswer;
    }
}
