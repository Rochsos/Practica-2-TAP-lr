package ufv.tap.backend.modelo;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario extends EntidadAbstracta{


    @NotNull
    @NotEmpty
    private String nombre = "";

    @NotNull
    @NotEmpty
    private String contraseña = "";

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

    @Override
    public String toString() {
        return nombre;
    }
}
