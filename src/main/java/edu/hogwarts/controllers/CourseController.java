package edu.hogwarts.controllers;


import edu.hogwarts.models.Course;
import edu.hogwarts.models.Student;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.StudentRepository;
import edu.hogwarts.repositories.TeacherRepository;
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

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping()
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/teacher")
    public Teacher getTeacherForCourse(@PathVariable int id) {
        return Objects.requireNonNull(courseRepository.findById(id).orElse(null)).getTeacher();
    }

    @GetMapping("/{id}/students")
    public Set<Student> getAllCourseStudents(@PathVariable int id) {
        return Objects.requireNonNull(courseRepository.findById(id).orElse(null)).getStudents();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        if (courseRepository.existsById(id)) {
            course.setId(id);
            courseRepository.save(course);
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/teacher/{teacherId}")
    public ResponseEntity<Course> updateTeacherForCourse(@PathVariable int id, @PathVariable int teacherId) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
            if (teacher != null) {
                course.setTeacher(teacher);

                courseRepository.save(course);
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/students/{studentId}")
    public ResponseEntity<Course> addStudentToCourse(@PathVariable int id, @PathVariable int studentId) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            Student student = studentRepository.findById(studentId).orElse(null);
            if (student != null) {
                Set<Student> students = course.getStudents();
                students.add(student);
                course.setStudents(students);

                courseRepository.save(course);
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
            courseRepository.deleteById(id);
            return ResponseEntity.of(course);

    }

    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<Course> deleteTeacherFromCourse(@PathVariable int id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setTeacher(null);
            courseRepository.save(course);
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<Course> deleteStudentFromCourse(@PathVariable int id, @PathVariable int studentId) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            Set<Student> students = course.getStudents();
            students.removeIf(student -> student.getId() == studentId);
            course.setStudents(students);
            courseRepository.save(course);
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
