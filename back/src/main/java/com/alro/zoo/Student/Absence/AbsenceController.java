package com.alro.zoo.Student.Absence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alro.zoo.Student.Absence.DTO.AbsenceDTO;


@Controller
public class AbsenceController {

	@Autowired
	private AbsenceService service;
	@CrossOrigin
	@PostMapping(path = "/Absence")
	public ResponseEntity<Absence> addNewAbsence(@Valid @RequestBody AbsenceDTO requestDto){
		return service.saveAbsence(requestDto);
	}
	@CrossOrigin
	@GetMapping(path = "/Absences")
	public ResponseEntity<List<Absence>> getAllAbsences(){
		return service.getAbsences();
	}
	@CrossOrigin
	@GetMapping(path = "/Absences/Date/{date}")
	public ResponseEntity<List<Absence>> getAllAbsencesByDate(@PathVariable String date) throws ParseException{
		Date dateD = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		System.out.println("aahhii" + dateD);
		return service.getAbsencesByDate(dateD);
	}
	@CrossOrigin
	@GetMapping(path = "/Absences/Student/{studentCode}")
	public ResponseEntity<List<Absence>> getAllAbsencesByStudent(@PathVariable String studentCode){
		return service.getAbsencesByStudent(studentCode);
	}
	@CrossOrigin
	@GetMapping(path = "/Absence/{absenceCode}")
	public ResponseEntity<Absence> getAbsenceByCode(@PathVariable String absenceCode){
		return service.getAbsenceByCode(absenceCode);
	}	
	@CrossOrigin
	@DeleteMapping(path ="/Absence/{absenceCode}")
	public ResponseEntity<Object> deleteAbsenceByCode(@PathVariable String absenceCode){
		return service.deleteAbsenceByCode(absenceCode);
	}
	
}
