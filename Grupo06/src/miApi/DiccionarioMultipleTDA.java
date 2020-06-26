package miApi;

import miApi.ConjuntoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface DiccionarioMultipleTDA {
	/**
	 *Inicializa el diccionario 
	 */
	void InicializarDiccionario();
	/**
	 *Agrega un valor asociado a una clave. Una clave puede tener varios valores asociados pero ninguno de estos puede repetirse.
	 *Precondicion:El diccionario debe estar inicializado
	 */
	void Agregar(int clave, int valor);
	/**
	 *Se elimina los valores asociados a la clave y la misma al no tener valores asociados, se elimina también.
	 *Si la clave no se encuentra en el diccionario no se hace nada
	 *Precondicion:El diccionario debe estar inicializado
	 */
	void Eliminar(int clave);
	/**
	* Se elimina el valor asociado a la clave. En el caso de que la clave no tenga ningún otro valor asociado, se la elimina también. 
	* Si la clave o el valor no se encuentra, no se hace nada.
	* Precondicion: El diccionario debe estar inicializado
	*/
	void EliminarValor(int clave, int valor);
	/**
	* Devuelve el valor o los valores asociado/s a una clave. En caso que la clave no se encuentre dentro del diccionario, se devuelve conjunto vacío
	* Precondicion: El diccionario debe estar inicializado
	*/
	ConjuntoTDA Recuperar(int clave);
	/**
	*Devuelve un conjunto con todas las claves del diccionario
	* Precondicion: El diccionario debe estar inicializado
	*/
	ConjuntoTDA Claves();

}