package misImplementaciones;

import miApi.ConjuntoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public class ConjuntoLD implements ConjuntoTDA {
	
	public class Nodo {
		int info;
		Nodo sig;
	}
	
	Nodo c;
	
	//private static final int MAX = 10;
	
	@Override
	public void InicializarConjunto() {
		c = null;
	}
	
	@Override
	public boolean ConjuntoVacio() {
		return (c == null);
	}
	
	@Override
	public void Agregar(int x) {
		
		if(!this.Pertenece(x)){
			Nodo nuevo = new Nodo();
			nuevo.info = x;
			nuevo.sig = c;	
			c = nuevo;			
		}
	}
	
	@Override
	public int Elegir() {
		return c.info ;
	}

	@Override
	public void Sacar(int x) {
		
		if (c != null) {
			
			//si es el primer elemento de la lista
			if (c.info == x) {
				c = c.sig;
			} else {
				Nodo aux = c;
				while (aux.sig != null && aux.sig.info != x)
					aux = aux.sig;
				
				if(aux.sig != null)
					aux.sig = aux.sig.sig;
			}
		}
	}
	
	@Override
	public boolean Pertenece(int x) {
		Nodo aux = c;
		while ((aux != null) && (aux.info != x)) {
			aux = aux.sig;
		}
		return (aux != null);
	}

}