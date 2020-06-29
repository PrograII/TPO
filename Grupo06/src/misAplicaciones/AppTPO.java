package misAplicaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import MisMetodos.Metodos;
import miApi.ConjuntoTDA;
import misImplementaciones.ConjuntoLD;
import miApi.DiccionarioMultipleTDA;
import miApi.DiccionarioSimpleTDA;
import misImplementaciones.DicMultipleL;


/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public class AppTPO {

	public static void main(String[] args) {		

		DiccionarioMultipleTDA diccM = new DicMultipleL();
		ArrayList<Materias> meteriaList = new ArrayList<Materias>();
		diccM.InicializarDiccionario();		
		meteriaList = leeyCargaDicMul(diccM);		
		mostrar(diccM);		
		
		contidadPorCarrera(diccM);
		porcentajeMateriasInfPorCarrera(diccM);
		
		porcentajeMateriasCienciasSPorCarrera(diccM);
		porcentajeMateriasCienciasBPorCarrera(diccM);
		
		contidadMateriasOptativas(diccM);	
		materiasComunes(diccM);
		
		compararCarreras(diccM);
		materiasUnicaCarrera(diccM);
		
		matrelacionadas20(diccM);
		matnocomunes(diccM);

	}

	
	public static class Materias 
	{
		public Integer nroCarrera;
		public Integer codMateria;
		public String materiaPresedente;
		public String carrera;
		public String materias;

		public Materias(Integer nroCarrera, Integer codMateria, String materiaPresedente, String carrera, String materias) {

			this.nroCarrera = nroCarrera;
			this.codMateria = codMateria;
			this.materiaPresedente = materiaPresedente;
			this.carrera = carrera;
			this.materias = materias;

		}		    


	}

	

	/**
	@Tarea : 1. Cargar los datos de las carreras en un diccionario múltiple, donde la clave corresponde al
				código de carrera y los valores son códigos de las materias para esa carrera.
	@Parámetros: null
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/

	public static ArrayList<Materias> leeyCargaDicMul(DiccionarioMultipleTDA var) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<Materias> meteriaList = new ArrayList<Materias>();

		System.out.println("1. Cargar los datos de las carreras en un diccionario múltiple, donde la clave corresponde al\r\n" + 
				"código de carrera y los valores son códigos de las materias para esa carrera.");
		System.out.println("Clave(Nro Carrera) Valor(Cod Materias)");
		
		try {
			archivo = new File ("Materias.csv");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			var.InicializarDiccionario();
			br.readLine();
			String linea = br.readLine();

			while(linea != null) {
				String[] lis = linea.split(";");

				meteriaList.add(new Materias(Integer.parseInt(lis[0]),Integer.parseInt(lis[1]),lis[2],lis[3],lis[4]));

				var.Agregar(Integer.parseInt(lis[0]),Integer.parseInt(lis[1]));
				linea = br.readLine();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return meteriaList;
	}

	
	/**
	@Tarea : 2A. Cantidad de materias de cada una de las carreras
	@Parámetros DiccionarioMultipleTDA
	@Devuelve El numero de carrera junto a la cantidad de materias
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo Cuadratico(debido a que hay un ciclo while dentro de otro ciclo while)
	 **/
	public static void contidadPorCarrera( DiccionarioMultipleTDA var	) {  
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.println("2a. Cantidad de materias de cada una de las carreras");
			ConjuntoTDA keys = var.Claves();
			int aux = 0; 
	    	int cont = 0;
	    	int aux2=0;
	    	ConjuntoTDA valores;
		    while(!keys.ConjuntoVacio()) {
		    	aux = keys.Elegir();
		    	cont = 0;
		    	valores = var.Recuperar(aux);
		    	while(!valores.ConjuntoVacio()) {
		    		aux2 = valores.Elegir();
		    		
		    		if(aux2 > 9) {
		    			cont += 1;
		    		}
		    		
		    		valores.Sacar(aux2);
		    	}
		    	keys.Sacar(aux);
		    	System.out.println("La carrera numero " + aux + " tiene " + cont + " materias");
		    } 
		    
		    System.out.println("------------------------------------------------------------------------------------------------------------------");
	}

	
	/**
	@Tarea : 2B. Porcentaje de materias de informática en cada una de las carreras (materias que
				comienzan con código 34xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de informatica
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo Cuadratico(porque hay un while en la funcion porcentajeMateriasInfPorCarrera y en el metodo porcentajeMaterias)
	 **/
	public static void porcentajeMateriasInfPorCarrera( DiccionarioMultipleTDA var) {  
		
			System.out.println("2.b Porcentaje de materias de informática en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 34xxx)");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	
		    	aux = keys.Elegir();
		    	System.out.println("La carrera numero " + aux + " tiene " + m.porcentajeMaterias(var,"34",aux) + "% de materias de informatica");//Se llama al metodo porcentajeMaterias el cual devuelve 
		    	//el porcentaje de materias que empiezan con el numero "34"
		    	keys.Sacar(aux);
		    		    	
		    }
		    System.out.println("------------------------------------------------------------------------------------------------------------------");

	}

	
	/**
	@Tarea : 2C. Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que
				comienzan con código 31xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de ciencias basicas
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo Cuadratico(porque hay un while en la funcion porcentajeMateriasInfPorCarrera y en el metodo porcentajeMaterias)
	 **/
	public static void porcentajeMateriasCienciasSPorCarrera( DiccionarioMultipleTDA var	) {  
			System.out.println("2.c Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 31xxx)");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	
		    	aux = keys.Elegir();
		    	System.out.println("La carrera numero " + aux + " tiene " + m.porcentajeMaterias(var,"31",aux) + "% de materias de ciencias basicas");//Se llama al metodo porcentajeMaterias 
		    	//el cual devuelve el porcentaje de materias que empiezan con el numero "31"
		    	keys.Sacar(aux);
		    		    	
		    }
		    System.out.println("------------------------------------------------------------------------------------------------------------------");
		}

	
	/**
	@Tarea : 2D. Porcentaje de materias de ciencias sociales en cada una de las carreras (materias
			comienzan con códigos 2xxxx y 33xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de ciencias sociales
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo Cuadratico(porque hay un while en la funcion porcentajeMateriasInfPorCarrera y en el metodo porcentajeMaterias)
	 **/
	public static void porcentajeMateriasCienciasBPorCarrera( DiccionarioMultipleTDA var	) { 
		System.out.println("2.d Porcentaje de materias de ciencias sociales en cada una de las carreras (materias\r\n" + "comienzan con códigos 2xxxx y 33xxx)");
		ConjuntoTDA keys = var.Claves();
		int aux=0;
		while(!keys.ConjuntoVacio()) {
			aux = keys.Elegir();
			Metodos m = new Metodos();
			System.out.println("La carrera numero " + aux + " tiene " + (m.porcentajeMaterias(var,"2",aux) + m.porcentajeMaterias(var,"33",aux)) + "% de materias de ciencias sociales");//Se llama al metodo porcentajeMaterias el cual devuelve 
			//el porcentaje de materias que empiezan con el numero "33" o el numero "2"
			keys.Sacar(aux);
		    		    	
		}
	    System.out.println("------------------------------------------------------------------------------------------------------------------");
	}

	
	/**
	@Tarea : 2E. Cantidad de materias optativas de cada una de las carreras			 
	@Parámetros  DiccionarioMultipleTDA
	@Devuelve Resultados en pantalla
	@Precondición  Tener los valores en el diccionario multiple
	@Postcondición -
	@Costo: Cuadratico(porque hay un ciclo while dentro de otro ciclo while)
	 **/

	public static void contidadMateriasOptativas( DiccionarioMultipleTDA dic) {  

	    
			System.out.println("2.e Cantidad de materias optativas de cada una de las carreras:");					
			ConjuntoTDA codCarre = dic.Claves();
			ConjuntoTDA codigoMat = new ConjuntoLD();
			Metodos m = new Metodos();
			while (!codCarre.ConjuntoVacio()) {
				int codCarreAux = codCarre.Elegir();
				codigoMat = dic.Recuperar(codCarreAux);
				int codigoMatAux = 0;
				while (!codigoMat.ConjuntoVacio()) {
					codigoMatAux = codigoMat.Elegir();
					if (codigoMatAux<100 ) { //solo las optativas
						System.out.println("La carrera: " + m.NombreCarrera(codCarreAux) + ", tiene " +codigoMatAux + " materias optativas.");						
					}
					codigoMat.Sacar(codigoMatAux);
				}

				codCarre.Sacar(codCarreAux);
				if (codigoMatAux > 100) {
					System.out.println("La carrera: " + m.NombreCarrera(codCarreAux) + ", tiene 0 materias optativas.");
					
				}
			}
			
			//chequear resultados
			//mostrar(diccS);
			 System.out.println("------------------------------------------------------------------------------------------------------------------");
		
		
	}


	/**
	@Tarea : 2F. Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no incluir materias optativas)
	@Parámetros  DiccionarioMultipleTDA
	@Devuelve Resultados en pantalla
	@Precondición Tener los valores en el diccionario multiple  
	@Postcondición -
	@Costo Cuadratico(porque hay un ciclo while dentro de otro ciclo while)
	 **/
	public static void materiasComunes( DiccionarioMultipleTDA dic) {
	     
			
			DiccionarioMultipleTDA diccMFinal = new DicMultipleL();
			diccMFinal.InicializarDiccionario()	;

			System.out.println("2.f Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
					"incluir materias optativas)");
			
			Metodos m = new Metodos();
			ConjuntoTDA codCarre = dic.Claves();			 			
			ConjuntoTDA codsMater ;
			DiccionarioMultipleTDA diccM = new DicMultipleL();
			diccM.InicializarDiccionario();		
			int varcodsMater =0;
			
			while(!codCarre.ConjuntoVacio()){
				int codCarreAux = codCarre.Elegir();
				codsMater = dic.Recuperar(codCarreAux);				
				while (!codsMater.ConjuntoVacio()) {
					varcodsMater = codsMater.Elegir(); 
					if (varcodsMater > 100) { //para no incluir las optativas
					diccM.Agregar(varcodsMater, codCarreAux);
					}
					codsMater.Sacar(varcodsMater);
				}
				codCarre.Sacar(codCarre.Elegir());
				}
			
			
			int clave;
			int conta = 0;
			ConjuntoTDA claves = diccM.Claves();
			ConjuntoTDA valores;
			if(!claves.ConjuntoVacio()){
				while(!claves.ConjuntoVacio()){
					clave = claves.Elegir();
					valores = diccM.Recuperar(clave);
					if (!valores.ConjuntoVacio()){
						conta = 0;
						while(!valores.ConjuntoVacio()){
							conta = conta +1;					
							valores.Sacar(valores.Elegir());							
							}
					}
					if (conta == 4 ) { //si estan en todas las carreras
						System.out.println("La materia "+ m.NombreMater(clave) + "(Codigo: " + clave  +  "), es comun a todas las carreras.");
					}					
					claves.Sacar(clave);
					
				}

			}	
			 System.out.println("------------------------------------------------------------------------------------------------------------------");	

	}

	

	/**
	@Tarea : 2G. Carrera que comparte mas del 80% de las materias de otra carrera
	@Parámetros DiccionarioMultipleTDA
	@Devuelve La carrera que comparte mas del 80% de materias de otra carrera
	@Precondición Diccionario multiple cargado e inicializado
	@Postcondición -
	@Costo Lineal (debido a que en los metodos utilizados hay un solo while)
	 **/
	public static void compararCarreras(DiccionarioMultipleTDA dic) {
		
		System.out.println("2.g Carrera/s que incluyan más del 80% de las materias de otra carrera, indicando las\r\n" + 
				"carreras relacionadas.");
        Metodos m = new Metodos();
		int c = m.contadorConjunto(dic.Claves());
		int i, j;
		boolean cumple;
		int[] vectorCarreras = m.carrerasAVector(dic.Claves());
		for(i=0; i<c; i++) {
			for(j=1; j<c; j++) {
				if(j>i) {
					cumple = m.cumplePorcentaje(dic.Recuperar(vectorCarreras[i]), dic.Recuperar(vectorCarreras[j]));
					if(cumple) {
						System.out.println("La carrera " + m.NombreCarrera(vectorCarreras[i]) + " comparte mas del 80% de las materias de la carrera " + m.NombreCarrera(vectorCarreras[j]));
					}
					cumple = m.cumplePorcentaje(dic.Recuperar(vectorCarreras[j]), dic.Recuperar(vectorCarreras[i]));
					if(cumple) {
						System.out.println("La carrera " + m.NombreCarrera(vectorCarreras[j]) + " comparte mas del 80% de las materias de la carrera " + m.NombreCarrera(vectorCarreras[i]));
					}
				}
			}
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------");
	}

	/**
	@Tarea :   2H. Materias que no comparten ninguna carrera
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Materias que no se comparten con ninguna otra carrera
	@Precondición El diccionario multiple debe estar inicializado y cargado
	@Postcondición-
	@Costo Cubico(debido a que hay dos ciclos while dentro de un ciclo while)
	 **/
	public static void materiasUnicaCarrera(DiccionarioMultipleTDA dic) {
		System.out.println("2.h Materias de cada carrera que no comparten con ninguna otra carrera, ordenadas por código de materia, indicando la carrera a la que pertenecen.");
        Metodos m = new Metodos();
		boolean pertenece;
        ConjuntoTDA materias = new ConjuntoLD();
        ConjuntoTDA carreras = new ConjuntoLD();
        ConjuntoTDA carreras2 = new ConjuntoLD();
        carreras.InicializarConjunto();
        carreras = dic.Claves();
        while(!carreras.ConjuntoVacio()) {//1
            materias.InicializarConjunto();
            materias = dic.Recuperar(carreras.Elegir());
            while(!materias.ConjuntoVacio()) {//2
                pertenece = false;
                carreras2.InicializarConjunto();
                carreras2 = dic.Claves();
                while(!carreras2.ConjuntoVacio()) {//3
                    if (carreras.Elegir()!=carreras2.Elegir() && pertenece==false) {
                        pertenece = dic.Recuperar(carreras2.Elegir()).Pertenece(materias.Elegir());
                    }
                    carreras2.Sacar(carreras2.Elegir());
                }
                if(!pertenece) {
                    System.out.println("La materia " + m.NombreMater(materias.Elegir()) + " pertenece unicamente a la carrera "  + m.NombreCarrera(carreras.Elegir()));
                }
                materias.Sacar(materias.Elegir());
            }
            carreras.Sacar(carreras.Elegir());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
	/**
	@Tarea: 2I. Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las
			carreras relacionadas
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Las carreras con el 20% de las materias de otra carrera
	@Precondición El diccionario multiple debe estar inicializado y cargado
	@Postcondición-
	@Costo Cubico(debido a que hay dos ciclos while dentro de un ciclo while)
	 **/
	public static void matrelacionadas20(DiccionarioMultipleTDA var){
		int matcoinc = 0;
		int mattotal = 0;
		int c = 0;
		ConjuntoTDA claves1 = var.Claves();
		ConjuntoTDA claves2 = var.Claves();		
		ConjuntoTDA clavesrep = new ConjuntoLD();
		clavesrep.InicializarConjunto();
		ConjuntoTDA matrel = new ConjuntoLD();
		matrel.InicializarConjunto();
		ConjuntoTDA valores1;
		ConjuntoTDA valores2; 
		System.out.println("2-I Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las carreras relacionadas.");
		while(!claves1.ConjuntoVacio()) {//1
			while(!claves2.ConjuntoVacio()){//2
				valores1 = var.Recuperar(claves1.Elegir());
				valores2 = var.Recuperar(claves2.Elegir());
					if (!clavesrep.Pertenece(claves1.Elegir()) && !clavesrep.Pertenece(claves2.Elegir()) && !valores1.ConjuntoVacio() && claves1.Elegir() != claves2.Elegir()) {
							while(!valores2.ConjuntoVacio()) {//3
								mattotal ++;
								if (valores1.Pertenece(valores2.Elegir())) 
									matcoinc ++;
								valores2.Sacar(valores2.Elegir());
							}
							c = porcentaje(mattotal, matcoinc, c);
							System.out.println("La Carrera con código " + claves1.Elegir() + " tiene en común con la carrera "+ claves2.Elegir() + " el " + c + "% de sus materias.");
							mattotal = 0;
							matcoinc = 0;
							claves2.Sacar(claves2.Elegir());
							if (!claves2.ConjuntoVacio())
								valores2 = var.Recuperar(claves2.Elegir());
							}

					else {
						claves2.Sacar(claves1.Elegir());
				}
			}
			claves1.Sacar(claves1.Elegir());
			claves2 = var.Claves();
		}
		if (c == 0)
			System.out.println(" No se encontraron carreras con el 20% de sus materias en común.");
		 System.out.println("------------------------------------------------------------------------------------------------------------------");
	}
	
	
	
	private static int porcentaje(int total, int tengo, int contador) {
		int a = 0;
		a = tengo * 100 / total;
		if (a <= 20) {
			contador ++;
		}
		return a;
	}
	
	/**
	@Tarea 2.J Para cada combinación de dos carreras, indicar materias no comunes
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Las materias no comunes entre dos carreras
	@Precondición El diccionario multiple debe estar inicializado y cargado
	@Postcondición -
	@Costo Cubico(debido a que hay un ciclo while de matnocomunes y del metodo check dentro de un ciclo while)
	 **/
	public static void matnocomunes(DiccionarioMultipleTDA var) {
		ConjuntoTDA carreras1 = var.Claves();
		ConjuntoTDA carreras2 = var.Claves();
		ConjuntoTDA repasadas = new ConjuntoLD();
		System.out.println("2-J Para cada combinación de dos carreras, indicar materias no comunes. ");
		ConjuntoTDA materias1;
		//materias1.InicializarConjunto();
		ConjuntoTDA materias2;
		//materias2.InicializarConjunto();
		ConjuntoTDA mnc;
		//mnc.InicializarConjunto();
		while (!carreras2.ConjuntoVacio()) {
			while (!carreras1.ConjuntoVacio()) {
				if (carreras1.Elegir() == carreras2.Elegir() || repasadas.Pertenece(carreras1.Elegir())) {
					carreras1.Sacar(carreras1.Elegir());
				}
				else {
					materias1 = var.Recuperar(carreras1.Elegir());
					materias2 = var.Recuperar(carreras2.Elegir());
					mnc = check(materias1,materias2);
					System.out.println("Materias que la carrera " + carreras2.Elegir() + " y la " + carreras1.Elegir() + " no tienen en común:");
					mostrarmaterias(mnc);
					carreras1.Sacar(carreras1.Elegir());
					
				}
			}
			
			repasadas.Agregar(carreras2.Elegir());
			carreras2.Sacar(carreras2.Elegir());
			carreras1 = var.Claves();
		}
		 System.out.println("------------------------------------------------------------------------------------------------------------------");
	}	
	
	public static ConjuntoTDA check(ConjuntoTDA a, ConjuntoTDA b) {
		ConjuntoTDA mnc = b;
		while(!a.ConjuntoVacio()) {
			if(mnc.Pertenece(a.Elegir())) {
				mnc.Sacar(a.Elegir());
				a.Sacar(a.Elegir());
			}
			else
				a.Sacar(a.Elegir());
		}
		return mnc;
	}
	
	public static void mostrarmaterias(ConjuntoTDA mnc) {
		while(!mnc.ConjuntoVacio()) {
			System.out.println(mnc.Elegir());
			mnc.Sacar(mnc.Elegir());
		}
	}
	
	public static int comparaConjuntos(ConjuntoTDA a, ConjuntoTDA b) {
		int estaenb = 0;
		ConjuntoTDA aux = new ConjuntoLD ();		
		aux.InicializarConjunto();
		copiarConjunto(a , aux);
		int elemento =0;
		
		if (!aux.ConjuntoVacio() && !b.ConjuntoVacio()) {
			while(!aux.ConjuntoVacio()) {
			elemento =aux.Elegir();
			if (b.Pertenece(elemento)) {
				estaenb = estaenb+1;
			}
			
			//a.Agregar(elemento);
			aux.Sacar(elemento);
			
			}		
		}
		//a = aux;
	return estaenb;
	}

	public static void copiarConjunto(ConjuntoTDA c1,ConjuntoTDA c2){
        ConjuntoLD aux = new ConjuntoLD();
        aux.InicializarConjunto();
        pasarConjunto(c1,aux);
        while(!aux.ConjuntoVacio()){
            int var = aux.Elegir();
            c2.Agregar(var);
            c1.Agregar(var);
            aux.Sacar(var);
        }
    }
	
    public static void pasarConjunto(ConjuntoTDA origen, ConjuntoTDA destino){
		
		int elemento = 0;
		
		while(!origen.ConjuntoVacio()){
			
			elemento = origen.Elegir();			
			destino.Agregar(elemento);			
			origen.Sacar(elemento);			
		}		

	}
	/*
	 * 
	 * 
	 * Utils
	 * 
	 * */


	public static void mostrar (DiccionarioSimpleTDA d){
		ConjuntoTDA claves = d.Claves();
		int clave,valor;
		if(!claves.ConjuntoVacio()){
			while(!claves.ConjuntoVacio()){
				clave = claves.Elegir();
				valor = d.Recuperar(clave);
				System.out.println("Clave:" + clave + " Valor:" + valor);
				claves.Sacar(clave);
			}
		}
		else
			System.out.println("Diccionario Vacio");
	}

	public static void mostrar (DiccionarioMultipleTDA DM){
		ConjuntoTDA claves = DM.Claves();
		int clave;
		//ConjuntoTDA valor;
		if(!claves.ConjuntoVacio()){
			while(!claves.ConjuntoVacio()){
				clave = claves.Elegir();
				ConjuntoTDA valores=DM.Recuperar(clave);
				System.out.print("Clave:"+clave + " ");
				System.out.print("valores:");
				mostrarConjunto(valores);
				System.out.println("");
				//valor = DM.Recuperar(clave);
				claves.Sacar(clave);
			}

		}		
	}	

	public static void mostrarConjunto(ConjuntoTDA c){
		ConjuntoLD aux = new ConjuntoLD();
		Metodos m = new Metodos();
		aux.InicializarConjunto();
		if (!c.ConjuntoVacio()){
			while(!c.ConjuntoVacio()){
				System.out.print(c.Elegir());
				System.out.print(";");
				aux.Agregar(c.Elegir());
				c.Sacar(c.Elegir());
			}
			m.PasarConjunto(aux, c);
		}else {
			System.out.println("Pila Vacia");
		}	}	
}

