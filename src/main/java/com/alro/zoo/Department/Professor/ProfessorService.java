package com.alro.zoo.Department.Professor;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alro.zoo.Department.Department;
import com.alro.zoo.Department.DepartmentService;
import com.alro.zoo.Department.dtos.ProfessorDTO;
import com.alro.zoo.shared.GenericService;



@Service
@Transactional
public class ProfessorService extends GenericService<Professor, ProfessorRepository>{

	@Autowired
	private ProfessorRepository repo;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Override
	public ProfessorRepository getRepo() {
		return repo;
	}

	public ProfessorService() {
		super();
	}
	
	public ProfessorService(ProfessorRepository repo) {
		super();
		this.repo = repo;
	}


	@Override
	public String getPrefix() {
		return Professor.prefix;
	}
	
	public ResponseEntity<Professor> saveProfessor(ProfessorDTO dto) {
		Professor prof = new Professor();
		prof.setCode(generateNewCode());
    	prof.setDepartment(departmentService.getRepo().findOneByTitle(dto.departmentName).get());
    	prof.setBirthDate(dto.birthDate);
    	prof.setFirstName(dto.firstName);
    	prof.setLastName(dto.lastName);
    	return ResponseEntity.created(null).body(repo.save(prof));
    }
	
	public ResponseEntity<Professor> getProfessorByCode(String code) {
    	return ResponseEntity.ok().body(findById(code));
    }
	
    
    public ResponseEntity<List<Professor>> findProfessorsByDepartment(String depName){
    	Department dep = departmentService.getRepo().findOneByTitle(depName).get();
    	return ResponseEntity.ok().body(repo.findAllByDepartment(dep));
    }
    
    public ResponseEntity<List<Professor>> findProfessors(){
    	return ResponseEntity.ok().body(repo.findAll());
    }
    
    public ResponseEntity<Object> deleteProfessorByCode(String code) {
		repo.deleteById(code);
		return ResponseEntity.ok().body("success");
	}

}
