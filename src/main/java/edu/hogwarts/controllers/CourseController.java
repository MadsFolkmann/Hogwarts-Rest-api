package edu.hogwarts.controllers;


import edu.hogwarts.dto.*;
import edu.hogwarts.models.Course;
import edu.hogwarts.models.Student;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.StudentRepository;
import edu.hogwarts.repositories.TeacherRepository;
import edu.hogwarts.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<CourseResponseDto> getCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable int id) {
        return ResponseEntity.of(courseService.findById(id));
    }

    @GetMapping("/{id}/teacher")
    public String getTeacherForCourse(@PathVariable int id) {
        return Objects.requireNonNull(courseService.findById(id).orElse(null)).getTeacherName();
    }

    @GetMapping("/{id}/students")
    public Set<String> getAllCourseStudents(@PathVariable int id) {
        return Objects.requireNonNull(courseService.findById(id).orElse(null)).getStudentNames();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto createCourse(@RequestBody CourseRequestDto courseRequestDto) {
        return courseService.createCourse(courseRequestDto);
    }

    //Post students til course
    @PostMapping("/{id}/students")
    public ResponseEntity<CourseResponseDto> addStudentsToCourse(@PathVariable int id, @RequestBody List<StudentRequestDto> studentRequestDtos) {
        return ResponseEntity.of(courseService.addStudentsToCourse(id, studentRequestDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable int id, @RequestBody CourseRequestDto updateCourseRequestDto) {
        return ResponseEntity.of(courseService.updateIfExist(id, updateCourseRequestDto));
    }

    // Fjern og tilføj teacher til course
    @PatchMapping("/{id}/teacher")
    public ResponseEntity<CourseResponseDto> updateCourseTeacher(@PathVariable int id, @RequestBody(required = false) TeacherRequestDto teacherRequestDto) {
        return ResponseEntity.of(courseService.updateCourseTeacher(id, teacherRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseResponseDto> deleteCourse(@PathVariable int id) {
        return ResponseEntity.of(courseService.deleteById(id));
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<CourseResponseDto> deleteStudentFromCourse(@PathVariable int id, @PathVariable int studentId) {
    return ResponseEntity.of(courseService.deleteStudentFromCourse(id, studentId));
    }

}
