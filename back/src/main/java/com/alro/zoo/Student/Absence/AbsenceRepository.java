package com.alro.zoo.Student.Absence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alro.zoo.Student.Student.Student;

public interface AbsenceRepository extends JpaRepository<Absence, String> {
	public List<Absence> findAllByDate(Date date);
	public List<Absence> findAllByStudent(Student student);

}
