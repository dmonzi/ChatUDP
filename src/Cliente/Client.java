package Cliente;

import Crypto.Generador;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    private static DatagramSocket cliente;
    private static InetAddress inet;
    private static KeyPair clavesCliente;
    private static KeyPair clavesServer;
    private static final Generador generador = new Generador();

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        iniciarCliente(numAleatorio(200, 100));
        System.out.println("Escribe -> archivo para mandar uno");
//        intercambioClaves();

//        mandarServidor("hola");
        leer();
        recibir();
    }

    private static void leer(){
        Thread hilo = new Thread(() -> {
            Scanner lector = new Scanner(System.in);
            String mensaje, ruta;

            while (true){
                try {
                    mensaje = lector.nextLine();
                    if(mensaje.toLowerCase().equals("archivo")){
                        System.out.println("Introduce la ruta");
                        ruta = lector.nextLine();
                        mandarArchivo(ruta);
                    }else {
                        mandarServidor(mensaje);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        hilo.start();
    }

    private static void iniciarCliente(int puerto) throws SocketException, UnknownHostException, NoSuchAlgorithmException {
//        clavesCliente = generador.generarClave();
        cliente = new DatagramSocket(puerto);
        inet = InetAddress.getByName("localhost");
    }

    private static void intercambioClaves() throws IOException {
        PublicKey cp = clavesCliente.getPublic();
        byte[] cpBytes = Base64.getEncoder().encode(cp.getEncoded());

        DatagramPacket paquete = new DatagramPacket(cpBytes, cpBytes.length, inet, 1234);
        cliente.send(paquete);
//        mandarServidor("$cl$" + );
    }

    private static void recibir(){
        Thread hilo = new Thread(() -> {
            byte[] lista = new byte[1024];
            String mensaje;
            DatagramPacket paquete = new DatagramPacket(lista, lista.length);

            while (true){
                try {
                    cliente.receive(paquete);
                    mensaje = new String(paquete.getData(), 0, paquete.getLength());
                    System.out.println(mensaje);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        hilo.start();
    }

    private static void mandarServidor(String mensaje) throws IOException {
        byte[] cadena = (mensaje).getBytes();

        DatagramPacket paquete = new DatagramPacket(cadena, cadena.length, inet, 1234);
        cliente.send(paquete);
        System.out.println("enviado");
    }

    private static void mandarArchivo(String ruta) throws IOException {
        byte[] cadena = Files.readAllBytes(Paths.get(ruta));
        DatagramPacket paquete = new DatagramPacket(cadena, cadena.length, inet, 1234);
        cliente.send(paquete);
        System.out.println("enviado");
    }

    private static int numAleatorio(int maximo, int minimo){
        int salida;

        salida = (int) (Math.random()*(maximo-minimo))+minimo;

        return salida;
    }
}
