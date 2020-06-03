package miApi;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/


public interface ConjuntoTDA {
	
	void InicializarConjunto ();
	
	boolean ConjuntoVacio() ;//inicializado
	
	void Agregar(int x);//inicializado

	int Elegir();//inicializado y no esté vacío
	
	void Sacar(int x);//inicializado
	
	boolean Pertenece(int x);//inicializado		

}