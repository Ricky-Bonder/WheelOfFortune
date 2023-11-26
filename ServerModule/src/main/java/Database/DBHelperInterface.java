package Database;

import RdF.Server.Phrases;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DBHelperInterface extends Remote {

    //Conessione al Database
    public boolean startConnection(String hostname, String port, String db) throws RemoteException, SQLException;

    //Query per USERS
    public String getIDUsers (String Email) throws SQLException, RemoteException;
    public String getIDUsersFromNick (String Nickname) throws SQLException, RemoteException;
    public String getNickFromEmail (String Email) throws SQLException, RemoteException;
    public boolean anyAdmin() throws SQLException, RemoteException;
    boolean isAdmin(String Email) throws SQLException, RemoteException;
    public void newUsers(String Nickname, String Name, String Surname, String Email, String Password, Boolean Admin, Boolean Online) throws Exception;
    boolean loginUsers(String Email, String Password) throws Exception;
    void logoutUsers(String Email) throws SQLException, RemoteException;
    String setPassword(String Email, String OldPw, String NewPw) throws Exception;
    void resetPassword(String Email, String ResetPass) throws Exception;
    String setUser(String Email, String NewName, String NewSurname, String NewNick) throws RemoteException, SQLException;

    //Query per GAMES
    String newGames() throws SQLException, RemoteException;
    String getIDGames(String Email) throws SQLException, RemoteException;
    public void startedGames(String IDGames) throws SQLException, RemoteException;
    public void endedGames(String IDGames, int Scoregames) throws SQLException, RemoteException;

    //Query per SUBS
    void insertSubs(String Email, String IDGames) throws SQLException, RemoteException;
    boolean checkSubs(String IDGames) throws RemoteException, SQLException, InterruptedException, RemoteException;
    void insertSubsObserver(String Email, String IDGames) throws SQLException, RemoteException;
    public ArrayList<String> getIDUsersFromSubs(String IDGames) throws SQLException, RemoteException;
    public boolean isObserver (String Email) throws SQLException, RemoteException;

    //QUERY per MANCHES
    String newManches(String IDGames, String IDPhrases, int NumberManches) throws SQLException, RemoteException, InterruptedException;
    public String getIDManches(String IDPlayers) throws SQLException, RemoteException;
    public int getNumberManches(String IDGames) throws SQLException, RemoteException;

    //Query per MOVES
    public void newMovesSpin(String IDPlayers, String IDManches,  String SpinResult) throws SQLException, RemoteException;
    public void newMovesCons(String IDPlayers, String IDManches,  String Letters, int Scoremoves) throws SQLException, RemoteException;
    public void newMovesVoc(String IDPlayers, String IDManches,  String Letters, int Scoremoves) throws SQLException, RemoteException;
    public String getSolution(String IDManches) throws SQLException, RemoteException;
    public void addJolly(String IDPlayers, String IDManches) throws SQLException, RemoteException;
    public void useJolly(String IDPlayers, String IDManches) throws SQLException, RemoteException;
    public boolean getJolly(String IDPlayers, String IDManches) throws SQLException, RemoteException;

    //Query per PLAYERS
    public void newPlayerFromIDUsers(String IDUsers, String IDManches) throws SQLException, RemoteException;
    public String getIDPlayerFromEmail(String IDManches, String Email) throws SQLException, RemoteException;
    public String getIDPlayerFromIDUser(String IDManches, String IDUsers) throws SQLException, RemoteException;
    public ArrayList<String> getPlayers(String IDManches) throws SQLException, RemoteException;
    public String getCurrentIDPlayer(String Email) throws SQLException, RemoteException;

    //Query per PHRASES
    public Phrases getRandomPhrases(ArrayList<String> listOfPhrases) throws SQLException, RemoteException;
    public ArrayList<String> refreshPhrases() throws SQLException, RemoteException;
    void insertPhrases(String Hint, String Phrase) throws SQLException, RemoteException;
    void deleteAllPhrases() throws SQLException, RemoteException;
    void editPhrases(String IDPhrases, String Hint, String Phrases) throws SQLException, RemoteException;
    public ArrayList<String> notViewedPhrases(String IDGames) throws SQLException, RemoteException;

    //Query per STATISTICHE
    public String userMaxManches() throws SQLException, RemoteException;
    public String userMaxGames() throws SQLException, RemoteException;
    public String userMoreManches() throws SQLException, RemoteException;
    public int userNumberGames(String Email) throws SQLException, RemoteException;
    public int userNumberManches(String Email) throws SQLException, RemoteException;

    public String getNickFromIDUser(String idUser) throws RemoteException, SQLException;
}