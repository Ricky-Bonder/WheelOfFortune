package SMTP;


import Database.DBHelper;
import RdF.Server.ServerInterface;
import RdF.Server.ServerRDF;
import RdF.Server.Utils;
import RdF.Server.ThreadUtils;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.lang.Thread.State.TIMED_WAITING;


public class EmailSender {

    private String username = "";
    private String password = "";
    private Message message;
    private String resetNumber;
    public static ServerInterface server;

    /**
     * Costruttore. Inizializza a valori di default i campi della classe e
     * richiede le credenziali
     */
    public EmailSender() {
        super();
        username = "";      //IMPORTANTE!! Riempire con vostra email e pw di uni per far funzionare EmailSender
        password = "";
    }

    /**
     * Getter dello username
     *
     * @return lo username dell'email
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter della password
     *
     * @return la password dell'emeil
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter dell'email da inviare
     *
     * @return il messaggio dell'email
     */
    public Message getMessage() {
        return message;
    }


    /**
     * Setta il messaggio da inviare e proprieta' collegate
     *
     * @return l'istanza che ha effettuata la chiamata
     * @throws MessagingException
     */
    private EmailSender setMessage() throws MessagingException {
        String host = "smtp.office365.com";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.user", username);
        props.put("mail.password", password);

        Session session = Session.getInstance(props);
        message = new MimeMessage(session);
        return this;
    }

    /**
     * Manda l'email tramite un altro thread
     *
     * @param subject l'oggetto dell'email
     * @param body il corpo dell'email
     * @param to i destinarari dell'email
     * @return vero se l'invio, non la ricezione, va a buon fine, falso
     * altrimenti
     */
    public boolean sendEmail(String subject, String body, String... to) {
        try {
            if (to.length == 0) {
                System.err.println("Non hai specificato destinatari");
                return false;
            }
            setMessage();
            message.setFrom(new InternetAddress(username));
            for (String dest : to) {
                dest = dest.trim();
                if (!dest.isEmpty()) {
                    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(dest, false));
                }
            }

            message.setSubject(subject);
            message.setText(body);

            new ThreadUtils(this).execute();
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, "Eccezione: {0}", ex.getMessage());
        }
        return false;
    }


    public String activationMail(String... to) {
        GenerateActivationCode code = new GenerateActivationCode();
        String activationNumber = code.generateCode(10);

      boolean OK =  sendEmail("Attivazione Account - Ruota della Fortuna",
              String.format("Ti ringraziamo di esserti registrato al gioco Ruota della Fortuna.\n" +
                      "Per attivare il tuo account esegui il primo accesso con questo codice come password: "
                        + activationNumber+"\n Ti consigliamo di modificare la password al più presto!\n\n" +
                      "Team RdF"), to);


        ThreadWaitMin countdown = new ThreadWaitMin();
        countdown.start();

        return activationNumber;
    }


    /**
     * Invio dell'email di reset password
     *
     * @param to email dell'utente
     * @return true se invia il messaggio, false altrimenti
     */
    public String resetPassword(String to) throws SQLException, RemoteException {
        GenerateActivationCode code = new GenerateActivationCode();
        server = new ServerRDF();
        resetNumber = code.generateCode(10);
        String body = "Hai richiesto il reset della password."
                + "Questa è la nuova password temporanea %s.";
        sendEmail("Reset password", String.format(body, resetNumber), to);
        return resetNumber;
    }

    public boolean editPassword(String to) {
        String body = "La password è stata modificata con successo.";
        return sendEmail("Password Modificata", String.format(body), to);
    }


}
