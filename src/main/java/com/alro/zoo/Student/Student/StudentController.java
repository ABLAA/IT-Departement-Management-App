package com.alro.zoo.Student.Student;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.alro.zoo.Student.Student.DTO.StudentDTO;


@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	
	@PostMapping(path = "/Student")
	public ResponseEntity<Student> addNewStudent(@Valid @RequestBody StudentDTO requestDto){
		return service.saveStudent(requestDto);
	}
	
	@GetMapping(path = "/Students")
	public ResponseEntity<List<Student>> getAllStudents(){
		return service.getStudents();
	}
	
	@GetMapping(path = "/Students/Status/{status}")
	public ResponseEntity<List<Student>> getAllStudentsByStatus(@PathVariable Status status){
		return service.getStudentsByStatus(status);
	}
	
	@GetMapping(path = "/Student/{studentCode}")
	public ResponseEntity<Student> getStudentByCode(@PathVariable String studentCode){
		return service.getStudentByCode(studentCode);
	}	
	
	@DeleteMapping(path ="/Student/{studentCode}")
	public ResponseEntity<Object> deleteStudentByCode(@PathVariable String studentCode){
		return service.deleteStudentByCode(studentCode);
	}
	
}
