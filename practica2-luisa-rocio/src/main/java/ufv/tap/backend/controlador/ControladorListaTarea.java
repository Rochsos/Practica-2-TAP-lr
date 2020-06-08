package ufv.tap.backend.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.repositorio.RepositorioLista;

@Service
public class ControladorListaTarea {
	
	private static final Logger LOGGER = Logger.getLogger(ControladorListaTarea.class.getName());
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
    
    public void delete(ListaTareas listaTareas) {
		repositorioLista.delete(listaTareas);
	}

	public void save(ListaTareas listaTareas) {
		
		if (listaTareas == null) {
			LOGGER.log(Level.SEVERE, "Tarea is null. Are you sure you have connected your form to the application?");
			return;
		}
		
		repositorioLista.save(listaTareas);
	}

}
