package Database;

import RdF.Server.Game;
import RdF.Server.Phrases;
import RdF.Server.ServerRDF;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DBHelper extends UnicastRemoteObject implements DBHelperInterface {

        public static Connection conn = null;
        public ServerRDF serverRDF;
        private Hashing hashing = new Hashing();

        public DBHelper(ServerRDF s) throws RemoteException {
            super();
            serverRDF = s;
        }

    /**Crea la conessione con il Database**/
    public boolean startConnection(String hostname, String port, String db) throws SQLException {
    boolean connection = false;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + db, "postgres", "123");
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
            if (conn != null) {
                System.out.println("Connected to the database!");
                connection = true;
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**Ottieni IDUsers da Email**/
    public String getIDUsers (String Email) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from users where email='"+Email+"' ");
        resultSet.next();
        System.out.println("la mail per trovare l'iduser è "+Email);
        String IDUsers = resultSet.getString(1);
        return IDUsers;
    }

    public String getIDUsersFromNick (String Nickname) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from users where nickname='"+Nickname+"' ");
        resultSet.next();
        String IDUsers = resultSet.getString(1);
        return IDUsers;
    }

    public String getNickFromEmail (String Email) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from users where email='"+Email+"' ");
        resultSet.next();
        String Nickname = resultSet.getString(2);
        return Nickname;
    }

    @Override
    public String getNickFromIDUser(String idUser) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from users where idusers='"+idUser+"' ");
        resultSet.next();
        String Nickname = resultSet.getString(2);
        return Nickname;
    }



    /**Controlla la presenza di Utenti Admin **/
    public boolean anyAdmin() throws SQLException, RemoteException {
        boolean admin = false;
        Statement stm = conn.createStatement();
        ResultSet result = stm.executeQuery("select * from users where admin='true'");
        if (result.next()) {
            admin = true;
            System.out.println("Esistono admin");
            return admin;
        }
        else {
            System.out.println("Non esistono admin");
            return admin;
        }
    }

    /**Controlla che un utente sia Admin**/
    public boolean isAdmin(String Email) throws SQLException, RemoteException {
        boolean admin1 = false;
        Statement stm = conn.createStatement();
        ResultSet result = stm.executeQuery("SELECT * FROM Users WHERE email='"+Email+"' and admin='true'");
        if (result.next()) {
            admin1 = true;
            System.out.println("Login Admin corretto!");
            return admin1;
        }
        else {
            System.out.println("Login Admin NON corretto!");
            return admin1;
        }

    }

    /**Registra un nuovo utente (sia Admin che non) in base ai parametri passati**/
    public void newUsers(String Nickname, String Name, String Surname, String Email, String Password, Boolean Admin, Boolean Online) throws Exception {
        //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
        String PwHashed = hashing.getSaltedHash(Password);
        Statement stm = conn.createStatement();
        stm.executeUpdate("INSERT INTO users (nickname, name, surname, email, password, gameswin, admin, online) " +
                "VALUES ('" + Nickname + "' , '" + Name + "', '" + Surname + "', '" + Email + "', '" + PwHashed + "', '0', '" + Admin + "','" + Online + "') ");

    }

    /**Autentica in fase di login**/
    public boolean loginUsers(String Email, String Password) throws Exception {
        boolean user = false;
        Statement stm = conn.createStatement();
        ResultSet resultSet1 = stm.executeQuery("select * from users where email='"+Email+"'");
        resultSet1.next();
        String StoredPw = resultSet1.getString(6);
        boolean PwOk = hashing.check(Password, StoredPw);
        System.out.println("hashing check è "+PwOk);
        //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");

        if(PwOk) {

            String sql = "SELECT * FROM Users WHERE Email='" + Email + "' AND Password='" + StoredPw + "' ";
            //Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = stm.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("Utente gia\' registrato! Settato 'Online' = True");
                stm.executeUpdate("update users set online='true' where email='" + Email + "' ");
                //resultSet.updateBoolean("online", true);
                //resultSet.updateRow();
                user = true;
            } else {
                user = false;
            }
            //Statement sql = conn.createStatement();
            //System.out.println(String.valueOf(stm));
            //sql.executeQuery("update users set online = true where idusers = '"+String.valueOf(sql)+"' ");
        }
        return user;
    }

    /**Permette di effettuare logout dal sistema**/
    public void logoutUsers(String Email) throws SQLException{
        Statement stm = conn.createStatement();
        stm.executeUpdate("update users set online='false' where email='"+Email+"' ");

    }


    /**Modifica la password di un utente**/
    public String setPassword(String Email, String OldPw, String NewPw) throws Exception {
        String passok = "Password modificata con successo";
        String passnotok = "Password vecchia errata!";

        //String oldPwHashed = hashing.getSaltedHash(OldPw);
        String PwHashed = hashing.getSaltedHash(NewPw);

        Statement stm = conn.createStatement();

        ResultSet resultSet1 = stm.executeQuery("select * from users where email='"+Email+"'");
        resultSet1.next();
        String HashedOldPw = resultSet1.getString(6);
        boolean isok = hashing.check(OldPw, HashedOldPw);

        //ResultSet resultSet = stm.executeQuery("select * from users where email='"+Email+"'and password='"+oldPwHashed+"' ");
        if(isok) {
            stm.executeUpdate("update users set password='" + PwHashed + "' where email='" + Email + "' ");
            return passok;
        }
        else
            return passnotok;
    }

    /**Imposta una password generata dal sistema ad un utente appena registrato**/
    public void resetPassword(String Email, String ResetPass) throws Exception {
        System.out.println("Questa è la pass in chiaro:" +ResetPass);

        String PwHashed = hashing.getSaltedHash(ResetPass);
        System.out.println("password salata: "+PwHashed);

        Statement stm = conn.createStatement();
        stm.executeUpdate("update users set password='"+PwHashed+"' where email='"+Email+"' ");

    }

    /**Modifica parametri di User: Nome, Cognome, Nickname**/
    public String setUser(String Email, String NewName, String NewSurname, String NewNick) throws SQLException {
        System.out.println("gggggg"+ NewName+" "+NewSurname+" "+NewNick);
        String isOk = "isOk";
        String notOk = "notOk";
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from users where nickname='"+NewNick+"' ");
        System.out.println("dbhelper pre if");
        if(!(resultSet.next())){
            stm.executeUpdate("update users set name='"+NewName+"' , surname='"+NewSurname+"' , nickname='"+NewNick+"' where email='" + Email + "' ");
            System.out.println("dbhelper ok true");
            return isOk;
        }
        else
            return notOk;

    }

    /**Crea un nuovo Gioco**/
    public String newGames() throws SQLException, RemoteException {

        //LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Statement stm = conn.createStatement();
        stm.executeUpdate( "INSERT INTO games (dategame , scoregames , state) VALUES('" + date + "','0','InAttesa')");
        ResultSet resultSet = stm.executeQuery("select * from games where dategame='"+date+"'");
        resultSet.next();
        String id = resultSet.getString(1);

        return id;

    }

    /**Ottiene l'ID di una specifica tupla di Gioco **/
    public String getIDGames(String IDUsers) throws SQLException, RemoteException{
        Statement stm = conn.createStatement();

        ResultSet resultSet = stm.executeQuery("select * from subs where idusers='"+IDUsers+"' ");
        resultSet.last();
        String IDGames = resultSet.getString(3); //terza colonna di subs è idmanches
        return IDGames;
    }

    /** Mette una partita allora stato "InCorso" che indica il fatto che la partita è in corso di svolgimento**/
    public void startedGames(String IDGames) throws SQLException{
        Statement stm = conn.createStatement();
        stm.executeUpdate("update games set state='InCorso' where idgames='"+IDGames+"' ");
    }

    /**Mette lo stato di una partita a "Terminata" che indica il fatto che la partita sia finita. **/
    public void endedGames(String IDGames, int Scoregames) throws SQLException{
        Statement stm = conn.createStatement();
        stm.executeUpdate("update games set state='Terminata' where idgames='"+IDGames+"' ");
    }


    /**Registra un utente come iscritto ad una partita come partecipante**/
    public void insertSubs(String Email, String IDGames) throws SQLException {
        //TODO sistemare getIDGames per ottenere l'id della partita desiderata

        //prima controllo che non ci sono gia 3 iscritti alla partita
        boolean partita = false;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL = "SELECT * FROM Subs WHERE IDGames='"+IDGames+"' AND UsersType='Partecipante' ";
        ResultSet resultSet = stm.executeQuery(SQL);
        //resultSet.next();
        resultSet.last();
        int rownumber = resultSet.getRow();
        //System.out.println(rownumber);
        //int idgames = Integer.parseInt(IDGames);
        /*
        while (resultSet.getRow()) {
            count[idgames]++;
        }

         */
        ResultSet result = stm.executeQuery("select * from users where email='"+Email+"'");
        result.next();
        String IDUsers = result.getString(1);

        if (rownumber<3) {
            //c'è ancora posto quindi posso aggiungere un iscritto
            stm.executeUpdate("INSERT INTO Subs (IDUsers, IDGames, UsersType) VALUES ('" + IDUsers + "','" + IDGames + "', 'Partecipante')");
        }
        else {
            stm.executeUpdate("INSERT INTO Subs (IDUsers, IDGames, UsersType) VALUES ('" + IDUsers + "','" + IDGames + "', 'Osservatore')");
            System.out.println("Partecipanti = 3 quindi Pieno!");
        }
        //if (rownumber == 2) // se ci sono due righe, vul dire che quello che ha chiamato per la terza volta questo metodo viene aggiunto come partecipante e saranno quindi 3, parte la partita
        //partita = true;


    }

    /**Controlla quando gli iscritti partecipanti ad una partita sono 3, e quando lo sono registro gli id in players e lancio il game **/
    public boolean checkSubs(String IDGames) throws RemoteException, SQLException, InterruptedException { //controllo se gli iscritti ad una partita sono 3 e quindi la lancio
        boolean partita = false;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result = stm.executeQuery("select * from subs where idgames='"+IDGames+"' AND UsersType='Partecipante'");
        result.last();
        int rownumber = result.getRow();

        if (rownumber == 3) {
            partita = true;

        }

        return partita;
    }

    /**Ottine i 3 IDUsers dall'IDGames specificato**/
    public ArrayList<String> getIDUsersFromSubs(String IDGames) throws SQLException {
        ArrayList<String> IDUsersFromSubs = new ArrayList<>();
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from subs where idgames='"+IDGames+"' and UsersType='Partecipante' ");
        while (resultSet.next()){
            IDUsersFromSubs.add(resultSet.getString(4));
        }
        return IDUsersFromSubs;
    }


    /**Registra un utente come iscritto osservatore ad una partita**/
    public void insertSubsObserver (String Email, String IDGames) throws SQLException {
        Statement stm = conn.createStatement();
        String IDUsers = this.getIDUsers(Email); //ottengo idusers dall'email
        //aggiungo l'user agli osservatori della partita
        stm.executeUpdate("insert into Subs (idusers, idgames, userstype) values ('"+IDUsers+"', '"+IDGames+"', 'Osservatore')");
    }

    /**Controlla se un utente è osservatore**/
    public boolean isObserver (String Email) throws SQLException {
        String IDUsers = this.getIDUsers(Email);
        boolean isObs = false;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from subs where idusers='"+IDUsers+"' and UsersType='Osservatore' ");
        if (resultSet.last()){
            isObs = true;
        }
        else {
            isObs = false;
        }

        return isObs;
    }

    /**Elimina un utente dalla lista degli iscritti osservatori di una partita**/
    public void deleteSubsObserver (String Email, String IDGames) throws SQLException{

    }

    /**Genera una nuova Manches **/
    public String newManches(String IDGames,  String IDPhrases, int NumberManches) throws SQLException, InterruptedException, RemoteException {
        System.out.println("parametri in ingresso di newManche: " +IDGames+"  " +IDPhrases+" " +NumberManches);
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        stm.executeUpdate("insert into Manches (idgames, idphrases, numbermanches) values ('"+IDGames+"', '"+IDPhrases+"', '"+NumberManches+"')");

        ResultSet resultSet = stm.executeQuery("select * from manches");
        String IDManches = new String();
        resultSet.last();
        IDManches = resultSet.getString(1);

        System.out.println("questa è l'idmanche"+IDManches);

        return IDManches;

    }


    /**Ottiene l'id di una manches dall'id del Players in cui sta partecipando**/
    public String getIDManches(String IDPlayers) throws SQLException { //metodo che ottiene idmanches da email utente
        Statement stm = conn.createStatement();

        ResultSet resultSet = stm.executeQuery("select * from players where idplayers='"+IDPlayers+"' ");
        resultSet.next();
        String IDManches = resultSet.getString(3);
        return IDManches;
    }

    /**Ottiene il numero della Manche in corso**/
    public int getNumberManches(String IDGames) throws SQLException {
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from Manches where idgames='"+IDGames+"' ");
        resultSet.next();
        resultSet.last();
        int manches = resultSet.getInt(3);
        return manches;
    }

    /**Inserisce una nuova mossa di tipo Spin relativa ad un giocatore in una manche di gioco**/
    public void newMovesSpin(String IDPlayers, String IDManches, String SpinResult) throws SQLException, RemoteException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("insert into moves (idplayers, idmanches, movetype, spinresult) values ('"+IDPlayers+"', '"+IDManches+"', 'Ruota', '"+SpinResult+"' ) ");
    }

    /**Inserisce una nuova mossa di tipo chiama consonante relativa ad un giocatore in una manche di gioco**/
    public void newMovesCons(String IDPlayers, String IDManches, String Letters, int Scoremoves) throws SQLException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("insert into moves (idplayers, idmanches, letters, scoremoves) values ('"+IDPlayers+"', '"+IDManches+"', 'Consonante', '"+Letters+"', '"+Scoremoves+"' ) ");
    }

    /**Inserisce una nuova mossa di tipo chiama vocale relativa ad un giocatore in una manche di gioco**/
    public void newMovesVoc(String IDPlayers, String IDManches, String Letters, int Scoremoves) throws SQLException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("insert into moves (idplayers, idmanches, movetype, letters, scoremoves) values ('"+IDPlayers+"', '"+IDManches+"', 'Vocale', '"+Letters+"', '"+Scoremoves+"' ) ");
    }

    /**Restituisce la Frase misteriosa della Manches**/
    public String getSolution(String IDManches) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from manches where idmanches='"+IDManches+"'");
        resultSet.next();
        String IDPhrases = resultSet.getString(5);
        ResultSet resultSet1 = stm.executeQuery("select * from phrases where idphrases='"+IDPhrases+"' ");
        resultSet1.next();
        String Phrase = resultSet1.getString(3);
        return Phrase;
    }


    /**Aggiunge il Jolly al giocatore che lo ha vinto**/
    public void addJolly(String IDPlayers, String IDManches) throws SQLException { //metodo che setta Jolly a true perchè ottenuto in una moves
        Statement stm = conn.createStatement();
        stm.executeUpdate("update players set jolly='true' where idplayers='"+IDPlayers+"' and idmanches='"+IDManches+"' ");
    }

    /**Elimina il Jolly al giocatore che lo ha usato**/
    public void useJolly(String IDPlayers, String IDManches) throws SQLException { //metodo che setta Jolly a false perchè sato in una moves
        Statement stm = conn.createStatement();
        stm.executeUpdate("update players set jolly='false' where idplayers='"+IDPlayers+"' and idmanches='"+IDManches+"' ");
    }

    /**Controlla se un giocatore ha il Jolly o meno**/
    public boolean getJolly(String IDPlayers, String IDManches) throws SQLException{
        boolean jolly = false;
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from players where idplayers='"+IDPlayers+"' and idmanches='"+IDManches+"' ");
        if(resultSet.next())
            jolly = true; //il player ha il jolly
        return jolly;
    }


    /**Registra un utente come Player di una partita tramite IDUser **/
    public void newPlayerFromIDUsers(String IDUsers, String IDManches) throws SQLException { //in questo metodo genero player con l'id dei tre giocatori, e l'id della relativa Manches
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("IDUser è "+IDUsers);
        stm.executeUpdate("insert into players (idusers, idmanches, jolly, mancheswin) values ('"+IDUsers+"', '"+IDManches+"', 'false', '0') ");

    }


    /**Fornisce l'ID di un Player dall'Email utente e relativo alla Manches in corso**/
    @Override
    public String getIDPlayerFromEmail(String IDManches, String Email) throws SQLException, RemoteException {
        Statement stm = conn.createStatement();
        //ottengo l'idusers dalla email
        ResultSet resultSet = stm.executeQuery("select * from users where email='"+Email+"' ");
        resultSet.next();
        String IDUsers = resultSet.getString(5);
        //ottengo l'idplayers da IDManches e IDUsers
        ResultSet resultSet1 = stm.executeQuery("select * from players where idusers='"+IDUsers+"' and idmanches='"+IDManches+"' ");
        resultSet1.next();
        String IDPlayers = resultSet1.getString(1);
        return IDPlayers;
    }

    /**Fornisce l'ID di un Player dall'IDUser e relativo alla Manches in corso**/
    @Override
    public String getIDPlayerFromIDUser(String IDManches, String IDUsers) throws SQLException, RemoteException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from players where idusers='"+IDUsers+"' and idmanches='"+IDManches+"' ");
        resultSet.next();
        String IDPlayers = resultSet.getString(1);
        return IDPlayers;
    }


    /**Ottiene l'ID Player corrente di un User**/
    public String getCurrentIDPlayer(String Email) throws SQLException{
        String IDUsers = this.getIDUsers(Email);
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from players where idusers='"+IDUsers+"' ");
        resultSet.last();
        String IDPlayers = resultSet.getString(1);
        return IDPlayers;
    }

    /**Fornisce tutti gli ID dei players che giocano una determinata manches**/
    public ArrayList<String> getPlayers(String IDManches) throws SQLException{ //ottengo l'id dei tre player di una manches passata in input
        ArrayList<String> players = null;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from players where idmanches='"+IDManches+"' ");
        int i = 0;
        while(resultSet.next()){
            players.add(i, resultSet.getString(3)); //la terza colonna è l'idusers del player
            i++;
        }
        return players;
    }

    /**Fornisce una frase random dalla lista delle frasi disponibili**/
    public Phrases getRandomPhrases(ArrayList<String> listOfPhrases) throws SQLException, RemoteException {
        Statement stm = conn.createStatement();
        String chosenPhrase;
        if(listOfPhrases.size() == 0) {
            System.out.println("tutte le frasi del database sono già state viste dai 3 concorrenti!");
            chosenPhrase = "1";
        }
        else {
            int ran = new Random().nextInt(listOfPhrases.size());
            chosenPhrase = listOfPhrases.get(ran);
        }

        ResultSet resultSet = stm.executeQuery("select * from phrases where idphrases='"+chosenPhrase+"'");
        resultSet.next();

        String id = chosenPhrase;
        String hint = resultSet.getString(2);
        String phrases = resultSet.getString(3);
        System.out.println("hint ="+hint+" e frase ="+phrases);
        Phrases p = new Phrases( id, hint, phrases);
        serverRDF.phrasesArrayList.add(p);

        return p;

    }

    /**Restituisce un ArrayList contenente tutte le frasi presenti sul database **/
    public ArrayList<String> refreshPhrases() throws SQLException {
        ArrayList<String> phrases = new ArrayList<String>();
        int i = 0;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from phrases");
        while (resultSet.next()){
            phrases.add(i, resultSet.getString(2));
            phrases.add(i+1, resultSet.getString(3));
            i+=2;
        }
        return phrases;
    }

    /**Aggiunge una frase alla lista frasi disponibili**/
    public void insertPhrases(String Hint, String Phrase) throws SQLException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("insert into Phrases (Hint, Phrase) values ('"+Hint+"', '"+Phrase+"')");
    }

    /**Cancella tutte le frasi dal DB**/
    public void deleteAllPhrases() throws SQLException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("delete from phrases");
    }



    /**Permette la modifica di una frase della lista frasi**/
    public void editPhrases(String IDPhrases, String Hint, String Phrases) throws SQLException {
        Statement stm = conn.createStatement();
        //definire cosa modificare da un if relativo
        stm.executeUpdate("update phrases set hint='"+Hint+"' where idphrases='"+IDPhrases+"'"); //modifica frase
        stm.executeUpdate("update phrases set phrases='"+Phrases+"' where idphrases='"+IDPhrases+"'"); //modifica hint

    }

    /**Restituisce le frasi mai viste dai tre Player di un Games**/
    public ArrayList<String> notViewedPhrases(String IDGames) throws SQLException {
        ArrayList<String> notViewedPhrases = new ArrayList<>();
        int i = 0;
        int j = 0;
        String[] IDUsers = new String[3];
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        //Trovo gli idusers interessati.
        ResultSet resultSet = stm.executeQuery("select * from subs where idgames='"+IDGames+"' and UsersType='Partecipante' ");
        while(resultSet.next()){
            IDUsers[i] = resultSet.getString(4);
            i++;
        }

        //trovo le frasi non viste dai 3 idusers
        ResultSet resultSet1 = stm.executeQuery("SELECT  *  from phrases where idphrases NOT IN (SELECT idphrases FROM Subs CROSS JOIN Manches WHERE Subs.IDGames = Manches.IDGames and IDUsers in ('"+IDUsers[0]+"', '"+IDUsers[1]+"', '"+IDUsers[2]+"')) ");
        while (resultSet1.next()){
            notViewedPhrases.add(resultSet1.getString(1));
            j++;
        }
        return notViewedPhrases;
    }

    /**Utente che detiene il primato di punteggio per Manches**/
    public String userMaxManches() throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from players where idmanches in " +
                "(select idmanches from manches where scoremanches=" +
                "(select MAX(scoremanches) from manches))");
        resultSet.next();
        String IDUsers = resultSet.getString(2);
        return IDUsers;
    }

    /**Utente che detiene il primato di punteggio per Games**/
    public String userMaxGames() throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("select * from subs where idgames in " +
                "(select idgames from games where scoregames=" +
                "(select MAX(scoregames) from games))");
        resultSet.next();
        String IDUsers = resultSet.getString(2);
        return IDUsers;
    }

    /**Utente che ha giocato più manches in assoluto**/
    public String userMoreManches() throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT IDUsers, COUNT(IDUsers) FROM Players " +
                "GROUP BY IDUsers HAVING COUNT(IDUsers)=" +
                "(SELECT COUNT(IDUsers) AS TotalCount FROM Players " +
                "GROUP BY IDUsers ORDER BY TotalCount )");
        resultSet.next();
        String IDUsers = resultSet.getString(1);
        return IDUsers;
    }

    /**Partite giocate per Users**/
    public int userNumberGames(String Email) throws SQLException {
        String IDUsers = this.getIDUsers(Email);
        int count = 0;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from subs where idusers='"+IDUsers+"' and usertype='partecipante' ");
        while(resultSet.next()){
            count++;
        }
        return count;
    }

    /**Manche giocate per Users**/
    public int userNumberManches(String Email) throws SQLException {
        String IDUsers = this.getIDUsers(Email);
        int count = 0;
        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stm.executeQuery("select * from players where idusers='"+IDUsers+"' ");
        while(resultSet.next()){
            count++;
        }
        return count;
    }


}

