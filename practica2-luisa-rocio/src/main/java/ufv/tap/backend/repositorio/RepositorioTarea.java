package ufv.tap.backend.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufv.tap.backend.modelo.Tarea;

public interface RepositorioTarea extends JpaRepository<Tarea, Long> {

	@Query("select t from Tarea t " + "where lower(t.nombre) like lower(concat('%', :searchTerm, '%')) ")
	List<Tarea> searchByName(@Param("searchTerm") String searchTerm);
	
	@Query("select t from Tarea t " + "where lower(t.listaTareas) like lower(concat('%', :searchTerm, '%')) ")
	List<Tarea> searchByList(@Param("searchTerm") String searchTerm);
}
