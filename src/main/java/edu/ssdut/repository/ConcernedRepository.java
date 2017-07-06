package edu.ssdut.repository;

import edu.ssdut.entity.Concerned;
import edu.ssdut.compositeID.ConcernedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface ConcernedRepository extends JpaRepository<Concerned,ConcernedId> {

}
