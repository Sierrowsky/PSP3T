
package actividadservidorudpobjeto;
import java.io.*;
import java.net.*;

public class ActividadServidorUDPObjeto {

    
    public static void main(String[] args) throws Exception{
        // primero defino objeto de tipo DatagramSocket con un puerto específico
		// porque comienzo con  el roll de proceso servisor a la escucha..
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		//donde almacenar el mensaje contenido en el datagrama que voy a recibir
		  byte[] recibidos = new byte[1024]; 
		
		
		// instancio un objeto DatagramaPacket para leer datagaramas
		
		DatagramPacket paqRecibido = new DatagramPacket(recibidos,
				recibidos.length);
				
				
		System.out.println("Esperando datagrama.....");	
                serverSocket.receive(paqRecibido);

		// CONVERTIMOS   DESDE LOS BYTES RECIBIDOS EN EL DATAGRAMA A OBJETO 
		// EN TRES PASOS: obtenemos un stream de entrada de array de bytes, b
		// segundo con b obtenemos un stream de entrada de objectos
		// por último leemos desde el el ObjectInputStream el objeto
		
		ByteArrayInputStream b = new ByteArrayInputStream(recibidos); 
		ObjectInputStream in = new ObjectInputStream(b);
		
		Persona persona = (Persona) in.readObject();
		in.close();
                
                // DESDE EL DATAGRAMA RECIBIDO
		// OBTENEMOS DIRECCION ORIGEN DEL MENSAJE. IP Y PUERTO DEL EMISOR DEL DATAGRAMA
		InetAddress IPOrigen = paqRecibido.getAddress();
		int puerto = paqRecibido.getPort();
		System.out.println("\tProcedente de: " + IPOrigen + ":" + puerto);
		System.out.println("\tDatos transmitidos: " + persona.getNombre() + "**"
				+ persona.getEdad());
                
                // ahora  nosotros cambiamos los datos recibidos
		persona.setNombre("Maria Jesus");
		persona.setEdad(34);
		 // para enviar un objeto a través de sockets datagram:
		 
		//  primero obtenemos bs un stream de salida de array de bytes 
		
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		 // segundo obtenemos  desde bs, un stream de salida de objetos os
	    ObjectOutputStream os = new ObjectOutputStream (bs);
		//sobre  el stream de escritura de objetos os escribo el objeto
	    os.writeObject(persona);  
	    os.close();
            
            //ahora por fin lo almaceno en el array de bytes a enviar en el datagrama
	    byte[] bytes =  bs.toByteArray(); 
		
	    // CONTESTANDO  AL CLIENTE
		System.out.println("Enviando " + bytes.length + " bytes al cliente.");
		// implemento datagrama para enviar, distinto constructor que datagrama para recibir
		DatagramPacket envio = new DatagramPacket(bytes, bytes.length,
				                     IPOrigen, puerto);
		serverSocket.send(envio);	
		serverSocket.close();
		System.out.println("Socket cerrado...");
                
    }
    
}
