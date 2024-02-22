package edu.hogwarts.dto;

import edu.hogwarts.models.Student;

import java.util.Date;

public class StudentRequestDto {
    private String firstname;
    private String middlename;
    private String lastname;
    private Date dateOfBirth;
    private String houseName;
    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private  boolean graduated;

    public static StudentRequestDto fromStudent(Student student) {
        StudentRequestDto studentDto = new StudentRequestDto();
        studentDto.setFirstname(student.getFirstName());
        studentDto.setMiddlename(student.getMiddleName());
        studentDto.setLastname(student.getLastName());
        studentDto.setHouseName(student.getHouse().get().getName());
        studentDto.setPrefect(student.isPrefect());
        studentDto.setDateOfBirth(student.getDateOfBirth());
        studentDto.setEnrollmentYear(student.getEnrollmentYear());
        studentDto.setGraduationYear(student.getGraduationYear());
        studentDto.setGraduated(student.isGraduated());
        return studentDto;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public boolean isPrefect() {
        return prefect;
    }

    public void setPrefect(boolean prefect) {
        this.prefect = prefect;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }
}

