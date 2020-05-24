package miApi;


public interface ConjuntoTDA {
	
	void InicializarConjunto ();
	
	boolean ConjuntoVacio() ;//inicializado
	
	void Agregar(int x);//inicializado

	int Elegir();//inicializado y no esté vacío
	
	void Sacar(int x);//inicializado
	
	boolean Pertenece(int x);//inicializado		

}