/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package py.una.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import py.una.bd.Operacion;
import static py.una.tcp.Menu.menuCliente;
import static py.una.tcp.Menu.menuError;
import static py.una.tcp.Menu.menuRespuesta;

/**
 *
 * @author Santi
 */
public class Cliente {

    public static void main(String[] args) throws Exception {

        String direccionServidor = "127.0.0.1";
        int puertoServidor = 4444;

        if (args.length == 2) {
            direccionServidor = args[0];
            puertoServidor = Integer.parseInt(args[1]);
        }else if(args.length!=0){
            menuError("Error en los args");
            System.exit(1);
        }
        
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int TimeOutConexion = 7000; //milisegundos
        int TimeOutRecepcion = 5000; //milisegundos
        long ini = 0;
        long fin = 0;


        try {

            SocketAddress sockaddr = new InetSocketAddress(direccionServidor, puertoServidor);
            kkSocket = new Socket();

   	    ini = System.currentTimeMillis();
            kkSocket.connect(sockaddr, TimeOutConexion);
            kkSocket.setSoTimeout(TimeOutRecepcion);
			
            // enviamos nosotros
            out = new PrintWriter(kkSocket.getOutputStream(), true);

            //viene del servidor
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }catch (SocketTimeoutException e){
            fin = System.currentTimeMillis();
            menuError("Fallo de Timeout de conexion en " + TimeOutConexion);
            menuError("Duracion " + (fin-ini));
            System.exit(1);
        }catch (UnknownHostException e) {
            menuError("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            menuError("Error de I/O en la conexion al host");
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        Operacion fromUser;

        try{
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Servidor: " + fromServer);
                menuRespuesta(new Operacion(fromServer));
                fromUser = menuCliente();
                if (fromUser != null) {
                    System.out.println("Cliente: " + fromUser.toJSON());
                    //escribimos al servidor
                    out.println(fromUser.toJSON());
                }
            }
        }catch(SocketTimeoutException exTime){
            menuError("Tiempo de espera agotado , no se recibieron datos del servidor " );
        }
		
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}
