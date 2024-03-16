package Servidor;

import Modelo.Conexion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

public class ConectionHandler extends Thread{

    private static Conexion cliente;

    public ConectionHandler(Conexion cliente) {
        ConectionHandler.cliente = cliente;
    }

    @Override
    public void run(){

        try {
            addList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        if (cliente.getClaveP() == null){
//        System.out.println(cliente.getMensaje().substring(0,4));

//        KeyFactory keyFactory = null;
//        try {
//            keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey clavePublicaClienteObjeto = keyFactory.generatePublic(
//                    new X509EncodedKeySpec(cliente.getMensaje()));
//            System.out.println("Clave pública del cliente:");
//            System.out.println(Base64.getEncoder().encodeToString
//                    (clavePublicaClienteObjeto.getEncoded()));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }


//        if (cliente.getMensaje().startsWith("$cl$")){
////            System.out.println("Mensaje del cliente: " + cliente.getMensaje());
//            try {
//                System.out.println("null");
//                mandarListado();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }else {
//            try {
//                cliente.setMensaje("Hola desde: " + cliente.getDireccion() + ":" + cliente.getPuerto());
//                addList();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private static void addList() throws IOException {
        boolean exists = false;

        Server.getLock().lock();

        System.out.println(cliente.getMensaje() + " desde: " + cliente.getPuerto());

        for (Conexion item : Server.getList()) {
            if (item.getPuerto() != cliente.getPuerto()) {
                mandar((cliente.getMensaje() + " desde: " + cliente.getPuerto() + " a las: " + LocalDateTime.now()), item.getDireccion(), item.getPuerto());
            }else{
                exists = true;
            }
        }

        if (!exists){
            Server.getList().add(cliente);
        }

        Server.getLock().unlock();
    }

//    private static void mandarListado() throws IOException {
//        Server.getLock().lock();
//
//        for (Conexion item : Server.getList()) {
//            if (!item.equals(cliente)) {
////                mandar(cliente.getMensaje(), item.getDireccion(), item.getPuerto());
//            }
//        }
//
//        Server.getLock().unlock();
//    }

    private static void mandar(String mensaje, InetAddress direccion, int puerto) throws IOException {
        byte[] cadena = (mensaje).getBytes();

        DatagramPacket paquete = new DatagramPacket(cadena, cadena.length, direccion, puerto);
        Server.getServer().send(paquete);
    }
}
