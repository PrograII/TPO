package misAplicaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import miApi.ConjuntoTDA;
import misImplementaciones.ConjuntoLD;
import miApi.DiccionarioMultipleTDA;
import miApi.DiccionarioSimpleTDA;
import misImplementaciones.DicMultipleL;
import misImplementaciones.DicSimpleL;

/**
@autores: Gaston Gomez; .......;.......;......;
@grupo: 06
 **/



public class AppTPO {



	public static void main(String[] args) {		

		DiccionarioMultipleTDA diccM = new DicMultipleL();
		ArrayList<Materias> meteriaList = new ArrayList<Materias>();

		diccM.InicializarDiccionario();		
		meteriaList = leeyCargaDicMul(diccM);		
		mostrar(diccM);		
		/*Ordeno la lista por nroCarrera*/
		//Collections.sort(meteriaList);	
		contidadPorCarrera(diccM);
		porcentajeMateriasInfPorCarrera(diccM);
		
		porcentajeMateriasCienciasBPorCarrera(diccM);
		porcentajeMateriasCienciasSPorCarrera(diccM);

		contidadMateriasOptativas(meteriaList);		
		materiasComunesConDiccionario(meteriaList);
		
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

	public static class MateriasComparator implements Comparator<Materias> {

		private List<Comparator<Materias>> listComparators;

		@SafeVarargs
		public MateriasComparator(Comparator<Materias>... comparators) {
			this.listComparators = Arrays.asList(comparators);
		}

		@Override
		public int compare(Materias emp1, Materias emp2) {
			for (Comparator<Materias> comparator : listComparators) {
				int result = comparator.compare(emp1, emp2);
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	}

	public static class MateriasnroCarreraComparator implements Comparator<Materias> {		  

		@Override
		public int compare(Materias emp1, Materias emp2) {
			return emp1.nroCarrera.compareTo(emp2.nroCarrera);
		}
	}

	public static class MateriasncodMateriaComparator implements Comparator<Materias> {


		@Override
		public int compare(Materias emp1, Materias emp2) {
			return emp1.codMateria.compareTo(emp2.codMateria);
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
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	public static void contidadPorCarrera( DiccionarioMultipleTDA var	) {  

		try {        
			
			System.out.println("2a. Cantidad de materias de cada una de las carreras");
			ConjuntoTDA keys = var.Claves();
		    
		    int cant = 1;
		    	
		    while(!keys.ConjuntoVacio()) {
		    	int aux = keys.Elegir();
		    	int cont = 0;
		    	ConjuntoTDA valores = var.Recuperar(aux);
		    	while(!valores.ConjuntoVacio()) {
		    		int aux2 = valores.Elegir();
		    		cont += 1;
		    		valores.Sacar(aux2);
		    	}
		    	keys.Sacar(aux);
		    	System.out.println("La carrera numero " + cant + " tiene " + cont + " materias");
		    	cant += 1;
		    } 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// return var;
	}

	
	/**
	@Tarea : 2.b Porcentaje de materias de informática en cada una de las carreras (materias que
				comienzan con código 34xxx)
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	public static void porcentajeMateriasInfPorCarrera( DiccionarioMultipleTDA var	) {  

		try {        
			
			System.out.println("2.b Porcentaje de materias de informática en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 34xxx)");
			ConjuntoTDA keys = var.Claves();
		    int car = 1;
		    
		    while(!keys.ConjuntoVacio()) {
		    	int aux = keys.Elegir();
		    	int cont1 = 0;
		    	int cont2 = 0;
		    	ConjuntoTDA materias = var.Recuperar(aux);
		    	
		    	while(!materias.ConjuntoVacio()) {
		    		int comp = materias.Elegir();
		    		
		    		if (Integer.toString(comp).startsWith("34")) {
		    			cont1 += 1;
		    		}
		    		
		    		cont2 += 1;
		    		materias.Sacar(comp);
		    	}
		    	
		    	keys.Sacar(aux);
		    	System.out.println("La carrera numero " + car + " tiene " + (cont1 * 100) / cont2 + "% de materias de informatica");
		    	car += 1;
		    		    	
		    }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// return var;
	}

	
	/**
	@Tarea : 2.c Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que
				comienzan con código 31xxx)
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	public static void porcentajeMateriasCienciasBPorCarrera( DiccionarioMultipleTDA var	) {  

		try {        
			
			System.out.println("2.c Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 31xxx)");
			 ConjuntoTDA keys = var.Claves();
			    keys = var.Claves();
			    int carre = 1;
			    
			    while(!keys.ConjuntoVacio()) {
			    	int aux = keys.Elegir();
			    	int contd = 0;
			    	int conte = 0;
			    	ConjuntoTDA materias = var.Recuperar(aux);
			    	while(!materias.ConjuntoVacio()) {
			    		int comp = materias.Elegir();
			    		if (Integer.toString(comp).startsWith("31")) {
			    			contd += 1;
			    		}
			    		conte += 1;
			    		materias.Sacar(comp);
			    	}
			    	keys.Sacar(aux);
			    	System.out.println("La carrera numero " + carre + " tiene " + ((contd * 100) / conte) + "% de materias de ciencias básicas");
			    	carre += 1;    		    	
			    }
			    System.out.println("---------------------------------------");
		    		    	
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// return var;
	}

	
	/**
	@Tarea : 2.d Porcentaje de materias de ciencias sociales en cada una de las carreras (materias
			comienzan con códigos 2xxxx y 33xxx)
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	public static void porcentajeMateriasCienciasSPorCarrera( DiccionarioMultipleTDA var	) {  

		try {        
			
			System.out.println("2.d Porcentaje de materias de ciencias sociales en cada una de las carreras (materias\r\n" + 
					"comienzan con códigos 2xxxx y 33xxx)");
			 ConjuntoTDA keys = var.Claves();
			    keys = var.Claves();
			    int carr = 1;
			    
			    while(!keys.ConjuntoVacio()) {
			    	int aux = keys.Elegir();
			    	int cont3 = 0;
			    	int cont4 = 0;
			    	ConjuntoTDA materias = var.Recuperar(aux);
			    	while(!materias.ConjuntoVacio()) {
			    		int comp = materias.Elegir();
			    		if (Integer.toString(comp).startsWith("2") || Integer.toString(comp).startsWith("33")) {
			    			cont3 += 1;
			    		}
			    		cont4 += 1;
			    		materias.Sacar(comp);
			    	}
			    	keys.Sacar(aux);
			    	System.out.println("La carrera numero " + carr + " tiene " + ((cont3 * 100) / cont4) + "% de materias de ciencias sociales");
			    	carr += 1;    		    	
			    }
		    		    	
		    
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// return var;
	}

	
	/**
	@Tarea : 2e. Cantidad de materias optativas de cada una de las carreras			 
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/

	public static void contidadMateriasOptativas( ArrayList<Materias>  materiasList	) {  

		try {        

			//ordeno por nroCarrera
			Collections.sort(materiasList, new MateriasComparator( new MateriasnroCarreraComparator()));

			DiccionarioSimpleTDA diccS = new DicSimpleL();
			diccS.InicializarDiccionario();
			int nroCarrera = 0;	



			System.out.println("2e Cantidad de materias optativas de cada una de las carreras:");
			System.out.println("Clave(Nro Carrera) Valor(Cant Optativas)");
			for(int indice = 0;indice < materiasList.size() ;indice++)
			{      
				if (nroCarrera !=  materiasList.get(indice).nroCarrera) {
					nroCarrera = materiasList.get(indice).nroCarrera;
				}

				if (materiasList.get(indice).materias.contains("Optativa") && nroCarrera ==materiasList.get(indice).nroCarrera){    				


					String str = materiasList.get(indice).materias;    
					str = str.replaceAll("[^-?0-9]+", " "); 
					diccS.Agregar(materiasList.get(indice).nroCarrera, Integer.parseInt(Arrays.asList(str.trim().split(" ")).get(0)));
					//System.out.println(materias.get(indice).carrera + ": "+ Arrays.asList(str.trim().split(" ")).get(0));

				}else {
					diccS.Agregar(materiasList.get(indice).nroCarrera, 0); 
					//System.out.println(materias.get(indice).carrera + ": "+ 0);
				}

			}
			//chequear resultados
			mostrar(diccS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// return var;
	}

	/**
	@Tarea :   2f. Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no
				incluir materias optativas)
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/

	public static void materiasComunes( ArrayList<Materias>  materiasList) {  

		try {        
			//ordeno por nroMaterias
			Collections.sort(materiasList, new MateriasComparator( new MateriasncodMateriaComparator()));

			DiccionarioMultipleTDA diccM = new DicMultipleL();
			diccM.InicializarDiccionario();
			int codMateria = 0;	
			int cont = 0;	

			System.out.println("2Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
					"incluir materias optativas)");
			System.out.println("Clave(Cod Materia) Valor(Nro Carreras)");
			for(int indice = 0;indice < materiasList.size() ;indice++)
			{      
				if (!materiasList.get(indice).materias.contains("Optativa")) {
					if (codMateria !=  materiasList.get(indice).codMateria) {
						codMateria = materiasList.get(indice).codMateria;	
						cont = 1;
						diccM.Agregar(codMateria, materiasList.get(indice).nroCarrera);
					}else {
						cont = cont +1;
						diccM.Agregar(codMateria, materiasList.get(indice).nroCarrera);	
						if (cont == 4) {
							System.out.println(materiasList.get(indice).materias);
							cont = 0;
						}
					}
				}else {
					cont = 0;
				}

			}
			mostrar(diccM);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	public static void materiasComunesConDiccionario( ArrayList<Materias>  materiasList) {  

		try {        
			//ordeno por nroMaterias
			Collections.sort(materiasList, new MateriasComparator( new MateriasncodMateriaComparator()));

			DiccionarioMultipleTDA diccM = new DicMultipleL();
			diccM.InicializarDiccionario();
			DiccionarioMultipleTDA diccMFinal = new DicMultipleL();
			diccMFinal.InicializarDiccionario()	;

			System.out.println("2f Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
					"incluir materias optativas)");
			System.out.println("Clave(Cod Materia) Valor(Nro Carreras)");
			for(int indice = 0;indice < materiasList.size() ;indice++)
			{      
				if (!materiasList.get(indice).materias.contains("Optativa")) {						
						diccM.Agregar(materiasList.get(indice).codMateria, materiasList.get(indice).nroCarrera);
				}
			}
			
			int clave;
			int conta = 0;
			ConjuntoTDA claves = diccM.Claves();
			if(!claves.ConjuntoVacio()){
				while(!claves.ConjuntoVacio()){
					clave = claves.Elegir();
					ConjuntoTDA valores=diccM.Recuperar(clave);	
					ConjuntoTDA valoresAux=diccM.Recuperar(clave);	
					if (!valores.ConjuntoVacio()){
						conta = 0;
						while(!valores.ConjuntoVacio()){
							conta = conta +1;							
							valores.Sacar(valores.Elegir());
							}
					}
					if (conta == 4 ) {
						while(!valoresAux.ConjuntoVacio()){
							diccMFinal.Agregar(clave, valoresAux.Elegir());
							valoresAux.Sacar(valoresAux.Elegir());
						}
					}					
					claves.Sacar(clave);
					
				}

			}		
			
			mostrar(diccMFinal);
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}


	/**
	@Tarea :   2G. Carrera que comparte mas del 80% de las materias de otra carrera
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
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
	/**
	@Tarea :   2h. Materias que no comparten ninguna carrera
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
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
	

	/**
	@Tarea: 2i. Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las
			carreras relacionadas
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	
	public static void matrelacionadas20(DiccionarioMultipleTDA var){
		
	
		int matcoinc = 0;
		int mattotal = 0;
		int contador = 0;
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
							contador ++;
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
		if (contador == c)
			System.out.println("No se encontraron carreras con el 20% de sus materias en común.");
	}
	
	
	
	private static int porcentaje(int total, int tengo, int clave1, int clave2, int contador) {
		int a = 0;
		a = tengo * 100 / total;
		if (a == 20)
			System.out.println("La Carrera con código " + clave1 + " tiene en común con la carrera "+ clave2 + " el " + a + "% de sus materias.");
		else {
			contador ++;
		}
		return contador;
	}
	
	
	/**
	@Tarea 2 j
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	 **/
	
	public static void matnocomunes(DiccionarioMultipleTDA var) {
		ConjuntoTDA carreras1 = var.Claves();
		ConjuntoTDA carreras2 = var.Claves();
		ConjuntoTDA repasadas = new ConjuntoLD();
		System.out.println("2-J Para cada combinación de dos carreras, indicar materias no comunes. ");
		while (!carreras2.ConjuntoVacio()) {
			System.out.println(carreras2.Elegir());
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
		ConjuntoTDA mnc = new ConjuntoLD();
		mnc.InicializarConjunto();
		while (!a.ConjuntoVacio()) {
			if (!b.Pertenece(a.Elegir())) {
				mnc.Agregar(a.Elegir());
				a.Sacar(a.Elegir());
			}
			else
				a.Sacar(a.Elegir());
		}
		if (b.ConjuntoVacio()) {
			while(!a.ConjuntoVacio()) {
				mnc.Agregar(a.Elegir());
				a.Sacar(a.Elegir());
			}
		}
		else
			while(!b.ConjuntoVacio()) {
				mnc.Agregar(b.Elegir());
				b.Sacar(b.Elegir());
			}
		return mnc;
	}
	
	public static void mostrarmaterias(ConjuntoTDA car1, ConjuntoTDA car2, ConjuntoTDA mnc) {
		System.out.println("Materias de la carrera " + car1.Elegir() + " que no están en la materia " + car2.Elegir() + " son:");
		while(!mnc.ConjuntoVacio()) {System.out.println(mnc.Elegir());mnc.Sacar(mnc.Elegir());
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

}

