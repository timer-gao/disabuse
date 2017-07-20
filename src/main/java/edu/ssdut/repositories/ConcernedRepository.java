package edu.ssdut.repositories;

import edu.ssdut.entities.Concerned;
import edu.ssdut.compositeIDs.ConcernedId;
import edu.ssdut.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface ConcernedRepository extends JpaRepository<Concerned,ConcernedId> {
    List<Concerned> findAllByConcernedUser_Id(Long id);
    List<Concerned> findAllByConcernedUser(User concernedUser);
    List<Concerned> findAllByUser_Id(Long id);
    List<Concerned> findAllByUser(User user);
    Concerned findConcernedByUserAndConcernedUser(User user, User concernedUser);
}
