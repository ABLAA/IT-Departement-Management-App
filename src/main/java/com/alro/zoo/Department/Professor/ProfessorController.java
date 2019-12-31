package com.alro.zoo.Department.Professor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.Department.dtos.ProfessorDTO;





@Controller
public class ProfessorController {

	@Autowired
	private ProfessorService service;
	
	
	@PostMapping(path = "/Professor")
	public ResponseEntity<Professor> addNewProfessor(@Valid @RequestBody ProfessorDTO requestDto){
		return service.saveProfessor(requestDto);
	}
	
	@GetMapping(path = "/Professors")
	public ResponseEntity<List<Professor>> getAllProfessors(){
		return service.findProfessors();
	}
	@GetMapping(path = "/Professors/{departmentTitle}")
	public ResponseEntity<List<Professor>> getAllProfessors(@PathVariable String departmentTitle){
		return service.findProfessorsByDepartment(departmentTitle);
	}
	
	@GetMapping(path = "/Professor/{professorCode}")
	public ResponseEntity<Professor> getDiscussionByCode(@PathVariable String professorCode){
		return service.getProfessorByCode(professorCode);
	}	
	
	
}
