package edu.hogwarts;

import edu.hogwarts.models.*;
import edu.hogwarts.repositories.CourseRepository;
import edu.hogwarts.repositories.HouseRepository;
import edu.hogwarts.repositories.StudentRepository;
import edu.hogwarts.repositories.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InitData implements CommandLineRunner {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private TeacherRepository teacherRepository;
    private HouseRepository houseRepository;

    public InitData(StudentRepository studentRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, HouseRepository houseRepository){
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
    }

    public void run(String... args) {

        //Houses
        House gryffindor = new House("Gryffindor", "Godric Gryffindor", Arrays.asList("Rød", "Guld"));
        House hufflepuff = new House("Hufflepuff", "Helga Hufflepuff", Arrays.asList("Gul", "Sort"));
        House ravenclaw = new House("Ravenclaw", "Rowena Ravenclaw", Arrays.asList("Blå", "Bronze"));
        House slytherin = new House("Slytherin", "Salazar Slytherin", Arrays.asList("Grøn", "Sølv"));

        houseRepository.saveAll(Arrays.asList(gryffindor, hufflepuff, ravenclaw, slytherin));

        //Teachers
        Teacher teacher = new Teacher("Minerva", "", "McGonagall", new Date(), gryffindor, true, EmpType.TENURED, new Date(), new Date());
        teacherRepository.save(teacher);

        //Students
        Set<Student> students = new HashSet<>();
        students.add(new Student("Harry", "James", "Potter", new Date(), gryffindor, true, 1991, 1998, true));
        students.add(new Student("Hermione", "", "Granger", new Date(), gryffindor, false, 1991, 1998, true));
        students.add(new Student("Ron", "Bilius", "Weasley", new Date(), gryffindor, false, 1991, 1998, true));
        students.add(new Student("Draco", "", "Malfoy", new Date(), slytherin, false, 1991, 1998, true));
        students.add(new Student("Luna", "", "Lovegood", new Date(), ravenclaw, false, 1992, 1999, true));
        students.add(new Student("Neville", "", "Longbottom", new Date(), gryffindor, false, 1991, 1998, true));
        students.add(new Student("Ginny", "Molly", "Weasley", new Date(), gryffindor, false, 1992, 1999, true));
        students.add(new Student("Fred", "", "Weasley", new Date(), gryffindor, false, 1991, 1998, true));
        students.add(new Student("George", "", "Weasley", new Date(), gryffindor, false, 1991, 1998, true));
        students.add(new Student("Cedric", "", "Diggory", new Date(), hufflepuff, true, 1991, 1998, true));
        students.add(new Student("Cho", "", "Chang", new Date(), ravenclaw, false, 1992, 1999, true));
        students.add(new Student("Vincent", "", "Crabbe", new Date(), slytherin, false, 1991, 1998, true));
        studentRepository.saveAll(students);

        //courses
        Course course1 = new Course("Transfiguration", 1991, true, teacher, students);
        courseRepository.save(course1);

    }
}
