package edu.ssdut.repositories;

import edu.ssdut.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
