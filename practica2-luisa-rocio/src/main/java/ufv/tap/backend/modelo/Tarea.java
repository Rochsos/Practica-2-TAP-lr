package ufv.tap.backend.modelo;

import java.sql.Date;

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

    @NotNull
    @NotEmpty
    private String nombre = "";

    @NotNull
    @NotEmpty
    private String descripcion = "";
    
    @NotNull
    @NotEmpty
    private String prioridad = "";
    
    @NotNull
    @NotEmpty
    private Date deadline = null;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Tarea.Estado estadoTarea;
    
    @ManyToOne
    @JoinColumn(name = "IdListaTareas")
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

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
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
