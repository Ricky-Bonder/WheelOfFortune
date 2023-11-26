package RdF.Client;


import RdF.Server.Phrases;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ClientInterface extends Remote {

    public void initializePhrase(Phrases phrases, String user) throws IOException;
   // public void notificaTurno(String user, char[] lettereUscite) throws IOException, SQLException;
    public void notificaVittoriaManche(String player) throws RemoteException;

    public void fraseAggiornata(String updPhrase, String s) throws IOException, SQLException;
  //  public String rispondiAlGame() throws RemoteException, SQLException;

}


