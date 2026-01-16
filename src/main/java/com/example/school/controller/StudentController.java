package com.example.school.controller;

import com.example.school.entity.Student;
import com.example.school.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saveStudent = studentService.save(student);
        return new ResponseEntity<>(saveStudent, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        student.setId(id);
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.deleteById(id);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public Student enrollInCourse(@PathVariable Integer studentId,
                                  @PathVariable Integer courseId) {
        return studentService.addCourseToStudent(studentId, courseId);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public Student dropCourse(@PathVariable Integer studentId,
                              @PathVariable Integer courseId) {
        return studentService.removeCourseFromStudent(studentId, courseId);

    }
}
