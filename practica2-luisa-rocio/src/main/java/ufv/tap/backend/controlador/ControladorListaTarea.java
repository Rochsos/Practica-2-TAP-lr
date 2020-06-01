package ufv.tap.backend.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.repositorio.RepositorioLista;

@Service
public class ControladorListaTarea {
	
	private RepositorioLista repositorioLista;

    public ControladorListaTarea(RepositorioLista repositorioLista) {
        this.repositorioLista = repositorioLista;
    }

    public List<ListaTareas> findAll() {
        return repositorioLista.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(listaTareas ->
            stats.put(listaTareas.getNombre(), listaTareas.getTareas().size()));
        return stats;
    }

}
