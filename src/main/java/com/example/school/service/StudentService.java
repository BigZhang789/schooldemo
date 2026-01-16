package com.example.school.service;

import com.example.school.entity.Course;
import com.example.school.entity.Student;
import com.example.school.repository.CourseRepository;
import com.example.school.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,
                          CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    @Transactional
    public void deleteById(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public Student addCourseToStudent(Integer studentId, Integer courseId) {
        Student student = findById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        student.getCourses().add(course);
        return studentRepository.save(student);

    }

    @Transactional
    public Student removeCourseFromStudent(Integer studentId, Integer courseId) {
        Student student = findById(studentId);
        student.getCourses().removeIf(course -> course.getId().equals(courseId));
        return studentRepository.save(student);
    }
}
