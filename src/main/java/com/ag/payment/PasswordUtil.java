package com.ag.payment;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;

import com.squareup.okhttp.OkHttpClient;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by agufed on 1/7/18.
 */
public class PasswordUtil {

    public String encodeUnamePwdAuth(){
        // Use base64 to encode the username and password.
        String app_key = "PD5Ck4fbJw1rkD43VNVpatg7MLzePSKX";
        String app_secret = "dsEWQHQjPfSCr7xf";
        String appKeySecret = app_key + ":" + app_secret;
        String auth = "";
        try {
            byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
//    byte[] bytes = usernameAndPassword.getBytes("ISO-8859-1");
            auth = Base64.encode(bytes);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return auth;
    }

    /*public Response getResponse(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .get()
                .addHeader("authorization", "Basic " + encodeUnamePwdAuth())
                .addHeader("cache-control", "no-cache")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }*/

    // Function to encrypt the initiator credentials
    public static String encryptInitiatorPassword(String securityCertificate, String password) {
        String encryptedPassword = "YOUR_INITIATOR_PASSWORD";
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            byte[] input = password.getBytes();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            FileInputStream fin = new FileInputStream(new File(securityCertificate));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(fin);
            PublicKey pk = certificate.getPublicKey();
            cipher.init(Cipher.ENCRYPT_MODE, pk);

            byte[] cipherText = cipher.doFinal(input);

            // Convert the resulting encrypted byte array into a string using base64 encoding
            encryptedPassword = Base64.encode(cipherText);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encryptedPassword;
    }
}
