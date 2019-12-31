package com.alro.zoo.Department;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alro.zoo.Department.dtos.DepartmentDTO;
import com.alro.zoo.shared.GenericService;

@Service
@Transactional
public class DepartmentService extends GenericService<Department, DepartmentRepository> {

	@Autowired
	private DepartmentRepository repo;	
	
	
	public DepartmentService() {
		// TODO Auto-generated constructor stub
	}
	public DepartmentService(DepartmentRepository repo) {
		this.repo = repo;
	}

	
//	public PostService(PostRepository repo, LoginService loginService, UserService userService,
//			CommentService commentService) {
//		super();
//		this.repo = repo;
//		this.loginService = loginService;
//		this.userService = userService;
//		this.commentService = commentService;
//	}



	public DepartmentRepository getRepo() {
		return repo;
	}
	@Override
	public String getPrefix() {
		return Department.prefix;
	}
	
	
	public ResponseEntity<Department> saveDepartment(DepartmentDTO dto) {
		Department dep = new Department();
		dep.setCode(generateNewCode());
    	dep.setTitle(dto.title);
    	return ResponseEntity.created(null).body(repo.save(dep));
    }
	
	public ResponseEntity<Department> getDepartmentByTitle(String title) {
    	return ResponseEntity.ok().body(repo.findOneByTitle(title).get());
    }
	
	public ResponseEntity<List<Department>> getAllDepartments(){
		return ResponseEntity.ok().body(repo.findAll());
	}
    
    
    
}
