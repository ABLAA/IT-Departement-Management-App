package com.alro.zoo.UniversitairyYear.Year;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alro.zoo.UniversitairyYear.Year.DTO.UniversitairyYearDTO;

@Service
@Transactional
public class UniversitairyYearService {

	@Autowired
	private UniversitairyYearRepository repo;	
	
	
	public UniversitairyYearService() {
		// TODO Auto-generated constructor stub
	}
	public UniversitairyYearService(UniversitairyYearRepository repo) {
		this.repo = repo;
	}

	public UniversitairyYearRepository getRepo() {
		return repo;
	}

	
	
	public ResponseEntity<UniversitairyYear> saveUniversitairyYear(UniversitairyYearDTO dto) {
		UniversitairyYear year = new UniversitairyYear();
    	year.setYear(dto.universitairyYear);;
    	return ResponseEntity.created(null).body(repo.save(year));
    }
	
	public ResponseEntity<UniversitairyYear> getUniversitairyYearByYear(String year) {
    	return ResponseEntity.ok().body(repo.findById(year).get());
    }
	
	public ResponseEntity<List<UniversitairyYear>> getUniversitairyYears() {
    	return ResponseEntity.ok().body(repo.findAll());
    }
	
	public ResponseEntity<Object> deleteUniversitairyClassByCode(String code) {
		repo.deleteById(code);
		return ResponseEntity.ok().body("success");
	}
    
    
    
}
