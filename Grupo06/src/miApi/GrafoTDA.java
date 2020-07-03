package miApi;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface GrafoTDA {
	/**
	 * Inicializa el grafo
	 */
	void inicializarGrafo();
	/**
	 * Agrega un nuevo vertice v al grafo
	 * Precondicion: Debe estar inicializado y no existir el vertice
	 */
	void agregarVertice(int v);
	/**
	 * Agrega una arista al grafo entre los vertices v1 y v2 con el peso dado
	 * Precondicion: Debe estar inicializado, no debe existir la arista entre los vertices v1 y v2 y existir ambos vertices
	 */
    void agregarArista(int v1, int v2, int p);
    /**
     * Elimina la arista entre los vertices v1 y v2
     * Precondicion: Debe estar inicializado, debe existir la arista entre los vertices v1 y v2 y ambos vertices deben existir.
     */
    void eliminarArista(int v1, int v2);
    /**
     * Elimina el vertice v
     * Precondicion: Debe estar inicializado y existir el vertice
     */
    void eliminarVertice(int v);
    /**
     * Devuelve el conjunto de vertices de un grafo
     * Precondicion: Debe estar inicializado
     */
    ConjuntoTDA vertices();
    /**
     * Indica si el grafo contiene una arista entre los vertices v1 y v2
     * Precondicion: Debe estar inicializado y existir ambos vertices
     */
    boolean existeArista(int v1, int v2);
    /**
     * Devuelve el peso de la arista entre los vertices v1 y v2
     * Precondicion: Debe estar inicializado, debe existir arista entre los vertices v1 y v2
     */
    int pesoArista(int v1, int v2);
}
