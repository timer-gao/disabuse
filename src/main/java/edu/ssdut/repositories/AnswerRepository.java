package edu.ssdut.repositories;

import edu.ssdut.entities.Answer;
import edu.ssdut.entities.Question;
import edu.ssdut.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    int countAllByQuestion_Id(Long questionId);
    List<Answer> findAllByUser(User user);
    List<Answer> findAllByQuestion(Question question);
    List<Answer> findAllByReAnswer(Answer answer);
}
