package edu.hogwarts.controllers;

import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    private TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/teachers")
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable int id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return ResponseEntity.of(teacher);
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isPresent()) {
            teacher.setId(id);
            teacherRepository.save(teacher);
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        teacherRepository.deleteById(id);
        return ResponseEntity.of(teacher);
    }
}
