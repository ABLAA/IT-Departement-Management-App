package com.alro.zoo.Department;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {

	Optional<Department> findOneByTitle(String title);
}
