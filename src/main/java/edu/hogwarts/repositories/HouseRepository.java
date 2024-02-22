package edu.hogwarts.repositories;

import edu.hogwarts.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Integer> {
    @Query("SELECT h FROM House h WHERE h.name LIKE %:houseName%")
    List<House> findByName(@Param("houseName") String houseName);
}
