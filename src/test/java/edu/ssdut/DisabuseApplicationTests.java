package edu.ssdut;

import edu.ssdut.entity.*;
import edu.ssdut.enums.EduDegreeType;
import edu.ssdut.repository.*;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DisabuseApplicationTests {
	private static Logger logger = LoggerFactory.getLogger(DisabuseApplicationTests.class);

	@Autowired
	private WebApplicationContext context;
	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfessionRepository professionRepository;

	@Autowired
	ConcernedRepository concernedRepository;

	@Autowired
	CityRepository cityRepository;
	@Autowired
	ProvinceRepository provinceRepository;

	@Autowired
	MajorRepository majorRepository;
	@Autowired
	UniversityRepository universityRepository;
	@Autowired
	EducationRepository educationRepository;

	@Before
	public void initData() throws ParseException {
		//清空表
		concernedRepository.deleteAll();
		userRepository.deleteAll();
		educationRepository.deleteAll();
		majorRepository.deleteAll();
		universityRepository.deleteAll();
		professionRepository.deleteAll();
		cityRepository.deleteAll();
		provinceRepository.deleteAll();


		//创建实体并持久化至数据库
		Profession profession=new Profession("软件工程");
		professionRepository.save(profession);
		Assert.notNull(profession.getId(),"!!!!!!!!!!!!!!!!!!!!!!profession为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		Province province= new Province(1212L,"辽宁");
		provinceRepository.save(province);
		Assert.notNull(province.getId());

		City city = new City(4545L,"大连",province);
		cityRepository.save(city);
		Assert.notNull(city);

		User simpleUser=new User("Baby","Mami",null,"baby@123.com",null,
				null,null,null,null,null,null,null);

		userRepository.save(simpleUser);
		Assert.notNull(simpleUser.getId(),"!!!!!!!!!!!!!!simpleUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		User fullUser=new User("admin","123456",'男',"admin@example.com",
				"一片冰心在玉壶","我是一只小小鸟，怎么飞也飞不高",
				null,8888L,profession,city,new HashSet<>(),null);

		userRepository.save(fullUser);
		Assert.notNull(fullUser.getId(),"!!!!!!!!!!!!!!fullUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		University university=new University(122L,"大连理工大学");
		universityRepository.save(university);
		Assert.notNull(university);
		Major major=new Major(32L,"软件工程");
		majorRepository.save(major);
		Assert.notNull(major);
		SimpleDateFormat dateFormat1 =new SimpleDateFormat("yyyy-MM-dd");
		Education education1 =new Education(university,major, EduDegreeType.本科,dateFormat1.parse("2014-09-01"),dateFormat1.parse("2018-06-01"));
		Assert.notNull(education1);
		Education education2 =new Education(university,major, EduDegreeType.硕士,dateFormat1.parse("2018-09-01"),dateFormat1.parse("2021-06-01"));
		Assert.notNull(education2);
		Set<Education> educations=new HashSet<>();
		educations.add(education1);
		educations.add(education2);
		Assert.isTrue(educations.size()==2);
		fullUser.setEducations(educations);
		fullUser.addEducation(education1);
		fullUser.addEducation(education2);
		userRepository.save(fullUser);
		Assert.notNull(fullUser.getId(),"!!!!!!!!!!!!!!fullUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		simpleUser=userRepository.readByNickname("Baby");
		fullUser=userRepository.readByNickname("admin");

		Set<Concerned> simpleUserConcerneds =new HashSet<>();
		Concerned concerned1=new Concerned(simpleUser,fullUser,new Date());
		simpleUserConcerneds.add(concerned1);
		simpleUser.setConcerns(simpleUserConcerneds);
		//concernedRepository.save(concerned1);
		//Assert.notNull(concerned1.getUser().getId());

		userRepository.save(simpleUser);
		Assert.notNull(simpleUser.getId(),"!!!!!!!!!!!!!!simpleUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


		Set<Concerned> fullUserConcerneds =new HashSet<>();
		Concerned concerned2=new Concerned(fullUser,simpleUser,new Date());
		fullUserConcerneds.add(concerned2);
		fullUser.setConcerns(fullUserConcerneds);

		//concernedRepository.save(concerned2);
		//Assert.notNull(concerned2.getUser().getId());
		userRepository.save(fullUser);
		Assert.notNull(fullUser.getId(),"!!!!!!!!!!!!!!fullUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


/*
		simpleUser=userRepository.readByNickname("Baby");
		fullUser=userRepository.readByNickname("admin");

		Set<Concerned> simpleUserConcerneds2 =new HashSet<>();
		Concerned concerned3=new Concerned(simpleUser,fullUser,new Date());
		simpleUserConcerneds.add(concerned3);
		simpleUser.setConcerns(simpleUserConcerneds2);

		userRepository.save(simpleUser);
		Assert.notNull(simpleUser.getId(),"!!!!!!!!!!!!!!simpleUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
*/

		/*Concerned concerned=new Concerned(simpleUser,simpleUser,new Date());
		concernedRepository.save(concerned);*/

		/*simpleUser=userRepository.readByNickname("Baby");
		for(Concerned concerned:concernedRepository.findAll()){
			Long id=concerned.getUser().getId();
			User tempUser=userRepository.findOne(id);
			concerned.setUser(tempUser);
		}
		assertTrue(simpleUser.getConcerns().size()==1);

		simpleUser.getConcerns().add(new Concerned(simpleUser,simpleUser,new Date()));
		userRepository.save(simpleUser);
		Assert.notNull(simpleUser.getId(),"!!!!!!!!!!!!!!simpleUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
*/
		/*fullUser=userRepository.readByNickname("admin");
		fullUser.getConcerns().add(new Concerned(fullUser,fullUser,new Date()));
		userRepository.save(fullUser);
		Assert.notNull(fullUser.getId(),"!!!!!!!!!!!!!!fullUser为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
*/	}
	@Test
	public void contextLoads() {
		assertEquals(2, userRepository.count());
		assertEquals(1,professionRepository.count());
		assertEquals(2,concernedRepository.count());
	}

	@Test
	public void userTest(){
		Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "id"));
		Page<User> page = userRepository.findAll(pageable);
		Assert.notNull(page,"!!!!!!!!!!!!!!!page为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		for(User user : page.getContent()) {
			logger.info("====++++++++=======+++++++++++++user====+++++++++============\n" +
							" id:{}, nickname:{}, pwd:{}, sex:{},email:{},profession:{},province:{},city:{},signature:{},introduction:{},views:{},avatar:{}",
					user.getId(),user.getNickname(),user.getPassword(),user.getSex(),user.getEmail(),
					user.getProfession()!=null?user.getProfession().getDescription():null,
					user.getCity()!=null&&user.getCity().getProvince()!=null?user.getCity().getProvince().getName():null,
					user.getCity()!=null?user.getCity().getName():null,user.getSignature(),
					user.getIntroduction(),user.getViews(),user.getAvatar());
		}
	}
	@Test
	public void userRepositoryTest(){

		//Assert.isTrue(userRepository.existsNickname("admin"),"admin存在");
		//Assert.isTrue(userRepository.existsEmail("admin@example.com"),"admin email存在");
		User user1 = userRepository.findByNicknameLike("a%");
		Assert.notNull(user1);

		User user2 = userRepository.readByNickname("admin");
		Assert.notNull(user2);

		User user3 = userRepository.readByEmail("baby@123.com");
		Assert.notNull(user3);

		Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "user_id"));
		Page<Concerned> page = concernedRepository.findAll(pageable);
		Assert.notNull(page,"!!!!!!!!!!!!!!!page为空!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		for(Concerned concerned : page.getContent()) {
			logger.info("====++++++++=======+++++++++++++concerned====+++++++++============\n" +
					"user_id:{},user name:{},concerned_id:{},createDate:{}",concerned.getUser().getId(),
					concerned.getConcernedUser().getId(),concerned.getUser().getNickname(),concerned.getCreateDate());
		}
	}
}
