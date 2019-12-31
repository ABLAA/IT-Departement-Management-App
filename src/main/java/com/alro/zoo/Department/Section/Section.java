package com.alro.zoo.Department.Section;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.Department.Department;
import com.alro.zoo.UniversitairyYear.StudentClass.StudentClass;
import com.alro.zoo.shared.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Section extends GenericEntity{
	@Transient
	public static String prefix = "SECT";
	
	@Id
	@Column(length = 15)
	private String code;
	@ManyToOne
	private Department department;
	
	private String name;
	
	@OneToMany(mappedBy = "section")
	@JsonIgnore
	private List<StudentClass> classes = new ArrayList<StudentClass>();
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
	
	
}