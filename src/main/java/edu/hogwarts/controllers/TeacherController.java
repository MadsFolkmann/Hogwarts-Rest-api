package edu.hogwarts.controllers;

import edu.hogwarts.dto.TeacherRequestDto;
import edu.hogwarts.dto.TeacherResponseDto;
import edu.hogwarts.models.Student;
import edu.hogwarts.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public List<TeacherResponseDto> getTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacher(@PathVariable int id) {
        return ResponseEntity.of(teacherService.findById(id));
    }

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponseDto createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        return teacherService.createTeacher(teacherRequestDto);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable int id, @RequestBody TeacherRequestDto teacherRequestDto) {
        return ResponseEntity.of(teacherService.updateIfExist(id, teacherRequestDto));
    }

    @PatchMapping("/teachers/{id}")
    public ResponseEntity<TeacherResponseDto> updatePathStudent(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return teacherService.patchIfExist(id, updates).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<TeacherResponseDto> deleteTeacher(@PathVariable int id) {
        return ResponseEntity.of(teacherService.deleteById(id));
    }
}
