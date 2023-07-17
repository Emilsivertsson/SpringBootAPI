package com.CodeForPizza.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//makes a Generic interface for the Student class, with a Long as the primary key
//JpaRepository has all the methods for CRUD operations
public interface StudentRepository extends JpaRepository<Student, Long> {


    //makes a custom query to find a student by email
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
