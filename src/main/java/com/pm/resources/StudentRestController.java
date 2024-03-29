package com.pm.resources;

import com.pm.model.Student;
import com.pm.repository.StudentRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/students")
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentRepository.save(student));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") int studentId,
          @RequestBody Student updatedStudent) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        if (Objects.isNull(existingStudent)) {
            return ResponseEntity.notFound().build();
        }
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setCourses(updatedStudent.getCourses());
        return ResponseEntity.ok(studentRepository.save(existingStudent));
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> findStudentById(@PathVariable("studentId") int studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        if (Objects.isNull(existingStudent)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("studentId") int studentId) {
        Student existingStudent = studentRepository.findById(studentId).orElse(null);
        if (Objects.isNull(existingStudent)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok("student with id " + studentId + " is deleted");
    }
}
