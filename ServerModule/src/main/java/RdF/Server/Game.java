package RdF.Server;

import RdF.Client.ClientInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.*;

public class Game implements Serializable {

    private static final long serialVersionUID = 7L;
    ArrayList<String> clientList = new ArrayList<String>(); //id aggiunti da ogni client su iscrizione partita o creazione
    public ArrayList<String> relativeIDUsers = new ArrayList<String>();
    public ArrayList<String> relativeNicknames = new ArrayList<String>();
    public static ArrayList<Character> listGuessedLetters = new ArrayList<>();
    public static ArrayList<Character> listGuessedConsonants = new ArrayList<>();
    ArrayList<Character> listPhraseLetters = new ArrayList<>();
    private String actualPlayer;
    ServerInterface server;
    private String idGames;
    private String idManches;
    private Phrases mysteriousPhrase;
    private int nManche = 1;
    private List<RdF.Client.ClientInterface> RmiPlayers;
    public Registry reg;
    static int userTurn = 0;


    public static int getUserTurn() {
        return userTurn;
    }

    public static void setUserTurn(int userTurn) {
        Game.userTurn = userTurn;
    }


    /**
     * Get idGames
     *
     * @return
     */
    public String getIdGames() {
        return idGames;
    }

    /**
     * Set idGames
     *
     * @param idGame
     */
    public void setIdGames(String idGame) {
        this.idGames = idGame;
    }


    public Game() throws RemoteException {
        super();

    }

    /**
     * Constructor
     *
     * @param s
     * @param idGame
     * @param IDManche
     * @param idPlayers
     * @param idUsers
     * @param phrase
     * @throws RemoteException
     * @throws SQLException
     * @throws InterruptedException
     */
    public Game(ServerRDF s, String idGame, String IDManche, ArrayList<String> idPlayers, ArrayList<String> idUsers, Phrases phrase, ArrayList<String> nicks) throws IOException, SQLException, InterruptedException {
        server = s;
        idGames = idGame;
        idManches = IDManche;
        clientList = (ArrayList<String>)idPlayers.clone();
        relativeIDUsers = (ArrayList<String>)idUsers.clone();
        relativeNicknames = (ArrayList<String>)nicks.clone();
        System.out.println("nick1: "+relativeNicknames.get(0));
        System.out.println("nick2: "+relativeNicknames.get(1));
        System.out.println("nick3: "+relativeNicknames.get(2));
        mysteriousPhrase = phrase;
        System.out.println("la frase è: "+phrase.hintP+" "+phrase.phraseP);
        listPhraseLetters = convertPhraseToChars(mysteriousPhrase.phraseP);
        setActualPlayer(firstPlayer(relativeNicknames));
        RmiPlayers = new ArrayList<ClientInterface>();
        RmiPlayers = s.getFuturePlayers();
        initializeTable(mysteriousPhrase, getActualPlayer());
        System.out.println("ActualPlayer è "+getActualPlayer());
    }


    public ArrayList<Character> getListGuessedLetters() {
        return listGuessedLetters;
    }

    public void setListGuessedLetters(ArrayList<Character> listGuessedLetters) {
        this.listGuessedLetters = listGuessedLetters;
    }

    public ArrayList<String> getRelativeNicknames() {
        return relativeNicknames;
    }

    public void setRelativeNicknames(ArrayList<String> relativeNicknames) {
        this.relativeNicknames = relativeNicknames;
    }

    /**
     * Get Hint
     *
     * @return
     */
    public String getHintPhrase() {
        System.out.println("viene chiamato hint del game: "+mysteriousPhrase.hintP);
        return mysteriousPhrase.hintP;

    }

    /**
     * Get phrases
     *
     * @return
     */
    public String getMysteriousPhrase() {
        return mysteriousPhrase.phraseP;
    }

    /**
     * If consonant is present
     *
     * @param lettersList
     * @return
     */
    public boolean isConsonantAlreadyPresent(ArrayList<Character> lettersList) throws IOException, SQLException {
        System.out.println("chiamato isConsonantAlreadyPresent in Game");
        boolean arePresent = false;
        for (int i = 0; i < lettersList.size(); i++) {
            if (mysteriousPhrase.getPhrase().indexOf(lettersList.get(i)) != -1) {
                arePresent = true;
            }
            else {
                String letterListToString = lettersList.toString().replaceAll(", |\\[|\\]", "");
                fraseAggiornata(letterListToString);
                System.out.println("String.valueOf(lettersList) è "+lettersList.toString()+" il contenuto è "+lettersList.get(i));
                arePresent = false;
            }
        }
        return arePresent;
    }

    /**
     * Convert to chart
     *
     * @param phrase
     * @return
     */
    public ArrayList<Character> convertPhraseToChars(String phrase) {
        phrase = phrase.toUpperCase().trim();
        for(int i = 0; i < phrase.length(); i++) {

            if(phrase.charAt(i) != 'a' || phrase.charAt(i) != 'e' || phrase.charAt(i) != 'i'
                    || phrase.charAt(i) != 'o' || phrase.charAt(i) != 'u'|| phrase.charAt(i) != ' '
                    || !listPhraseLetters.contains(phrase.charAt(i))) {
                listPhraseLetters.add(phrase.charAt(i));
            }
        }
        return listPhraseLetters;
    }

    /**
     * Consonant are expired
     *
     * @return
     */
    public boolean haveConsonantsRunOut() {
        if (listGuessedConsonants.containsAll(listPhraseLetters)) {
         return true;
     }
     else {
        return false;
     }
    }

    /**
     * Get IdPlayer
     *
     * @param IDUser
     * @return
     */
    public String getMyIDPlayer(String IDUser) {
        int wheresMyIDPlayer = relativeIDUsers.indexOf(IDUser);
        String myIdPlayer = clientList.get(wheresMyIDPlayer);
        return myIdPlayer;
    }

    /**
     * Get IdManches
     *
     * @return
     */
    public String getIdManches() {
        return idManches;
    }

    /**
     * Set idManches
     *
     * @param idManches
     */
    public void setIdManches(String idManches) {
        this.idManches = idManches;
    }

    /**
     * Get IdUsers
     *
     * @return
     */
    public ArrayList<String> getRelativeIDUsers() {
        return relativeIDUsers;
    }

    /**
     * Add player
     *
     * @param idPlayer
     */
    public void addPlayer(String idPlayer) {
        clientList.add(idPlayer);
    }

    public void setActualPlayer(String actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    /**
     * Get Actual player
     *
     * @return
     */
    public String getActualPlayer() {
        return actualPlayer;
    }


    /**
     * If solution is correct
     *
     * @return
     */
    public synchronized boolean isWordGuessedRight(String mySolution) throws SQLException, IOException, InterruptedException {
        boolean wordGuessed = false;
        System.out.println("myst frase: "+mysteriousPhrase.getPhrase().toUpperCase()+ " e mysolution: "+mySolution.toUpperCase());
        if(mysteriousPhrase.getPhrase().equals(mySolution)) {
            nManche = numberManche(idGames);
            server.buildMatch(idGames, nManche+1);
            wordGuessed = true;
        }
        return wordGuessed;
    }

    public synchronized boolean createNewManche() throws SQLException, IOException, InterruptedException {
        System.out.println("ho indovinato e creo la nuova manche");
        nManche = numberManche(idGames);
        server.buildMatch(idGames, nManche+1);
        return true;
    }

    /**
     * Constructor
     * Numbver Mnaches
     *
     * @param idGames
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public int numberManche(String idGames) throws SQLException, RemoteException {
        int nManche = server.getNumberManchesS(idGames); //ottiene il numero della manches relativa all'id games. Restituisce l'ultima manche inserita.
        return nManche;
    }

    /**
     * First Player
     *
     * @param nicknames
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    public String firstPlayer(ArrayList<String> nicknames) throws RemoteException, SQLException {
        ArrayList<String> players = nicknames;
        int randomizer = new Random().nextInt(3);
        System.out.println("firstPlayer ha scelto "+players.get(randomizer));
        setUserTurn(randomizer);
        return players.get(randomizer);
    }

    public void initializeTable(Phrases phrases, String ActualPlayer) throws IOException {
        for(int i=0 ; i< RmiPlayers.size(); i++) {
            System.out.println("entro nel for di initializeTable con rmiplayers");
            RmiPlayers.get(i).initializePhrase(phrases, ActualPlayer);
        }
    }


    public void notificaTurno(ArrayList<Character> lettereUscite) throws IOException, SQLException {
        int nextPlayer = getUserTurn();
        nextPlayer=(nextPlayer+1)%3;
        setUserTurn(nextPlayer);
        String lettereUsciteToString = lettereUscite.toString().replaceAll(", |\\[|\\]", "");
        for(int i=0;i<RmiPlayers.size();i++) {
            RmiPlayers.get(i).fraseAggiornata(lettereUsciteToString, relativeNicknames.get(getUserTurn()));
        }
    }


    public void fraseAggiornata(String updPhrase) throws IOException, SQLException {
        System.out.println("chiamo FraseAggiornata "+updPhrase);
        for(int i=0 ; i< RmiPlayers.size(); i++) {
            System.out.println("entro nel for di frase aggiornata con rmiplayers");
            RmiPlayers.get(i).fraseAggiornata(updPhrase, relativeNicknames.get(userTurn));
        }

    }


    public void notificaVittoriaManche(String nickWinner) throws RemoteException {
        for(int i=0;i<RmiPlayers.size();i++)
            if(i!=userTurn)
                RmiPlayers.get(i).notificaVittoriaManche(nickWinner);
    }

}
