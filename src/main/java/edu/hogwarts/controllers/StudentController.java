package edu.hogwarts.controllers;

import edu.hogwarts.models.House;
import edu.hogwarts.models.Student;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    private StudentRepository studentRepository;
    private HouseRepository houseRepository;

    public StudentController(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        return ResponseEntity.of(student);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        House house = houseRepository.findById(student.getHouse().getId()).orElse(null);
        if (house != null) {
            student.setHouse(house);
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            student.setId(id);
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return ResponseEntity.of(student);
    }
}


