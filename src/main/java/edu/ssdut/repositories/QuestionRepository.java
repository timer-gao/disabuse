package edu.ssdut.repositories;

import edu.ssdut.entities.Question;
import edu.ssdut.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQueries;
import java.util.List;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
    List<Question> findAllByTitleLike(String keywords);
    @Query("SELECT distinct question FROM Question question JOIN question.section section WHERE section.title like :keywords")
    List<Question> findQuestionBySectionKeywords(@Param("keywords") String keywords);
    @Query("SELECT distinct question FROM Question question WHERE question.section in (select distinct q.section from Question q " +
            "where q.user.id= :userId or q.id in(select coll.question.id from Collection coll where coll.user.id=:userId))" +
            "or question.user in (select concerned.concernedUser from Concerned concerned where concerned.user.id=:userId)")
    List<Question> findQuestionByUserEquals(@Param("userId") Long userId);
    /*(select i from User u join u.interests i where u.id=:userId)*/
    @Query("SELECT distinct question FROM Question question join question.tags tag WHERE tag in (select i from User u join u.interests i where u.id=:userId) order by question.createDate DESC")
    List<Question> findQuestionByUserInterests(@Param("userId") Long userId);
    List<Question> findDistinctByUser(User user);
}
