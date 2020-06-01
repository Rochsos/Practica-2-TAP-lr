package ufv.tap.backend.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import ufv.tap.backend.modelo.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long>{

}
