package RdF.Server;

// import RdF.Client.InterfaceClientRDF;

import Database.DBHelper;
import RdF.Client.ClientInterface;
import RdF.Client.ClientRDF;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    boolean startConnectionS(String hostname, String port, String db) throws RemoteException, SQLException;

    //query per USERS
    boolean anyAdminS() throws SQLException, RemoteException;
    boolean isAdminS(String Email) throws RemoteException, SQLException;
    public void newUsersS(String Nickname, String Name, String Surname, String Email, Boolean Admin, Boolean Online) throws Exception;
    public boolean loginUsersS(String Email, String Password) throws Exception;
    void logoutUsersS(String Email) throws RemoteException, SQLException;
    public String setPasswordS(String Email, String OldPw, String NewPw) throws Exception;
    void resetPasswordS(String Email) throws Exception;
    String setUserS(String Email, String NewName, String NewSurname, String NewNick) throws RemoteException, SQLException;
    public String getIDUsersS (String Email) throws SQLException, RemoteException;
    public String getIDUsersFromNickS (String Nickname) throws SQLException, RemoteException;
    public String getNickFromEmailS (String Email) throws SQLException, RemoteException;
    public String getNickFromIDUserS (String IDUser) throws SQLException, RemoteException;
    public String getMyIDMancheS(String IDUser) throws RemoteException;

    //query per GAMES
    String newGamesS() throws SQLException, RemoteException, InterruptedException;
    public String getIDGamesS(String IDUsers) throws SQLException, RemoteException;
    public void startedGamesS(String IDGames) throws SQLException, RemoteException;
    public void endedGamesS(String IDGames) throws SQLException, RemoteException;

    //query per MANCHES
    public int getNumberManchesS(String IDGames) throws SQLException, RemoteException;
    public String getIDManchesS(String IDPlayers) throws SQLException, RemoteException;
    //public String newMancheS(String idGames, String email) throws SQLException, RemoteException, InterruptedException;
    public boolean buildMatch(String IDGames, int nManche) throws IOException, SQLException, InterruptedException;
    public ArrayList<String> notViewedPhrasesS(String IDGames) throws RemoteException, SQLException;
    public String getCurrentIDPlayerS(String Email) throws SQLException, RemoteException;
    public String getMyIDPlayerS(String IDUser) throws RemoteException;

    //Query per MOVES
    public void newMovesSpinS(String IDPlayers, String IDManches, String SpinResult) throws SQLException, RemoteException;
    public void newMovesConsS(String IDPlayers, String IDManches, String Letters, int Scoremoves) throws SQLException, RemoteException;
    public void newMovesVocS(String IDPlayers, String IDManches, String Letters, int Scoremoves)throws  SQLException, RemoteException;
    public String getSolutionS(String IDManches) throws SQLException, RemoteException;
    public void addJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException;
    public void useJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException;
    public boolean getJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException;


    //query per SUBS
    void insertSubsS(String Email, String IDGames) throws RemoteException, SQLException;
    boolean checkSubsS(String IDGames) throws RemoteException, SQLException, InterruptedException;
    void insertSubsObserverS(String Email, String IDGames) throws RemoteException, SQLException;
    public boolean isObserverS (String Email) throws SQLException, RemoteException;

    //Query per PLAYERS
    //void newPlayerS(String IDGames, String IDManches) throws SQLException, RemoteException;
    public String getIDPlayerS(String IDManches, String Email) throws SQLException, RemoteException;
    public ArrayList<String> getPlayersS(String IDManches) throws SQLException, RemoteException;

    //Query per PHRASES
    public ArrayList<String> refreshPhrasesS() throws SQLException, RemoteException;
    void deleteAllPhrasesS() throws SQLException, RemoteException;

    //Query per STATISTICHE
    public String userMaxManchesS() throws SQLException, RemoteException;
    public String userMaxGamesS() throws SQLException, RemoteException;
    public String userMoreManchesS() throws SQLException, RemoteException;



    void createGameWaitingS() throws RemoteException;
    void startGameWaitingS() throws RemoteException;
    public boolean GameStartS() throws RemoteException;
    public void callConsonant(char consonant) throws RemoteException, SQLException;

    public DBHelper getDBHelper() throws RemoteException;
    public ArrayList<String> synchronizeGames() throws RemoteException;
    public Game getMyGame(String idPlayer) throws RemoteException, SQLException;
    public Game refToMyGame(Object theGame) throws RemoteException, SQLException;


    public ArrayList<String> CSVReaderS(String Path) throws Exception;

    //public void startDB() throws RemoteException;
//    public void addObserver(InterfaceClientRDF client) throws RemoteException;
//    public void removeObserver(InterfaceClientRDF client) throws RemoteException;
   //public String echo(Object obj) throws RemoteException;
//    public void signUp(String[] campi, InterfaceClientRDF client) throws RemoteException;

//    public void notifyOb() throws RemoteException;

    //public String[] login(String[] fields) throws RemoteException;
  //  public void close() throws RemoteException;


    public void addMe(ClientInterface clientInterface) throws RemoteException;
    public void endMyTurn(ArrayList<Character> lettereUscite) throws RemoteException, IOException, SQLException;
    public void winTheGame(String nickWinner) throws RemoteException, IOException, SQLException;
}
