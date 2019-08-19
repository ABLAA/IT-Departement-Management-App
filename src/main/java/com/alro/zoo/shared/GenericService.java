package com.alro.zoo.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.shared.GeneralMethods;

@Service
public class GenericService<T extends GenericEntity ,R extends JpaRepository<T, String>> {
	
	@Autowired
	private GeneralMethods methods;

	protected R repo;
	
	public R getRepo() {
		return repo;
	}
	
	protected String getPrefix() {
		return T.prefix;
	}

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

}
