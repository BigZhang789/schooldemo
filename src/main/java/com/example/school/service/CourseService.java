package com.example.school.service;

import com.example.school.entity.Course;
import com.example.school.entity.Student;
import com.example.school.repository.CourseRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository,
            StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Transactional
    public Course addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = findById(courseId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        course.getStudents().add(student);
        return courseRepository.save(course);
    }

    @Transactional
    public Course removeStudentFromCourse(Integer courseId, Integer studentId) {
        Course course = findById(courseId);
        course.getStudents().removeIf(student -> student.getId().equals(studentId));
        return courseRepository.save(course);
    }

    public List<Student> getStudentsByCourseId(Integer courseId) {
        Course course = findById(courseId);
        return course.getStudents();
    }
}
