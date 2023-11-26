package RdF.Client;


import GUI.GUI_ClientLogin;
import GUI.Gioco;
import RdF.Server.Phrases;
import RdF.Server.ServerInterface;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientRDF extends UnicastRemoteObject implements ClientInterface{


    public static Stage stage = null;
    public static ServerInterface server;
    public ClientInterface clientInterface;
    public static String updatedPhrase;
    public AnchorPane parent;

    public static String getUpdatedPhrase() {
        return updatedPhrase;
    }

    public static void setUpdatedPhrase(String updatedPhrase) {
        ClientRDF.updatedPhrase = updatedPhrase;
    }

    /**
     * Super
     *
     * @throws RemoteException
     * @throws NotBoundException
     * @throws SQLException
     */
    public ClientRDF() throws RemoteException, NotBoundException, SQLException, EOFException {
        super();
        inizialize();
    }

    /**
     * Return Server
     *
     * @return
     */
    public static ServerInterface getServer() {
        return  server;
    }

    /**
     * Inizialize registry post
     *
     * @throws RemoteException
     * @throws NotBoundException
     * @throws SQLException
     */
    public void inizialize() throws RemoteException, NotBoundException, SQLException{
        Registry stub = LocateRegistry.getRegistry(5554);
        server = (ServerInterface) stub.lookup("//localhost/server");
        server.addMe( this);
        System.out.println("Collegato lo stub del client al server");
    }

    /**
     * Main
     *
     * @param args
     * @throws RemoteException
     * @throws NotBoundException
     * @throws SQLException
     */
    public static void main(String... args) throws RemoteException, NotBoundException, SQLException, EOFException {
        new ClientRDF();
        Application.launch(GUI_ClientLogin.class, args);
    }

    @Override
    public void initializePhrase(Phrases phrases, String user) throws IOException {
        ArrayList<String> hintAndPhrase = new ArrayList<>();
        hintAndPhrase.add(phrases.getHint());
        hintAndPhrase.add(phrases.getPhrase());
        System.out.println("faccio newGioco di initializePhrase con primpPlayer "+user);
        new Gioco(hintAndPhrase, user);
    }

    @Override
    public void notificaVittoriaManche(String playerWinner) throws RemoteException {
        new Gioco(playerWinner);
    }

    @Override
    public void fraseAggiornata(String updPhrase, String actualPlayer) throws IOException, SQLException {
        new Gioco(updPhrase, actualPlayer);
    }
}
