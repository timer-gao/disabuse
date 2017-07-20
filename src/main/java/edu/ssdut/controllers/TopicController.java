package edu.ssdut.controllers;

import edu.ssdut.entities.Section;
import edu.ssdut.entities.Tag;
import edu.ssdut.entities.User;
import edu.ssdut.repositories.SectionRepository;
import edu.ssdut.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gaomj on 2017/7/19 23:20.
 */
@Controller
public class TopicController {
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TagRepository tagRepository;
    @RequestMapping("/topic")
    public String topic(Model model, @RequestParam("sectionId")String id){
        Long sectionId=Long.parseLong(id);
        Section section=sectionRepository.findOne(sectionId);
        model.addAttribute("section",section);
        return "topic";
    }
    @RequestMapping(value = "/editTopic",method = RequestMethod.GET)
    public String editTopic(){
        return "editTopic";
    }
    @RequestMapping(value="/editTopic", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(HttpServletRequest request,
                            @RequestParam("inputTitle") String title,
                            @RequestParam("inputContent") String content) {
        User user = (User) request.getSession().getAttribute("user");
        String tag = request.getParameter("tag");
        Section section = new Section(title,content,null);
        if(tag!=null){
            Tag tag1=new Tag(tag);
            tagRepository.save(tag1);
           section.setTag(tag1);
        }
        sectionRepository.save(section);
        return "You successfully created topic:"+title;
    }
}