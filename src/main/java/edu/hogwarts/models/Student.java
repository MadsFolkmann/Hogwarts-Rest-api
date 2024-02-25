package edu.hogwarts.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    @ManyToOne
    private House house;
    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private  boolean graduated;
    private int schoolYear;

    public Student() {
    }

    public Student(String firstName, String middleName, String lastName, Date dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
        this.schoolYear = schoolYear;
    }

    public Student(String fullName, Date dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {

        String[] nameParts = fullName.split(" ");
        this.firstName = nameParts[0];
        this.middleName = nameParts.length > 2 ? nameParts[1] : null;
        this.lastName = nameParts[nameParts.length - 1];
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
        this.schoolYear = schoolYear;
    }

    public Student(String firstName, String middleName, String lastName, Date dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasMiddleName(){
        return middleName != null;
    }

    public String getFullName(){
        if(hasMiddleName()) {
            return firstName + " " + middleName + " " + lastName;
        } else{
            return firstName + " " + lastName;
        }
    }

    public void setFullName(String fullName){
        String[] parts = fullName.split(" ");
        firstName = parts[0];
        lastName = parts[parts.length - 1];

        if (parts.length > 1){
            middleName = parts[1];
        } else {
            middleName = null;
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Optional<House> getHouse() {
        return Optional.ofNullable(house);
    }

    public void setHouse(House house) {
        this.house = house;
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

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }



}
