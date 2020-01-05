package com.alro.zoo.Student.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
	public List<Student> findAllByStatus(Status status);

}
