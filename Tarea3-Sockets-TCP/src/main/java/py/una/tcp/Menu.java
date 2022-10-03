/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.una.tcp;

import java.util.Scanner;
import py.una.bd.Operacion;
import py.una.entidad.Nis;
import static py.una.entidad.NisJSON.objetoString;

/**
 *
 * @author Santi
 */
public class Menu {

    public static void menuError(String error) {
        System.err.println(error);
    }

    public static Operacion menuCliente() {

        System.out.println("Bienvenido al sistema de NIS");
        System.out.println("1 - Registrar consumo");
//        System.out.println("2 - Informar Conectividad");
        System.out.println("2 - Enviar orden de desconexión");
        System.out.println("3 - Enviar orden de conexión");
        System.out.println("4 - Listar NIS activos");
        System.out.println("5 - Listar NIS inactivos");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        String cuerpo = null;
        Nis datos = null;

        switch (input) {
            case 1:
                datos = new Nis();
                System.out.println("Ingrese ID del NIS");
                datos.setId(Long.parseLong(scanner.next()));
                System.out.println("Consumo: ");
                datos.setConsumo(Integer.parseInt(scanner.next()));
                cuerpo = objetoString(datos);
                break;
            case 2:
                datos = new Nis();
                System.out.println("Ingrese ID del NIS");
                datos.setId(Long.parseLong(scanner.next()));
                datos.setConsumo(0);
                datos.setEstado(Boolean.FALSE);
                cuerpo = objetoString(datos);
                break;
            case 3:
                datos = new Nis();
                System.out.println("Ingrese ID del NIS");
                datos.setId(Long.parseLong(scanner.next()));
                System.out.println("Ingrese consumo del NIS");
                datos.setConsumo(Integer.parseInt(scanner.next()));
                datos.setEstado(Boolean.TRUE);
                cuerpo = objetoString(datos);
                break;
            case 4:
                datos = new Nis();
                datos.setEstado(Boolean.TRUE);
                cuerpo = objetoString(datos);
                break;
            case 5:
                datos = new Nis();
                datos.setEstado(Boolean.FALSE);
                cuerpo = objetoString(datos);
                break;
            default:
                System.exit(input);
        }
        return new Operacion(input, cuerpo);
    }

    public static void menuRespuesta(Operacion respuesta) {
        System.out.println();
        System.out.println(respuesta.cuerpo);
        System.out.println();
    }
}
