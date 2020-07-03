package misImplementaciones;

import miApi.ConjuntoTDA;
import miApi.GrafoTDA;

/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public class Grafo implements GrafoTDA {
	class VerticeNodo{
		int et;
		AristaNodo aristaInicio;
		VerticeNodo verticeSig;
	}
	class AristaNodo{
		int peso;
		VerticeNodo destino;
		AristaNodo aristaSig;
	}
	
	VerticeNodo verticeInicio;
	public void inicializarGrafo() {
		// TODO Auto-generated method stub
		verticeInicio = null;
	}


	public void agregarVertice(int v) {
		// TODO Auto-generated method stub
		VerticeNodo nuevo = new VerticeNodo (); // crear
		
		// guardo los datos del nodo
		nuevo.et = v;
		nuevo.aristaInicio = null;
		
		// vinculación del nodo. Como no hay orden
		// insertar al inicio.
		nuevo.verticeSig = verticeInicio;
		verticeInicio = nuevo;
	}


	public void agregarArista(int v1, int v2, int p) {
		// TODO Auto-generated method stub
		VerticeNodo origen = verticeInicio;
		while (origen.et != v1)
			origen = origen.verticeSig;
		
		VerticeNodo refDestino = verticeInicio;
		while (refDestino.et != v2)
			refDestino = refDestino.verticeSig;
		
		AristaNodo nuevo = new AristaNodo(); // crear
		
		// completar los datos
		nuevo.peso = p;
		nuevo.destino = refDestino;
		
		// vincular a la lista (se realiza a traves del sig)
		nuevo.aristaSig = origen.aristaInicio;
		origen.aristaInicio = nuevo;
	}

	@Override
	public void eliminarArista(int v1, int v2) {
		// TODO Auto-generated method stub
		VerticeNodo origen = verticeInicio;
		while(origen.et!=v1) 
			origen = origen.verticeSig;
		
		VerticeNodo destino = verticeInicio;
		while(destino.et!=v2)
			destino = destino.verticeSig;
		
		AristaNodo anterior = null;
		AristaNodo listaAristas = origen.aristaInicio;
		while(listaAristas != null && listaAristas.destino != destino) {
			anterior = listaAristas;
			listaAristas = listaAristas.aristaSig;
		}
		if(listaAristas!=null) {
			
			if(anterior!=null) {//No es el primer nodo
			anterior.aristaSig = listaAristas.aristaSig;
			}
			else {//elimino primer nodo
				origen.aristaInicio = origen.aristaInicio.aristaSig;
			}
		}
	}

	
	public void eliminarVertice(int v) {
		// TODO Auto-generated method stub
		VerticeNodo anterior = null;
		VerticeNodo actual = verticeInicio;
		VerticeNodo lista = verticeInicio;
		while(actual!=null && actual.et!=v) {//Busco referencia del Nodo(vertice) a eliminar
			anterior = actual;
			actual = actual.verticeSig;
		}
		while(lista!=null) {//Busco aristas entrantes al vertice a analizar(comparo el actual con todo el resto)
			if(lista.et!=actual.et) {
				if(existeArista(lista.et, actual.et)) {
					eliminarArista(lista.et, actual.et);
				}
			}
			lista = lista.verticeSig;
		}
		if (anterior!=null) {//Si el vertice a eliminar no es el primero
			anterior.verticeSig = actual.verticeSig;
		}
		else {//Si es el primero
			verticeInicio = verticeInicio.verticeSig;
		}
	}

	
	public ConjuntoTDA vertices() {
		// TODO Auto-generated method stub
		ConjuntoTDA resultado = new ConjuntoLD();
		resultado.InicializarConjunto();
		VerticeNodo actual = verticeInicio;
		while (actual!=null) {
			resultado.Agregar(actual.et);
			actual= actual.verticeSig; // avanza sobre la lista
		}
		
		return resultado;
	}


	public boolean existeArista(int v1, int v2) {
		// TODO Auto-generated method stub
		VerticeNodo origen = verticeInicio;
		while(origen.et!=v1) 
			origen = origen.verticeSig;
		
		VerticeNodo destino = verticeInicio;
		while(destino.et!=v2)
			destino = destino.verticeSig;
		
		AristaNodo lista = origen.aristaInicio;
		while(lista!=null && lista.destino!=destino) {
			lista = lista.aristaSig;
		}
		return lista!=null;
	}

	
	public int pesoArista(int v1, int v2) {
		AristaNodo listaAristas = null;
		if(existeArista(v1, v2)) {
			VerticeNodo origen = verticeInicio;
			while(origen.et!=v1) 
				origen = origen.verticeSig;
			
			VerticeNodo destino = verticeInicio;
			while(destino.et!=v2)
				destino = destino.verticeSig;
			
			listaAristas = origen.aristaInicio;
			while(listaAristas != null && listaAristas.destino != destino) {
				listaAristas = listaAristas.aristaSig;
			}
		}
		return listaAristas.peso;
	}
}
