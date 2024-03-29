package edu.hogwarts.models;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    @ManyToOne
    private House house;
    private boolean headOfHouse;
    private EmpType employment;
    private Date employmentStart;
    private Date employmentEnd;

    public Teacher() {
    }

    public Teacher(String firstName, String middleName, String lastName, Date dateOfBirth, House house, boolean headOfHouse, EmpType employment, Date employmentStart, Date employmentEnd) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.headOfHouse = headOfHouse;
        this.employment = employment;
        this.employmentStart = employmentStart;
        this.employmentEnd = employmentEnd;
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

    public boolean isHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(boolean headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public EmpType getEmployment() {
        return employment;
    }

    public void setEmployment(EmpType employment) {
        this.employment = employment;
    }

    public Date getEmploymentStart() {
        return employmentStart;
    }

    public void setEmploymentStart(Date employmentStart) {
        this.employmentStart = employmentStart;
    }

    public Date getEmploymentEnd() {
        return employmentEnd;
    }

    public void setEmploymentEnd(Date employmentEnd) {
        this.employmentEnd = employmentEnd;
    }
}
