package GUI;

import Database.CSVReader;
import RdF.Client.ClientInterface;
import RdF.Client.ClientRDF;
//import RdF.Client.WrappedObserver;
import RdF.Server.ServerInterface;
import SMTP.EmailSender;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class Partite extends UnicastRemoteObject implements Initializable, Serializable {
    //ALL
    public AnchorPane pane_error,parent,pane_top,pane_lobby,pane_stat,pane_noPlayerSearch,pane_modifyData,pane_tutorial,pane_gameAndPhrases,pane_modifyPassword,pane_addAdmin;
    public Text text_T1_01_PlayerName,text_T1_02_Point,text_T1_11_PlayerName,text_T1_12_Point,text_T1_21_PlayerName,text_T1_22_Point,text_T1_31_PlayerName,text_T1_32_Point;
    public Text text_T2_01_PlayerName,text_T2_02_Point,text_T2_11_PlayerName,text_T2_12_Point;
    public Text text_T3_01_Point,text_T3_11_Point,text_T3_21_Point,text_T3_31_Point;
    public Text text_T4_01_Point;
    public Text text_T5_00_Cons,text_T5_01_Point,text_T5_11_PlayerName,text_T5_3_Phrase;
    public Text text_T6_01_Point,text_T6_11_Point,text_T6_21_Point,text_T6_31_Point,text_T6_71_Point,text_T6_61_Point,text_T6_51_Point,text_T6_41_Point;
    public Text text_T7_01_Point,text_T7_11_Point,text_T7_21_Point,text_T7_31_Point,text_T7_71_Point,text_T7_61_Point,text_T7_51_Point,text_T7_41_Point;
    public JFXButton btn_saveAD,btn_allStat,btn_addAdmin,btn_refresh,btn_search;
    public FontAwesomeIcon btn_closeError,fonta_Tutorial,fonta_UserORStat;
    public Text text_error,text_error1;
    public Text text_currentPlayerName,text_CurrentUserORStat,text_know;
    public JFXTextField edit_ModUser,edit_ModifyName,edit_ModifySurname,edit_ModifyUser,edit_player,edit_ModifyPasswordOld,edit_ModifyPasswordNew,edit_ModifyPasswordNew2,edit_userAD,edit_surnameAD,edit_emailAD,edit_usernameAD;
    public ScrollPane scroll_Game;
    //PARTITE
    public Label pane_game,pane_game1,pane_game2,pane_game3,pane_game4,pane_game5,pane_game6,pane_game7,pane_game8,pane_game9;
    public Text text_NameGame,text_NameGame1,text_NameGame2,text_NameGame3,text_NameGame4,text_NameGame5,text_NameGame6,text_NameGame7,text_NameGame8,text_NameGame9;
    public Text text_GameWating,text_GameWating1,text_GameWating2,text_GameWating3,text_GameWating4,text_GameWating5,text_GameWating6,text_GameWating7,text_GameWating8,text_GameWating9;
    public JFXButton btn_Observer,btn_Observer1,btn_Observer2,btn_Observer3,btn_Observer4,btn_Observer5,btn_Observer6,btn_Observer7,btn_Observer8,btn_Observer9;
    public JFXButton btn_JGameJoin,btn_JGameJoin1,btn_JGameJoin2,btn_JGameJoin3,btn_JGameJoin4,btn_JGameJoin5,btn_JGameJoin6,btn_JGameJoin7,btn_JGameJoin8,btn_JGameJoin9;
    public Text text_GameEnd,text_GameEnd1,text_GameEnd2,text_GameEnd3,text_GameEnd4,text_GameEnd5,text_GameEnd6,text_GameEnd7,text_GameEnd8,text_GameEnd9;
    public ScrollPane scroll_Phrases;
    public Text text_ID_lobbyCreate;
    //FRASI
    public Label pane_Phrase,pane_Phrase1,pane_Phrase2,pane_Phrase3,pane_Phrase4,pane_Phrase5,pane_Phrase6,pane_Phrase7,pane_Phrase8,pane_Phrase9;
    public Text text_PhraseTitle,text_PhraseTitle1,text_PhraseTitle2,text_PhraseTitle3,text_PhraseTitle4,text_PhraseTitle5,text_PhraseTitle6,text_PhraseTitle7,text_PhraseTitle8,text_PhraseTitle9;
    public Text text_PhraseHint,text_PhraseHint1,text_PhraseHint2,text_PhraseHint3,text_PhraseHint4,text_PhraseHint5,text_PhraseHint6,text_PhraseHint7,text_PhraseHint8,text_PhraseHint9;
    public Text text_PhraseCancel,text_PhraseCancel1,text_PhraseCancel2,text_PhraseCancel3,text_PhraseCancel4,text_PhraseCancel5,text_PhraseCancel6,text_PhraseCancel7,text_PhraseCancel8,text_PhraseCancel9;
    public Text text_ID_Phrases,text_ID_Phrases1,text_ID_Phrases2,text_ID_Phrases3,text_ID_Phrases4,text_ID_Phrases5,text_ID_Phrases6,text_ID_Phrases7,text_ID_Phrases8,text_ID_Phrases9;
    public JFXButton btn_SavePhrases,btn_cancelPhrases,btn_Phrases,btn_CreateGames;
    public AnchorPane pane_createPhrases,pane_editPhrases;
    public JFXTextField edit_createHintPhrases,edit_hintPhrases, edit_insertPathCSV;
    public JFXTextArea edit_createTextPhrases,edit_textPhrases;
    public JFXButton btn_GamesBack,btn_CreatePhrase;
    public Text text_ID_ModifyPhrases;
    public AnchorPane pane_CSV;
    public JFXTextField edit_CSV_PATCH;
    public JFXButton btn_LeaveORaccess;
    public Text text_waitPlayer_Access;
    //Variabili
    private double xOffSet=0;
    private double yOffSet=0;
    private boolean moreStat=false;
    private boolean accountVisible=false;
    private boolean tutorialVisible=false;
    private String[] sapev = {"ci sono oltre 34 frasi da indovinare","la probabilità di indovinare al primo colpo è del 6%","ci sono 56 giocatori registrati"};
    public static String IDUser;


    //Server
    ServerInterface server;
    CSVReader csvreader;
    String emailUser = Login.getEmailLogin();
    //Array button
    public ArrayList<Button> jobserver = new ArrayList<>();
    public ArrayList<Button> jgame = new ArrayList<>();
    //Array frasi
    public int nphrases[]=new int[10];
    public ArrayList<Label> panephrase = new ArrayList<Label>();
    public ArrayList<Text> titlephrase = new ArrayList<Text>();
    public ArrayList<Text> hintphrase = new ArrayList<Text>();
    public ArrayList<Text> idphrases = new ArrayList<Text>();
    //Array partite
    public int ngames[]=new int[10];
    public ArrayList<Label> panegames = new ArrayList<Label>();
    public ArrayList<Text> namegames = new ArrayList<Text>();
    ArrayList<String> phrases = new ArrayList<String>();

    private static final long serialVersionUID = 4L;
    public Partite() throws InterruptedException, SQLException, IOException {
        super();
    }

    /**
     * First method that starts, checks if the authenticated user is admin
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        addpanephrase();
        addtitlephrase();
        addhintphrase();
        addidphrases();
        addpanegames();
        addnamegames();

        addjobserver();
        addjgame();

        makeStageDragable();
        server = ClientRDF.getServer();
        boolean ok = false;
        //emailUser = Login.getEmailLogin();
        try {
            ok = server.isAdminS(emailUser);
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }
        if (ok) {
            btn_Phrases.setVisible(true);
            btn_CreateGames.setVisible(false);
        }else{
            btn_addAdmin.setVisible(false);
        }
        gamesinplay();

        try {
            IDUser = server.getIDUsersS(emailUser);
            System.out.println("----iduser "+IDUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //ADD

    /**
     * Add to ArrayList pane_Phrase
     */
    private void addhintphrase() {
        panephrase.add(pane_Phrase);
        panephrase.add(pane_Phrase1);
        panephrase.add(pane_Phrase2);
        panephrase.add(pane_Phrase3);
        panephrase.add(pane_Phrase4);
        panephrase.add(pane_Phrase5);
        panephrase.add(pane_Phrase6);
        panephrase.add(pane_Phrase7);
        panephrase.add(pane_Phrase8);
        panephrase.add(pane_Phrase9);
    }
    /**
     * Add to ArrayList text_PhraseTitle
     */
    private void addtitlephrase() {
        titlephrase.add(text_PhraseTitle);
        titlephrase.add(text_PhraseTitle1);
        titlephrase.add(text_PhraseTitle2);
        titlephrase.add(text_PhraseTitle3);
        titlephrase.add(text_PhraseTitle4);
        titlephrase.add(text_PhraseTitle5);
        titlephrase.add(text_PhraseTitle6);
        titlephrase.add(text_PhraseTitle7);
        titlephrase.add(text_PhraseTitle8);
        titlephrase.add(text_PhraseTitle9);
    }
    /**
     * Add to ArrayList text_PhraseHint
     */
    private void addpanephrase() {
        hintphrase.add(text_PhraseHint);
        hintphrase.add(text_PhraseHint1);
        hintphrase.add(text_PhraseHint2);
        hintphrase.add(text_PhraseHint3);
        hintphrase.add(text_PhraseHint4);
        hintphrase.add(text_PhraseHint5);
        hintphrase.add(text_PhraseHint6);
        hintphrase.add(text_PhraseHint7);
        hintphrase.add(text_PhraseHint8);
        hintphrase.add(text_PhraseHint9);
    }
    /**
     * Add to ArrayList text_ID_Phrases
     */
    private void addidphrases() {
        idphrases.add(text_ID_Phrases);
        idphrases.add(text_ID_Phrases1);
        idphrases.add(text_ID_Phrases2);
        idphrases.add(text_ID_Phrases3);
        idphrases.add(text_ID_Phrases4);
        idphrases.add(text_ID_Phrases5);
        idphrases.add(text_ID_Phrases6);
        idphrases.add(text_ID_Phrases7);
        idphrases.add(text_ID_Phrases8);
        idphrases.add(text_ID_Phrases9);
    }
    /**
     * Add to ArrayList text_NameGame
     */
    private void addnamegames() {
        namegames.add(text_NameGame);
        namegames.add(text_NameGame1);
        namegames.add(text_NameGame2);
        namegames.add(text_NameGame3);
        namegames.add(text_NameGame4);
        namegames.add(text_NameGame5);
        namegames.add(text_NameGame6);
        namegames.add(text_NameGame7);
        namegames.add(text_NameGame8);
        namegames.add(text_NameGame9);
    }
    /**
     * Add to ArrayList pane_game
     */
    private void addpanegames() {
        panegames.add(pane_game);
        panegames.add(pane_game1);
        panegames.add(pane_game2);
        panegames.add(pane_game3);
        panegames.add(pane_game4);
        panegames.add(pane_game5);
        panegames.add(pane_game6);
        panegames.add(pane_game7);
        panegames.add(pane_game8);
        panegames.add(pane_game9);
    }

    /**
     * Add to ArrayList button join
     */
    private void addjgame() {
        jgame.add(btn_JGameJoin);
        jgame.add(btn_JGameJoin1);
        jgame.add(btn_JGameJoin2);
        jgame.add(btn_JGameJoin3);
        jgame.add(btn_JGameJoin4);
        jgame.add(btn_JGameJoin5);
        jgame.add(btn_JGameJoin6);
        jgame.add(btn_JGameJoin7);
        jgame.add(btn_JGameJoin8);
        jgame.add(btn_JGameJoin9);
    }
    /**
     * Add to ArrayList button observer
     */
    private void addjobserver() {
        jobserver.add(btn_Observer);
        jobserver.add(btn_Observer1);
        jobserver.add(btn_Observer2);
        jobserver.add(btn_Observer3);
        jobserver.add(btn_Observer4);
        jobserver.add(btn_Observer5);
        jobserver.add(btn_Observer6);
        jobserver.add(btn_Observer7);
        jobserver.add(btn_Observer8);
        jobserver.add(btn_Observer9);
    }
    /**
     * Allows to move the window
     */
    private void makeStageDragable() {
        pane_top.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        pane_top.setOnMouseDragged((event) -> {
            GUI_ClientLogin.stage.setX(event.getScreenX() - xOffSet);
            GUI_ClientLogin.stage.setY(event.getScreenY() - yOffSet);
            GUI_ClientLogin.stage.setOpacity(0.8f);
        });
        pane_top.setOnDragDone((event) -> {
            GUI_ClientLogin.stage.setOpacity(1.0f);
        });
        pane_top.setOnMouseReleased((event) -> {
            GUI_ClientLogin.stage.setOpacity(1.0f);
        });
    }
    /**
     * Icon window
     */
    public void minus(MouseEvent mouseEvent) {
        GUI_ClientLogin.stage.setIconified(true);
    }
    /**
     * Close windows
     */
    public void close(MouseEvent mouseEvent) {
        System.exit(0);
    }
    /**
     * Show Tutorial
     */
    public void tutorial(MouseEvent mouseEvent) {
        if(!tutorialVisible){
            pane_tutorial.setVisible(true);
            tutorialVisible=true;
        }else{
            pane_tutorial.setVisible(false);
            tutorialVisible=false;

        }
    }
    
    //Statistiche & Ricerca

    /**
     * Show more statistics
     */
    public void btn_allStat() {
        TranslateTransition slide=new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane_stat);
        if(!moreStat) {
            slide.setToY(-478);
            slide.play();
            moreStat=true;
        }else{
            slide.setToY(0);
            slide.play();
            moreStat=false;
        }
    }

    /**
     * To search a Player
     */
    public void btn_search() {
        pane_noPlayerSearch.setVisible(true);
    }

    /**
     * Close pane error of search player name
     */
    public void btn_closeNoPlayerSearch() {
        pane_noPlayerSearch.setVisible(false);
    }

    //Account
    /**
     * Show account pane
     */
    public void account() {
        if(!accountVisible) {
            fonta_UserORStat.setGlyphName("BAR_CHART");
            text_CurrentUserORStat.setText("Statistiche");
            pane_stat.setStyle("-fx-background-color: #ffe29a;");
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(pane_stat);
            slide.setToY(-1006);
            slide.play();
            slide.setOnFinished(event -> {pane_stat.setStyle("-fx-background-color: #ffc373;");});
            accountVisible=true;
        }else{
            moreStat=false;
            fonta_UserORStat.setGlyphName("USER");
            text_CurrentUserORStat.setText("Nome utente");
            pane_stat.setStyle("-fx-background-color: #ffe29a;");
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(pane_stat);
            slide.setToY(0);
            slide.play();
            slide.setOnFinished(event -> {pane_stat.setStyle("-fx-background-color: #FFE0B2;");});
            accountVisible=false;
        }
    }

    //Modifica Utente
    /**
     * Add admin user
     */
    public void btn_addAdmin() {
        pane_addAdmin.setVisible(true);
    }
    /**
     * Modify password of current user
     */
    public void text_ModifyPassword() {
        pane_modifyPassword.setVisible(true);
    }
    /**
     * Modify user data like Name, Surname, Nickname
     */
    public void text_modifyUserData() {
        pane_modifyData.setVisible(true);
    }
    /**
     * Exit from modify pane
     */
    public void btn_cancelModify() {
        pane_addAdmin.setVisible(false);
        pane_modifyPassword.setVisible(false);
        pane_modifyData.setVisible(false);
    }
    /**
     * Save new admin user
     */
    public void btn_saveAD() {
        //DA FARE
    }

    /**
     * Save new user data
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void btn_saveModifyData() throws RemoteException, SQLException {
        pane_error.setVisible(true);
        pane_modifyData.setDisable(true);

        String newName =  edit_ModifyName.getText();
        System.out.println(newName);
        String newSurname = edit_ModifySurname.getText();
        System.out.println(newSurname);
        String newNick = edit_ModUser.getText();
        System.out.println(newNick);

        System.out.println(edit_ModUser.getText());

        emailUser = Login.getEmailLogin();

        System.out.println(emailUser);

        server = ClientRDF.getServer();
        String okOrNot = server.setUserS(emailUser, newName, newSurname, newNick);

        if (okOrNot.equals("isOk")){
            text_error.setText("Modificato");
            text_error1.setText("Dati utente aggiornati");
            pane_modifyData.setVisible(false);
        }
        else {
            text_error.setText("USERNAME gia esistente!");
            text_error1.setText("Prova con una combinazione diversa");
        }
    }

    /**
     * Save new password of user
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void btn_saveModifyPassword() throws Exception {
        pane_error.setVisible(true);
        pane_modifyPassword.setDisable(true);

        String vecchiaPw = edit_ModifyPasswordOld.getText();
        String nuovaPw = edit_ModifyPasswordNew.getText();
        String nuovaPw2 = edit_ModifyPasswordNew2.getText();

        if(nuovaPw.equals(nuovaPw2)) {
            if (isValidPasswordFormat(nuovaPw)) {
                emailUser = Login.getEmailLogin();
                server = ClientRDF.getServer();
                String okOrNot = server.setPasswordS(emailUser, vecchiaPw, nuovaPw);
                System.out.println(okOrNot);

                if (okOrNot.equals("Password modificata con successo")) {
                    text_error.setText("Modificato");
                    text_error1.setText("Password modificata");
                    new EmailSender().editPassword(emailUser);
                    pane_modifyPassword.setVisible(false);
                } else {
                    text_error.setText("ERRORE!");
                    text_error1.setText("Password vecchia errata!");
                }
            } else {
                text_error.setText("ERRORE!");
                text_error1.setText("La nuova password non rispetta i requisiti di formato.");
            }
        }
        else {
            text_error.setText("ERRORE!");
            text_error1.setText("Le due nuove password inserite non corrispondono.");
        }
    }

    public boolean isValidPasswordFormat(String password) {
        boolean result = true;
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (password.matches(pattern)) {
            System.out.println("la password è accettata" + password.matches(pattern) + "perché rispetta la regex");
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Close error pane
     */
    public void btn_closeError() {
        pane_error.setVisible(false);
        pane_modifyPassword.setDisable(false);
        pane_modifyData.setDisable(false);
    }

    //Logout
    /**
     * Logout
     *
     * @throws IOException
     * @throws SQLException
     */
    public void btn_logout() throws IOException, SQLException {
        Parent logout = FXMLLoader.load(getClass().getResource("/rdf_login.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(logout);
        emailUser = Login.getEmailLogin();
        server = ClientRDF.getServer();
        server.logoutUsersS(emailUser);
    }

    //Refresh

    /**
     * Refresh games table
     *
     * @throws RemoteException
     */
    public void btn_refresh() throws RemoteException, SQLException, InterruptedException {
        server = ClientRDF.getServer();
        ArrayList<String> updatedListGames = server.synchronizeGames();
        System.out.println("aggiorno la list games con quella del server");
        for(int i=0; i<10; i++){
            if(ngames[i]!=1){
                ngames[i]=1;
                namegames.get(i).setText("Partita: "+i+"|"+updatedListGames.get(i));
                break;
            }
        }
        for (int i=0; i<10; i++) {
            String idgames = namegames.get(i).getText().substring(11);
            boolean subs3 = false;
            subs3 = server.checkSubsS(idgames);
            if (subs3){
                try {
                    jobserver.get(i).setDisable(false);
                    jgame.get(i).setDisable(true);
                }catch (Exception e){}
            }
        }
        gamesinplay();
    }

    //Partite

    /**
     * Create games
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_CreateGames() throws IOException, SQLException, InterruptedException {
        //listGamesClient.add(id);
        //TODO IMPORTANTE NON CANCELLARE
        server = ClientRDF.getServer();

        //creo il gioco
        String idgames = server.newGamesS();
        System.out.println(idgames);

        emailUser = Login.getEmailLogin();

        server.insertSubsS(emailUser, idgames);

        server.createGameWaitingS();
        server.GameStartS();
        //NEW
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        for(int i=0; i<10; i++){
            if(ngames[i]!=1){
                ngames[i]=1;
                namegames.get(i).setText("Partita: "+i+"|"+idgames);
                text_ID_lobbyCreate.setText("Partita: "+i+"|"+idgames);
                //idgames= String.valueOf(i);
                break;
            }
        }
        gamesinplay();

        boolean subs3 = false;
        boolean timer = server.GameStartS(); //da questo boolean so quando il tempo è scaduto se true scaduto
        while (!timer) { //se cicla vuol dire che il tempo non è ancora scaduto
            subs3 = server.checkSubsS(idgames);
  //          if (subs3) { //se subs3 = true vuol dire che non ci sono ancora 3 persone
                timer = true; //se ci sono 3 concorrenti e il tempo non è scaduto, lo faccio scadere (in realtà lo considero inutile)
          /*      new GameManager(IDUser);
                Thread.sleep(9000);
                Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
                parent.getChildren().removeAll();
                parent.getChildren().setAll(gioco);

           */

               //new Gioco(IDUser);
             //   new Gioco(idgames, idManche, idPlayer);
            //   String idManche = server.newMancheS(idgames);
            //   server.newPlayerS(idgames, idManche);
 //               break; //se entra nell'if la partita è iniziata e chiude il while.
            }
 //       }
        /*Se tempo scade
        pane_lobby.setVisible(false);
        scroll_Game.setDisable(false);
        btn_CreateGames.setDisable(false);
        //ELIMINA PARTITA
        deletegame();*/
    }

    /**
     * Games in play
     */
    private void gamesinplay() {
        int lasti=0;
        for (int i=0; i<10; i++) {
            if (ngames[i] == 1) {
                if(i==0) {
                    panegames.get(i).setLayoutY(0);
                }else {
                    panegames.get(i).setLayoutY(panegames.get(lasti).getLayoutY() + 68);
                }
                lasti=i;
            }
        }
    }

    /**
     * Delete game
     */
    private void deletegame() {
        int pos = Integer.parseInt(text_ID_lobbyCreate.getText().substring(8,9));
        panegames.get(pos).setLayoutY(-68);
        ngames[pos]=0;
        gamesinplay();
    }
/*

    public void btn_LeaveORaccess() throws SQLException, IOException, InterruptedException {
        if(text_waitPlayer_Access.getText().equals("PARTITA PRONTA ACCEDI!")){

            //ACCEDI AL GAME
            String idgames=text_ID_lobbyCreate.getText().substring(11); NO MA QUESTO NON SERVE PERCHé LO FACCIO IN CLIENTrDF
            new Gioco(IDUser);
            Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
            parent.getChildren().removeAll();
            parent.getChildren().setAll(gioco);
            btn_Observer.setDisable(false);
        }else{
            //Leave lobby
            int finale=text_ID_lobbyCreate.getText().length()-7;
            String idgames = text_ID_lobbyCreate.getText().substring(10,finale);
            server.endedGamesS(idgames);
            pane_lobby.setVisible(false);
            scroll_Game.setDisable(false);
            btn_CreateGames.setDisable(false);
            deletegame();
        }
    }
    */

    //Frasi
    //TODO aggiungere DB
    /**
     * Go on the pane Phrases
     */
    public void btn_Phrases() throws SQLException, RemoteException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane_gameAndPhrases);
        slide.setToX(-932);
        slide.play();
        fonta_UserORStat.setDisable(true);
        fonta_Tutorial.setDisable(true);
        //TODO qui arriva l'arraylist delle frasi su db con:
        // -Hint in posizione 'i'
        // -Phrase in posizione 'i+1'
        phrases = server.refreshPhrasesS(); //restituisce tutte le frasi presenti sul db
        guarda();

    }

    /**
     * Update list of Phrases
     */
    private void guarda(){
        int lasti=0;
        for (int i=0; i<10; i++) {
            if (nphrases[i] == 1) {
                if(i==0) {
                    panephrase.get(i).setLayoutY(0);
                }else {
                    panephrase.get(i).setLayoutY(panephrase.get(lasti).getLayoutY() + 68);
                }
                lasti=i;
            }
        }
    }

    /**
     * Show Filed for CSV Patch
     */
    public void btn_csv() {
        pane_CSV.setVisible(true);
    }

    /**
     * Get CSV PATH
     */
    public void font_checkCSV() throws Exception {
        String path = edit_CSV_PATCH.getText(); //Prendo il path
        pane_CSV.setVisible(false);
        server.CSVReaderS(path);
        //csvreader.CSVReader(path);
    }

    /**
     * Refresh phrases
     */
    public void btn_refreshF() throws SQLException, RemoteException {
        phrases = server.refreshPhrasesS();
        updateallCSV();
    }

    /**
     * Delete all phrases and replace with the csv phrases
     */
    private void updateallCSV() {
        //Delete all phrases
        for(int i=0; i<9; i++) {
            nphrases[i] = 0;
        }
        int l=0;
        for(int i=0; i<(phrases.size())/2; i++){
            if(nphrases[i]!=1){
                nphrases[i]=1;
                idphrases.get(i).setText("ID "+i);
                titlephrase.get(i).setText(phrases.get(l)); //titolo
                hintphrase.get(i).setText(phrases.get(l+1)); //hint
                l=l+2;
                break;
            }
        }
        guarda();
    }


    /**
     * Create Phrase
     */
    public void btn_CreatePhrase() throws Exception {
        pane_createPhrases.setVisible(true);
        edit_createHintPhrases.setText("");
        edit_createTextPhrases.setText("");
        btn_GamesBack.setDisable(true);
        btn_CreatePhrase.setDisable(true);
        scroll_Phrases.setDisable(true);
    }

    /**
     * Save Phrases
     */
    public void btn_SaveCreatePhrases() {
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        pane_createPhrases.setVisible(false);
        for(int i=0; i<10; i++){
            if(nphrases[i]!=1){
                nphrases[i]=1;
                idphrases.get(i).setText("ID "+i);
                titlephrase.get(i).setText(edit_createTextPhrases.getText());
                hintphrase.get(i).setText(edit_createHintPhrases.getText());
                break;
            }
        }
        guarda();
    }

    /**
     * Go back to games pane
     */
    public void btn_GamesBack() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane_gameAndPhrases);
        slide.setToX(0);
        fonta_UserORStat.setDisable(false);
        fonta_Tutorial.setDisable(false);
        slide.play();
    }

    /**
     * Save modify Phrases
     */
    public void btn_SavePhrases() {
        int pos = Integer.parseInt(text_ID_ModifyPhrases.getText().substring(3,4));
        pane_editPhrases.setVisible(false);
        titlephrase.get(pos).setText(edit_textPhrases.getText());
        hintphrase.get(pos).setText(edit_hintPhrases.getText());
    }

    /**
     * Exit from edit o create Phreses
     */
    public void btn_cancelPhrases() {
        pane_createPhrases.setVisible(false);
        pane_editPhrases.setVisible(false);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        edit_hintPhrases.setText("");
        edit_textPhrases.setText("");
    }

    //PARTITE

    /**
     * Observes game 1
     */
    public void btn_Observer() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(0).getText();
        idgames =  idgames.substring(11);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);
    }

    /**
     * Join Game 1
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(0).getText();
        idgames =  idgames.substring(11);

        text_ID_lobbyCreate.setText(namegames.get(0).getText());

        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);

        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);
          //  text_waitPlayer_Access.setText("PARTITA PRONTA ACCEDI!");
          //  btn_LeaveORaccess.setText("ACCEDI");
        }
    }

    public void btn_refreshPlayer(ActionEvent actionEvent) throws InterruptedException, RemoteException, SQLException {
        //REFRESH
        String idgames = namegames.get(0).getText();
        idgames =  idgames.substring(11);
        if(server.checkSubsS(idgames)){ }
    }

    /**
     * Observer Game 2
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer1() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(1).getText();
        idgames =  idgames.substring(11);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 2
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin1() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(1).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);
        }
        //}
    }

    /**
     * Observer Game 3
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer2() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(2).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 3
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin2() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(2).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 4
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer3() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(3).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 4
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin3() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(3).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 5
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer4() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(4).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 5
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin4() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(4).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 6
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer5() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(5).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 6
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin5() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(5).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 7
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer6() throws IOException, SQLException, InterruptedException{

        String idgames = namegames.get(6).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 7
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin6() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(6).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 8
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer7() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(7).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 8
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin7() throws IOException, SQLException, InterruptedException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(7).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 9
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer8() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(8).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 9
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin8() throws IOException, InterruptedException, SQLException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(8).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    /**
     * Observer Game 10
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_Observer9() throws IOException, SQLException, InterruptedException {

        String idgames = namegames.get(9).getText();
        idgames =  idgames.substring(10);
        server.insertSubsObserverS(emailUser, idgames);
        boolean subs = server.checkSubsS(idgames);

        Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_gioco.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(gioco);

    }

    /**
     * Join Game 10
     *
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */
    public void btn_JGameJoin9() throws InterruptedException, IOException, SQLException {
        pane_lobby.setVisible(true);
        scroll_Game.setDisable(true);
        btn_CreateGames.setDisable(true);
        int r= (int) (Math.random()*3);
        text_know.setText(sapev[r]);

        server = ClientRDF.getServer();
        String idgames = namegames.get(9).getText();
        idgames =  idgames.substring(11);
        emailUser = Login.getEmailLogin();
        server.insertSubsS(emailUser, idgames);
        boolean subs3 = false;
        subs3 = server.checkSubsS(idgames);
        System.out.println("subs: "+subs3);
        if(subs3){ //se subs3 = false vuol dire che non ci sono ancora 3 persone
            server.buildMatch(idgames, 1);

        }
    }

    //FRASI
    /**
     * Delete Phrases 1
     */
    public void edit_Delete() {
        panephrase.get(0).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(0).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 1
     */
    public void edit_Modify() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(0).getText());
        edit_textPhrases.setText(titlephrase.get(0).getText());
        edit_hintPhrases.setText(hintphrase.get(0).getText());
    }
    /**
     * Delete Phrases 1
     */
    public void edit_Delete1() {
        panephrase.get(1).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(1).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 1
     */
    public void edit_Modify1() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(1).getText());
        edit_textPhrases.setText(titlephrase.get(1).getText());
        edit_hintPhrases.setText(hintphrase.get(1).getText());
    }
    /**
     * Delete Phrases 2
     */
    public void edit_Delete2() {
        panephrase.get(2).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(2).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 2
     */
    public void edit_Modify2() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(2).getText());
        edit_textPhrases.setText(titlephrase.get(2).getText());
        edit_hintPhrases.setText(hintphrase.get(2).getText());
    }
    /**
     * Delete Phrases 3
     */
    public void edit_Delete3() {
        panephrase.get(3).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(3).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 3
     */
    public void edit_Modify3() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(3).getText());
        edit_textPhrases.setText(titlephrase.get(3).getText());
        edit_hintPhrases.setText(hintphrase.get(3).getText());
    }
    /**
     * Delete Phrases 4
     */
    public void edit_Delete4() {
        panephrase.get(4).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(4).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 4
     */
    public void edit_Modify4() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(4).getText());
        edit_textPhrases.setText(titlephrase.get(4).getText());
        edit_hintPhrases.setText(hintphrase.get(4).getText());
    }
    /**
     * Delete Phrases 5
     */
    public void edit_Delete5() {
        panephrase.get(5).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(5).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 5
     */
    public void edit_Modify5() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(5).getText());
        edit_textPhrases.setText(titlephrase.get(5).getText());
        edit_hintPhrases.setText(hintphrase.get(5).getText());
    }
    /**
     * Delete Phrases 6
     */
    public void edit_Delete6() {
        panephrase.get(6).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(6).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 6
     */
    public void edit_Modify6() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(6).getText());
        edit_textPhrases.setText(titlephrase.get(6).getText());
        edit_hintPhrases.setText(hintphrase.get(6).getText());
    }
    /**
     * Delete Phrases 7
     */
    public void edit_Delete7() {
        panephrase.get(7).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(7).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 7
     */
    public void edit_Modify7() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(7).getText());
        edit_textPhrases.setText(titlephrase.get(7).getText());
        edit_hintPhrases.setText(hintphrase.get(7).getText());
    }
    /**
     * Delete Phrases 8
     */
    public void edit_Delete8() {
        panephrase.get(8).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(8).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 8
     */
    public void edit_Modify8() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(8).getText());
        edit_textPhrases.setText(titlephrase.get(8).getText());
        edit_hintPhrases.setText(hintphrase.get(8).getText());
    }
    /**
     * Delete Phrases 9
     */
    public void edit_Delete9() {
        panephrase.get(9).setLayoutY(-68);
        int pos = Integer.parseInt(idphrases.get(9).getText().substring(3,4));
        nphrases[pos]=0;
        guarda();
    }
    /**
     * Modify Phrases 9
     */
    public void edit_Modify9() {
        pane_editPhrases.setVisible(true);
        btn_GamesBack.setDisable(false);
        btn_CreatePhrase.setDisable(false);
        scroll_Phrases.setDisable(false);
        text_ID_ModifyPhrases.setText(idphrases.get(9).getText());
        edit_textPhrases.setText(titlephrase.get(9).getText());
        edit_hintPhrases.setText(hintphrase.get(9).getText());
    }



}