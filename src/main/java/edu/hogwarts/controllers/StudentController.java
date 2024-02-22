package edu.hogwarts.controllers;

import edu.hogwarts.dto.StudentRequestDto;
import edu.hogwarts.models.House;
import edu.hogwarts.models.Student;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<StudentRequestDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentRequestDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            studentDtos.add(StudentRequestDto.fromStudent(student));
        }
        return studentDtos;
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        return ResponseEntity.of(student);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        if (student.getHouse().isPresent()) {
            student.setHouse(student.getHouse().get());
            return studentRepository.save(student);
        }
        return null;
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


