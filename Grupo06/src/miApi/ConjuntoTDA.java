package miApi;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/


public interface ConjuntoTDA {
	/**
	 * Inicializa el conjunto
	 */
	void InicializarConjunto ();
	/**
	 * Devuelve Verdadero si el conjunto esta vacio o Falso si no lo esta
	 * Precondicion: El conjunto debe estar inicializado
	 */
	boolean ConjuntoVacio() ;
	/**
	 * Agrega un elemento al conjunto
	 * Precondicion: El conjunto debe estar inicializado
	 */
	void Agregar(int x);
	/**
	 *Devuelve un elemento cualquiera del conjunto
	 *Precondiciones:El conjunto debe estar inicializado
	 * El conjunto no debe estar vacio
	 */
	int Elegir();
	/**
	 * Elimina el elemento pasado por parametro
	 * Precondiciones:El conjunto debe estar inicializado
	 * El conjunto no debe estar vacio
	 */
	void Sacar(int x);
	/**
	 * Devuelve Verdadero si el elemento pasado por parametro se encuentra en el conjunto
	 * Precondicion:El conjunto debe estar inicializado
	 */
	boolean Pertenece(int x);	

}