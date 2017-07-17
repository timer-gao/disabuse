package edu.ssdut.repositories;

import edu.ssdut.entities.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    Profession findByDescription(String description);
    Profession findOrInsertByDescription(String description);
}
