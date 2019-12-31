package com.alro.zoo.Student.Absence;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alro.zoo.Student.Absence.DTO.AbsenceDTO;
import com.alro.zoo.Student.Student.StudentService;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class AbsenceService extends GenericService<Absence, AbsenceRepository>{

	@Autowired
	private AbsenceRepository repo;
	
	@Autowired
	private StudentService studentService;

	public AbsenceService() {
		super();
	}
	
	public AbsenceService(AbsenceRepository repo) {
		super();
		this.repo = repo;
	}

//	public AbsenceService(AbsenceRepository repo, LoginService loginService) {
//		super();
//		this.repo = repo;
//		this.loginService = loginService;
//	}

	@Override
	public AbsenceRepository getRepo() {
		return repo;
	}

	@Override
	public String getPrefix() {
		return Absence.prefix;
	}
	
	public ResponseEntity<Absence> saveAbsence ( AbsenceDTO dto) {
		Absence newAbsence = new Absence();
		
		newAbsence.setCode(generateNewCode());
		newAbsence.setDate(dto.date);
		newAbsence.setStudent(studentService.findById(dto.studentCode));
		
		return ResponseEntity.created(null).body(repo.save(newAbsence));
		
	}
	
	public ResponseEntity<Absence> getAbsenceByCode ( String code) {
		return ResponseEntity.ok().body(findById(code));	
	}
	public ResponseEntity<List<Absence>> getAbsencesByDate ( Date date) {
		return ResponseEntity.ok().body(repo.findAllByDate(date));	
	}
	public ResponseEntity<List<Absence>> getAbsencesByStudent ( String studentCode) {
		return ResponseEntity.ok().body(repo.findAllByStudent(studentService.findById(studentCode)));	
	}
	public ResponseEntity<List<Absence>> getAbsences ( ) {
		return ResponseEntity.ok().body(repo.findAll());	
	}
}
