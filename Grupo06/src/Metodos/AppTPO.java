package Metodos;
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
import misImplementaciones.DicSimpleL;

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

		DiccionarioSimpleTDA dicNomMaterias = new DicSimpleL(); 
		
		contidadMateriasOptativas(diccM);	
		materiasComunes(diccM);
		
		/*compararCarreras(diccM);
		materiasUnicaCarrera(diccM);
		
		matrelacionadas20(diccM);
		matnocomunes(diccM);
		*/

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
	@Tarea : 2a. Cantidad de materias de cada una de las carreras
	@Parámetros DiccionarioMultipleTDA
	@Devuelve El numero de carrera junto a la cantidad de materias
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo
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
	@Tarea : 2.b Porcentaje de materias de informática en cada una de las carreras (materias que
				comienzan con código 34xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de informatica
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo
	 **/
	public static void porcentajeMateriasInfPorCarrera( DiccionarioMultipleTDA var) {  
		
			System.out.println("2.b Porcentaje de materias de informática en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 34xxx)");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	
		    	aux = keys.Elegir();
		    	System.out.println("La carrera numero " + aux + " tiene " + m.porcentajeMaterias(var,"34",aux) + "% de materias de informatica");
		    	keys.Sacar(aux);
		    		    	
		    }
		    System.out.println("------------------------------------------------------------------------------------------------------------------");

	}

	
	/**
	@Tarea : 2.c Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que
				comienzan con código 31xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de ciencias basicas
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo
	 **/
	public static void porcentajeMateriasCienciasSPorCarrera( DiccionarioMultipleTDA var	) {  
			System.out.println("2.c Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 31xxx)");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	
		    	aux = keys.Elegir();
		    	System.out.println("La carrera numero " + aux + " tiene " + m.porcentajeMaterias(var,"31",aux) + "% de materias de ciencias basicas");
		    	keys.Sacar(aux);
		    		    	
		    }
		    System.out.println("------------------------------------------------------------------------------------------------------------------");
		}

	
	/**
	@Tarea : 2.d Porcentaje de materias de ciencias sociales en cada una de las carreras (materias
			comienzan con códigos 2xxxx y 33xxx)
	@Parámetros DiccionarioMultipleTDA
	@Devuelve Codigo de cada carrera junto al porcentaje de materias de ciencias sociales
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo
	 **/
	public static void porcentajeMateriasCienciasBPorCarrera( DiccionarioMultipleTDA var	) { 
		System.out.println("2.d Porcentaje de materias de ciencias sociales en cada una de las carreras (materias\r\n" + "comienzan con códigos 2xxxx y 33xxx)");
		ConjuntoTDA keys = var.Claves();
		int aux=0;
		while(!keys.ConjuntoVacio()) {
			aux = keys.Elegir();
			Metodos m = new Metodos();
			System.out.println("La carrera numero " + aux + " tiene " + (m.porcentajeMaterias(var,"2",aux) + m.porcentajeMaterias(var,"33",aux)) + "% de materias de ciencias sociales");
			keys.Sacar(aux);
		    		    	
		}
	    System.out.println("------------------------------------------------------------------------------------------------------------------");
	}

	
	/**
	@Tarea : 2e. Cantidad de materias optativas de cada una de las carreras			 
	@Parámetros  DiccionarioMultipleTDA
	@Devuelve Resultados en pantalla
	@Precondición  Tener los valores en el diccionario multiple
	@Postcondición -
	@Costo: Cuadratico
	 **/

	public static void contidadMateriasOptativas( DiccionarioMultipleTDA dic) {  

	    
			System.out.println("2.e Cantidad de materias optativas de cada una de las carreras:");			
			DiccionarioSimpleTDA diccS = new DicSimpleL();
			ConjuntoTDA codCarre = dic.Claves();
			ConjuntoTDA codigoMat = new ConjuntoLD();
			while (!codCarre.ConjuntoVacio()) {
				int codCarreAux = codCarre.Elegir();
				codigoMat = dic.Recuperar(codCarreAux);
				int codigoMatAux = 0;
				while (!codigoMat.ConjuntoVacio()) {
					codigoMatAux = codigoMat.Elegir();
					if (codigoMatAux<100 ) { //solo las optativas
						System.out.println("La carrera: " + NombreCarrera(codCarreAux) + ", tiene " +codigoMatAux + " materias optativas.");						
					}
					codigoMat.Sacar(codigoMatAux);
				}

				codCarre.Sacar(codCarreAux);
				if (codigoMatAux > 100) {
					System.out.println("La carrera: " + NombreCarrera(codCarreAux) + ", tiene 0 materias optativas.");
					
				}
			}
			
			//chequear resultados
			//mostrar(diccS);
			System.out.println("---------------------------------------");
		
		
	}


	/**
	@Tarea :   2f. Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no
				incluir materias optativas)
	@Parámetros  DiccionarioMultipleTDA
	@Devuelve Resultados en pantalla
	@Precondición Tener los valores en el diccionario multiple  
	@Postcondición
	@Costo Cuadratico
	 **/
	public static void materiasComunes( DiccionarioMultipleTDA dic) {
	     
			
			DiccionarioMultipleTDA diccMFinal = new DicMultipleL();
			diccMFinal.InicializarDiccionario()	;

			System.out.println("2.f Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
					"incluir materias optativas)");
			
			
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
						System.out.println("La materia "+ NombreMater(clave) + "(Codigo: " + clave  +  "), es comun a todas las carreras.");
					}					
					claves.Sacar(clave);
					
				}

			}	
			System.out.println("---------------------------------------");		

	}

	

	/**
	@Tarea :   2G. Carrera que comparte mas del 80% de las materias de otra carrera
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	/*
	public static void compararCarreras(DiccionarioMultipleTDA dic) {
		
		System.out.println("2.g Carrera/s que incluyan más del 80% de las materias de otra carrera, indicando las\r\n" + 
				"carreras relacionadas.");
		int c = contadorConjunto(dic.Claves());
		int i, j;
		boolean cumple;
		int[] vectorCarreras = carrerasAVector(dic.Claves());
		for(i=0; i<c; i++) {
			for(j=1; j<c; j++) {
				if(j>i) {
					cumple = cumplePorcentaje(dic.Recuperar(vectorCarreras[i]), dic.Recuperar(vectorCarreras[j]));
					if(cumple) {
						System.out.println("La carrera " + vectorCarreras[i] + " comparte mas del 80% de las materias de la carrera " + vectorCarreras[j]);
					}
					cumple = cumplePorcentaje(dic.Recuperar(vectorCarreras[j]), dic.Recuperar(vectorCarreras[i]));
					if(cumple) {
						System.out.println("La carrera " + vectorCarreras[j] + " comparte mas del 80% de las materias de la carrera " + vectorCarreras[i]);
					}
				}
			}
		}
	}
	
	public static int contadorConjunto(ConjuntoTDA a) {
		ConjuntoTDA aux = new ConjuntoLD();
		aux.InicializarConjunto();
		int c = 0;
		while(!a.ConjuntoVacio()) {
			c++;
			aux.Agregar(a.Elegir());
			a.Sacar(a.Elegir());
		}
		PasarConjunto(aux, a);
		return c;
	}
	public static int[] carrerasAVector(ConjuntoTDA a) {
		ConjuntoTDA aux = new ConjuntoLD();
		aux.InicializarConjunto();
		int c = contadorConjunto(a);
		int[] carreras = new int[c];
		c-=1;
		while((!a.ConjuntoVacio()) && c>=0) {
			carreras[c]=a.Elegir();
			aux.Agregar(a.Elegir());
			a.Sacar(a.Elegir());
			c--;
		}
		PasarConjunto(aux,a);
		return carreras;
		
	}
	public static boolean cumplePorcentaje(ConjuntoTDA a, ConjuntoTDA b) {
		int c = 0;
		int cantMaterias = contadorConjunto(b);
		boolean cumpleCondicion = false;
		while(!a.ConjuntoVacio()) {
			if (b.Pertenece(a.Elegir())) {
				c++;
			}
			a.Sacar(a.Elegir());
		}
		int porcentaje = (c*100)/cantMaterias;
		System.out.println(porcentaje);
		if(porcentaje>80) {
			cumpleCondicion = true;
		}
		return cumpleCondicion;
	}
	*/
	/**
	@Tarea :   2h. Materias que no comparten ninguna carrera
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	/*
	public static void materiasUnicaCarrera(DiccionarioMultipleTDA dic) {
		
		System.out.println("2h. Materias que no comparten ninguna carrera");
		int cantCarreras = contadorConjunto(dic.Claves());
		int[] codCarreras = new int[cantCarreras];
		boolean pertenece;
		codCarreras = carrerasAVector(dic.Claves());
		int cantMaterias;
		for(int i=0; i<cantCarreras; i++) {
			cantMaterias = contadorConjunto(dic.Recuperar(codCarreras[i]));
			int[] materias = new int[cantMaterias];
			materias = carrerasAVector(dic.Recuperar(codCarreras[i]));
			for (int j=0; j<cantMaterias; j++) {
				pertenece = false;
				for(int x=0; x<cantCarreras; x++) {
					if (x!=i && pertenece==false) {
						pertenece = dic.Recuperar(codCarreras[x]).Pertenece(materias[j]);
					}
				}
				if(!pertenece) {
					System.out.println("La materia " + materias[j] + " pertenece unicamente a la carrera "  + codCarreras[i]);
				}
			}
		}
	}
	
*/
	/**
	@Tarea: 2i. Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las
			carreras relacionadas
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	/*
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
		System.out.println("2-I Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las carreras relacionadas.");
		while(!claves1.ConjuntoVacio()) {
			while(!claves2.ConjuntoVacio()){
				ConjuntoTDA valores1 = var.Recuperar(claves1.Elegir());
				ConjuntoTDA valores2 = var.Recuperar(claves2.Elegir());
					if (!clavesrep.Pertenece(claves1.Elegir()) && !clavesrep.Pertenece(claves2.Elegir()) && !valores1.ConjuntoVacio() && claves1.Elegir() != claves2.Elegir()) {
							while(!valores2.ConjuntoVacio()) {
								mattotal ++;
								if (valores1.Pertenece(valores2.Elegir())) 
									matcoinc ++;
								valores2.Sacar(valores2.Elegir());
							}
							c = porcentaje(mattotal, matcoinc, claves1.Elegir(), claves2.Elegir(), c);
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
	}
	
	
	
	private static int porcentaje(int total, int tengo, int clave1, int clave2, int contador) {
		int a = 0;
		a = tengo * 100 / total;
		System.out.println("La Carrera con código " + clave1 + " tiene en común con la carrera "+ clave2 + " el " + a + "% de sus materias.");
		if (a <= 20) {
			contador ++;
		}
		return contador;
	}
	*/
	/**
	@Tarea 2 j
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	/*
	public static void matnocomunes(DiccionarioMultipleTDA var) {
		ConjuntoTDA carreras1 = var.Claves();
		ConjuntoTDA carreras2 = var.Claves();
		ConjuntoTDA repasadas = new ConjuntoLD();
		System.out.println("2-J Para cada combinación de dos carreras, indicar materias no comunes. ");
		while (!carreras2.ConjuntoVacio()) {
			while (!carreras1.ConjuntoVacio()) {
				if (carreras1.Elegir() == carreras2.Elegir() || repasadas.Pertenece(carreras1.Elegir())) {
					carreras1.Sacar(carreras1.Elegir());
				}
				else {
					ConjuntoTDA materias1 = var.Recuperar(carreras1.Elegir());
					ConjuntoTDA materias2 = var.Recuperar(carreras2.Elegir());
					ConjuntoTDA mnc = check(materias1,materias2);
					mostrarmaterias(carreras1, carreras2, mnc);
					carreras1.Sacar(carreras1.Elegir());
					
				}
			}
			
			repasadas.Agregar(carreras2.Elegir());
			carreras2.Sacar(carreras2.Elegir());
			carreras1 = var.Claves();
		}
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
	
	public static void mostrarmaterias(ConjuntoTDA car1, ConjuntoTDA car2, ConjuntoTDA mnc) {
		System.out.println("Materias que la carrera " + car2.Elegir() + " y la " + car1.Elegir() + " no tienen en común:");
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
			c2.Agregar(aux.Elegir());
			c1.Agregar(aux.Elegir());
			aux.Sacar(aux.Elegir());
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

	*/
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
		aux.InicializarConjunto();
		if (!c.ConjuntoVacio()){
			while(!c.ConjuntoVacio()){
				System.out.print(c.Elegir());
				System.out.print(";");
				aux.Agregar(c.Elegir());
				c.Sacar(c.Elegir());
			}
			PasarConjunto(aux, c);
		}else {
			System.out.println("Pila Vacia");
		}	}

	public static void PasarConjunto(ConjuntoTDA o, ConjuntoTDA d){
		while(!o.ConjuntoVacio()){
			d.Agregar(o.Elegir());
			o.Sacar(o.Elegir());

		}
	}
	
	

	public static String NombreMater(int  codMater) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String nombre= "";
		

		try {
			archivo = new File ("Materias.csv");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			br.readLine();
			String linea = br.readLine();
			

			while(linea != null) {
				String[] lis = linea.split(";");				
				
				if (Integer.parseInt(lis[1]) == codMater){
					nombre =  lis[4];
				};
				linea = br.readLine();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return nombre;
	}
	
	public static String NombreCarrera(int  codCarre) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String nombre= "";
		

		try {
			archivo = new File ("Materias.csv");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			br.readLine();
			String linea = br.readLine();
			

			while(linea != null) {
				String[] lis = linea.split(";");				
				
				if (Integer.parseInt(lis[0]) == codCarre){
					nombre =  lis[3];
				};
				linea = br.readLine();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return nombre;
	}

}

