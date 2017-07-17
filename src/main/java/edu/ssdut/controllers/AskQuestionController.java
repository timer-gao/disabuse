package edu.ssdut.controllers;

import edu.ssdut.entities.ImageOfQuestion;
import edu.ssdut.entities.Question;
import edu.ssdut.entities.Section;
import edu.ssdut.entities.User;
import edu.ssdut.repositories.ImageOfQuestionRepository;
import edu.ssdut.repositories.QuestionRepository;
import edu.ssdut.repositories.SectionRepository;
import edu.ssdut.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gaomj on 2017/7/14.
 */
@Controller
public class AskQuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ImageOfQuestionRepository imageOfQuestionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @RequestMapping(value="/askQuestion", method= RequestMethod.GET)
    public String provideUploadInfo() {
        return "askQuestion";
    }

    @RequestMapping(value="/askQuestion", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(HttpServletRequest request,
                                                 @RequestParam("inputSection") String sectionTitle,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("inputTitle") String title,
                                                 @RequestParam("inputContent") String content,
                                                 @RequestParam("imageFile") MultipartFile file){
        User user=userRepository.readByNickname(username);
        Section section=sectionRepository.findAllByTitle(sectionTitle);
        if(section==null){
            return "The section "+sectionTitle+" does not exist!";
        }
        Question question=new Question(title,content,0L,new Date(),user,section,null,null);
        if (!file.isEmpty()) {
            try {
                ImageOfQuestion imageOfQuestion=new ImageOfQuestion(question,file.getBytes());
                Set<ImageOfQuestion> imageOfQuestionSet=new HashSet<>();
                imageOfQuestionSet.add(imageOfQuestion);
                question.setImageOfQuestionSet(imageOfQuestionSet);
                questionRepository.save(question);
                return "You successfully uploaded title:\n" + title + "\n" +
                        "content:\n" + content + "\nfile:" + file.getOriginalFilename() +"!";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
    }
}
