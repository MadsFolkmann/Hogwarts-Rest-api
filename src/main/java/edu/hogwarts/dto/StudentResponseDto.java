package edu.hogwarts.dto;

import edu.hogwarts.models.Student;

import java.util.Date;

public class StudentResponseDto {
    private int id;
    private String firstname;
    private String middlename;
    private String lastname;
    private Date dateOfBirth;
    private String houseName;
    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private  boolean graduated;
    private int schoolYear;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean hasMiddleName(){
        return middlename != null;
    }

    public String getFullName(){
        if(hasMiddleName()) {
            return firstname + " " + middlename + " " + lastname;
        } else{
            return firstname + " " + lastname;
        }
    }

    public void setFullName(String fullName){
        String[] parts = fullName.split(" ");
        firstname = parts[0];
        lastname = parts[parts.length - 1];

        if (parts.length > 1){
            middlename = parts[1];
        } else {
            middlename = null;
        }

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
