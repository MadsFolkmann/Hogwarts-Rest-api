package edu.hogwarts.controllers;

import edu.hogwarts.dto.StudentResponseDto;
import edu.hogwarts.services.StudentService;
import edu.hogwarts.dto.StudentRequestDto;
import edu.hogwarts.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<StudentResponseDto> getStudents() {
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable int id) {
        return ResponseEntity.of(studentService.findById(id));
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        return studentService.createStudent(studentRequestDto);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable int id, @RequestBody StudentRequestDto student) {
        return ResponseEntity.of(studentService.updateIfExist(id, student));
    }

    @PatchMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> updatePathStudent(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return studentService.patchIfExist(id, updates).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> deleteStudent(@PathVariable int id) {
        return ResponseEntity.of(studentService.deleteById(id));
    }
}


