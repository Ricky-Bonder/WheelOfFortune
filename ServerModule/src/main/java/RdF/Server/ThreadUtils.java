package RdF.Server;

import SMTP.EmailSender;
import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class ThreadUtils extends Utils {

    private Runnable task;
    /**
     * Costruttore
     */
    public ThreadUtils() {
        super();
    }

    /**
     * Serve per mandare un'email e non bloccare l'applicazione in attesa
     * dell'invio
     *
     * @param utils contiene tutti i parametri dell'email
     */
    public ThreadUtils(final EmailSender utils) {
        this();
        task = () -> {
            try {
                Transport.send(utils.getMessage(), utils.getUsername(), utils.getPassword());
            } catch (AuthenticationFailedException ex) {
                Logger.getLogger(ThreadUtils.class.getName()).log(Level.SEVERE, "Errore credenziali: {0}", ex.getMessage());
            } catch (MessagingException ex) {
                Logger.getLogger(ThreadUtils.class.getName()).log(Level.SEVERE, "Eccezione: {0}", ex.getMessage());
            }
        };
    }

    /**
     * Esegue in un nuovo thread il task stabilito nel costruttore
     *
     * @return l'istanza che ha chiamato il metodo
     */
    public ThreadUtils execute() {
        new Thread(task).start();
        return this;
    }
}