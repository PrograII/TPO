package MisMetodos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import miApi.ConjuntoTDA;
import miApi.DiccionarioMultipleTDA;
import miApi.DiccionarioSimpleTDA;
import misImplementaciones.ConjuntoLD;
public class Metodos {
	public int porcentajeMaterias(DiccionarioMultipleTDA var, String comp, int car) {
		int cont = 0;
		int cont1 = 0;
		ConjuntoTDA valores = var.Recuperar(car);
		
    	while(!valores.ConjuntoVacio()) {
    		
    		int aux = valores.Elegir();
    		
    		if (Integer.toString(aux).startsWith(comp) && aux > 9)
    			cont1 += 1;
    		
    		if(aux > 9)
    			cont += 1;
    		
    		valores.Sacar(aux);
    	}
    	
    	return (cont1 * 100) / cont;
		
	}
	
	public String NombreMater(int  codMater) {
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
	
	public String NombreCarrera(int  codCarre) {
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
	
	public boolean cumplePorcentaje(ConjuntoTDA a, ConjuntoTDA b) {
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
		if(porcentaje>80) {
			cumpleCondicion = true;
		}
		return cumpleCondicion;
	}
	public int[] carrerasAVector(ConjuntoTDA a) {
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
	public int contadorConjunto(ConjuntoTDA a) {
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
	public void PasarConjunto(ConjuntoTDA o, ConjuntoTDA d){
		while(!o.ConjuntoVacio()){
			d.Agregar(o.Elegir());
			o.Sacar(o.Elegir());

		}
	}
	/****Metodos TDA***/
	
	

}
