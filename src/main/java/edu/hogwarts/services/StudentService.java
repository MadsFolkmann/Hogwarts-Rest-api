package edu.hogwarts.services;

import edu.hogwarts.models.Student;
import edu.hogwarts.repositories.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(int id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> updateIfExist(int id, Student student) {
        if (studentRepository.findById(id).isPresent()) {
            student.setId(id);
            studentRepository.save(student);
            return Optional.of(student);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Student> deleteById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return student;
    }

}
