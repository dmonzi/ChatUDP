package Servidor;

import Modelo.Conexion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private static DatagramSocket server;

    private static ReentrantLock lock = new ReentrantLock();

    private static ArrayList<Conexion> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String mensaje;
        byte[] lista = new byte[1024];
        DatagramPacket paquete = new DatagramPacket(lista, lista.length);

        iniciarServidor(1234);

        while (true){
            server.receive(paquete);

            mensaje = new String(paquete.getData(), 0, paquete.getLength());

            Thread hilo = new ConectionHandler(new Conexion(paquete.getPort(), paquete.getAddress(),
                    mensaje, true));
            hilo.start();
        }
    }

    private static void iniciarServidor(int puerto) throws SocketException {
        server = new DatagramSocket(puerto);
    }

    public static DatagramSocket getServer() {
        return server;
    }

    public static void setServer(DatagramSocket server) {
        Server.server = server;
    }

    public static ReentrantLock getLock() {
        return lock;
    }

    public static void setLock(ReentrantLock lock) {
        Server.lock = lock;
    }

    public static ArrayList<Conexion> getList() {
        return list;
    }

    public static void setList(ArrayList<Conexion> list) {
        Server.list = list;
    }
}
