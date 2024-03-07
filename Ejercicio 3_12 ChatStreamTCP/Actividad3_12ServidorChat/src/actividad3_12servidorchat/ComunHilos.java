package actividad3_12servidorchat;
import java.net.Socket;
/*clase que define al objeto compartido por todos los hilos del chat
tiene definidos varios métodos sincronizados.
*/
public class ComunHilos {	
	 int CONEXIONES; //NUMERO  TOTAL DE CONEXIONES REALIZADAS HASTA EL MOMENTO
	 int ACTUALES;   //NUMERO DE CONEXIONES ACTUALES
	 int MAXIMO;     //MAXIMO DE CONEXIONES PERMITIDAS	
	 Socket tabla[] = new Socket[MAXIMO];// SOCKETS CONECTADOS, ARRAY DE SOCKTES PARA COMUNICARSE CON TODOS
	 String mensajes; //MENSAJES DEL CHAT
	
	public ComunHilos(int maximo, int actuales, int conexiones, 
                                         Socket[] tabla) {
		MAXIMO = maximo;	 
		ACTUALES = actuales; 
		CONEXIONES = conexiones;	
		this.tabla = tabla;  
		mensajes="";        
	}

	public ComunHilos() { super(); }

     public int getMAXIMO() { return MAXIMO;	}
	public void setMAXIMO(int maximo) { MAXIMO = maximo;}


	public int getCONEXIONES() { return CONEXIONES;	}
        
	public synchronized void  setCONEXIONES(int conexiones) {
		CONEXIONES = conexiones;
	}

	public String getMensajes() { return mensajes; }
        
	public synchronized void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}
	
	public int getACTUALES() { return ACTUALES; }
        
	public synchronized void setACTUALES(int actuales) {
		ACTUALES = actuales;
	}
    
	// almacenar  un nuevo socket en posición i
	public synchronized void addTabla(Socket s, int i) {		
		tabla[i] = s;
	}	
	public Socket getElementoTabla(int i) { return tabla[i]; }
		
}//ComunHilos
