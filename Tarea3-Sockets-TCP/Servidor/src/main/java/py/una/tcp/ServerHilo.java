/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.una.tcp;

import org.json.simple.parser.ParseException;
import py.una.bd.Operacion;
import py.una.entidad.Nis;
import py.una.entidad.NisJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 *
 * @author Santi
 */
public class ServerHilo extends Thread {

    private Socket socket = null;

    MultiServer servidor;

    public ServerHilo(Socket socket, MultiServer servidor) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            Operacion recibidoCliente;
            Operacion enviarCliente = new Operacion(1, "Comunicacion TCP establecida");
            out.println(enviarCliente.toJSON());

            while ((inputLine = in.readLine()) != null) {
                recibidoCliente = new Operacion(inputLine);
                enviarCliente = procesarOperacion(recibidoCliente);
                if (enviarCliente == null) {
                    break;
                }
                out.println(enviarCliente.toJSON());
            }

            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            System.err.println(ex.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Operacion procesarOperacion(Operacion datoRecibido) throws Exception {
        int tipo_operacion = datoRecibido.tipo_operacion;
        String cuerpo = datoRecibido.cuerpo;
        Nis nis1;
        Nis nis2;
        switch (tipo_operacion) {
            case 1 -> {
                MultiServer.nisDAO.insertar(new Nis(cuerpo));
                return new Operacion(1, "Operacion completada con exito");
            }
            case 2 -> {
                nis1 = NisJSON.stringObjeto(datoRecibido.cuerpo);
                System.out.println(nis1);
                nis2 = MultiServer.nisDAO.seleccionarPorIdNis(nis1.getId());
                if (nis2 != null && nis2.getEstado().equals(TRUE)) {
                    MultiServer.nisDAO.desconectar(nis1);
                    return new Operacion(2, "Se realizó con exito");
                }
                return new Operacion(2, "No se pudo realizar la operación");
            }
            case 3 -> {
                nis1 = NisJSON.stringObjeto(datoRecibido.cuerpo);
                System.out.println(nis1);
                nis2 = MultiServer.nisDAO.seleccionarPorIdNis(nis1.getId());
                if (nis2 != null && nis2.getEstado().equals(FALSE)) {
                    MultiServer.nisDAO.conectar(nis1);
                    return new Operacion(3, "Se realizó con exito");
                }
                return new Operacion(3, "No se pudo realizar la operación");
            }
            case 4 -> {
                nis1 = NisJSON.stringObjeto(datoRecibido.cuerpo);
                String lista = "NIS activos: ";
                System.out.println(nis1);
                ArrayList<Nis> listaArray = MultiServer.nisDAO.seleccionarPorEstado(nis1);
                for (int i = 0; i < listaArray.size(); i++) {
                    lista = lista + "\n Id: " + listaArray.get(i);
                }

                return new Operacion(4, lista);
            }
            case 5 -> {
                 nis1 = NisJSON.stringObjeto(datoRecibido.cuerpo);
                String lista1 = "NIS inactivos: ";
                System.out.println(nis1);
                ArrayList<Nis> listaArray1 = MultiServer.nisDAO.seleccionarPorEstado(nis1);
                for (int i = 0; i < listaArray1.size(); i++) {
                    lista1 = lista1 + "\n Id:" + listaArray1.get(i);
                }

                return new Operacion(5, lista1);
            }
            default -> System.out.println("Opción no válida");
        }
        return null;
    }
}
