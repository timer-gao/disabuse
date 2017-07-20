package edu.ssdut.controllers;

import edu.ssdut.entities.*;
import edu.ssdut.enums.EduDegreeType;
import edu.ssdut.repositories.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Created by Gaomj on 2017/7/7.
 */
@Controller
public class IndexController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @RequestMapping(value = {"/","/index"})
    public String index(HttpServletRequest request, Model model, HttpSession session){
        //model.addAttribute("number",new NumberAuto());

        //model.addAttribute("questionListItem",new ModelAndView("questionListItem"));

        /*Profession profession=new Profession("互联网");
        professionRepository.save(profession);
        Province province=new Province(1L,"辽宁");
        provinceRepository.save(province);
        City city=new City(2L,"大连",province );
        cityRepository.save(city);
        User user=new User("gaomj","123456",'男',"gaomj@123.com",
                "一片冰心在玉壶","一只程序员而已",null,0L,
                profession,city, new HashSet<Education>());
        SimpleDateFormat dateFormat1 =new SimpleDateFormat("yyyy-MM-dd");
        University university=new University(1L,"大连理工大学");
        universityRepository.save(university);
        Major major=new Major(1L,"软件工程");
        majorRepository.save(major);
        try {
            user.addEducation(new Education(university,major,EduDegreeType.本科,
                    dateFormat1.parse("2014-09-01"),dateFormat1.parse("2018-06-01")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userRepository.save(user);*/

        /*User user=userRepository.readByNickname("gaomj");*/

        /*questionRepository.deleteAll();
        sectionRepository.save(new Section("学校里的那点事儿","每个人都有自己的青春，说说自己上学时候的心得吧。",null));*/
        User user=(User)(session.getAttribute("user"));
        user=userRepository.findOne(user.getId());
        model.addAttribute("user",user);

        String searchString=request.getParameter("search");
        logger.info("search:"+searchString);
        if (searchString!=null){
            List<Question> list=questionRepository.findAllByTitleLike(searchString);
            session.setAttribute("resultList",list);
        }
        return "index";
    }
}
class NumberAuto{
    private int num=0;
    public int getNum(){
        return num++;
    }
}