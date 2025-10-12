package ar.edu.cs.ed;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.excepciones.PacientesException;

import ar.edu.uns.cs.ed.tdas.excepciones.MedicoException;
import ar.edu.uns.cs.ed.tdas.tdacola.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;


import ar.edu.uns.cs.ed.tdas.tdalista.*;

public class Hospital {

    private Map<Especialidad, Queue<Paciente>> colasPacientes; // colas por especialidad
    private ListaDoblementeEnlazada<Medico> listaMedicos;      // lista de médicos

    public Hospital() {
        colasPacientes = new MapeoConHash<>();
        listaMedicos = new ListaDoblementeEnlazada<>();

        // Inicializar una cola para cada especialidad
        for (Especialidad esp : Especialidad.values()) {
        	
            colasPacientes.put(esp, new ColaConArregloCircular<Paciente>());
        }
    }

    /*
     * */
    public boolean agregarMedico(Medico m) {
        if (m == null) {
            return false;
        }
        listaMedicos.addLast(m);

        
        return true;
    }
    
    public boolean vaciarListaMedicos() {
    	while (!listaMedicos.isEmpty()) {
            try {
            	Position<Medico> primeraPosicion = listaMedicos.first();
                listaMedicos.remove(primeraPosicion);
            } catch (EmptyListException e) {
                // Esto no debería ocurrir, ya que el bucle lo evita.
                System.err.println("Error inesperado: la lista ya estaba vacía.");
            }
        }
        return true;
    }
    
    public boolean ingresarPaciente(Paciente p) throws InvalidKeyException  {
    	Queue<Paciente> colaAux = null;
    	colaAux =  colasPacientes.get(p.getEspecialidadSolicitada());
    	if (p == null || colaAux==null) {
    		throw new InvalidKeyException("El paciente es incorrecto ");
        }
        colasPacientes.get(p.getEspecialidadSolicitada()).enqueue(p);
        return true;
    }

    public Paciente atenderPaciente(Especialidad especialidad)throws PacientesException, MedicoException {
        Queue<Paciente> cola = colasPacientes.get(especialidad);

        if (cola == null || cola.isEmpty()) {
            throw new PacientesException("No hay pacientes en la cola de " + especialidad);
        }

        Paciente paciente = cola.front();
        Medico medicoAsignado = null;

        // Buscar médico con la especialidad solicitada que esté disponible
        for (Medico m : listaMedicos) {
            if (m.getEspecialidad() == especialidad && m.isDisponible()) {
                medicoAsignado = m;
                break;
            }
        }

        if (medicoAsignado == null) {
            throw new MedicoException("No hay médico disponible para la especialidad: " + especialidad);
        }

        // Marcar médico como ocupado
        medicoAsignado.ocupar();

        paciente.agregarAccion("Atendido por " + medicoAsignado.getEspecialidad() +
                " - Dr./Dra. " + medicoAsignado.getNombre());

        // Simulación: luego de atender, el médico queda libre otra vez
        medicoAsignado.liberar();

        cola.dequeue();

        return paciente;
    }

     /**
     * Retorna una lista de los pacientes en espera para una especialidad específica.
     * La lista mantiene el orden de llegada (FIFO).
     * @param esp La especialidad de la que se quieren obtener los pacientes.
     * @return Una PositionList de Pacientes en espera para la especialidad dada respetando el orden de llegada.
     */
    public PositionList<Paciente> getPacientesEnEspera(Especialidad esp) {
        PositionList<Paciente> pacientesEnEspera = new ListaDoblementeEnlazada<>();
        Queue<Paciente> cola = colasPacientes.get(esp);

        if (cola != null && !cola.isEmpty()) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                try {
                    Paciente p = cola.dequeue();
                    pacientesEnEspera.addLast(p);
                    cola.enqueue(p); // Se vuelve a encolar para no perderlo
                } catch (EmptyQueueException e) {
                    // Esto no debería ocurrir
                }
            }
        }
        return pacientesEnEspera;
    }
    
    /**
     * Retorna una lista de todos los médicos registrados en el hospital.
     * La lista mantiene el orden en que los médicos fueron agregados.
     * @return Una PositionList de Medico.
     */
    public PositionList<Medico> getMedicos() {
        // Devuelve la lista de médicos directamente.
        // Como la clase interna ListaDoblementeEnlazada ya implementa
        // la interfaz PositionList, no es necesario crear una nueva lista.
        return listaMedicos;
    }
}