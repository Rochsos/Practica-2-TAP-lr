package ufv.tap.backend.controlador;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import ufv.tap.backend.modelo.ListaTareas;
import ufv.tap.backend.modelo.Tarea;
import ufv.tap.backend.repositorio.RepositorioLista;
import ufv.tap.backend.repositorio.RepositorioTarea;

@Service
public class ControladorTarea {

	private static final Logger LOGGER = Logger.getLogger(ControladorTarea.class.getName());
	private RepositorioTarea repositorioTarea;
	private RepositorioLista repositorioLista;

	public ControladorTarea(RepositorioTarea repositorioTarea,
			RepositorioLista repositorioLista) {
		this.repositorioTarea = repositorioTarea;
		this.repositorioLista = repositorioLista;
	}

	public List<Tarea> findAll() {
		return repositorioTarea.findAll();
	}

	public List<Tarea> findAllTarea(String filterText) {
		if (filterText == null || filterText.isEmpty()) {
			return repositorioTarea.findAll();
		} else {
			return repositorioTarea.searchByName(filterText);
		}
	}
	
    public List<Tarea> findAllLista(ListaTareas lista) {
		if (lista == null) {
			return repositorioTarea.findAll();
		} 
		else {
			return repositorioTarea.searchByList(lista);
		}
	}

	public long count() {
		return repositorioTarea.count();
	}

	public void delete(Tarea tarea) {
		repositorioTarea.delete(tarea);
	}

	public void save(Tarea tarea) {
		if (tarea == null) {
			LOGGER.log(Level.SEVERE, "Tarea is null. Are you sure you have connected your form to the application?");
			return;
		}
		repositorioTarea.save(tarea);
	}
}
