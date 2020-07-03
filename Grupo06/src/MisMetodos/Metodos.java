package MisMetodos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import miApi.ConjuntoTDA;
import miApi.DiccionarioMultipleTDA;
import miApi.DiccionarioSimpleTDA;
import misImplementaciones.ConjuntoLD;
public class Metodos {
	/**
	 * @param DiccionarioMultipleTDA var,String codini(codigo con el que debe iniciar las materias ,int car(carrera)
	 * @return Porcentaje de materias que comienzan con un numero especifico de una carrera
	 */
	public int porcentajeMaterias(DiccionarioMultipleTDA var, String codini, int car) {
		int cont = 0;
		int cont1 = 0;
		ConjuntoTDA valores = var.Recuperar(car);
		int aux=0;
		int e=0;
    	while(!valores.ConjuntoVacio()) {
    		e=valores.Elegir();
    		aux = e;
    		if (Integer.toString(aux).startsWith(codini) && aux > 100)//Se pasa el codini como string ya que startswith usa un string.Tambien se verifica que no haya materias optativas
    			cont1 += 1;
    		if(aux > 100)
    			cont += 1;
    		valores.Sacar(aux);
    	}
    	
    	return (cont1 * 100) / cont;
	}
	/**
	 * @param codMater
	 * @return Nombre de la materia
	 */
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
	/**
	 * @param codCarre
	 * @return Nombre de la carrera
	 */
	public String NombreCarrera(int  codCarre) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String nombre= "";
		String linea= "";

		try {
			archivo = new File ("Materias.csv");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			br.readLine();
			linea = br.readLine();
			

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
	/**
	 * @param 2 ConjuntoTDA(a y b)
	 * @return Verdadero o Falso en caso de cumplir o no con el porcentaje
	 */
	public boolean cumplePorcentaje(ConjuntoTDA a, ConjuntoTDA b) {
		int c = 0;
		int cantMaterias = contadorConjunto(b);
		boolean cumpleCondicion = false;
		int porcentaje=0;
		int e=0;
		while(!a.ConjuntoVacio()) {
			e=a.Elegir();
			if (b.Pertenece(e)) {
				c++;
			}
			a.Sacar(e);
		}
		porcentaje = (c*100)/cantMaterias;
		if(porcentaje>80) {
			cumpleCondicion = true;
		}
		return cumpleCondicion;
	}
	/**
	 * @param ConjuntoTDA
	 * @return Carreras
	 */
	public int[] carrerasAVector(ConjuntoTDA a) {
		ConjuntoTDA aux = new ConjuntoLD();
		aux.InicializarConjunto();
		int c = contadorConjunto(a);
		int[] carreras = new int[c];
		c-=1;
		int e=0;
		while((!a.ConjuntoVacio()) && c>=0) {
			e=a.Elegir();
			carreras[c]=e;
			aux.Agregar(e);
			a.Sacar(e);
			c--;
		}
		PasarConjunto(aux,a);
		return carreras;	
	}
	public int contadorConjunto(ConjuntoTDA a) {
		ConjuntoTDA aux = new ConjuntoLD();
		aux.InicializarConjunto();
		int c = 0;
		int e=0;
		while(!a.ConjuntoVacio()) {
			e=a.Elegir();
			c++;
			aux.Agregar(e);
			a.Sacar(e);
		}
		PasarConjunto(aux, a);
		return c;
	}
	/**
	 * @param ConjuntoTDA de origen y otro destino
	 * @return ConjuntoTDA origen vacio y el ConjuntoTDA destino queda cargado
	 */
	public void PasarConjunto(ConjuntoTDA o, ConjuntoTDA d){
		int e=0;
		while(!o.ConjuntoVacio()){
			e=o.Elegir();
			d.Agregar(e);
			o.Sacar(e);

		}
	}
	/**
	 * @param ConjuntoTDA a y b
	 * @return
	 */
	public static ConjuntoTDA check(ConjuntoTDA a, ConjuntoTDA b) {
		ConjuntoTDA mnc = b;
		int e=0;
		while(!a.ConjuntoVacio()) {
			e=a.Elegir();
			if(mnc.Pertenece(e)) {
				mnc.Sacar(e);
				a.Sacar(e);
			}
			else
				a.Sacar(e);
		}
		return mnc;
	}
	
	/**
	 * @param ConjuntoTDA
	 * @return materias
	 */
	public static int mostrarmaterias(ConjuntoTDA mnc) {
		int e=0;
		e=mnc.Elegir();
		mnc.Sacar(e);
		return e;
	}
	
	
	/**
	 * @param total, tengo y contador
	 * @return Porcentaje 
	 */
	public int porcentaje(int total, int tengo, int contador) {
        int a = 0;
        a = tengo * 100 / total;
        if (a <= 20) {
            contador ++;
        }
        return a;
    }
}
