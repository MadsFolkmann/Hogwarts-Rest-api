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
import java.util.Map;

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
    public ResponseEntity<StudentRequestDto> getStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            StudentRequestDto studentRequestDto = StudentRequestDto.fromStudent(studentOptional.get());
            return new ResponseEntity<>(studentRequestDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody StudentRequestDto StudentRequestDto) {
        String[] nameParts = StudentRequestDto.getFullName().split(" ");
        String firstName = nameParts[0];
        String middleName = nameParts.length > 2 ? nameParts[1] : null;
        String lastName = nameParts[nameParts.length - 1];

        Optional<House> house = houseRepository.findFirstByName(StudentRequestDto.getHouseName());

        if (house.isPresent()) {
            Student student = new Student(firstName, middleName, lastName, StudentRequestDto.getDateOfBirth(), house.get(), StudentRequestDto.isPrefect(), StudentRequestDto.getEnrollmentYear(), StudentRequestDto.getGraduationYear(), StudentRequestDto.isGraduated());
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

    @PatchMapping("/students/{id}")
    public ResponseEntity<Student> updatePathStudent(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Student student = studentOptional.get();

        if (updates.containsKey("isPrefect")) {
            student.setPrefect((Boolean) updates.get("isPrefect"));
        }

        if (updates.containsKey("schoolYear")) {
            student.setSchoolYear((Integer) updates.get("schoolYear"));
        }

        if (updates.containsKey("graduationYear")) {
            student.setGraduationYear((Integer) updates.get("graduationYear"));
            student.setGraduated(true);
        }

        studentRepository.save(student);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return ResponseEntity.of(student);
    }
}


