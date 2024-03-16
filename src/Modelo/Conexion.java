package Modelo;

import java.net.InetAddress;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;

public class Conexion {
    private int puerto;

    private InetAddress direccion;

    private String mensaje;

    private byte[] mensajeByte;

    private boolean conectado;

    private PublicKey claveP;

    public Conexion(int puerto, InetAddress direccion, String mensaje, boolean conectado) {
        this.puerto = puerto;
        this.direccion = direccion;
        this.mensaje = mensaje;
        this.conectado = conectado;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public InetAddress getDireccion() {
        return direccion;
    }

    public void setDireccion(InetAddress direccion) {
        this.direccion = direccion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public byte[] getMensajeByte() {
        return mensajeByte;
    }

    public void setMensajeByte(byte[] mensajeByte) {
        this.mensajeByte = mensajeByte;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public PublicKey getClaveP() {
        return claveP;
    }

    public void setClaveP(PublicKey claveP) {
        this.claveP = claveP;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "puerto=" + puerto +
                ", direccion=" + direccion +
                ", mensaje='" + mensaje + '\'' +
                ", mensajeByte=" + Arrays.toString(mensajeByte) +
                ", conectado=" + conectado +
                ", claveP=" + claveP +
                '}';
    }
}
