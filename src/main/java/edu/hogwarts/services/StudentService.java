package edu.hogwarts.services;

import edu.hogwarts.dto.StudentRequestDto;
import edu.hogwarts.dto.StudentResponseDto;
import edu.hogwarts.models.House;
import edu.hogwarts.models.Student;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final HouseRepository houseRepository;

    public StudentService(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    public List<StudentResponseDto> findAll() {
        return studentRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<StudentResponseDto> findById(int id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    public StudentResponseDto save(StudentRequestDto student) {
        return toDTO(studentRepository.save(toEntity(student)));
    }

    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {
        Optional<House> house = houseRepository.findFirstByName(studentRequestDto.getHouseName());
        if (house.isPresent()) {
            Student student = new Student(studentRequestDto.getFullName(), studentRequestDto.getDateOfBirth(), house.get(), studentRequestDto.isPrefect(), studentRequestDto.getEnrollmentYear(), studentRequestDto.getGraduationYear(), studentRequestDto.isGraduated(), studentRequestDto.getSchoolYear());
            return toDTO(studentRepository.save(student));
        }
        return null;
    }

    public Optional<StudentResponseDto> updateIfExist(int id, StudentRequestDto updateStudent) {
        if (studentRepository.existsById(id)) {
            Student entity = toEntity(updateStudent);
            entity.setId(id);
            return Optional.of(toDTO(studentRepository.save(entity)));
        } else{
            return Optional.empty();
        }
    }

    public Optional<StudentResponseDto> deleteById(int id) {
        Optional<StudentResponseDto> existinStudent = this.findById(id);
        studentRepository.deleteById(id);
        return existinStudent;
    }

    public Optional<ResponseEntity<Student>> patchIfExist(int id, Map<String, Object> updates) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) {
            return Optional.empty();
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

        return Optional.of(new ResponseEntity<>(student, HttpStatus.OK));
    }

    public StudentResponseDto toDTO(Student entity) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setFirstname(entity.getFirstName());
        dto.setMiddlename(entity.getMiddleName());
        dto.setLastname(entity.getLastName());
        dto.setHouseName(entity.getHouse().get().getName());
        dto.setPrefect(entity.isPrefect());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEnrollmentYear(entity.getEnrollmentYear());
        dto.setGraduationYear(entity.getGraduationYear());
        dto.setGraduated(entity.isGraduated());
        dto.setSchoolYear(entity.getSchoolYear());
        return dto;
    }


    private Student toEntity(StudentRequestDto student) {
        Student entity = new Student();
        entity.setFirstName(student.getFirstname());
        entity.setMiddleName(student.getMiddlename());
        entity.setLastName(student.getLastname());
        entity.setPrefect(student.isPrefect());
        entity.setDateOfBirth(student.getDateOfBirth());
        entity.setEnrollmentYear(student.getEnrollmentYear());
        entity.setGraduationYear(student.getGraduationYear());
        entity.setGraduated(student.isGraduated());
        entity.setSchoolYear(student.getSchoolYear());

        Optional<House> house = houseRepository.findFirstByName(student.getHouseName());
        house.ifPresent(entity::setHouse);

        return entity;
    }
}
