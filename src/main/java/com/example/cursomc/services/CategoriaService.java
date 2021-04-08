package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	/*public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
	*/
	public Categoria find(Integer id) throws ObjectNotFoundException {
	Optional<Categoria> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id " + id + " Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) throws ObjectNotFoundException {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	public Void delete(Integer id) throws ObjectNotFoundException{
		try {
			find(id);
			repo.deleteById(id);
			return null;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		//PageRequest pageRequest = new PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	
		
	}
}
