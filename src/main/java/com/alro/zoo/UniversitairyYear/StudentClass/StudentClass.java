package com.alro.zoo.UniversitairyYear.StudentClass;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.Department.Section.Section;
import com.alro.zoo.Student.Student.Student;
import com.alro.zoo.UniversitairyYear.Year.UniversitairyYear;
import com.alro.zoo.shared.GenericEntity;


@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"section", "year"})
	)
@Entity

public class StudentClass extends GenericEntity{
	@Transient
	public static String prefix = "CLAS";
	
	@Id
	@Column(length = 15)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "section")
	private Section section;
	
	@ManyToOne
	@JoinColumn(name = "year")
	private UniversitairyYear year;
	
	@OneToMany(mappedBy = "studentClass")
	private  List<Student> students = new ArrayList<Student>();
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section Section) {
		this.section = Section;
	}
	public UniversitairyYear getYear() {
		return year;
	}
	public void setYear(UniversitairyYear year) {
		this.year = year;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	
	
}