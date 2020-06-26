package miApi;

import miApi.ConjuntoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface DiccionarioSimpleTDA {
	/**
	 *Inicializa el diccionario 
	 */
	void InicializarDiccionario();
	/**
	 * Se agrega un valor al diccionario asociado a una clave. 
	 * En caso de que la clave ya se encuentre dentro del diccionario, se reemplaza el valor dejando el del recien ingresado.
	 * Precondicion: El conjunto debe estar inicializado.
	 */
	void Agregar(int clave, int valor);
	/**
	 * Se elimina el valor asociado a la clave y esta clave al no tener ningún valor se la elimina también. 
	 * Si la clave no se encuentra, no se hace nada. 
	 * Precondicion: El diccionario debe estar inicializado.
	 */
	void Eliminar(int clave);
	/**
	 * Devuelve el valor asociado a una clave.
	 * Precondicion: El diccionario debe estar inicializado y la clave debe existir .
	 */
	int Recuperar(int clave);
	/**
	 * Devuelve el conjunto de todas las claves que existen en el diccionario
	 * Precondicion: El diccionario debe estar inicializado
	 */
	ConjuntoTDA Claves();
	
}