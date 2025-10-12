/**
 * Clase Paciente:  Contiene atributos como nombre, DNI, fecha de nacimiento, 
 * la especialidad solicitada y una estructura para su historial clínico realizado sobre esta consulta médica.
 * **/
package ar.edu.cs.ed;
import java.util.*;
import java.time.*;


public class Paciente {
    private String nombre;
    private int dni;
    private LocalDate fechaNacimiento;
    private Especialidad especialidadSolicitada;
    private Stack<String> historial;  // pila para registrar acciones

    public Paciente(String nombre, int dni, LocalDate fechaNacimiento, Especialidad cardiologia) {
        this.nombre = nombre;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.especialidadSolicitada = cardiologia;
        this.historial = new Stack<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getDni() {
        return dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Especialidad getEspecialidadSolicitada() {
        return especialidadSolicitada;
    }

    // Calcula edad en años según la fecha actual
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public void agregarAccion(String accion) {
        historial.push(accion);
    }

    public void mostrarHistorial() {
        System.out.println("Historial del paciente " + nombre + ":");
        if (historial.isEmpty()) {
            System.out.println("(sin acciones registradas)");
        } else {
            for (String accion : historial) {
                System.out.println("- " + accion);
            }
        }
    }

    @Override
    public String toString() {
        return nombre + " (DNI: " + dni +
               ", Nacimiento: " + fechaNacimiento +
               ", Edad: " + getEdad() +
               ", Especialidad solicitada: " + especialidadSolicitada + ")";
    }

}
