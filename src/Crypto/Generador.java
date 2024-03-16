package Crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

public class Generador {
    public KeyPair generarClave() throws NoSuchAlgorithmException {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(2048);
        return generador.generateKeyPair();
    }

    public String encriptar(String mensaje, PublicKey clave) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cifrador = Cipher.getInstance("RSA");
        cifrador.init(Cipher.ENCRYPT_MODE, clave);
        byte[] salida = cifrador.doFinal(mensaje.getBytes());
        return Base64.getEncoder().encodeToString(salida);
    }

    public String desencriptar(String cadena, PrivateKey clave) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cifrador = Cipher.getInstance("RSA");
        cifrador.init(Cipher.DECRYPT_MODE, clave);
        byte[] salida = cifrador.doFinal(Base64.getDecoder().decode(cadena));
        return new String(salida);
    }
}
