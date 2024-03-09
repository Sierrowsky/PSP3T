/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad3_12servidorchat;

import java.io.*;
import java.net.*;
public class Actividad3_12ServidorChat {
     static final int MAXIMO = 5;//MAXIMO DE CONEXIONES PERMITIDAS	
    
    public static void main(String[] args)throws IOException {
        int PUERTO = 44444;	
		
	ServerSocket servidor = new ServerSocket(PUERTO);
	System.out.println("Servidor iniciado...");
	
	Socket tabla[] = new Socket[MAXIMO];//para controlar las  distintas conexiones	
	ComunHilos comun = new ComunHilos(MAXIMO, 0, 0, tabla); //para distintos hilos
         //esta vez proceso servidor no es un ciclo sin fin  
         while (comun.getCONEXIONES() < MAXIMO) {
	   Socket socket = new Socket(); //para nueva conexión			
	   socket = servidor.accept();// esperando cliente
	
	   comun.addTabla(socket, comun.getCONEXIONES());
	   comun.setACTUALES(comun.getACTUALES() + 1);
	   comun.setCONEXIONES(comun.getCONEXIONES() + 1);
           //lanzo un hilo para cada cliente conectado
	   // socket distinto para cada hilo, MISMO OBJETO comun PARA TODOS LOS HILOS
           // ESTE OBJETO oomun ES MANIPULADO POR EL HILO PRIMARIO EN CADA NUEVA CONEXIÓN
           // Y POR CADA UNO DE LOS HILOS EN LOS MENSAJES QUE AÑADEN AL CHAT Y EN EL CONTADOR DE CONEXIONES
	   HiloServidorChat hilo = new HiloServidorChat(socket, comun);
	   hilo.start();
	}	
	   //EL SERVIDOR SE DESCONECTA AL NO PODER ACEPTAR NUEVAS CONEXIONES	
         servidor.close();
	}//main
           
                
    }
    
