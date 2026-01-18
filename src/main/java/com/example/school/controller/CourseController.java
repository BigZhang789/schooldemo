package com.example.school.controller;

import com.example.school.entity.Course;
import com.example.school.entity.Student;
import com.example.school.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        return courseService.save(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Integer id) {
        courseService.deleteById(id);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByCourse(@PathVariable Integer id) {
        return courseService.getStudentsByCourseId(id);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public Course addStudentToCourse(@PathVariable Integer courseId,
            @PathVariable Integer studentId) {
        return courseService.addStudentToCourse(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public Course removeStudentFromCourse(@PathVariable Integer courseId,
            @PathVariable Integer studentId) {
        return courseService.removeStudentFromCourse(courseId, studentId);
    }
}
