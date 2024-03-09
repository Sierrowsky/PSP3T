package actividad3_12servidorchat;

import java.io.*;
import java.net.*;

public class HiloServidorChat extends Thread {
	DataInputStream fentrada;
	Socket socket = null;  //para recibir el socket cliente , el hilo atiende a este cliente
	ComunHilos comun; //para recibir objeto comun a todos los hilos,en este objeto tenemos referenciados
                          //a todos los demás sockets

	public HiloServidorChat(Socket s, ComunHilos comun) {
		this.socket = s;
		this.comun = comun; /*objeto compartido por todos los hilos*/
		try {
			// CREO FLUJO DE entrada para leer los mensajes que envía mi cliente desde su aplicación
			// flujo de entrada para leer en él tipos primitivos java
			fentrada = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
		}
	}// ..fin constructor del hilo
  //tarea que realiza el hilo para atender a su cliente en el chat
    	public void run() {
		System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());

		// NADA MAS CONECTARSE LE ENVIO TODOS LOS MENSAJES
		String texto = comun.getMensajes();
		EnviarMensajesaTodos(texto);
                //ahora me pongo a atender a mi cliente en un ciclo donde escucho sus mensajes
		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {// EL CLIENTE SE DESCONECTA
                                        //accedo a una sección crítica del objeto, mi cliente se desconecta
                                        //o bien la ejecuto o bien me bloqueo en espera del monitor del objeto
					comun.setACTUALES(comun.getACTUALES() - 1);
					System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getACTUALES());
					break;
				}
                                //el cliente no se desconecta, sino que escribe un mensaje en el chat/
                                // accedo a una sección crítica del objeto, mi cliente añade un mensaje en el objeto compartido
                                ////o bien la ejecuto o bien me bloqueo en espera del monitor del objeto
				comun.setMensajes(comun.getMensajes() + cadena + "\n");
                                
				EnviarMensajesaTodos(comun.getMensajes());
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		} // fin while

		// se cierra el socket del cliente, porque el cliente se desconecta con un '*'
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// run

	// ENVIA LOS MENSAJES DEL CHAT A LOS CLIENTES
	private void EnviarMensajesaTodos(String texto) {
		int i;
		// recorremos tabla de sockets para enviarles los mensajes
		for (i = 0; i < comun.getCONEXIONES(); i++) {
			Socket s1 = comun.getElementoTabla(i);
			if (!s1.isClosed()) {
				try {
					DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
					fsalida.writeUTF(texto);					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // for
		
	}// EnviarMensajesaTodos

}// ..HiloServidorChat