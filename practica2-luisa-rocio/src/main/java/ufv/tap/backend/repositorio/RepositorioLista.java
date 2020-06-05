package ufv.tap.backend.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.modelo.Tarea;

public interface RepositorioLista extends JpaRepository<ListaTareas, Long>{

	
}
