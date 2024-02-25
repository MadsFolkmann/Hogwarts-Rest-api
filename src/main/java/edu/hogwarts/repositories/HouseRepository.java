package edu.hogwarts.repositories;

import edu.hogwarts.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, String> {
    @Query("SELECT h FROM House h WHERE h.name LIKE %:houseName%")
    List<House> findByName(@Param("houseName") String houseName);

    @Query("SELECT h FROM House h WHERE h.name = :houseName")
    Optional<House> findFirstByName(@Param("houseName") String houseName);


}
