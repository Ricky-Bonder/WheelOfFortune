package SMTP;

import java.util.Random;

public class GenerateActivationCode {

    public static String codice;


    public String generateCode(int length) {
        String numbers = "0123456789";
        Random r = new Random();
        //char[] otp = new char[length];
        //for (int i = 0; i < length; i++) {
        //  otp[i] = numbers.charAt(r.nextInt(numbers.length()));
        //0}
        int numero = r.nextInt(999999 + 1 - 100000) + 100000;
        codice = Integer.toString(numero);
        return codice;
    }
}
