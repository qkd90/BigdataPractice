package com.data.data.hmly.action.yihaiyou.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    private KeyPairGenerator keyGen; // Key pair generator for RSA
    private PrivateKey privateKey; // Private Key Class
    private PublicKey publicKey; // Public Key Class
    private KeyPair keypair; // KeyPair Class
    private Signature sign; // Signature, used to sign the data

    /**
     * Default Constructor. Instantiates the signature algorithm.
     */
    public RSAUtil() {
        try {
            // Get the instance of Signature Engine.
            sign = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException nsa) {
            System.out.println("" + nsa.getMessage());
        }
    }

    /**
     * Signs the data and return the signature for a given data.
     *
     * @param user        The current user’s external person number
     * @param time        The current time in string format: yyyy-mm-dd hh:mm:ss
     * @param EncodedCert The hard coded certificate string, i.e. <b>private key</b>
     * @return String URLEncode string of Signature
     * @throws UnsupportedEncodingException
     */
    public String Sign(String content, String EncodedCert) {

        String returnStr = "";
        try {
            String toBeSigned = content;
            byte[] signature = signData(toBeSigned.getBytes(), EncodedCert);
            String base64Signature = b64encode(signature);
            returnStr = base64Signature;// java.net.URLEncoder.encode(base64Signature,
            // "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }
        return returnStr;
    }

    public boolean Verify(String base64signature, String content, String EncodedCert) {
        String toBeSigned = content;
        byte[] signature = b64decode(base64signature);
        return verifySignature(signature, toBeSigned.getBytes(), EncodedCert);
    }

    /**
     * Generates the keys for given size.
     *
     * @param size             Key Size [512|1024]
     * @param privateKeyPath   Private key will be generated in file which can be named with "privateKeyPath" parameter;
     * @param publicKeyPath    Public key will be generated in file which can be named with "publicKeyPath" parameter;
     * @param netPublicKeyPath Public key can be read for .Net platform will be generated in file which can be named with "netPublicKeyPath" parameter;
     */
    public void GenerateKeys(int size, String privateKeyPath, String publicKeyPath, String netPublicKeyPath, String netPrivateKeyPath) {
        try {
            System.out.println("Generatign Keys");
            // Get Key Pair Generator for RSA.
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(size);
            keypair = keyGen.genKeyPair();
            privateKey = keypair.getPrivate();
            publicKey = keypair.getPublic();

            // Get the bytes of the public and private keys
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();

            // write bytes to corresponding files.
            writeKeyBytesToFile(b64encode(privateKeyBytes).getBytes(), privateKeyPath);
            String encodedValue = b64encode(publicKeyBytes);
            writeKeyBytesToFile(encodedValue.getBytes(), publicKeyPath);

            // Generate the Private Key, Public Key and Public Key in XML
            // format.
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            // RSAPublicKey rsaPublicKey = (RSAPublicKey)
            // KeyFactory.getInstance(
            // "RSA").generatePublic(
            // new X509EncodedKeySpec(publicKeyBytes));
            // // get the modules and exponent of public key to make compatible
            // // .Net public key file
            // String netPublicKey = getRSAPublicKeyAsNetFormat(rsaPublicKey);
            // Store the modules and exponent (Generated .Net public key file)
            // in file
            // writeKeyBytesToFile(netPublicKey.getBytes(), netPublicKeyPath);

            String netPrivateKey = getRSAPrivateKeyAsNetFormat(privateKeyBytes);
            writeKeyBytesToFile(netPrivateKey.getBytes(), netPrivateKeyPath);

            String netPublicKey = getRSAPublicKeyAsNetFormat(privateKeyBytes);
            writeKeyBytesToFile(netPublicKey.getBytes(), netPublicKeyPath);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm. Please check the JDK version." + e.getCause());
        } catch (InvalidKeySpecException ik) {
            System.out.println("Invalid Key Specs. Not valid Key files." + ik.getCause());
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException ioe) {
            System.out.println("Files not found on specified path. " + ioe.getCause());
        } catch (Exception ex1) {
            System.out.println(ex1);
        }

    }

    /**
     * Initialize only the private key.
     */
    private void initializePrivateKey(String privateKeyStr) {
        try {
            // Read key files back and decode them from BASE64
            byte[] privateKeyBytes = b64decode(privateKeyStr);

            // Convert back to public and private key objects
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Invalid Key Specs. Not valid Key files." + e.getCause());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is no such algorithm. Please check the JDK ver." + e.getCause());
        }
    }

    /**
     * Signs the data and return the signature for a given data.
     *
     * @param toBeSigned Data to be signed
     * @return byte[] Signature
     */
    private byte[] signData(byte[] toBeSigned, String EncodedCert) {
        if (privateKey == null) {
            initializePrivateKey(EncodedCert);
        }
        try {
            Signature rsa = Signature.getInstance("SHA1withRSA");
            rsa.initSign(privateKey);
            rsa.update(toBeSigned);
            return rsa.sign();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException in) {
            System.out.println("Invalid Key file.Please check the key file path" + in.getCause());
        } catch (SignatureException se) {
            System.out.println(se);
        }
        return null;
    }

    /**
     * Verifies the signature for the given bytes using the public key.
     *
     * @param signature   Signature
     * @param data        Data that was signed
     * @param EncodedCert public key string
     * @return boolean True if valid signature else false
     */
    private boolean verifySignature(byte[] signature, byte[] data, String EncodedCert) {
        try {
            initializePublicKey(EncodedCert);
            sign.initVerify(publicKey);
            sign.update(data);
            return sign.verify(signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
        }

        return false;
    }

    /**
     * Initializes the public and private keys.
     */
    private void initializePublicKey(String publicKeyStr) {
        try {
            // Read key files back and decode them from BASE64
            byte[] publicKeyBytes = b64decode(publicKeyStr);

            // Convert back to public and private key objects
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = keyFactory.generatePublic(publicKeySpec);

        } catch (InvalidKeySpecException e) {
            System.out.println("Invalid Key Specs. Not valid Key files." + e.getCause());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is no such algorithm. Please check the JDK ver." + e.getCause());
        }
    }

    // /**
    // * Gets the RSA Public Key. The key idea is to make the key readable for
    // * .Net platform.
    // *
    // * @param key
    // * RSAPublicKey
    // * @return String the public key that .Net platform can read
    // */
    // private String getRSAPublicKeyAsNetFormat(RSAPublicKey key) {
    //
    // byte[] modulusBytes = key.getModulus().toByteArray();
    // modulusBytes = stripLeadingZeros(modulusBytes);
    // String modules = b64encode(modulusBytes);
    //
    // byte[] exponentBytes = key.getPublicExponent().toByteArray();
    // String exponent = b64encode(exponentBytes);
    //
    // String result = "modules : " + modules + "\r\n" + "exponent : "
    // + exponent;
    // return result;
    // }

    /**
     * Utility method to delete the leading zeros from the modulus.
     *
     * @param a modulus
     * @return modulus
     */
    private byte[] stripLeadingZeros(byte[] a) {
        int lastZero = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                lastZero = i;
            } else {
                break;
            }
        }
        lastZero++;
        byte[] result = new byte[a.length - lastZero];
        System.arraycopy(a, lastZero, result, 0, result.length);
        return result;
    }

    /**
     * Writes the bytes of the key in a file.
     *
     * @param key  byte array of key data.
     * @param file File Name
     */
    private void writeKeyBytesToFile(byte[] key, String file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        out.write(key);
        out.close();
    }

    // --- Returns XML encoded RSA private key string suitable for .NET
    // CryptoServiceProvider.FromXmlString(true) ------
    // --- Leading zero bytes (most significant) must be removed for XML
    // encoding for .NET; otherwise format error ---

    private String getRSAPrivateKeyAsNetFormat(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pvkKeySpec);

            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>" + b64encode(removeMSZero(pvkKey.getModulus().toByteArray())) + "</Modulus>");

            buff.append("<Exponent>" + b64encode(removeMSZero(pvkKey.getPublicExponent().toByteArray())) + "</Exponent>");

            buff.append("<P>" + b64encode(removeMSZero(pvkKey.getPrimeP().toByteArray())) + "</P>");

            buff.append("<Q>" + b64encode(removeMSZero(pvkKey.getPrimeQ().toByteArray())) + "</Q>");

            buff.append("<DP>" + b64encode(removeMSZero(pvkKey.getPrimeExponentP().toByteArray())) + "</DP>");

            buff.append("<DQ>" + b64encode(removeMSZero(pvkKey.getPrimeExponentQ().toByteArray())) + "</DQ>");

            buff.append("<InverseQ>" + b64encode(removeMSZero(pvkKey.getCrtCoefficient().toByteArray())) + "</InverseQ>");

            buff.append("<D>" + b64encode(removeMSZero(pvkKey.getPrivateExponent().toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");

            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    // --- Returns XML encoded RSA public key string suitable for .NET
    // CryptoServiceProvider.FromXmlString(true) ------
    // --- Leading zero bytes (most significant) must be removed for XML
    // encoding for .NET; otherwise format error ---

    private String getRSAPublicKeyAsNetFormat(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pvkKeySpec);
            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>" + b64encode(removeMSZero(pvkKey.getModulus().toByteArray())) + "</Modulus>");
            buff.append("<Exponent>" + b64encode(removeMSZero(pvkKey.getPublicExponent().toByteArray())) + "</Exponent>");
            buff.append("</RSAKeyValue>");
            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    // --------- remove leading (Most Significant) zero byte if present
    // ----------------
    private byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else
            data1 = data;

        return data1;
    }

    private String b64encode(byte[] data) {
        String b64str = new String(Base64.encodeBase64(data));
        return b64str;
    }

    private byte[] b64decode(String data) {
        byte[] decodeData = Base64.decodeBase64(data.getBytes());
        return decodeData;
    }
}
