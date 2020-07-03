package misImplementaciones;

import miApi.ColaPrioridadTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public class ColaPrioridad implements ColaPrioridadTDA {

	class NodoPrioridad{
		int info;
		int prioridad;
		NodoPrioridad sig;
		}
	NodoPrioridad aux = new NodoPrioridad(); 
	NodoPrioridad MayorPrioridad;
	@Override
	public void inicializarCola() {
		MayorPrioridad = null;
	}

	@Override
	public void acolarPrioridad(int x, int p) {
		NodoPrioridad nuevo = new NodoPrioridad ();
		nuevo.info = x;
		nuevo.prioridad = p;
		
		if (MayorPrioridad == null || p < MayorPrioridad.prioridad) { //Se cambia el signo para poder ordenar de menor a mayor
			nuevo.sig = MayorPrioridad;
			MayorPrioridad = nuevo;
		}
		else{
			aux = MayorPrioridad;
			
			while(aux.sig!= null && aux.sig.prioridad <= p) {// Se cambia el signo para poder ordenar de menor a mayor
				aux = aux.sig;
			}
			nuevo.sig = aux.sig;
			aux.sig = nuevo;
		}
			
	}

	@Override
	public void desacolar() {
		MayorPrioridad = MayorPrioridad.sig;
	}

	@Override
	public int primero() {
		return MayorPrioridad.info;
	}

	@Override
	public boolean ColaVacia() {
		return (MayorPrioridad == null);
	}

	@Override
	public int Prioridad() {
		return MayorPrioridad.prioridad;
	}


}
