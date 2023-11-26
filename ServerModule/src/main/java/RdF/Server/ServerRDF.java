package RdF.Server;


import Database.CSVReader;
import Database.DBHelper;
import GUI.GUI_Server;
//import com.sun.xml.bind.v2.TODO;
import RdF.Client.ClientInterface;
import RdF.Client.ClientRDF;
import SMTP.EmailSender;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.State.TIMED_WAITING;

public class ServerRDF extends UnicastRemoteObject implements ServerInterface, Serializable {

    public static Stage stage = null;
//    private ArrayList <InterfaceClientRDF> obs;
    private static ServerInterface server;
    private DBHelper dbHelper;
    private CSVReader csvReader = new CSVReader();
    public ArrayList<String> listGames = new ArrayList<String>();
    public ArrayList<Game> partiteInGioco = new ArrayList<Game>();
    public ArrayList<String> pendingGames = new ArrayList<String>();
    public ArrayList<Phrases> phrasesArrayList = new ArrayList<Phrases>();


    private Game game;

//    public Game game;

    /**
     * Constructor
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public ServerRDF() throws RemoteException, SQLException {
       super();
       System.out.println("Server pronto");
        dbHelper = new DBHelper(this);
   }

    /**
     * Main width registry port
     *
     * @param args
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized static void main(String... args) throws RemoteException, SQLException{
        server = new ServerRDF();
        Registry reg = LocateRegistry.createRegistry(5554);
        //  Registry registry = LocateRegistry.getRegistry(1099);
        reg.rebind("//localhost/server", server);
        Application.launch(GUI_Server.class, args);
    }

    @Override
    /**
     * New users
     */
    public synchronized void newUsersS(String Nickname, String Name, String Surname, String Email, Boolean Admin, Boolean Online) throws Exception {
        String Password = new EmailSender().activationMail(Email);
        System.out.println(Password);
        dbHelper.newUsers(Nickname, Name, Surname, Email, Password, Admin, Online);
    }

    /**
     * Login users
     *
     * @param Email
     * @param Password
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized boolean loginUsersS(String Email, String Password) throws Exception {
        boolean ok = dbHelper.loginUsers(Email, Password);
        return ok;
    }

    /**
     * Users logout
     *
     * @param Email
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized  void logoutUsersS(String Email) throws RemoteException, SQLException {
        dbHelper.logoutUsers(Email);

    }

    /**
     * Start connection of server
     *
     * @param hostname
     * @param port
     * @param db
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized boolean startConnectionS(String hostname, String port, String db) throws SQLException, RemoteException{
        boolean connection = dbHelper.startConnection(hostname, port, db);
        return connection;
    }

    /**
     * Set new password for users
     *
     * @param Email
     * @param OldPw
     * @param NewPw
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized String setPasswordS(String Email, String OldPw, String NewPw) throws Exception {
        String okOrNot = dbHelper.setPassword(Email, OldPw, NewPw);
        return okOrNot;
    }

    /**
     * Reset users password
     *
     * @param Email
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void resetPasswordS(String Email) throws Exception {
        String resetPass = new EmailSender().resetPassword(Email);
        dbHelper.resetPassword(Email, resetPass);
    }

    /**
     * Set new data for users
     *
     * @param Email
     * @param NewName
     * @param NewSurname
     * @param NewNick
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized String setUserS(String Email, String NewName, String NewSurname, String NewNick) throws RemoteException, SQLException {
        String okOrNot = dbHelper.setUser(Email,NewName,NewSurname, NewNick);
        return okOrNot;
    }

    @Override
    /**
     * Get id users
     */
    public synchronized String getIDUsersS(String Email) throws SQLException, RemoteException {
        String IDUser = dbHelper.getIDUsers(Email);
        return IDUser;
    }

    public synchronized String getIDUsersFromNickS (String Nickname) throws SQLException, RemoteException{
        String IDUser = dbHelper.getIDUsers(Nickname);
        return IDUser;
    }

    public synchronized String getNickFromEmailS (String Email) throws SQLException, RemoteException{
        String Nickname = dbHelper.getNickFromEmail(Email);
        return Nickname;
    }

    @Override
    public String getNickFromIDUserS(String IDUser) throws SQLException, RemoteException {
        return dbHelper.getNickFromIDUser(IDUser);
    }

    /**
     * New Games
     *
     * @return
     * @throws SQLException
     * @throws RemoteException
     * @throws InterruptedException
     */
    public synchronized String newGamesS() throws SQLException, RemoteException, InterruptedException {
        String id = dbHelper.newGames();
      //  listGames.add(id);
      //  partiteInGioco.add(new Game(this, id));
        return id;
    }

    /**
     * Get idGames
     *
     * @param IDUsers
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized String getIDGamesS(String IDUsers) throws SQLException, RemoteException{
        String IDGames = dbHelper.getIDGames(IDUsers);
        System.out.println(IDGames);
        return IDGames;
    }

    /**
     * Start games
     *
     * @param IDGames
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void startedGamesS(String IDGames) throws SQLException, RemoteException{
        dbHelper.startedGames(IDGames);
    }

    /**
     * End Games
     *
     * @param IDGames
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void endedGamesS(String IDGames) throws SQLException, RemoteException{
        //TODO qui bisogna passare il risultato finale delle manches
        int Scoregames = 0;
        dbHelper.endedGames(IDGames, Scoregames);
    }

    /**
     * Get Number of manches
     *
     * @param IDGames
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized int getNumberManchesS(String IDGames) throws SQLException, RemoteException {
        int nManche = dbHelper.getNumberManches(IDGames);
        return nManche;
    }

    /**
     * Get idManches
     *
     * @param IDPlayers
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized String getIDManchesS(String IDPlayers) throws SQLException, RemoteException{
        String IDManches = dbHelper.getIDManches(IDPlayers);
        return IDManches;
    }

    @Override
    /**
     * Create match
     */
    public synchronized boolean buildMatch(String IDGames, int nManche) throws IOException, SQLException, InterruptedException {

        ArrayList<String> listOfPhrases = dbHelper.notViewedPhrases(IDGames);
        Phrases phrase = dbHelper.getRandomPhrases(listOfPhrases);
        String IDPhrase = phrase.getId();
        System.out.println("id frase scelta ="+IDPhrase);
        String IDManche = dbHelper.newManches(IDGames, IDPhrase, nManche);
        ArrayList<String> my3players = dbHelper.getIDUsersFromSubs(IDGames);
        System.out.println("newPlayer 0: "+my3players.get(0)+" della manche "+IDManche);
        dbHelper.newPlayerFromIDUsers(my3players.get(0), IDManche);
        dbHelper.newPlayerFromIDUsers(my3players.get(1), IDManche);
        dbHelper.newPlayerFromIDUsers(my3players.get(2), IDManche);
        ArrayList<String> nick=new ArrayList<String>();
        nick.add(0,dbHelper.getNickFromIDUser(my3players.get(0)));
        nick.add(1,dbHelper.getNickFromIDUser(my3players.get(1)));
        nick.add(2,dbHelper.getNickFromIDUser(my3players.get(2)));
        ArrayList<String> listIDPlayers = new ArrayList<>();
        listIDPlayers.add(dbHelper.getIDPlayerFromIDUser(IDManche, my3players.get(0)));
        listIDPlayers.add(dbHelper.getIDPlayerFromIDUser(IDManche, my3players.get(1)));
        listIDPlayers.add(dbHelper.getIDPlayerFromIDUser(IDManche, my3players.get(2)));
        System.out.println("un idplayer mandato a game è: "+dbHelper.getIDPlayerFromIDUser(IDManche, my3players.get(0)));
        Game x = new Game(this, IDGames, IDManche, listIDPlayers, my3players, phrase, nick);
        game = x;
        partiteInGioco.add(x);
        return true;
    }

    /*@Override
    public synchronized String newMancheS(String idManche) throws SQLException, RemoteException, InterruptedException {
        Phrases p = new Phrases("suggerimento", "frase misteriosa");
        String IDManches = dbHelper.newManches(idManche, p, 1);
        return IDManches;
    }
     */

    /**
     * Move Spin
     *
     * @param IDPlayers
     * @param IDManches
     * @param SpinResult
     * @throws SQLException
     * @throws RemoteException
     */

    public synchronized void newMovesSpinS(String IDPlayers, String IDManches,  String SpinResult) throws SQLException, RemoteException {
        dbHelper.newMovesSpin(IDPlayers, IDManches,  SpinResult);
    }

    /**
     * Move Consonant
     *
     * @param IDPlayers
     * @param IDManches
     * @param Letters
     * @param Scoremoves
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void newMovesConsS(String IDPlayers, String IDManches,  String Letters, int Scoremoves) throws SQLException, RemoteException{
        dbHelper.newMovesCons(IDPlayers, IDManches, Letters, Scoremoves);
    }

    /**
     * Move Vocal
     *
     * @param IDPlayers
     * @param IDManches
     * @param Letters
     * @param Scoremoves
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void newMovesVocS(String IDPlayers, String IDManches,  String Letters, int Scoremoves)throws  SQLException, RemoteException{
        dbHelper.newMovesVoc(IDPlayers, IDManches, Letters, Scoremoves);
    }

    /**
     * Move get Solution
     *
     * @param IDManches
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized String getSolutionS(String IDManches) throws SQLException, RemoteException{
        String Phrase = dbHelper.getSolution(IDManches);
        return Phrase;
    }

    /**
     * Add Jolly
     *
     * @param IDPlayers
     * @param IDManches
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void addJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException{
        dbHelper.addJolly(IDPlayers, IDManches);
    }

    /**
     * Move use Jolly
     *
     * @param IDPlayers
     * @param IDManches
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void useJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException{
        dbHelper.useJolly(IDPlayers, IDManches);
    }

    /**
     * Get Jolly
     *
     * @param IDPlayers
     * @param IDManches
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized boolean getJollyS(String IDPlayers, String IDManches) throws SQLException, RemoteException{
        boolean jolly = dbHelper.getJolly(IDPlayers, IDManches);
        return jolly;
    }

    /**
     * Syncronized Games
     *
     * @return
     */
    public synchronized ArrayList<String> synchronizeGames() {
        return pendingGames;
   /*     ArrayList<String> seenByClientGames = new ArrayList<>();
        for(int i = 0; i<pendingGames.size(); i++) {
            String gameOnServer = pendingGames.get(i);
            System.out.println("partita in gioco con id: "+pendingGames.get(i));
            seenByClientGames.add(gameOnServer);
        }
        System.out.println("seenByClientGames 0: "+seenByClientGames.get(0));
        return seenByClientGames;

    */
    }

    /**
     * There are any andmin
     *
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized boolean anyAdminS() throws SQLException, RemoteException{

        Boolean okany = dbHelper.anyAdmin();
        return okany;
    }

    /**
     * You is andim
     *
     * @param Email
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized boolean isAdminS(String Email) throws RemoteException, SQLException {
        Boolean ok = dbHelper.isAdmin(Email);
        return ok;
    }

    /**
     * Insert subs
     *
     * @param Email
     * @param IDGames
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized void insertSubsS(String Email, String IDGames) throws RemoteException, SQLException {
        if (!(pendingGames.contains(IDGames))) {
            pendingGames.add(IDGames);
        }
        dbHelper.insertSubs(Email, IDGames);

    }

    /**
     * Check subs
     *
     * @param IDGames
     * @return
     * @throws RemoteException
     * @throws SQLException
     * @throws InterruptedException
     */
    public synchronized boolean checkSubsS(String IDGames) throws RemoteException, SQLException, InterruptedException {
        boolean partita = dbHelper.checkSubs(IDGames);
        return partita;
    }

    /**
     * Insert observer
     *
     * @param Email
     * @param IDGames
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized void insertSubsObserverS(String Email, String IDGames) throws RemoteException, SQLException{
        dbHelper.insertSubsObserver(Email, IDGames);
    }
    /*
    public synchronized void newPlayerS(String IDGames, String IDManches) throws SQLException, RemoteException {
        dbHelper.newPlayerFromIDUsers(IDGames, IDManches);
    }
     */

    public synchronized boolean isObserverS (String Email) throws SQLException, RemoteException{
        boolean isObs = dbHelper.isObserver(Email);
        return isObs;
    }

    @Override
    /**
     * Get id of player
     */
    public synchronized String getIDPlayerS(String IDManches, String Email) throws SQLException, RemoteException {
        String IDPlayer = dbHelper.getIDPlayerFromEmail(IDManches, Email);
        return IDPlayer;
    }

    /**
     * Get players
     *
     * @param IDManches
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized ArrayList<String> getPlayersS(String IDManches) throws SQLException, RemoteException {
        //
        ArrayList<String> players = dbHelper.getPlayers(IDManches);
        return players;
    }

    /**
     * Refresh phrases
     *
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized ArrayList<String> refreshPhrasesS() throws SQLException, RemoteException{
        ArrayList<String> phrases = dbHelper.refreshPhrases();
        return phrases;
    }

    /**
     * Delete all phrases
     *
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized void deleteAllPhrasesS() throws SQLException, RemoteException{
        dbHelper.deleteAllPhrases();
    }

    @Override
    /**
     * Create wait
     */
    public synchronized void createGameWaitingS() throws RemoteException {
        System.out.println("chiamo waitPlayers della classe ThreadUtils");
        //TODO nuova istanza Games in DB
    }

    @Override
    /**
     * Start waiting game
     */
    public synchronized void startGameWaitingS() throws RemoteException {

    }

    public synchronized boolean GameStartS() throws RemoteException{
        ThreadGameStart countdown = new ThreadGameStart(60000);
        countdown.start();
        boolean count = false;
        while(countdown.getState() == TIMED_WAITING) {
            count = false;
        }
        count = true;
        return count;//false in attesa e true scaduto
    }

    /**
     * Call consonant
     *
     * @param consonant
     * @throws RemoteException
     * @throws SQLException
     */
    public synchronized void callConsonant(char consonant) throws RemoteException, SQLException {
        char c=consonant;
        //.callConsonant(c);
    }

    @Override
    public DBHelper getDBHelper() throws RemoteException {
        return null;
    }

/*
    public synchronized void tryConsonant(String idManche, char consonant) throws IOException, SQLException {
        boolean consPresent = false;
        for(int i = 0; i < partiteInGioco.size(); i++) {
            if(idManche == partiteInGioco.get(i).getIdManches()) {
                partiteInGioco.get(i).isConsonantAlreadyPresent(consonant);
            }
        }
    }

 */

    @Override
    /**
     * Get my games
     */
    public Game getMyGame(String idPlayer) {
        Game found = null;
        for(int i = partiteInGioco.size() - 1; i >= 0; i--) {
            if(partiteInGioco.get(i).clientList.size() > 0) {
                System.out.println("mio idP per gettare game: "+idPlayer);
                System.out.println("clientList di una partita non vuota: "+partiteInGioco.get(i).clientList.get(0));
                if (partiteInGioco.get(i).clientList.get(0).equals(idPlayer) || partiteInGioco.get(i).clientList.get(1).equals(idPlayer) ||
                        partiteInGioco.get(i).clientList.get(2).equals(idPlayer)) {
                        System.out.println("found: "+partiteInGioco.get(i));
                        found = partiteInGioco.get(i);
                }
            }
        }
        return found;
    }

    @Override
    /**
     * my game
     */
    public Game refToMyGame(Object theGame) throws RemoteException, SQLException {
        int index = partiteInGioco.indexOf(theGame);
        return partiteInGioco.get(index);

    }

    /**
     * Not phrases viwes
     *
     * @param IDGames
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public synchronized ArrayList<String> notViewedPhrasesS(String IDGames) throws SQLException, RemoteException{
        ArrayList<String> viewedPhrases = dbHelper.notViewedPhrases(IDGames);
        return viewedPhrases;
    }

    /**
     * get idPlayer
     *
     * @param Email
     * @return
     * @throws SQLException
     */
    public String getCurrentIDPlayerS(String Email) throws SQLException {
        String myIDPlayer = dbHelper.getCurrentIDPlayer(Email);
        return myIDPlayer;
    }

    @Override
    /**
     * Get id manches
     */
    public String getMyIDMancheS(String IDUser) throws RemoteException {
        for(int i = 0; i < partiteInGioco.size(); i++) {
            if (partiteInGioco.get(i).getRelativeIDUsers().contains(IDUser)) {
                String myIDManche = partiteInGioco.get(i).getIdManches();
                System.out.println("getmyidmanche "+myIDManche);
                return myIDManche;
            }
        }
        System.out.println("IDManche non trovato nella lista delle partite attive.");
        return null;
    }

    @Override
    /**
     * Get id player
     */
    public String getMyIDPlayerS(String IDUser) throws RemoteException {
        for(int i = partiteInGioco.size() - 1 ; i >= 0 ; i--) {
            System.out.println("lista partite in gioco: " +partiteInGioco.get(i));
            if(partiteInGioco.get(i).relativeIDUsers.size() > 0) {
                ArrayList<String> giocatori = (ArrayList<String>) partiteInGioco.get(i).relativeIDUsers.clone();

                System.out.println("i relative user sono " + giocatori.get(0));
                System.out.println("i relative user sono " + giocatori.get(1));
                System.out.println("i relative user sono " + giocatori.get(2));

                System.out.println("questo è il game in cui c'è il mio " + IDUser + " iduser ");
                if (partiteInGioco.get(i).relativeIDUsers.contains(IDUser)) {
                    String myIDPlayer = partiteInGioco.get(i).getMyIDPlayer(IDUser);
                    System.out.println("getmyidplayers " + myIDPlayer);
                    return myIDPlayer;
                }
            }
        }
        System.out.println("IDPlayer non trovato nella lista delle partite attive.");
        return null;
    }


    //Query per STATISTICHE
    public synchronized String userMaxManchesS() throws SQLException, RemoteException{
        String IDUsers = dbHelper.userMaxManches();
        return IDUsers;
    }
    public synchronized String userMaxGamesS() throws SQLException, RemoteException{
        String IDUsers = dbHelper.userMaxGames();
        return IDUsers;
    }
    public synchronized String userMoreManchesS() throws SQLException, RemoteException{
        String IDUsers = dbHelper.userMoreManches();
        return IDUsers;
    }

    public ArrayList<String> CSVReaderS(String Path) throws Exception {
        ArrayList<String> path = new ArrayList<>();
        path = csvReader.CSVReader(Path);
        return path;
    }


    @Override
    public void endMyTurn(ArrayList<Character> lettereUscite) throws IOException, SQLException {
        game.notificaTurno(lettereUscite);
    }

    @Override
    public void winTheGame(String nickWinner) throws RemoteException, IOException, SQLException {
        game.notificaVittoriaManche(nickWinner);
    }

    public List<RdF.Client.ClientInterface> futurePlayers = new ArrayList<>();

    public List<RdF.Client.ClientInterface> getFuturePlayers() {
        return futurePlayers;
    }

    public void setFuturePlayers(List<RdF.Client.ClientInterface> futurePlayers) {
        this.futurePlayers = futurePlayers;
    }

    @Override
    public void addMe(RdF.Client.ClientInterface clientInterface) throws RemoteException {
        List<ClientInterface> newPlayer = new ArrayList<>();
        newPlayer = getFuturePlayers();
        newPlayer.add(clientInterface);
        setFuturePlayers(newPlayer);
    }



}


