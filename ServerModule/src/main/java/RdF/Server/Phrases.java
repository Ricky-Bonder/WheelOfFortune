package RdF.Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Phrases implements Serializable {
    private static final long serialVersionUID = 3L;
    String hintP;
    String phraseP;
    String idP;

    /**
     * Super
     *
     * @throws RemoteException
     */
    public Phrases() throws RemoteException {
        super();
    }

    /**
     * Get Id
     *
     * @return
     */
    public String getId() { return idP; }

    /**
     *
     * @param id
     */
    public void setId(String id) { this.idP = id; }

    /**
     * Get Hint
     *
     * @return
     */
    public String getHint() {
        return hintP;
    }

    /**
     * Set Hint
     *
     * @param hint
     */
    public void setHint(String hint) {
        this.hintP = hint;
    }

    /**
     * Get Phrases
     *
     * @return
     */
    public String getPhrase() {
        return phraseP;
    }

    /**
     * Set Phrases
     *
     * @param phrase
     */
    public void setPhrase(String phrase) {
        this.phraseP = phrase;
    }

    /**
     * Contructor phrases
     *
     * @param id
     * @param hint
     * @param phrase
     * @throws RemoteException
     */
    public Phrases(String id, String hint, String phrase ) throws RemoteException {
        super();
        hintP = hint;
        phraseP = phrase;
        idP = id;
    }
}
