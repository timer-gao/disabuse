package edu.ssdut.repositories;

import edu.ssdut.compositeIDs.VoteId;
import edu.ssdut.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote,VoteId> {

}
