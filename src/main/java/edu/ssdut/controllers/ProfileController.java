package edu.ssdut.controllers;

import edu.ssdut.entities.*;
import edu.ssdut.entities.Collection;
import edu.ssdut.repositories.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Gaomj on 2017/7/14.
 */
@Controller
public class ProfileController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(IndexController.class);
    private String userName;
//    private String rootPath = "E:\\code\\ideaCode\\disabuse\\target\\tomcat\\work\\Tomcat\\localhost\\ROOT";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private ConcernedRepository concernedRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    private SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");

    private boolean isTravel(String userName, HttpSession session){
        User user = (User) session.getAttribute("user");
        //最后注释.
        if(user == null){
            return true;
        }
        return !userName.equals(user.getNickname());
    }
    //根据保存的user判断.
    private boolean isTravel(String userName){
        return !userName.equals(this.userName);
    }

    //判断是进入自己主页还是他人主页。
    @RequestMapping(value = "/people/{userName}", method = RequestMethod.GET)
    public String enterProfile(@PathVariable("userName") String userName, Model model, HttpSession session) {
        if(!isTravel(userName, session)){
            this.userName = userName;
//            User user = new User();
//            user.setSex('无');
//            user.setNickname("lucas");
//            user.setEmail("1014895171@qq.com");
//            user.setAvatar("/avatar/admin.png");
//            user.setPassword("123456");
//            user.setIntroduction("pythoner");
//            user.setAvatar("/avatar/lucas.jpg");
//            user.setViews(new Long(0));
//            userRepository.save(user);

        }
        String redirect =  "redirect:/people/" + userName + "/focus";
        return  redirect;
    }

    //关注或取消关注他人.
    @RequestMapping(value = "/people/{nickName}", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleFocus(@RequestParam("type") String type, @RequestParam("output") String output,
                                                            HttpSession session){
        Map<String, String> map = new HashMap<>();
        User user = (User)session.getAttribute("user");
        user = userRepository.readByNickname(user.getNickname());
        User concernedUser = userRepository.readByNickname(output);
        logger.info("output:"+output);
        logger.info("user:"+user.getNickname());
        logger.info("concernedUser:"+concernedUser.getNickname());
        logger.info("type:"+type);
        //根据type增加或删除。
        if(type.equals("focus")){
            Concerned concerned = new Concerned(user, concernedUser, new Date());
            logger.info("compositeId:"+concerned.hashCode());
            logger.info("userId:"+concerned.getUser().getId());
            logger.info("concernedId:"+concerned.getConcernedUser().getId());
            logger.info("date:"+concerned.getCreateDate());
            //concernedRepository.save(concerned);
            Set<Concerned> concerneds=new HashSet<>();
            concerneds.add(concerned);
            user.setConcerns(concerneds);
            userRepository.save(user);
        }
        else if(type.equals("unfocus")){
            Concerned concerned = concernedRepository.findConcernedByUserAndConcernedUser(user, concernedUser);
            concernedRepository.delete(concerned);
        }
        map.put("success", "OK");
        return map;
    }

    //进入关注页面。
    @RequestMapping(value = "/people/{userName}/focus", method = RequestMethod.GET)
    public String  getFocus(@PathVariable("userName") String userName, Model model, HttpSession session){
        model.addAttribute("nickName",userName);
        User user = userRepository.findByNicknameLike(userName);
        List list = new ArrayList<Activity>();
        PriTemplete templete = new PriTemplete();
        //获取所有的关注记录.
        initTemplete(user, templete, session);
        List<Concerned> concernedList = concernedRepository.findAllByUser(user);
        String img, nickName, putTime, targetUrl, bodyText, title;
        int id;
        for (int i = 0; i < concernedList.size(); i++) {
            //获取被关注者与关注记录.
            Concerned concerned = concernedList.get(i);
            User concernedUser = concerned.getConcernedUser();
            //获取数据.
            img = concernedUser.getAvatar();
            nickName = concernedUser.getNickname();
            putTime = datePattern.format(concerned.getCreateDate());
            targetUrl = "/people/" + nickName;
            bodyText = concernedUser.getIntroduction();
            title = "";
            id = 0;
            Activity activity = new Activity(img, nickName, putTime, targetUrl, bodyText, title, id);
            list.add(activity);
        }
        //无需修改
        templete.list = list;
        //无需修改.
        templete.tagName = "关注";
        model.addAttribute("templete", templete);
        return "profile";
    }

    @RequestMapping(value = "/people/{userName}/collection", method = RequestMethod.GET)
    public String getCollection(@PathVariable("userName") String userName, Model model, HttpSession session){
        model.addAttribute("nickName",userName);
        User user = userRepository.findByNicknameLike(userName);
        List list = new ArrayList<Activity>();
        PriTemplete templete = new PriTemplete();
        initTemplete(user, templete, session);
        //获取所有的收藏记录.
        List<Collection> collectionList = collectionRepository.findAllByUser(user);
        String img, nickName, putTime, targetUrl, bodyText, title;
        long id;
        for (int i = 0; i < collectionList.size(); i++) {
            //收藏的问题。.
            Collection collection = collectionList.get(i);
            Question question = collection.getQuestion();
            User asker = question.getUser();
            //获取数据.
            img = asker.getAvatar();
            nickName = asker.getNickname();
            putTime = datePattern.format(question.getCreateDate());
            targetUrl = "/questionDetail.html?questionId=" + question.getId();
            bodyText = question.getDescription();
            title = question.getTitle();
            id = question.getId();
            Activity activity = new Activity(img, nickName, putTime, targetUrl, bodyText, title, id);
            list.add(activity);
        }
        //无需修改
        templete.list = list;
        //无需修改.
        templete.tagName = "收藏";
        model.addAttribute("templete", templete);
        return "profile";
    }

    @RequestMapping(value = "/people/{userName}/answer", method = RequestMethod.GET)
    public String getAnswer(@PathVariable("userName") String userName, Model model, HttpSession session){
        model.addAttribute("nickName",userName);
        User user = userRepository.findByNicknameLike(userName);
        List list = new ArrayList<Activity>();
        PriTemplete templete = new PriTemplete();
        initTemplete(user, templete, session);
        //获取所有的个回答..
        List<Answer> answerList = answerRepository.findAllByUser(user);
        String img, nickName, putTime, targetUrl, bodyText, title;
        long id;
        for (int i = 0; i < answerList.size(); i++) {
            //收藏的问题。.
            Answer answer = answerList.get(i);
            //获取数据.
            img = user.getAvatar();
            nickName = user.getNickname();
            putTime = datePattern.format(answer.getCreateDate());
            targetUrl = "/questionDetail.html?questionId=" + answer.getQuestion().getId();
            bodyText = answer.getDescription();
            title = "回答:" + answer.getQuestion().getTitle();
            id = answer.getId();
            Activity activity = new Activity(img, nickName, putTime, targetUrl, bodyText, title, id);
            list.add(activity);
        }
        //无需修改
        templete.list = list;
        //无需修改.
        templete.tagName = "回答";
        model.addAttribute("templete", templete);
        return "profile";
    }

    @RequestMapping(value = "/people/{userName}/question", method = RequestMethod.GET)
    public String getQuestion(@PathVariable("userName") String userName, Model model, HttpSession session){
        model.addAttribute("nickName",userName);
//        渲染操作全部移到其他区域.
        User user = userRepository.readByNickname(userName);
        List list = new ArrayList<Activity>();
        PriTemplete templete = new PriTemplete();
        initTemplete(user, templete, session);

        //获取所有的个提问..
        List<Question> questionsList = questionRepository.findDistinctByUser(user);
        logger.info("question size:"+questionsList.size());
        String img, nickName, putTime, targetUrl, bodyText, title;
        long id;
        for (int i = 0; i < questionsList.size(); i++) {
            //收藏的问题。.
            Question question = questionsList.get(i);
            //获取数据.
            img = user.getAvatar();
            nickName = user.getNickname();
            putTime = datePattern.format(question.getCreateDate());
            targetUrl = "/questionDetail.html?questionId=" + question.getId();
            bodyText = question.getDescription();
            title = question.getTitle();
            id = question.getId();
            Activity activity = new Activity(img, nickName, putTime, targetUrl, bodyText, title, id);
            list.add(activity);
        }
        //无需修改
        templete.list = list;
        //无需修改.
        templete.tagName = "提问";
        model.addAttribute("templete", templete);
        return "profile";
    }

    //处理关注取消
    //在对应的表中进行关注，收藏，对提问回答的删除.
    //type为"unlike", 额value为对应id.数据传递就是type=unlike&value=" + id.
    @RequestMapping(value = "/people/{userName}/focus", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleUnFocus(@PathVariable("userName") String nickName, @RequestParam("type") String type, @RequestParam("name") String concernedName){
        Map<String, String> map = new HashMap<>();
        if(type.equals("unlike")){
            User user = userRepository.findByNicknameLike(nickName),
                 concernedUser = userRepository.findByNicknameLike(concernedName);
            Concerned concerned = concernedRepository.findConcernedByUserAndConcernedUser(user, concernedUser);
            concernedRepository.delete(concerned);
            map.put("success", "OK");
        }else{
            map.put("success", "FAILED");
        }
        return map;
    }

    @RequestMapping(value = "/people/{userName}/collection", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleUnCollection(@PathVariable("userName") String nickName, @RequestParam("type") String type, @RequestParam("value") long value){
        Map<String, String> map = new HashMap<>();
        if(type.equals("unlike")){
            Collection collection = collectionRepository.findOne(value);
            collectionRepository.delete(collection);
            map.put("success", "OK");
        }else{
            map.put("success", "FAILED");
        }
        return map;
    }
    @RequestMapping(value = "/people/{userName}/question", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleUnQuestion(@PathVariable("userName")String nickName, @RequestParam("type") String type, @RequestParam("value") long value){
        Map<String, String> map = new HashMap<>();
        if(type.equals("unlike")){
            Question question = questionRepository.findOne(value);
            questionRepository.delete(question);
            map.put("success", "OK");
        }else{
            map.put("success", "FAILED");
        }
        return map;
    }

    @RequestMapping(value = "/people/{userName}/answer", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> handleUnAnswer(@PathVariable("userName")String nickName, @RequestParam("type") String type, @RequestParam("value") long value){
        Map<String, String> map = new HashMap<>();
        if(type.equals("unlike")){
            Answer answer = answerRepository.findOne(value);
            answerRepository.delete(answer);
            map.put("success", "OK");
        }else{
            map.put("success", "FAILED");
        }
        return map;
    }

    //从数据库中得到数据加入渲染,
    @RequestMapping(value = "/people/{nickName}/edit-info", method=RequestMethod.GET)
    public String getEditHTML(@PathVariable("nickName") String nickName,  Model model, HttpSession session){
        if(isTravel(nickName, session)){
            //没有权限返回主页.
            return  "redirect:/";
        }
        User user = userRepository.findByNicknameLike(nickName);
        List<City> cityList = cityRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("cityList", cityList);
        String city;
        if(user.getCity() != null){
            city = user.getCity().getName();
        }else{
            city = "none";
        }
        model.addAttribute("cityName", city);
        return "edit_info";
    }

    @RequestMapping(value = "/people/{nickName}/edit-info", method=RequestMethod.POST)
    public String postSelfInfo(@PathVariable("nickName") String nickName, @RequestParam("sex") char sex, @RequestParam("email") String email,
                               @RequestParam("signature") String signature, @RequestParam("introduction") String introduction,
                               @RequestParam("city") String cityName, @RequestParam("profession") String professionInfo){
        System.out.println(cityName + sex);
        User user = userRepository.findByNicknameLike(nickName);
        user.setSex(sex);
        user.setSignature(signature);
        user.setIntroduction(introduction);
        user.setEmail(email);
        Profession profession = new Profession(professionInfo);
        professionRepository.save(profession);
        if(cityName != "none") {
            City city = cityRepository.findCityByName(cityName);
            user.setCity(city);
        }
        user.setProfession(profession);
        userRepository.save(user);
        String redirect =  "redirect:/people/" + nickName + "/edit-info";
        return  redirect;
    }

    //处理图片上传.
    //对于本地图片与服务器地址对应还得处理，
    //覆盖对应函数即可。
    @RequestMapping(value = "/people/postImg", method=RequestMethod.POST)
    public @ResponseBody Map<String, String> postImg(@RequestParam("file") MultipartFile file, HttpSession session){
        Map<String, String> map = new HashMap<>();
        //通过session获取用户名。
        User user = (User)session.getAttribute("user");
        //此路径为存储图片的路径，对应MyWebAppConfigurer中的图片存储路径。
        String rootPath = "E:\\disabuse\\avatar\\";
        String origenName = file.getOriginalFilename();
        //获取后缀名。
        origenName = origenName.split("\\.")[1];
        String newName = user.getNickname() + '.' + origenName;
        //保存url到user中.
        System.out.println(newName);
        File newFile = new File(rootPath + newName);
        try{
            file.transferTo(newFile);
            user = userRepository.findByNicknameLike(user.getNickname());
            //这是图片在服务器上的对应映射路径。
            user.setAvatar("/avatar/" + newName);
            userRepository.save(user);
            map.put("success", "OK");
            map.put("url", newName);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success", "FAILED");
        }
        return map;
    }

    private void initTemplete(User user, PriTemplete templete, HttpSession session){
        templete.answerNum = (answerRepository.findAllByUser(user)).size();
        templete.collectionNum = (collectionRepository.findAllByUser(user)).size();
        templete.questionNum=(questionRepository.findDistinctByUser(user)).size();
        if(user.getAvatar() != null)
            templete.avatarUrl = user.getAvatar();
        else templete.avatarUrl = "/avatar/admin.png";
        //无需修改这项.
        templete.buttonText = "取消";
        templete.focusedNum = (concernedRepository.findAllByConcernedUser(user)).size();
        templete.focusNum = concernedRepository.findAllByUser(user).size();
        templete.travel = isTravel(user.getNickname(), session);
        templete.introduction = user.getIntroduction();
        templete.sex = user.getSex();
        if(templete.isTravel()) {
            User currentUser = (User)session.getAttribute("user");
            currentUser = userRepository.findByNicknameLike(currentUser.getNickname());
            if(concernedRepository.findConcernedByUserAndConcernedUser(currentUser,user) != null)
                templete.hasFocus = "已关注";
            else templete.hasFocus = "关注";
        }
    }

    public class Activity{
        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPutTime() {
            return putTime;
        }

        public void setPutTime(String putTime) {
            this.putTime = putTime;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public String getBodyText() {
            return bodyText;
        }

        public void setBodyText(String bodyText) {
            this.bodyText = bodyText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String img;
        public String nickName;
        public String putTime;
        public String targetUrl;
        public String bodyText;
        public String title;
        public long id;

        Activity(String img , String nickName, String putTime, String targetUrl, String bodyText, String title, long id){
            this.bodyText = bodyText;
            this.img = img;
            this.putTime = putTime;
            this.targetUrl = targetUrl;
            this.nickName = nickName;
            this.title = title;
            this.id = id;
        }
    }

    private class PriTemplete{
        public char getSex() {
            return sex;
        }

        public void setSex(char sex) {
            this.sex = sex;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }

        public boolean isTravel() {
            return travel;
        }

        public void setTravel(boolean travel) {
            this.travel = travel;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getButtonText() {
            return buttonText;
        }

        public void setButtonText(String buttonText) {
            this.buttonText = buttonText;
        }

        public long getFocusNum() {
            return focusNum;
        }

        public void setFocusNum(long focusNum) {
            this.focusNum = focusNum;
        }

        public int getFocusedNum() {
            return focusedNum;
        }

        public void setFocusedNum(int focusedNum) {
            this.focusedNum = focusedNum;
        }

        public int getCollectionNum() {
            return collectionNum;
        }

        public void setCollectionNum(int collectionNum) {
            this.collectionNum = collectionNum;
        }

        public int getAnswerNum() {
            return answerNum;
        }

        public void setAnswerNum(int answerNum) {
            this.answerNum = answerNum;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(int questionNum) {
            this.questionNum = questionNum;
        }

        public String getHasFocus() {
            return hasFocus;
        }

        public void setHasFocus(String hasFocus) {
            this.hasFocus = hasFocus;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public char sex;
        public List list;
        public boolean travel;
        public String avatarUrl;
        public String introduction;
        public String buttonText;
        public long focusNum;
        public int focusedNum;
        public int collectionNum;
        public int answerNum;
        public int questionNum;
        public String hasFocus;
        public String tagName;
    }
}
