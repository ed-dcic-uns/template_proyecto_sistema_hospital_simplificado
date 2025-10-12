package ar.edu.cs.ed;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

import ar.edu.uns.cs.ed.tdas.tdacola.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;


import ar.edu.uns.cs.ed.tdas.tdalista.*;

public class Hospital {

	// TODO:Completar la definición
	//private . . . colasPacientes; 
	// TODO:Completar la definición
	//private . . .  listaMedicos;     

    public Hospital() {
    	//TODO: Completar el código
    }

    /*
     *agregarMedico: Agrega un médico m a la lista de médicos.
     *	Retornar true en caso que se haya agregado exitosamente. 
     *  False en caso contrario.
     * */
    public boolean agregarMedico(Medico m) {
       	//TODO: Completar el código
    }
    
    /*
     * vaciarListaMedicos: Método que permite vaciar la lista de médicos.
     * Retorna true en caso de que se haya vaciado exitosamente
     * */
    public boolean vaciarListaMedicos() {
    	//TODO: Completar el código
    }
    
    /*
     * ingresarPaciente: Ingresa un paciente p a la cola de espera. 
     *  Arroja InvalidKeyException en caso que el paciente sea nulo.
     * */
    public boolean ingresarPaciente(Paciente p) throws InvalidKeyException {
    	//TODO: Completar el código
    }

    /**
     * Atiende al próximo paciente en la cola de espera de una especialidad específica.
     * 
     * Este método extrae el primer paciente de la cola de la especialidad indicada,
     * le asigna un médico disponible de esa especialidad, registra la atención utilizando el método
     * agregarAcción de Paciente donde se debe indicar la especialidad y el nombre del médico. 
     * Luego debe liberarlo de la cola.
     *       
     * Retorna el paciente atendido.
     * 
     * @param especialidad La especialidad médica de la que se atenderá el próximo paciente
     * @throws PacientesException si la cola de pacientes está vacía o no existe
     * @throws MedicoException si no hay médicos disponibles con la especialidad requerida
     */
    public Paciente atenderPaciente(Especialidad especialidad) throws PacientesException, MedicoException{
       	//TODO: Completar el código
    }

     /**
     * Retorna una lista de los pacientes en espera para una especialidad específica.
     * La lista mantiene el orden de llegada (FIFO).
     * @param esp La especialidad de la que se quieren obtener los pacientes.
     * @return Una PositionList de Pacientes en espera para la especialidad dada respetando el orden de llegada.
     */
    public PositionList<Paciente> getPacientesEnEspera(Especialidad esp) {
    	//TODO: Completar el código
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
    	//TODO: Completar el código
    }
}
