package edu.ssdut.repositories;

import edu.ssdut.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface CityRepository extends JpaRepository<City,Long> {

}
