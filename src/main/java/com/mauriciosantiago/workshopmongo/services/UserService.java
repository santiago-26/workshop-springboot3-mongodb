package com.mauriciosantiago.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mauriciosantiago.workshopmongo.DTO.UserDTO;
import com.mauriciosantiago.workshopmongo.domain.User;
import com.mauriciosantiago.workshopmongo.repository.UserRepository;
import com.mauriciosantiago.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll(){
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert (User obj) {
		return repo.save(obj);
	}
	
	public void delete (String id) {
		try {
			if(!repo.existsById(id)) throw new ObjectNotFoundException(id);
			repo.deleteById(id);
		}catch(ObjectNotFoundException e) {
			throw new ObjectNotFoundException(id);
		}
	}
	public User update (User obj) {
		User newObj = repo.findById(obj.getId()).get();
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(),objDto.getEmail());
	}
	
}
