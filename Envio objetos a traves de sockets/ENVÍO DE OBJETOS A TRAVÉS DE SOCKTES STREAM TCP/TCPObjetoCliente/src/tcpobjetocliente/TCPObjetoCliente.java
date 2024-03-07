
package tcpobjetocliente;
import java.io.*;
import java.net.*;
// ENVÍO Y LECTURA DE OBJETOS A TRAVÉS DE SOCKETS STREAM TCP
// CLASES ObjectInputStream y ObjectOutputStream
// MÉTODOS .writeObject(objeto_enviado) y .readObject()
public class TCPObjetoCliente {

    
    public static void main(String[] args)throws IOException,ClassNotFoundException {
        
    String Host = "localhost";
    int Puerto = 6000;//puerto remoto por el que escucha el servidor	
		
    
	//socket para comunicarme con esa aplicación servidor de objetos de tipo persona
    Socket cliente = new Socket(Host, Puerto);	
	
    // defino Flujo de entrada para objetos
    ObjectInputStream objetosEnt = new ObjectInputStream(
			             cliente.getInputStream());
	// defino FLUJO DE salida para objetos 		
    ObjectOutputStream objetosSal = new ObjectOutputStream(
			              cliente.getOutputStream());   
    System.out.println("PROGRAMA CLIENTE INICIADO....");
    //proceso recepción y envío de objetos a través de sockets TCP						  
						 
    //Se recibe un objeto
    Persona dato = (Persona) objetosEnt.readObject();//recibo objeto
    System.out.println("Recibo: "+dato.getNombre()+"*"+dato.getEdad());
    
    //Modifico el objeto
    dato.setNombre("Juan Ramos");
    dato.setEdad(22);
	
    
    // Se envía el objeto
    objetosSal.writeObject(dato);
    System.out.println("Envio: "+dato.getNombre()+"*"+dato.getEdad());                       
		
    // CERRAR STREAMS Y SOCKETS
    objetosEnt.close();
    objetosSal.close();
    cliente.close();	
    
}
}