package ar.edu.uns.cs.ed.tdas.excepciones;

@SuppressWarnings("serial")
public class PacientesException extends Exception{
	 public PacientesException(String mensaje) {
	        super(mensaje);
	    }

	    public PacientesException(String mensaje, Throwable causa) {
	        super(mensaje, causa);
	    }
}
