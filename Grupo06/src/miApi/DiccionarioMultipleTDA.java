package miApi;

import miApi.ConjuntoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface DiccionarioMultipleTDA {
	
	void InicializarDiccionario();
	
	void Agregar(int clave, int valor);
	
	void Eliminar(int clave);
	
	void EliminarValor(int clave, int valor);
	
	ConjuntoTDA Recuperar(int clave);
	
	ConjuntoTDA Claves();

}