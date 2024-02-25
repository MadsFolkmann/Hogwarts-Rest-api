package edu.hogwarts.services;

import edu.hogwarts.dto.*;
import edu.hogwarts.models.Course;
import edu.hogwarts.models.Student;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.StudentRepository;
import edu.hogwarts.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<CourseResponseDto> findAll() {
        return courseRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<CourseResponseDto> findById(int id) {
        return courseRepository.findById(id).map(this::toDTO);
    }

    public CourseResponseDto save(CourseRequestDto course) {
        return toDTO(courseRepository.save(toEntity(course)));
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        Course course = toEntity(courseRequestDto);
        return toDTO(courseRepository.save(course));
    }

    public Optional<CourseResponseDto> updateIfExist(int id, CourseRequestDto updateCourse) {
        if (courseRepository.existsById(id)) {
            Course entity = toEntity(updateCourse);
            entity.setId(id);
            return Optional.of(toDTO(courseRepository.save(entity)));
        } else{
            return Optional.empty();
        }
    }

    public Optional<CourseResponseDto> addStudentsToCourse(int id, List<StudentRequestDto> studentRequestDtos) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        Set<Student> students = course.getStudents();

        for (StudentRequestDto studentRequestDto : studentRequestDtos) {
            Optional<Student> studentOptional;
            if (studentRequestDto.getId() != 0) {
                studentOptional = studentRepository.findById(studentRequestDto.getId());
            } else if (studentRequestDto.getFullName() != null) {
                studentOptional = studentRepository.findByFirstName(studentRequestDto.getFirstname());
            } else {
                continue;
            }

            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                if (student.getSchoolYear() != course.getSchoolYear()) {
                    throw new IllegalArgumentException("Student's school year does not match the course's school year");
                }
                students.add(student);
            }
        }

        course.setStudents(students);
        course = courseRepository.save(course);

        return Optional.of(toDTO(course));
    }

    public Optional<CourseResponseDto> updateCourseTeacher(int id, TeacherRequestDto teacherRequestDto) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        if (teacherRequestDto != null) {
            Optional<Teacher> teacherOptional = teacherRepository.findById(teacherRequestDto.getId());
            teacherOptional.ifPresent(course::setTeacher);
        } else {
            course.setTeacher(null);
        }

        course = courseRepository.save(course);

        return Optional.of(toDTO(course));
    }

    public Optional<CourseResponseDto> deleteById(int id) {
        Optional<CourseResponseDto> existingCourse = this.findById(id);
        if (existingCourse.isPresent()) {
            courseRepository.deleteById(id);
        }
        return existingCourse;
    }

    public Optional<CourseResponseDto> deleteStudentFromCourse(int id, int studentId) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        Set<Student> students = course.getStudents();
        students.removeIf(student -> student.getId() == studentId);
        course.setStudents(students);
        course = courseRepository.save(course);

        return Optional.of(toDTO(course));
    }

    private CourseResponseDto toDTO(Course entity) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setSubject(entity.getSubject());
        dto.setSchoolYear(entity.getSchoolYear());
        dto.setCurrent(entity.isCurrent());
        dto.setTeacherName(entity.getTeacher().getFullName());
        dto.setStudentNames(entity.getStudents().stream().map(Student::getFullName).collect(Collectors.toSet()));        return dto;
    }

    private Course toEntity(CourseRequestDto dto) {
        Course entity = new Course();
        entity.setSubject(dto.getSubject());
        entity.setSchoolYear(dto.getSchoolYear());
        entity.setCurrent(dto.isCurrent());
        entity.setTeacher(dto.getTeacher());
        entity.setStudents(dto.getStudents());
        return entity;
    }

}
