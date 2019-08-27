package com.alro.zoo.shared;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.shared.GeneralMethods;

@Service
public abstract class  GenericService<T extends GenericEntity ,R extends JpaRepository<T, String>> {
	
	@Autowired
	private GeneralMethods methods;
	
	public abstract R getRepo();
	
	public abstract String getPrefix();

	public String generateNewCode() {
		try {
			String code = methods.generateAnId(getPrefix());
			while(getRepo().findById(code).isPresent()) {
				code = methods.generateAnId(getPrefix());
			}
			return code;
		}catch (Exception e) {
			System.err.println(e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}	
	}
	
	public T findById(String code) {
		Optional<T> option = (Optional<T>) getRepo().findById(code);
		if (option.isPresent()) {
			return option.get();
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "theres no element with the code: "+ code);
		}
		
	}

}
