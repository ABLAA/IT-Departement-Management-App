package com.alro.zoo.Department.Section;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.alro.zoo.Department.Department;
import com.alro.zoo.Department.DepartmentService;
import com.alro.zoo.Department.dtos.SectionDTO;
import com.alro.zoo.shared.GenericService;


@Service
@Transactional
public class SectionService extends GenericService<Section, SectionRepository> {

	@Autowired
	private SectionRepository repo;
	
	@Autowired 
	private DepartmentService departmentService;
	
	public SectionService() {
	}
	public SectionService(SectionRepository repo) {
		this.repo = repo;
	}

	@Override
	public String getPrefix() {
		return Section.prefix;
	}
	
	public SectionRepository getRepo() {
		return repo;
	}
	public ResponseEntity<Section> saveSection(SectionDTO dto) {
		Section sec = new Section();
		sec.setCode(generateNewCode());
    	sec.setName(dto.name);
    	sec.setDepartment(departmentService.getRepo().findOneByTitle(dto.department).get());
    	return ResponseEntity.created(null).body(repo.save(sec));
    }
	
	public ResponseEntity<Section> getSectionByCode(String code) {
    	return ResponseEntity.ok().body(repo.findById(code).get());
    }
	
    
    public ResponseEntity<List<Section>> findSectionsByDepartment(String depName){
    	Department dep = departmentService.getRepo().findOneByTitle(depName).get();
    	return ResponseEntity.ok().body(repo.findAllByDepartment(dep));
    }
    
    public ResponseEntity<List<Section>> findSections(){
    	return ResponseEntity.ok().body(repo.findAll());
    }
    
    public ResponseEntity<Object> deleteSectionByCode(String code) {
		repo.deleteById(code);
		return ResponseEntity.ok().body("success");
	}
    
}
