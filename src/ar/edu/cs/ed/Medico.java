/**
 * Esta clase contiene atributos escenciales de un médico.
 * 
 * **/
package ar.edu.cs.ed;

public class Medico {
    private String nombre;
    private Especialidad especialidad;
    private boolean disponible;  // nuevo atributo

    public Medico(String nombre, Especialidad especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponible = true; // por defecto está libre
    }

	public String getNombre() {
        return nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void ocupar() {
        disponible = false;
    }

    public void liberar() {
        disponible = true;
    }

    @Override
    public String toString() {
        return nombre + " (" + especialidad + ") - " + (disponible ? "Disponible" : "Ocupado");
    }

}
