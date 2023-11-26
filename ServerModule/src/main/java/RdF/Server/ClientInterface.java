package RdF.Server;



import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Observable;

public interface ClientInterface extends Remote {

    public void notificaTurno(String user) throws RemoteException;

    public void UpdateYourTurn() throws RemoteException;

    public void notificaVittoriaManche(String player) throws RemoteException;

    public void aggiornaTextArea(String nick) throws RemoteException;

    public void fraseAggiornata(String updPhrase) throws RemoteException;

    public String rispondiAlGame() throws RemoteException, SQLException;

    public void getGameManager() throws RemoteException;

}


