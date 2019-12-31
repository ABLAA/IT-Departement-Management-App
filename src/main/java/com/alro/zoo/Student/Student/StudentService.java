package com.alro.zoo.Student.Student;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alro.zoo.Student.Student.DTO.StudentDTO;
import com.alro.zoo.shared.GenericService;



@Service
@Transactional
public class StudentService extends GenericService<Student, StudentRepository>{

	@Autowired
	private StudentRepository repo;
	
	
	@Override
	public StudentRepository getRepo() {
		return repo;
	}

	public StudentService() {
		super();
	}
	
	public StudentService(StudentRepository repo) {
		super();
		this.repo = repo;
	}


	@Override
	public String getPrefix() {
		return Student.prefix;
	}
	
	public ResponseEntity<Student> saveStudent(StudentDTO dto) {
		Student stud = new Student();
		stud.setCode(generateNewCode());
    	stud.setBirthDate(dto.birthDate);
    	stud.setFirstName(dto.firstName);
    	stud.setLastName(dto.lastName);
    	return ResponseEntity.created(null).body(repo.save(stud));
    }
	
	public ResponseEntity<Student> getStudentByCode(String code) {
    	return ResponseEntity.ok().body(findById(code));
    }
	
	public ResponseEntity<List<Student>> getStudents() {
    	return ResponseEntity.ok().body(repo.findAll());
    }
	
	
	
    
//    public ResponseEntity<List<Student>> findStudentByDepartment(String depName){
//    	Department dep = departmentService.getRepo().findOneByTitle(depName);
//    	return ResponseEntity.ok().body(repo.findAllByDepartment(dep));
//    }
	

}
