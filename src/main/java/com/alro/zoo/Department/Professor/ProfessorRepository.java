package com.alro.zoo.Department.Professor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.Department.Department;

public interface ProfessorRepository extends JpaRepository<Professor, String>{
	List<Professor> findAllByDepartment(Department department);

}
