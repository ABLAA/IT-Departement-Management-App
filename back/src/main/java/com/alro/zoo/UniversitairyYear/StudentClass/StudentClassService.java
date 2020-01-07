package com.alro.zoo.UniversitairyYear.StudentClass;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alro.zoo.Department.Section.SectionService;
import com.alro.zoo.UniversitairyYear.StudentClass.DTO.StudentClassDTO;
import com.alro.zoo.UniversitairyYear.Year.UniversitairyYearService;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class StudentClassService extends GenericService<StudentClass, StudentClassRepository>{

	@Autowired
	private StudentClassRepository repo;
	@Override
	public String getPrefix() {
		return StudentClass.prefix;
	}
	@Autowired
	private UniversitairyYearService yearService;
	
	@Autowired
	private SectionService sectionService;
	
	
	public StudentClassRepository getRepo() {
		return repo;
	}

	public StudentClassService() {
		super();
	}
	
	public StudentClassService(StudentClassRepository repo) {
		super();
		this.repo = repo;
	}


	public ResponseEntity<StudentClass> saveStudentClass(StudentClassDTO dto) {
		StudentClass stuclass = new StudentClass();
		stuclass.setCode(generateNewCode());
		stuclass.setSection(sectionService.getRepo().findById(dto.sectionCode).get());
		stuclass.setYear(yearService.getRepo().findById(dto.universitairyYear).get());
    	return ResponseEntity.created(null).body(repo.save(stuclass));
    }
	
	public ResponseEntity<StudentClass> getStudentClassByCode(String code) {
    	return ResponseEntity.ok().body(findById(code));
    }
	
	public ResponseEntity<List<StudentClass>> getStudentClasses() {
    	return ResponseEntity.ok().body(repo.findAll());
    }
	
	public ResponseEntity<Object> deleteStudentClassByCode(String code) {
		repo.deleteById(code);
		return ResponseEntity.ok().body("success");
	}

	

}
