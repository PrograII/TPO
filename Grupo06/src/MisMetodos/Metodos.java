package MisMetodos;
import miApi.ConjuntoTDA;
import miApi.DiccionarioMultipleTDA;
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

}
