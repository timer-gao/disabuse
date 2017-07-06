package edu.ssdut.repository;

import edu.ssdut.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface MajorRepository extends JpaRepository<Major,Long> {

}
