/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.una.tcp;

import py.una.bd.NisDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santi
 */
public class MultiServer {

    public static NisDAO nisDAO = new NisDAO();

    //variables compartidas
    boolean listening = true;
    List<ServerHilo> hilosClientes; //almacenar los hilos (no se utiliza en el ejemplo, se deja para que el alumno lo utilice)
    List<String> usuarios; //almacenar una lista de usuarios (no se utiliza, se deja para que el alumno lo utilice)

    static int puertoServidor = 4444;

    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(puertoServidor);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: " + puertoServidor);
            System.exit(1);
        }

        while (listening) {

            ServerHilo hilo = new ServerHilo(serverSocket.accept(), this);
            hilosClientes.add(hilo);
            hilo.start();
        }

        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {

        puertoServidor = 4444;
        if (args.length == 1) {
            puertoServidor = Integer.parseInt(args[0]);
        } else if (args.length > 1) {
            System.err.println("ERROR en args");
            System.exit(1);
        }

        MultiServer tms = new MultiServer();

        tms.hilosClientes = new ArrayList<ServerHilo>();
        tms.usuarios = new ArrayList<String>();

        tms.ejecutar();

    }
}
