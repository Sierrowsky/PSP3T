
package tcpobjetoservidor;
import java.io.*;
import java.net.*;
//ENVÍO Y LECTURA DE OBJETOS A TRAVÉS DE SOCKETS STREAM TCP
// CLASES ObjectInputStream y ObjectOutputStream
//MÉTODOS .writeObject(objeto_enviado) y .readObject()
public class TCPObjetoServidor {

    
    public static void main(String[] args)throws IOException,ClassNotFoundException  {
     int numeroPuerto = 6000;// Puerto definido para escuchar
     ServerSocket servidor =  new ServerSocket(numeroPuerto);
     System.out.println("Esperando al cliente....."); 
   
     Socket cliente = servidor.accept();
     // Se define un flujo de salida para objetos 		
      ObjectOutputStream outObjeto = new ObjectOutputStream(
				cliente.getOutputStream()); 
     // Se define un stream de entrada  para leer objetos
      ObjectInputStream inObjeto = new ObjectInputStream(
				cliente.getInputStream());
      //proceso.. de envío y lectura de objetos a través de sockets TCP
	
      // Se prepara un objeto y se envía 
    Persona per = new Persona("Juan", 20);
     outObjeto.writeObject(per); //enviando objeto,
                                 //SOBRE EL STREAM DE SALIDA SE EJECUTA MÉTODO writeObject(objeto_enviado)
     System.out.println(" Hemos enviado:   " + per.getNombre() +"**"+ per.getEdad()); 
    // se escucha para leer un objeto desde el cliente
    Persona dato = (Persona) inObjeto.readObject();
   System.out.println(" Hemos recibo:  "+dato.getNombre()+"**"+dato.getEdad());
		
   // CERRAR STREAMS Y SOCKETS
   outObjeto.close();
   inObjeto.close();
   cliente.close();
   servidor.close(); 
     
    }
   
    
}
