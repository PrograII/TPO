package misAplicaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import MisMetodos.Metodos;
import miApi.ColaPrioridadTDA;
import miApi.ConjuntoTDA;
import misImplementaciones.ColaPrioridad;
import misImplementaciones.ConjuntoLD;
import miApi.DiccionarioMultipleTDA;
import miApi.GrafoTDA;
import misImplementaciones.DicMultipleL;
import misImplementaciones.Grafo;


/**
@autores: Fabre, Juan; Rozenblum, Lautaro;Sapoznik, Lucas; Di Paolo, Ivan; Gaston Gomez; 
@grupo: 06
 **/

public class AppTPO {

	public static void main(String[] args) {		

		DiccionarioMultipleTDA diccM = new DicMultipleL();
		DiccionarioMultipleTDA diccG = new DicMultipleL();
		diccM.InicializarDiccionario();		
		leeyCargaDicMul(diccM);
		
		cantidadPorCarrera(diccM);
		porcentajeMateriasInfPorCarrera(diccM);
		
		porcentajeMateriasCienciasSPorCarrera(diccM);
		porcentajeMateriasCienciasBPorCarrera(diccM);
		
		contidadMateriasOptativas(diccM);	
		materiasComunes(diccM);
		
		compararCarreras(diccM);
		materiasUnicaCarrera(diccM);
		
		matrelacionadas20(diccM);
		matnocomunes(diccM);
		
		leeyCargaDicMul1(diccG);
        GrafoTDA grafo = generargrafo(diccG);
        informarMayorPrecedentes(grafo);
        informarMayorSubsiguientes(grafo);
        informarPorcentajeSinCorrelativas(grafo);
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
	public static void leeyCargaDicMul(DiccionarioMultipleTDA var) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		System.out.println("1. Cargar los datos de las carreras en un diccionario múltiple, donde la clave corresponde al\r\n" + 
				"código de carrera y los valores son códigos de las materias para esa carrera.");
		
		try {
			archivo = new File ("Materias.csv");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			var.InicializarDiccionario();
			br.readLine();
			String linea = br.readLine();

			while(linea != null) {
				String[] lis = linea.split(";");
				var.Agregar(Integer.parseInt(lis[0]),Integer.parseInt(lis[1]));
				linea = br.readLine();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	 public static void leeyCargaDicMul1(DiccionarioMultipleTDA par) {
	        File archivo = null;
	        FileReader fr = null;
	        BufferedReader br = null;
	       
	        try {
	            archivo = new File ("Materias.csv");
	            fr = new FileReader (archivo);
	            br = new BufferedReader(fr);
	            par.InicializarDiccionario();
	            br.readLine();
	            String linea = br.readLine();
	            while(linea != null) {
	                String[] lis = linea.split(";");
	                if (!lis[2].isEmpty()) 
	                    par.Agregar(Integer.parseInt(lis[1]),Integer.parseInt(lis[2]));
	                else
	                    par.Agregar(Integer.parseInt(lis[1]),0);
	                linea = br.readLine();
	            }
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	
	/**
	@Tarea : 2A. Cantidad de materias de cada una de las carreras
	@Parámetros DiccionarioMultipleTDA
	@Devuelve El numero de carrera junto a la cantidad de materias
	@Precondición Diccionario Multiple inicializado y cargado
	@Postcondición -
	@Costo Cuadratico(debido a que hay un ciclo while dentro de otro ciclo while)
	 **/
	public static void cantidadPorCarrera( DiccionarioMultipleTDA var	) {  
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.println("2a. Cantidad de materias de cada una de las carreras");
			ConjuntoTDA keys = var.Claves();
			int aux = 0; 
	    	int cont = 0;
	    	int aux2=0;
	    	ConjuntoTDA valores;
	    	Metodos m = new Metodos();
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
		    	System.out.println("La carrera " + m.NombreCarrera(aux)+ " (Codigo: " + aux + ")" + " tiene " + cont + " materias");
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
		
			System.out.println("2b. Porcentaje de materias de informática en cada una de las carreras (materias que comienzan con código 34xxx");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	aux = keys.Elegir();
		    	System.out.println("La carrera " + m.NombreCarrera(aux)+ " (Codigo: " + aux + ")" + " tiene " + m.porcentajeMaterias(var,"34",aux) + "% de materias de informatica");//Se llama al metodo porcentajeMaterias el cual devuelve 
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
			System.out.println("2c. Porcentaje de materias de ciencias básicas en cada una de las carreras (materias que\r\n" + 
					"comienzan con código 31xxx)");
			ConjuntoTDA keys = var.Claves();
		    Metodos m = new Metodos();
		    int aux=0;
		    while(!keys.ConjuntoVacio()) {
		    	
		    	aux = keys.Elegir();
		    	System.out.println("La carrera " + m.NombreCarrera(aux) + " (Codigo: " + aux + ")" + " tiene " + m.porcentajeMaterias(var,"31",aux) + "% de materias de ciencias basicas");//Se llama al metodo porcentajeMaterias 
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
	@Costo Cuadratico(porque hay un while en la funcion porcentajeMateriasCienciasBPorCarrera y en el metodo porcentajeMaterias)
	 **/
	public static void porcentajeMateriasCienciasBPorCarrera( DiccionarioMultipleTDA var	) { 
		System.out.println("2d. Porcentaje de materias de ciencias sociales en cada una de las carreras (materias\r\n" + "comienzan con códigos 2xxxx y 33xxx)");
		ConjuntoTDA keys = var.Claves();
		int aux=0;
		while(!keys.ConjuntoVacio()) {
			aux = keys.Elegir();
			Metodos m = new Metodos();
			System.out.println("La carrera " + m.NombreCarrera(aux) + " (Codigo: " + aux + ")" + " tiene " + (m.porcentajeMaterias(var,"2",aux) + m.porcentajeMaterias(var,"33",aux)) + "% de materias de ciencias sociales");//Se llama al metodo porcentajeMaterias el cual devuelve 
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
			int e=0;
			int h=0;
			while (!codCarre.ConjuntoVacio()) {
				e=codCarre.Elegir();
				int codCarreAux = e;
				codigoMat = dic.Recuperar(codCarreAux); //con el codigo de carrera recupero los codigos de marerias
				int codigoMatAux = 0;
				while (!codigoMat.ConjuntoVacio()) {
					h=codigoMat.Elegir();
					codigoMatAux = h;
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
	     
			
			/*DiccionarioMultipleTDA diccMFinal = new DicMultipleL();
			diccMFinal.InicializarDiccionario()	;
			*/
			System.out.println("2f. Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
					"incluir materias optativas)");
			
			Metodos m = new Metodos();
			ConjuntoTDA codCarre = dic.Claves();			 			
			ConjuntoTDA codsMater ;
			DiccionarioMultipleTDA diccM = new DicMultipleL();
			diccM.InicializarDiccionario();		
			int varcodsMater =0;
			int codCarreAux=0;
			while(!codCarre.ConjuntoVacio()){
				codCarreAux = codCarre.Elegir();
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

        System.out.println("2g. Carrera/s que incluyan más del 80% de las materias de otra carrera, indicando las\r\n" + 
                "carreras relacionadas.");
        Metodos m = new Metodos();
        int c = m.contadorConjunto(dic.Claves());
        int i, j;
        boolean cumple;
        int[] vectorCarreras = m.carrerasAVector(dic.Claves());
        ConjuntoTDA carreras = new ConjuntoLD();
        ConjuntoTDA carreras2 = new ConjuntoLD();
        carreras.InicializarConjunto();
        carreras = dic.Claves();
        int e=0;
        int h=0;
        while(!carreras.ConjuntoVacio()) {
            carreras2.InicializarConjunto();
            carreras2 = dic.Claves();
            while(!carreras2.ConjuntoVacio()) {
            	e=carreras.Elegir();
            	h=carreras2.Elegir();
                if(e!=h) {
                    cumple = m.cumplePorcentaje(dic.Recuperar(e), dic.Recuperar(h));
                    if(cumple) {
                        System.out.println("La carrera " + m.NombreCarrera(e) + " (Codigo: " + e + ")" + " comparte mas del 80% de las materias de la carrera " + m.NombreCarrera(h) + " (Codigo: " + h + ")");
                    }	
                }
                carreras2.Sacar(h);
            }
            carreras.Sacar(e);
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
		System.out.println("2h. Materias de cada carrera que no comparten con ninguna otra carrera, ordenadas por código de materia, indicando la carrera a la que pertenecen.");
        Metodos m = new Metodos();
		boolean pertenece;
        ConjuntoTDA materias = new ConjuntoLD();
        ConjuntoTDA carreras = new ConjuntoLD();
        ConjuntoTDA carreras2 = new ConjuntoLD();
        carreras.InicializarConjunto();
        carreras = dic.Claves();
        materias.InicializarConjunto();
        carreras2.InicializarConjunto();
        int e=0;
        int h=0;
        int j=0;
        while(!carreras.ConjuntoVacio()) {//1
        	e=carreras.Elegir();
            materias = dic.Recuperar(e);
            while(!materias.ConjuntoVacio()) {//2
                pertenece = false;
                carreras2 = dic.Claves();
                while(!carreras2.ConjuntoVacio()) {//3
                	h=carreras2.Elegir();
                	j=materias.Elegir();
                    if (e!=h && pertenece==false) {
                        pertenece = dic.Recuperar(h).Pertenece(j);
                    }
                    carreras2.Sacar(h);
                }
                if (j>100) {
                	if(!pertenece) {
                		System.out.println("La materia " + m.NombreMater(j) + " (Codigo: " + j + ")" + " pertenece unicamente a la carrera "  + m.NombreCarrera(e) + " (Codigo: " + e + ")" );
                	}
                }
                materias.Sacar(j);
            }
            carreras.Sacar(e);
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
        Metodos m = new Metodos();
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
		System.out.println("2i. Carrera/s que sólo tengan el 20% de las materias de otra carrera, indicando las carreras relacionadas.");
		int e=0;
		int h=0;
		int j=0;
		int contador=0;
		while(!claves1.ConjuntoVacio()) {//1
			while(!claves2.ConjuntoVacio()){//2
				e=claves2.Elegir();
				h=claves1.Elegir();
				valores1 = var.Recuperar(h);
				valores2 = var.Recuperar(e);
					if (!clavesrep.Pertenece(h) && !clavesrep.Pertenece(e) && !valores1.ConjuntoVacio() && h != e) {
							while(!valores2.ConjuntoVacio()) {//3
								j=valores2.Elegir();
								if(j>100) //Se verifica que si la materia es optativa no se tiene en cuenta para el total de las materias
									mattotal ++;
									if (valores1.Pertenece(j)) 
										matcoinc ++;
								valores2.Sacar(j);
							}
							c = m.porcentaje(mattotal, matcoinc, c);
							if (c <= 20) {
					            contador ++;
					        }
							System.out.println("La Carrera " + m.NombreCarrera(h)+ " (Codigo: " + h + ")" + " tiene en común con la carrera "+ m.NombreCarrera(e) + " (Codigo: " + e + ")" + " el " + c + "% de sus materias.");
							mattotal = 0;
							matcoinc = 0;
							claves2.Sacar(e);
							if (!claves2.ConjuntoVacio())
								valores2 = var.Recuperar(e);
							}

					else {
						claves2.Sacar(h);
				}
			}
			claves1.Sacar(h);
			claves2 = var.Claves();
		}
		if (contador == 0)
			System.out.println(" No se encontraron carreras con el 20% de sus materias en común.");
		 System.out.println("------------------------------------------------------------------------------------------------------------------");
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
        Metodos m = new Metodos();
		ConjuntoTDA carreras1 = var.Claves();
		ConjuntoTDA carreras2 = var.Claves();
		ConjuntoTDA repasadas = new ConjuntoLD();
		System.out.println("2j. Para cada combinación de dos carreras, indicar materias no comunes. ");
		ConjuntoTDA materias1;
		ConjuntoTDA materias2;
		ConjuntoTDA mnc;
		int e=0;
		int j=0;
		int h=0;
		while (!carreras2.ConjuntoVacio()) {
			while (!carreras1.ConjuntoVacio()) {
				e=carreras1.Elegir();
				j=carreras2.Elegir();
				if (e == j || repasadas.Pertenece(e)) {
					carreras1.Sacar(e);
				}
				else {
					materias1 = var.Recuperar(e);
					materias2 = var.Recuperar(j);
					mnc = m.check(materias1,materias2);
					System.out.println("Materias que la carrera " + m.NombreCarrera(j) + " (Codigo: " + j + ")" + " y la " + m.NombreCarrera(e) + " (Codigo: " + e + ")"+ " no tienen en común:");
					while(!mnc.ConjuntoVacio()) {
						h=mnc.Elegir();
						if(h>100) {
							System.out.println(m.NombreMater(h) + " (Codigo: " + h + ")");
						}
						mnc.Sacar(h);
					}
					carreras1.Sacar(e);
					
				}
			}
			
			repasadas.Agregar(j);
			carreras2.Sacar(j);
			carreras1 = var.Claves();
		}
		 System.out.println("------------------------------------------------------------------------------------------------------------------");
	}	
	/**
	 * @Tarea 3a. Generar un grafo de correlativas donde cada vértice es una materia y el arco la relación de cursada con peso 1
	 * @Parámetros DiccionarioMultipleTDA 
	 * @Devuelve Genera el grafo
	 * @Precondición El diccionario debe estar inicializado y cargado
	 * @Postcondición -
	 * @Costo Cuadratico (ciclo while dentro de otro ciclo while)
	 */	
	 public static GrafoTDA generargrafo(DiccionarioMultipleTDA dicc) {
		 	System.out.println("3a. Generar un grafo de correlativas donde cada vértice es una materia y el arco la relación de cursada con peso 1");
	        GrafoTDA grafo = new Grafo();
	        grafo.inicializarGrafo();
	        ConjuntoTDA claves = dicc.Claves();
	        ConjuntoTDA valores;
	        int aux;
	        int aux2;
	        while (!claves.ConjuntoVacio()) {
	            aux = claves.Elegir();
	            if (!grafo.vertices().Pertenece(aux)) {
	                grafo.agregarVertice(aux);
	            }
	            valores = dicc.Recuperar(aux);
	            while(!valores.ConjuntoVacio()) {
	                aux2 = valores.Elegir();
	                
	                if(aux2 != 0) {
	                    if (!grafo.vertices().Pertenece(aux2)) {
	                        grafo.agregarVertice(aux2);
	                    }
	                    if(!grafo.existeArista(aux2, aux)) {
	                        grafo.agregarArista(aux2, aux, 1);
	                    }                   
	                }
	                valores.Sacar(aux2);
	            }
	                claves.Sacar(aux);
	        }
	        System.out.println("------------------------------------------------------------------------------------------------------------------");
	        return grafo;
	    }
	 /**
		 * @Tarea 3b. Informar la/s materia/s con mayor cantidad de correlativas precedentes inmediatas, ordenadas por código.
		 * @Parámetros GrafoTDA 
		 * @Devuelve la materia con la mayor cantidad de precedentes
		 * @Precondición El grafo debe estar inicializado y cargado
		 * @Postcondición -
		 * @Costo Cuadratico (ciclo while dentro de otro ciclo while)
		 */	
	    public static void informarMayorPrecedentes(GrafoTDA gra) {
	    	System.out.println("3b. Informar la/s materia/s con mayor cantidad de correlativas precedentes inmediatas, ordenadas por código.");
	        ConjuntoTDA ver = gra.vertices();
	        ConjuntoTDA var;
	        ConjuntoTDA mayor = new ConjuntoLD();
	        int aux;
	        int aux2;
	        int aux3;
	        int cont;
	        int max = 0;
	        Metodos m = new Metodos();
	        ColaPrioridadTDA colaux = new ColaPrioridad();
	        colaux.inicializarCola();
	        
	        while(!ver.ConjuntoVacio()) {
	            aux = ver.Elegir();
	            var = gra.vertices();
	            cont = 0;
	            
	            while(!var.ConjuntoVacio()) {
	                aux2 = var.Elegir();
	                
	                if (gra.existeArista(aux2, aux))
	                    cont ++;
	                
	                var.Sacar(aux2);
	            }
	            
	            if (cont > max) {
	                while(!mayor.ConjuntoVacio()) {
	                    mayor.Sacar(mayor.Elegir());
	                }
	                mayor.Agregar(aux);
	                max = cont;
	            } else if(cont == max)
	                mayor.Agregar(aux);
	            
	            ver.Sacar(aux);
	            
	        }
	        
	        System.out.println("Las materias con la mayor cantidad de precedentes inmediatas son:");
	        
	        while(!mayor.ConjuntoVacio()) {
	            aux3 = mayor.Elegir();
	            colaux.acolarPrioridad(aux3, aux3);
	            mayor.Sacar(aux3);
	        }
	
	        while(!colaux.ColaVacia()) {
	        	System.out.println(m.NombreMater(colaux.primero()) + " (Codigo: " + colaux.primero() + ")");
		        colaux.desacolar();
		    }
	        
	        System.out.println("------------------------------------------------------------------------------------------------------------------");
	    }
	    /**
		 * @Tarea 3b. Informar la/s materia/s con mayor cantidad de correlativas subsiguientes inmediatas.
		 * @Parámetros GrafoTDA
		 * @Devuelve las materias con la mayor cantidad de subsiguientes
		 * @Precondición El grafo debe estar inicializado y cargado
		 * @Postcondición -
		 * @Costo Cuadratico (ciclo while dentro de otro ciclo while)
		 */	
	    public static void informarMayorSubsiguientes(GrafoTDA gra) {
	    	System.out.println("3b. Informar la/s materia/s con mayor cantidad de correlativas subsiguientes inmediatas.");
	        ConjuntoTDA ver = gra.vertices();
	        ConjuntoTDA var;
	        ConjuntoTDA mayor = new ConjuntoLD();
	        ColaPrioridadTDA colaux = new ColaPrioridad();
	        colaux.inicializarCola();
	        int aux;
	        int aux2;
	        int aux3;
	        int cont;
	        int max = 0;
	        Metodos m = new Metodos();
	        
	        while(!ver.ConjuntoVacio()) {
	            aux = ver.Elegir();
	            var = gra.vertices();
	            cont = 0;
	            while(!var.ConjuntoVacio()) {
	                aux2 = var.Elegir();
	                
	                if (gra.existeArista(aux, aux2))
	                    cont ++;
	                var.Sacar(aux2);
	            }
	            
	            if (cont > max) {
	                while(!mayor.ConjuntoVacio()) {
	                    mayor.Sacar(mayor.Elegir());
	                }
	                mayor.Agregar(aux);
	                max = cont;
	            } else if(cont == max)
	                mayor.Agregar(aux);
	            ver.Sacar(aux);
	        }
	        System.out.println("Las materias con la mayor cantidad de subsiguientes inmediatas son:");
	        
	        while(!mayor.ConjuntoVacio()) {
	            aux3 = mayor.Elegir();
	            colaux.acolarPrioridad(aux3, aux3);
	            mayor.Sacar(aux3);
	        }
	        while(!colaux.ColaVacia()) {
	            System.out.println(m.NombreMater(colaux.primero()) + " (Codigo: " + colaux.primero() + ")");
	            colaux.desacolar();
	        }
	        System.out.println("------------------------------------------------------------------------------------------------------------------");
	    }
	    /**
		 * @Tarea 3b. Porcentaje de materias que no tienen correlativas precedentes ni subsiguientes
		 * @Parámetros GrafoTDA
		 * @Devuelve el porcentaje de materias que no poseen precendentes ni subsiguientes
		 * @Precondición El grafo debe estar inicializado y cargado
		 * @Postcondición -
		 * @Costo Cuadratico (ciclo while dentro de otro ciclo while)
		 */	
	    public static void informarPorcentajeSinCorrelativas(GrafoTDA gra) {
	    	System.out.println("3b. Porcentaje de materias que no tienen correlativas precedentes ni subsiguientes");
	        ConjuntoTDA ver = gra.vertices();
	        ConjuntoTDA var;
	        int aux;
	        int aux2;
	        int total = 0;
	        boolean cont;
	        int cant = 0;
	        
	        while(!ver.ConjuntoVacio()) {
	            aux = ver.Elegir();
	            var = gra.vertices();
	            cont = false;
	            
	            while(!var.ConjuntoVacio() && cont == false) {
	                aux2 = var.Elegir();
	                
	                if (gra.existeArista(aux, aux2) || gra.existeArista(aux2, aux))
	                    cont = true;
	                
	                var.Sacar(aux2);
	            }
	            
	            if (cont == false) 
	                cant ++;
	            
	            total ++;
	            ver.Sacar(aux);
	        }
	        System.out.println("El " + ((cant * 100) / total) + "% de las materias no tienen correlativas");
	        System.out.println("------------------------------------------------------------------------------------------------------------------");
	    }
    
}

