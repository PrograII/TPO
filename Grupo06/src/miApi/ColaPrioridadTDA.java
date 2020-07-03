package miApi;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public interface ColaPrioridadTDA {
	/**
	 * Inicializa la cola
	 */
		void inicializarCola();
		/**
		 * Agrego un elemento a la cola de acuerdo a su prioridad
		 * Precondicion: La cola debe estar inicializada
		 */
		void acolarPrioridad(int x, int prioridad);
		/**
		 * Se elimina el elemento con mayor prioridad de la cola
		 * Precondicion: La cola no debe estar vacía
		 */
		void desacolar();
		/**
		 * Elemento de mayor prioridad ingresado
		 * Precondicion: La cola no debe estar vacia
		 */
		int primero();
		/**
		 * Devuelve la prioridad del elemento con mayor prioridad dentro de la cola
		 * Precondicion: La cola no debe estar vacia
		 */
		int Prioridad();
		/**
		 * Devuelve true o false si la cola esta o no vacia
		 * La cola debe estar inicializada
		 */
		boolean ColaVacia();
}
