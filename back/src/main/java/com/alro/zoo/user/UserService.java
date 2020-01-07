package com.alro.zoo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alro.zoo.Department.DepartmentService;
import com.alro.zoo.user.dtos.SignInDTO;
import com.alro.zoo.shared.GenericService;


@Service
public class UserService extends GenericService<User, UserRepository> {
	
	@Autowired
	private UserRepository repo;
	

	
	@Autowired
	private DepartmentService depService;
	
	public UserService(UserRepository repo, DepartmentService depService) {
		super();
		this.repo = repo;

		this.depService = depService;
	}


	
	public UserService() {

	}
	
	@Override 
	public UserRepository getRepo() {
		return repo;
	}
	
	@Override
	public String getPrefix() {
		return User.prefix;
	}
	

	
	public User saveNewUser(User user) {
		return repo.save(user);
	}
	
	public User createUserObject(SignInDTO dto) {
		User user = new User();
		String userCode = generateNewCode();
		user.setCode(userCode);
		user.setBirthDate(dto.birthDate);
		user.setFirstName(dto.firstName);
		user.setLastName(dto.lastName);
		user.setDepartment(depService.getRepo().findOneByTitle(dto.departmentName).get());
		return user;
	}
	
}
