package ufv.tap.backend.modelo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Tarea extends EntidadAbstracta{


	public enum Estado {
        Completada, NoCompletada
    }
	
	public enum Prioridad {
        Alta, Media, Baja
    }

    @NotNull
    @NotEmpty
    private String nombre = "";

    @NotNull
    @NotEmpty
    private String descripcion = "";
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tarea.Prioridad prioridad;
    
    @NotNull
    @NotEmpty
    private String deadline = "";
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tarea.Estado estadoTarea;
    
    @ManyToOne
    @JoinColumn(name = "listatareas_id")
    private ListaTareas listatareas;

    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tarea.Prioridad getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Tarea.Prioridad prioridad) {
		this.prioridad = prioridad;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Tarea.Estado getEstadoTarea() {
		return estadoTarea;
	}

	public void setEstadoTarea(Tarea.Estado estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

    public ListaTareas getListaTareas() {
		return listatareas;
	}

	public void setListaTareas(ListaTareas listatareas) {
		this.listatareas = listatareas;
	}


    @Override
    public String toString() {
        return nombre + " " + descripcion;
    }

}
