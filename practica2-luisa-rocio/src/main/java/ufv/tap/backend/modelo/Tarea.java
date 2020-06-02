package ufv.tap.backend.modelo;

import java.time.LocalDate;

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
    private LocalDate deadline = null;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tarea.Estado estadoTarea;
    
    @ManyToOne
    @JoinColumn(name = "listatareas_id")
    private ListaTareas listaTareas;

    
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

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Tarea.Estado getEstadoTarea() {
		return estadoTarea;
	}

	public void setEstadoTarea(Tarea.Estado estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

    public ListaTareas getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(ListaTareas listaTareas) {
		this.listaTareas = listaTareas;
	}


    @Override
    public String toString() {
        return nombre + " " + descripcion;
    }

}
