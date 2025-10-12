package ar.edu.uns.cs.ed.tdas.excepciones;

@SuppressWarnings("serial")
public class MedicoException extends Exception{
	public MedicoException(String mensaje) {
        super(mensaje);
    }

    public MedicoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
