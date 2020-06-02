package ufv.tap.backend.modelo;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class ListaTareas extends EntidadAbstracta {

	private String nombre;

	@OneToMany(mappedBy = "listaTareas", fetch = FetchType.EAGER)
	private List<Tarea> tareas = new LinkedList<>();

	public ListaTareas() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public ListaTareas(String nombre) {
		setNombre(nombre);
	}
}
