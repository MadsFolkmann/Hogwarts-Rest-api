package edu.hogwarts.repositories;

import edu.hogwarts.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByFirstName(String firstName);
}