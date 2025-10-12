package test.ar.edu.cs.ed;


import ar.edu.cs.ed.*;
import ar.edu.uns.cs.ed.tdas.excepciones.MedicoException;
import ar.edu.uns.cs.ed.tdas.excepciones.PacientesException;
import ar.edu.uns.cs.ed.tdas.tdalista.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Tests para la clase Hospital usando JUnit 4
 */
@RunWith(JUnit4.class)
public class HospitalTest {

    private Hospital2 hospital;
    private Medico medicoCardiologia;
    private Medico medicoNeurologia;
    private Medico medicoTraumatologia;
    private Paciente pacienteCardio;
    private Paciente pacienteNeuro;
    private Paciente pacienteTrauma;
    private Paciente pacienteClinica;

    // Para capturar salidas de System.out
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        hospital = new Hospital2();

        // Crear médicos de prueba como objetos reales
        medicoCardiologia = new Medico("Dr. Pérez", Especialidad.CARDIOLOGIA);
        medicoNeurologia = new Medico("Dra. García", Especialidad.NEUROLOGIA);
        medicoTraumatologia = new Medico("Dr. López", Especialidad.TRAUMATOLOGIA); // Ocupado

        // Crear pacientes de prueba como objetos reales
        pacienteCardio = new Paciente("Juan Pérez", 12345678, LocalDate.of(1980, 5, 15), Especialidad.CARDIOLOGIA);
        pacienteNeuro = new Paciente("María González", 87654321, LocalDate.of(1993, 8, 20), Especialidad.NEUROLOGIA);
        pacienteTrauma = new Paciente("Carlos Rodríguez", 98765432, LocalDate.of(1997, 10, 25), Especialidad.TRAUMATOLOGIA);
        new Paciente("Marta Sanchez", 23456665, LocalDate.of(1977, 1, 25), Especialidad.TRAUMATOLOGIA);
        pacienteClinica = new Paciente("Maria Oz", 25555555, LocalDate.of(1977, 2, 25), Especialidad.CLINICA_MEDICA);
        // Redirigir System.out para pruebas
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ========== TESTS DEL CONSTRUCTOR ==========

    @Test
    public void testConstructorInicializaCorrectamente() {
        Hospital2 nuevoHospital = new Hospital2();
        assertNotNull("El hospital no debe ser null", nuevoHospital);

        try {
            nuevoHospital.agregarMedico(medicoCardiologia);
            nuevoHospital.ingresarPaciente(pacienteCardio);
        } catch (Exception e) {
            fail("No debería lanzar excepción al agregar médicos y pacientes: " + e.getMessage());
        }
    }

    @Test
    public void testConstructorInicializaColasParaTodasLasEspecialidades() {
        Hospital2 nuevoHospital = new Hospital2();

        for (Especialidad esp : Especialidad.values()) {
            Paciente pacientePrueba = new Paciente("Paciente de " + esp, 11111111, LocalDate.of(2000, 1, 1), esp);

            try {
                nuevoHospital.ingresarPaciente(pacientePrueba);
            } catch (Exception e) {
                fail("No debería lanzar excepción para la especialidad " + esp + ": " + e.getMessage());
            }
        }
    }

    // ========== TESTS DE AGREGAR MEDICO ==========

    @Test
    public void testAgregarMedicoCorrectamente() {
        boolean res=false;
        try {
            res = hospital.agregarMedico(medicoCardiologia);
        } catch (Exception e) {
            fail("No debería lanzar excepción al agregar médico: " + e.getMessage());
        }

        
        assertTrue("El médico no pudo agregarse",res);
    }

    @Test
    public void testAgregarMultiplesMedicos() {
    	
    		hospital.vaciarListaMedicos();
    		
    	    // The setup is done in the @Before method, so we just add the doctors here.
    	    hospital.agregarMedico(medicoCardiologia);
    	    hospital.agregarMedico(medicoNeurologia);
    	    hospital.agregarMedico(medicoTraumatologia);

    	    
    	    // Obtiene lista de médicos
    	    PositionList<Medico> medicosEnHospital = hospital.getMedicos();

    	  
    	    // Verificamos la lista
    	    assertFalse("La lista no debe ser vacía.", medicosEnHospital.isEmpty());
    	    assertEquals("La lista debe contener 3 médicos.", 3, medicosEnHospital.size());

    	    // Verificar los médicos agregados
    	    assertEquals("El primer médico debe ser de cardiología", medicoCardiologia, medicosEnHospital.first().element());
    	    assertEquals("El segundo médico debe ser de neurología", medicoNeurologia, medicosEnHospital.next(medicosEnHospital.first()).element());
    	    assertEquals("El tercer médico debe ser de traumatología", medicoTraumatologia, medicosEnHospital.next(medicosEnHospital.next(medicosEnHospital.first())).element());
    	}
    

    @Test
    public void testAgregarMedicoNulo() {
        try {
            hospital.agregarMedico(null);
        } catch (Exception e) {
            fail("No debería lanzar excepción al agregar médico nulo: " + e.getMessage());
        }
    }

    // ========== TESTS DE INGRESAR PACIENTE ==========

    @Test
    public void testIngresarPacienteCorrectamente() {
    	    hospital.ingresarPaciente(pacienteCardio);
    	    
    	    // Obtener solo la lista de espera de Cardiología
    	    PositionList<Paciente> colaCardio = hospital.getPacientesEnEspera(Especialidad.CARDIOLOGIA);

    	    // Verificar que la lista no está vacía y contiene al paciente
    	    assertFalse(colaCardio.isEmpty());
    	    assertEquals(1, colaCardio.size());
    	    assertEquals(pacienteCardio, colaCardio.first().element());
    }
    

    @Test
    public void testIngresarMultiplesPacientesDiferentesEspecialidades() {
        
        assertTrue("El paciente no fue ingresado correctamente",
               hospital.ingresarPaciente(pacienteCardio));
        assertTrue("El paciente de neurología no fue ingresado correctamente",
                hospital.ingresarPaciente(pacienteNeuro));
        assertTrue("El paciente de traumatología no fue ingresado correctamente",
                hospital.ingresarPaciente(pacienteTrauma));
    }

    @Test
    public void testIngresarMultiplesPacientesMismaEspecialidad() {
        Paciente otroPacienteCardio = new Paciente("Ana Silva", 22222222, LocalDate.of(1995, 3, 10), Especialidad.CARDIOLOGIA);

        assertTrue("El paciente no fue ingresado correctamente",
                 hospital.ingresarPaciente(pacienteCardio));
        assertTrue("El segundo paciente no fue ingresado correctamente",
                hospital.ingresarPaciente(otroPacienteCardio));
    }

    // ========== TESTS DE ATENDER PACIENTE ==========

    public void testAtenderPacienteExitosamente() throws PacientesException, MedicoException {
        hospital.agregarMedico(medicoCardiologia);
        hospital.ingresarPaciente(pacienteCardio);

        outContent.reset(); // Limpiar salida anterior

        // El método no debe lanzar excepciones en este caso exitoso
        hospital.atenderPaciente(Especialidad.CARDIOLOGIA);

        String output = outContent.toString();
        assertTrue("Debe mostrar que está atendiendo al paciente",
                output.contains("Atendiendo a " + pacienteCardio.getNombre()));
        assertTrue("Debe mostrar el médico que atiende",
                output.contains("Dr./Dra. " + medicoCardiologia.getNombre()));
        assertTrue("El médico debería estar disponible después de atender",
                medicoCardiologia.isDisponible());
    }

    @Test
    public void testAtenderPacienteSinPacientesEnCola() {
        hospital.agregarMedico(medicoCardiologia);
        // No se ingresa ningún paciente

        // Debe lanzar PacientesException
        assertThrows(PacientesException.class, () -> {
            hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        });
    }

    @Test
    public void testAtenderPacienteSinMedicoDisponible() {
        hospital.agregarMedico(medicoCardiologia);
        hospital.ingresarPaciente(pacienteCardio);

        // Marcar el médico como ocupado
        medicoCardiologia.ocupar();

        // Debe lanzar MedicoException
        assertThrows(MedicoException.class, () -> {
            hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        });
    }

    @Test
    public void testAtenderPacienteEspecialidadSinMedicos() {
        hospital.ingresarPaciente(pacienteCardio);
        // No se agrega ningún médico

        // Debe lanzar MedicoException
        assertThrows(MedicoException.class, () -> {
            hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        });
    }

    @Test
    public void testAtenderPacienteValidaMensajeExcepcion() {
        hospital.agregarMedico(medicoCardiologia);
        // No se ingresa ningún paciente

        // Verifica que la excepción tenga el mensaje correcto
        PacientesException excepcion = assertThrows(PacientesException.class, () -> {
            hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        });
        assertTrue("El mensaje debe indicar que no hay pacientes",
                excepcion.getMessage().contains("No hay pacientes"));
    }
    @Test
    public void testAtenderMultiplesPacientesOrdenFIFO() throws PacientesException, MedicoException {
        Paciente segundoPaciente = new Paciente("Segundo Paciente", 33333333, LocalDate.of(1975, 12, 1), Especialidad.CARDIOLOGIA);
        Paciente pacienteAtendido; 

        hospital.agregarMedico(medicoCardiologia);
        hospital.ingresarPaciente(pacienteCardio);
        hospital.ingresarPaciente(segundoPaciente);

        outContent.reset();

        try {
			pacienteAtendido = hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
            assertTrue("Debe atender al primer paciente primero",
                pacienteAtendido.getNombre().equals(pacienteCardio.getNombre()));
		} catch (PacientesException | MedicoException e) {
			
			fail("Exepción inesperada: " + e.getMessage());
		}

        pacienteAtendido = hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        assertTrue("Debe atender al primer paciente primero",
                pacienteAtendido.getNombre().equals(segundoPaciente.getNombre()));
    }

    // ========== TESTS DE MOSTRAR PACIENTES EN ESPERA ==========

   

    @Test
    public void testPacientesEnEsperaMantieneOrden() {
        Paciente segundoPaciente = new Paciente("Segundo Paciente", 44444444, LocalDate.of(1960, 4, 20), Especialidad.CARDIOLOGIA);

        hospital.ingresarPaciente(pacienteCardio);
        hospital.ingresarPaciente(segundoPaciente);

        PositionList<Paciente> listaPacientes = hospital.getPacientesEnEspera(Especialidad.CARDIOLOGIA);

        assertFalse(listaPacientes.isEmpty());
        assertEquals("La lista de espera debe tener 2 pacientes.", 2, listaPacientes.size());

        // Verificar que el primer paciente en la lista sea el que se agregó primero.
        assertEquals("El primer paciente en la lista debe ser el primero que ingresó.",
                     pacienteCardio, listaPacientes.first().element());

        // Verificar que el segundo paciente en la lista sea el que se agregó después.
        assertEquals("El segundo paciente en la lista debe ser el que ingresó después.",
                     segundoPaciente, listaPacientes.next(listaPacientes.first()).element());
    }

    // ========== TESTS DE MOSTRAR MEDICOS ==========

    @Test
    public void testGetMedicosRetornaListaCompleta() {
    	hospital.vaciarListaMedicos();
    	
 
        // Agregar algunos médicos al hospital.
        hospital.agregarMedico(medicoCardiologia);
        hospital.agregarMedico(medicoNeurologia);
        hospital.agregarMedico(medicoTraumatologia);


        // Obtener la lista de médicos usando el nuevo método.
        PositionList<Medico> medicosEnHospital = hospital.getMedicos();


        // Asegurarse de que la lista no esté vacía y tenga el tamaño correcto.
        assertFalse("La lista de médicos no debe estar vacía.", medicosEnHospital.isEmpty());
        assertEquals("La lista de médicos debe contener 3 médicos.", 3, medicosEnHospital.size());

        // Opcional: Verificar que los médicos específicos están en la lista en el orden correcto.
        // Esto es especialmente útil para probar la propiedad de orden de la lista.
        assertEquals("El primer médico debe ser el de cardiología.", medicoCardiologia, medicosEnHospital.first().element());
        assertEquals("El segundo médico debe ser el de neurología.", medicoNeurologia, medicosEnHospital.next(medicosEnHospital.first()).element());
        assertEquals("El tercer médico debe ser el de traumatología.", medicoTraumatologia, medicosEnHospital.next(medicosEnHospital.next(medicosEnHospital.first())).element());
    }


    @Test
    public void testMostrarMedicosConMedicos() {
        hospital.agregarMedico(medicoCardiologia);
        hospital.agregarMedico(medicoNeurologia);
 
        
        // Mostrar médicos y pacientes restantes 
        PositionList<Medico> listaMed = hospital.getMedicos();
        List<Medico> medicosEsperados = Arrays.asList(medicoCardiologia, medicoNeurologia);
        
        // Verificar tamaño
        assertEquals("Debe haber 2 médicos", 2, listaMed.size());
        
        // Verificar que todos los médicos esperados estén presentes
        for (Medico esperado : medicosEsperados) {
            boolean encontrado = false;
            for (Medico actual : listaMed) {
                if (actual.equals(esperado)) {
                    encontrado = true;
                    break;
                }
            }
            assertTrue("Debe contener al médico: " + esperado, encontrado);
        }
    }

 
    // ========== TESTS DE ESCENARIOS COMPLEJOS ==========

    @Test
    public void testFlujoCompletoDelHospital() throws PacientesException, MedicoException {
        hospital.vaciarListaMedicos();

        // Agregar médicos al hospital.
        hospital.agregarMedico(medicoCardiologia);
        hospital.agregarMedico(medicoNeurologia);

        // Ingresar pacientes.
        hospital.ingresarPaciente(pacienteCardio);
        hospital.ingresarPaciente(pacienteNeuro);

        // Obtener las listas de pacientes en espera para cada especialidad.
        PositionList<Paciente> esperaCardio = hospital.getPacientesEnEspera(Especialidad.CARDIOLOGIA);
        PositionList<Paciente> esperaNeuro = hospital.getPacientesEnEspera(Especialidad.NEUROLOGIA);

        // Asegurarse de que los pacientes estén en las colas correctas.
        assertFalse("La cola de cardiología no debe estar vacía.", esperaCardio.isEmpty());
        assertEquals("El paciente de cardiología debe estar en su cola.", pacienteCardio, esperaCardio.first().element());

        assertFalse("La cola de neurología no debe estar vacía.", esperaNeuro.isEmpty());
        assertEquals("El paciente de neurología debe estar en su cola.", pacienteNeuro, esperaNeuro.first().element());

        // Act (Atender a los pacientes)
        // Llamar a los métodos de atención. No se esperan excepciones en este flujo exitoso.
        hospital.atenderPaciente(Especialidad.CARDIOLOGIA);
        hospital.atenderPaciente(Especialidad.NEUROLOGIA);

        // Assert (Verificar estado final después de la atención)
        // Verificar que las colas de espera ahora estén vacías.
        assertTrue("La cola de cardiología debe estar vacía después de la atención.",
                hospital.getPacientesEnEspera(Especialidad.CARDIOLOGIA).isEmpty());
        assertTrue("La cola de neurología debe estar vacía después de la atención.",
                hospital.getPacientesEnEspera(Especialidad.NEUROLOGIA).isEmpty());

        // Verificar que los médicos estén disponibles después de atender.
        assertTrue("El médico de cardiología debe estar disponible después de atender.",
                medicoCardiologia.isDisponible());
        assertTrue("El médico de neurología debe estar disponible después de atender.",
                medicoNeurologia.isDisponible());
    }

    @Test
    public void testPacienteVuelveAColasSinMedicoDisponible() {
        hospital.vaciarListaMedicos();
        // No se agrega ningún médico. No hay médicos disponibles para esta especialidad.
        
        // Ingresar al paciente en la cola.
        hospital.ingresarPaciente(pacienteTrauma);

        // Obtener la cola antes de intentar atender
        PositionList<Paciente> colaTraumaAntes = hospital.getPacientesEnEspera(Especialidad.TRAUMATOLOGIA);
        assertFalse("La cola de traumatología debe contener al paciente antes de intentar atenderlo.",
                colaTraumaAntes.isEmpty());
        assertEquals("Debe haber un paciente en la cola.", 1, colaTraumaAntes.size());

        // Intentar atender al paciente y capturar la excepción.
        MedicoException excepcion = assertThrows(MedicoException.class, () -> {
            hospital.atenderPaciente(Especialidad.TRAUMATOLOGIA);
        });

        // Verificar que la excepción contiene un mensaje descriptivo.
        assertTrue("La excepción debe indicar que no hay médico disponible.",
                excepcion.getMessage().contains("No hay médico disponible"));

        // Verificar que el paciente sigue en la cola después de la excepción.
        PositionList<Paciente> colaTraumaDespues = hospital.getPacientesEnEspera(Especialidad.TRAUMATOLOGIA);
        assertFalse("La cola de traumatología no debe estar vacía.",
                colaTraumaDespues.isEmpty());
        assertEquals("El paciente debe seguir en la cola.",
                1, colaTraumaDespues.size());
        assertEquals("El paciente correcto debe estar en la cola.",
                pacienteTrauma, colaTraumaDespues.first().element());
    }
}