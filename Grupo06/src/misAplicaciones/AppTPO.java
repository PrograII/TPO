package misAplicaciones;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
		Collections.sort(meteriaList);		
		
		contidadMateriasOptativas(meteriaList);		
		
	}
	
	  public static class Materias implements Comparable<Materias> 
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
		   

		    
		    public Integer getnroCarrera() {
		        return nroCarrera;
		      }
		      
		      public void setnroCarrera(Integer nroCarrera) {
		        this.nroCarrera = nroCarrera;
		      }
		    
		    @Override
		    public int compareTo(Materias u) {
		      if (getnroCarrera() == 0 || u.getnroCarrera() == 0) {
		        return 0;
		      }
		      return getnroCarrera().compareTo(u.getnroCarrera());
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
	@Tarea : 2e. Cantidad de materias optativas de cada una de las carreras			 
	@Parámetros
	@Devuelve
	@Precondición
	@Postcondición
	@Costo
	**/
	
	public static void contidadMateriasOptativas( ArrayList<Materias>  materiasList	) {  
	   
	    try {        
	    	
	    	DiccionarioSimpleTDA diccS = new DicSimpleL();
	    	diccS.InicializarDiccionario();
	    	int nroCarrera = 0;	
	    	
	    	
	    	
	    	System.out.println("2e Cantidad de materias optativas de cada una de las carreras:");
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
	
	public static void materiasComnes( ArrayList<Materias>  materiasList) {  
	   
		 try {        
		    	
		    	DiccionarioSimpleTDA diccS = new DicSimpleL();
		    	diccS.InicializarDiccionario();
		    	int nroCarrera = 0;	
		    	
		    	System.out.println("2Materias comunes a todas las carreras indicadas, ordenadas por código de materia (no\r\n" + 
		    			"				incluir materias optativas)");
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
		    	mostrar(diccS);
		    }
		    catch(Exception e){
		        e.printStackTrace();
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
				System.out.println("Clave:"+clave);
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
				System.out.print("-");
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

