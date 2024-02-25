package edu.hogwarts.services;

import edu.hogwarts.dto.TeacherRequestDto;
import edu.hogwarts.dto.TeacherResponseDto;
import edu.hogwarts.models.EmpType;
import edu.hogwarts.models.House;
import edu.hogwarts.models.Teacher;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final HouseRepository houseRepository;

    public TeacherService(TeacherRepository teacherRepository, HouseRepository houseRepository) {
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
    }

    public List<TeacherResponseDto> findAll() {
        return teacherRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<TeacherResponseDto> findById(int id) {
        return teacherRepository.findById(id).map(this::toDTO);
    }

    public TeacherResponseDto save(TeacherRequestDto teacherDto) {
        return toDTO(teacherRepository.save(toEntity(teacherDto)));
    }

    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto) {
        Optional<House> house = houseRepository.findFirstByName(teacherRequestDto.getHouseName());
        if (house.isPresent()) {
            Teacher teacher = new Teacher(teacherRequestDto.getFirstName(), teacherRequestDto.getMiddleName(), teacherRequestDto.getLastName(), teacherRequestDto.getDateOfBirth(), house.get(), teacherRequestDto.isHeadOfHouse(), teacherRequestDto.getEmployment(), teacherRequestDto.getEmploymentStart(), teacherRequestDto.getEmploymentEnd());
            return toDTO(teacherRepository.save(teacher));
        }
        return null;
    }

    public Optional<TeacherResponseDto> updateIfExist(int id, TeacherRequestDto teacherDto) {
        if (teacherRepository.existsById(id)) {
            Teacher entity = toEntity(teacherDto);
            entity.setId(id);
            return Optional.of(toDTO(teacherRepository.save(entity)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ResponseEntity<TeacherResponseDto>> patchIfExist(int id, Map<String, Object> updates) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (!teacherOptional.isPresent()) {
            return Optional.empty();
        }

        Teacher teacher = teacherOptional.get();

        if (updates.containsKey("headOfHouse")) {
            teacher.setHeadOfHouse((Boolean) updates.get("headOfHouse"));
        }

        if (updates.containsKey("employmentEnd")) {
            teacher.setEmploymentEnd((Date) updates.get("employmentEnd"));
        }

        if (updates.containsKey("employment")) {
            teacher.setEmployment(EmpType.valueOf((String) updates.get("employment")));
        }

        teacherRepository.save(teacher);

        return Optional.of(new ResponseEntity<>(toDTO(teacher), HttpStatus.OK));
    }

    public Optional<TeacherResponseDto> deleteById(int id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        teacherRepository.deleteById(id);
        return teacher.map(this::toDTO);
    }

    private TeacherResponseDto toDTO(Teacher teacher) {
        TeacherResponseDto dto = new TeacherResponseDto();
        dto.setFirstName(teacher.getFirstName());
        dto.setMiddleName(teacher.getMiddleName());
        dto.setLastName(teacher.getLastName());
        dto.setDateOfBirth(teacher.getDateOfBirth());
        dto.setHeadOfHouse(teacher.isHeadOfHouse());
        dto.setEmployment(teacher.getEmployment());
        dto.setEmploymentStart(teacher.getEmploymentStart());
        dto.setEmploymentEnd(teacher.getEmploymentEnd());
        dto.setHouseName(teacher.getHouse().get().getName());
        return dto;

    }

    private Teacher toEntity(TeacherRequestDto teacher) {
        Teacher entity = new Teacher();
        entity.setFirstName(teacher.getFirstName());
        entity.setMiddleName(teacher.getMiddleName());
        entity.setLastName(teacher.getLastName());
        entity.setDateOfBirth(teacher.getDateOfBirth());
        entity.setHeadOfHouse(teacher.isHeadOfHouse());
        entity.setEmployment(teacher.getEmployment());
        entity.setEmploymentStart(teacher.getEmploymentStart());
        entity.setEmploymentEnd(teacher.getEmploymentEnd());

        Optional<House> house = houseRepository.findFirstByName(teacher.getHouseName());
        house.ifPresent(entity::setHouse);

        return entity;
    }
}
