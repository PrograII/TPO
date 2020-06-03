package miApi;

import miApi.ConjuntoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface DiccionarioSimpleTDA {
	
	void InicializarDiccionario();
	
	void Agregar(int clave, int valor);
	
	void Eliminar(int clave);
	
	int Recuperar(int clave);
	
	ConjuntoTDA Claves();
	
}