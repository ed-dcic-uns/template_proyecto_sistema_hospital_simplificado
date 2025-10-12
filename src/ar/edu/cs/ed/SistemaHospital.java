package ar.edu.cs.ed;

import java.time.LocalDate;

import ar.edu.uns.cs.ed.tdas.excepciones.MedicoException;
import ar.edu.uns.cs.ed.tdas.excepciones.PacientesException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class SistemaHospital {

	public static void mostrarPacientesEspera(Hospital hospital) {
        // Mostrar médicos y pacientes restantes 
        PositionList<Medico> listaMed = hospital.getMedicos();
        for (Medico m: listaMed)
        	m.toString();
        
        Especialidad[] esp = Especialidad.values();
        for (Especialidad e: esp) {
        	PositionList<Paciente> pac = hospital.getPacientesEnEspera(e);
        	for (Paciente p: pac) {
        		p.toString();
        	}
        }		
	}
	
	public static void main(String[] args) {
	    Hospital hospital = new Hospital();

	    // Agregar médicos
	    hospital.agregarMedico(new Medico("Ana Perez", Especialidad.PEDIATRIA));
	    hospital.agregarMedico(new Medico("Carlos Gomez", Especialidad.CLINICA_MEDICA));
	    hospital.agregarMedico(new Medico("Lucía Ramirez", Especialidad.DENTISTA));

	    // Ingresar pacientes
	    Paciente p1 = new Paciente("Juana Dominguez", 123, LocalDate.of(2015, 3, 10), Especialidad.PEDIATRIA);
	    Paciente p2 = new Paciente("Maria Torres", 456, LocalDate.of(1985, 7, 22), Especialidad.CLINICA_MEDICA);
	    Paciente p3 = new Paciente("Pedro Sanchez", 789, LocalDate.of(1990, 5, 14), Especialidad.CLINICA_MEDICA);

	    hospital.ingresarPaciente(p1);
	    hospital.ingresarPaciente(p2);
	    hospital.ingresarPaciente(p3);

	    // Mostrar estado
	    System.out.println("=== PACIENTES EN ESPERA (Inicial) ===");
	    mostrarPacientesEspera(hospital);


	    // Atender pacientes según especialidad
	    System.out.println("\n=== ATENDIENDO PACIENTES ===");
	    
	    try {
	        System.out.println("Atendiendo paciente de PEDIATRIA...");
	        hospital.atenderPaciente(Especialidad.PEDIATRIA);
	        System.out.println("✓ Paciente de Pediatría atendido exitosamente.\n");
	    } catch (PacientesException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    } catch (MedicoException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    }

	    try {
	        System.out.println("Atendiendo primer paciente de CLINICA_MEDICA...");
	        hospital.atenderPaciente(Especialidad.CLINICA_MEDICA);
	        System.out.println("✓ Primer paciente de Clínica Médica atendido exitosamente.\n");
	    } catch (PacientesException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    } catch (MedicoException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    }

	    try {
	        System.out.println("Atendiendo segundo paciente de CLINICA_MEDICA...");
	        hospital.atenderPaciente(Especialidad.CLINICA_MEDICA);
	        System.out.println("✓ Segundo paciente de Clínica Médica atendido exitosamente.\n");
	    } catch (PacientesException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    } catch (MedicoException e) {
	        System.out.println("✗ Error: " + e.getMessage() + "\n");
	    }

	    // Mostrar estado final
	    System.out.println("=== PACIENTES EN ESPERA (Final) ===");
	    mostrarPacientesEspera(hospital);

	    // Mostrar historial de un paciente
	    System.out.println("\n=== HISTORIAL DE " + p1.getNombre().toUpperCase() + " ===");
	    p1.mostrarHistorial();
	}

}
