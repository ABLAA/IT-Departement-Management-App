package com.alro.zoo.Department;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.Department.Professor.Professor;
import com.alro.zoo.Department.Section.Section;
import com.alro.zoo.shared.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Department extends GenericEntity {
	
	@Transient
	public static String prefix = "DEP";
	
	@Id
	@Column(length = 15)
	private String code;
	
	@Column(length = 15 ,unique = true)
	private String title;
	
	@OneToMany(mappedBy = "department")
	@JsonIgnore
	private List<Professor> professors = new ArrayList<Professor>();
	
	@OneToMany(mappedBy = "department")
	@JsonIgnore
	private List<Section> sections = new ArrayList<Section>();

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	
	
	
}
