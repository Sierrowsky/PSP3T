
package actividadclienteudpobjeto;


import java.io.*;
import java.net.*;

public class ActividadClienteUDPObjeto {

    
    public static void main(String[] args)throws Exception {
       // instanciamos un DatagramaSocket,  tenemos el roll cliente
       // no definimos para el socket un puerto específico
       // somos los que vamos a inciar la comunicación enviando
       // un datagrama con IP y puerto de la otra aplicación, 
	DatagramSocket clientSocket = new DatagramSocket();	
		
	byte[] recibidos = new byte[1024]; //para contener el mensaje 

		// DATOS DEL SERVIDOR
		InetAddress IPServidor = InetAddress.getLocalHost();// localhost
		int puerto = 9876; // puerto por el que escucha

		Persona per = new Persona("Maria", 22);  
                //para enviar objeto a través del socket UDP
		//  primero obtenemos  en bs un stream de salida de array de bytes
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		 // segundo obtenemos  desde bs, un stream de salida de objetos os
	    ObjectOutputStream os = new ObjectOutputStream (bs);
		//sobre  el stream de escritura de objetos os escribo el objeto
	    os.writeObject(per);  
	    os.close();
		//ahora por fin lo almaceno en el array de bytes a enviar en el datagrama
	    byte[] bytes =  bs.toByteArray(); 
            // ENVIANDO AL SERVIDOR
		System.out.println("Enviando " + bytes.length + " bytes al servidor.");
		//instancio el datagrama para enviar, constructor distinto si es un datagrama
                // para recibir		
		DatagramPacket envio = new DatagramPacket(bytes, bytes.length,
				IPServidor, puerto);
	    // envío el datagramapor elsocket			
		clientSocket.send(envio);
             // RECIBIENDO DEL SERVIDOR
		//Instancio datagrama para recibir de la otra aplicación
		DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
		System.out.println("Esperando datagrama....");
		clientSocket.receive(recibo);
		// CONVERTIMOS   DESDE LOS BYTES RECIBIDOS EN EL DATAGRAMA A OBJETO 
		// EN TRES PASOS: obtenemos un stream de entrada de array de bytes, b
		// segundo con b obtenemos un stream de entrada de objectos
		// por último leemos desde el ObjectInputStream el objeto	
		ByteArrayInputStream b= new ByteArrayInputStream(recibidos); // bytes es el byte[]
	    ObjectInputStream is = new ObjectInputStream(b);
	    Persona persona = (Persona)is.readObject();
	    is.close();
            //visualizo datos
		InetAddress IPOrigen = recibo.getAddress();
		int puertoOrigen = recibo.getPort();
		System.out.println("\tProcedente de: " + IPOrigen + ":" + puertoOrigen);
		System.out.println("\tDatos: " + persona.getNombre() +"*"+persona.getEdad());
		clientSocket.close();//cerrar socket
            
                
                
    }
    
}
