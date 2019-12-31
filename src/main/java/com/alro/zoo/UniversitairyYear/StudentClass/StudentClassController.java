package com.alro.zoo.UniversitairyYear.StudentClass;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.UniversitairyYear.StudentClass.DTO.StudentClassDTO;


@Controller
public class StudentClassController {

	@Autowired
	private StudentClassService service;
	
	@PostMapping(path = "/StudentClass")
	public ResponseEntity<StudentClass> addNewStudentClass(@Valid @RequestBody StudentClassDTO requestDto){
		return service.saveStudentClass(requestDto);
	}
	
	@GetMapping(path = "/StudentClasss")
	public ResponseEntity<List<StudentClass>> getAllStudentClasss(){
		return service.getStudentClasses();
	}
	
	@GetMapping(path = "/StudentClass/{StudentClassCode}")
	public ResponseEntity<StudentClass> getDiscussionByCode(@PathVariable String StudentClassCode){
		return service.getStudentClassByCode(StudentClassCode);
	}	
	
	
}
