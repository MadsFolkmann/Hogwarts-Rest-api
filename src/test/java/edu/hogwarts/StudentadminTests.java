package edu.hogwarts;

import edu.hogwarts.models.Course;
import edu.hogwarts.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentadminTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCourseSchoolYear() {
        Course course = new Course();
        course.setSchoolYear(2023);
        assertEquals(2023, course.getSchoolYear());
    }

    @Test
    void testStudentSchoolYear() {
        Student student = new Student();
        student.setSchoolYear(2023);
        assertEquals(2023, student.getSchoolYear());
    }
}
