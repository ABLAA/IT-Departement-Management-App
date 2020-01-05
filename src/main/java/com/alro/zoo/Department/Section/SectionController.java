package com.alro.zoo.Department.Section;

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

import com.alro.zoo.Department.dtos.SectionDTO;





@Controller
public class SectionController {

	@Autowired
	private SectionService service;
	
	
	@PostMapping(path = "/Section")
	public ResponseEntity<Section> addNewSection(@Valid @RequestBody SectionDTO requestDto){
		return service.saveSection(requestDto);
	}
	
	@GetMapping(path = "/Sections")
	public ResponseEntity<List<Section>> getAllSections(){
		return service.findSections();
	}
	@GetMapping(path = "/Sections/{departmentTitle}")
	public ResponseEntity<List<Section>> getAllSections(@PathVariable String departmentTitle){
		return service.findSectionsByDepartment(departmentTitle);
	}
	
	@GetMapping(path = "/Section/{sectionCode}")
	public ResponseEntity<Section> getSectionByCode(@PathVariable String sectionCode){
		return service.getSectionByCode(sectionCode);
	}	
	
	@DeleteMapping(path ="/Section/{sectionCode}")
	public ResponseEntity<Object> deleteSectionByCode(@PathVariable String sectionCode){
		return service.deleteSectionByCode(sectionCode);
	}
	
	
}
