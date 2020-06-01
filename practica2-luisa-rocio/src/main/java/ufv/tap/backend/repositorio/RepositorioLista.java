package ufv.tap.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import ufv.tap.backend.modelo.ListaTareas;

public interface RepositorioLista extends JpaRepository<ListaTareas, Long>{

}
