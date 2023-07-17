package com.CodeForPizza.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// @Component to make this class a Spring Bean, so that it can be injected into other classes
@Service // same as @Component
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired // dependency injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll(); // returns all students in the database
    }

    public void addNewStudent(Student student) {
        // checks if the email is taken
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) { // if the email is taken
            throw new IllegalStateException("email taken");
        }
        // if the email is not taken
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId); // checks if the student exists
        if (!exists) { // if the student does not exist
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        // if the student exists
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId) // finds the student by id
                .orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist")); // if the student does not exist, throw an exception
        // if the student exists
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(),name)) { // if the name is not null, not empty, and not the same as the current name
            student.setName(name); // set the name
        }

        if (email != null && email.length() > 0 && !email.equals(student.getEmail())) { // if the email is not null, not empty, and not the same as the current email
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email); // checks if the email is taken
            if (studentOptional.isPresent()) { // if the email is taken
                throw new IllegalStateException("email taken");
            }
            // if the email is not taken
            student.setEmail(email); // set the email
        }
    }
}
