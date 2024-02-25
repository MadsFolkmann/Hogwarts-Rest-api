package edu.hogwarts.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.hogwarts.models.Student;
import edu.hogwarts.models.Teacher;

import java.util.Set;

public class CourseResponseDto {
    private int id;
    private String subject;
    private int schoolYear;
    private boolean current;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private String teacherName;
    private Set<String> studentNames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Set<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(Set<String> studentNames) {
        this.studentNames = studentNames;
    }
}

