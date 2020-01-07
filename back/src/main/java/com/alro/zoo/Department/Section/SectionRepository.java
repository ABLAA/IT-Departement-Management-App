package com.alro.zoo.Department.Section;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.Department.Department;


public interface SectionRepository extends JpaRepository<Section, String> {

	List<Section> findAllByDepartment(Department department);
	
}
