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
	
    public List<Tarea> findAllLista(String filterText) {
		if (filterText == null || filterText.isEmpty()) {
			return repositorioTarea.findAll();
		} 
		else {
			return repositorioTarea.searchByList(filterText);
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
/*
	@PostConstruct
	public void populateTestData() {
		if (repositorioLista.count() == 0) {
			repositorioLista.saveAll(Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
					.map(ListaTareas::new)
					.collect(Collectors.toList()));
		}

		if (repositorioTarea.count() == 0) {
			Random r = new Random(0);
			List<ListaTareas> listas = repositorioLista.findAll();
			repositorioTarea.saveAll(
					Stream.of("estudiar estudiar_matematicas")
					.map(nombre -> {
						String[] split = nombre.split(" ");
						Tarea tarea = new Tarea();
						tarea.setNombre(split[0]);
						tarea.setDescripcion(split[1]);
						tarea.setPrioridad(split[2]);
						tarea.setDeadline(split[3]);
						tarea.setListaTareas(listas.get(r.nextInt(listas.size())));
						tarea.setEstadoTarea(Tarea.Estado.values()[r.nextInt(Tarea.Estado.values().length)]);

						return tarea;
					}).collect(Collectors.toList()));
		}
	}
	*/
}
