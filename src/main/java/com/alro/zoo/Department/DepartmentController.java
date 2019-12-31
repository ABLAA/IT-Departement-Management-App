package com.alro.zoo.Department;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.Department.dtos.DepartmentDTO;





@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService service;
	
	
	@PostMapping(path = "/department")
	public ResponseEntity<Department> addNewDepartment(@Valid @RequestBody DepartmentDTO requestDto){
		return service.saveDepartment(requestDto);
	}
	
	@GetMapping(path = "/departments")
	public ResponseEntity<List<Department>> getAllDepartments(){
		return service.getAllDepartments();
	}
	
	@GetMapping(path = "/department/{departmentTitle}")
	public ResponseEntity<Department> getDiscussionByCode(@PathVariable String departmentTitle){
		return service.getDepartmentByTitle(departmentTitle);
	}	
	
	
}
