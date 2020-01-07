package com.alro.zoo.Student.Absence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Transient;

import com.alro.zoo.Student.Student.Student;
import com.alro.zoo.shared.GenericEntity;

@Table(
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"date", "student"})
	)
@Entity
public class Absence extends GenericEntity{

	
	@Transient
	public static String prefix = "ABS";
	
	@Id
	@Column(length = 15)
	private String code;
	
	@JoinColumn(name = "date")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "student")
	private Student student;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
