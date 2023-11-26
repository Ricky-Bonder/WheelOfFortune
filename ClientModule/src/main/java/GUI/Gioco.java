package GUI;

import RdF.Client.ClientRDF;
import RdF.Server.ServerInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Gioco implements Initializable {
    public AnchorPane pane_top,pane_Player,pane_Spectator, parent;
    public Pane pane_loseTurn,pane_GameEnd,pane_Leave,pane_win,pane_OnlySolution;
    public JFXButton btn_jolly,btn_consonant,btn_vocal,btn_spin,btn_solution,btn_ConsORVocalConfermation;
    public Text text_ConsonantORSolution, text_resultSpin,text_TotalPoint, text_Totaljolly, text_ManchesPoint;
    public JFXTextArea edit_Solution;
    public JFXTextField edit_ConsORVocal;
    public JFXButton btn_SolutionConfermation;
    public Text t0,t1,t2,t3,t4,t5,t6,t7,t8,t9 = new Text("_");
    public Text t10,t11,t12,t13,t14,t15,t16,t17,t18,t19= new Text("_");
    public Text t20,t21,t22,t23,t24,t25,t26,t27,t28,t29= new Text("_");
    public Text t30,t31,t32,t33,t34,t35,t36,t37,t38,t39= new Text("_");
    public Text t40,t41,t42,t43,t44,t45,t46,t47,t48,t49= new Text("_");
    public Text t50,t51,t52,t53,t54,t55,t56,t57,t58,t59= new Text("_");
    public Pane pane_H1,pane_H2,pane_H3,pane_H4;
    public Label label_H1,label_H2,label_H3,label_H4;
    public Text text_number_H4,text_number_H3,text_number_H2,text_number_H1;
    public FontAwesomeIcon btn_closeLoseTurn,btn_closeOnlySolution,btn_closeWin;
    public Text text_HowWinManches,text_HowWinGame,text_EndPointWin;
    public Text text_name1,text_name2,text_name3;
    public Text text_player1GamePoint,text_player2GamePoint,text_player3GamePoint;
    public Text text_player1ManchesPoint,text_player2ManchesPoint,text_player3ManchesPoint;
    public Text text_jolly1,text_jolly2,text_jolly3;
    public Text text_ManchesNumber,text_PlayerTurn,text_spinO;
    public Text text_time;
    public Text text_hint = new Text();
    public Text text_NH1,text_NH2,text_NH3,text_NH4;
    public Text text_MH1,text_MH2,text_MH3,text_MH4;
    //Ruota
    public String[] ruota = {"600","400","500","300","600","1000","PERDE","300","400","700","500","300","400","PERDE","300","500","400","600","500","400","300","JOLLY","PASSA","300"};
    //Array Frase Misteriosa
    public ArrayList<Text> phrase = new ArrayList<Text>();
    //Variabili
    Stage stage = GUI_ClientLogin.stage;
    private double xOffSet = 0;
    private double yOffSet = 0;
    public int consORvocal=0; // =0 Nulla, =1 Cons, =2, Vocal
    private String history[]=new String[4];
    private int h=0;
    private int punti;
    private String mossa;
    static ServerInterface server;
    static String emailUser = Login.getEmailLogin();
    String nickname;
    static String idManche;
    static String idPlayer;
    static String idUsers;
   public static String nickCurrentPlayer;
    char[] charPhraseArr;
    public static String[] points={"0","punti"};
    public static String[] tPoints= {"0","punti"};
    public static ArrayList<String> hintAndPhrase = new ArrayList<>();
    int r;
    static String chosenMove = null;
    public static int mossaPrecPoints =0;
    public static int totalPoints=0;
    //TIMER
    private final Integer STARTTIME = 10;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    Scene scene;
    public static ArrayList<Character> lettereUscite = new ArrayList<>();
    //STORIA
    public static ArrayList<String> laStoria0 = new ArrayList<>();
    public static ArrayList<String> laStoria1 = new ArrayList<>();
    public static ArrayList<Text> nh = new ArrayList<>();
    public static ArrayList<Text> mh = new ArrayList<>();

    public static String possJolly="Jolly: 0";
    public static int nManche = 0;



    public Gioco() {
        super();
    }


    public Gioco(ArrayList<String> hintAndPhrase, String actualPlayer) throws IOException {
        System.out.println("hint ricevuto "+hintAndPhrase.get(0));
        System.out.println("phrase ricevuta "+hintAndPhrase.get(1));
        idUsers = Partite.IDUser;
        server = ClientRDF.getServer();
        Platform.runLater(() -> {
            try {
                this.nickCurrentPlayer = actualPlayer;
                setNickCurrentPlayer(actualPlayer);
                setWhichConstructor(0);
                setHintAndPhrase(hintAndPhrase);
                System.out.println("IDUSER: "+idUsers);
                idPlayer = server.getMyIDPlayerS(idUsers);
                System.out.println("getmyidplayers "+idPlayer);
                idManche = server.getMyIDMancheS(idUsers);
                System.out.println("getmyidmanche "+idManche);
                System.out.println("lancio gui gioco");
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/rdf_gioco.fxml"));
                //loader.setController(this);
                AnchorPane root = (AnchorPane)loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        Platform.runLater(() -> {
            try {
                lettereUscite.clear();
                String letters = "";
                setNewLetters(letters);
                this.nickCurrentPlayer = actualPlayer;
                setNickCurrentPlayer(actualPlayer);
                setWhichConstructor(0);
                System.out.println("ghetto nManche "+getnManche());
                int n = getnManche();
                setnManche(++n);
                System.out.println("ho settato nManche "+getnManche());
                setHintAndPhrase(hintAndPhrase);
                System.out.println("IDUSER: "+idUsers);
                idPlayer = server.getMyIDPlayerS(idUsers);
                System.out.println("getmyidplayers "+idPlayer);
                idManche = server.getMyIDMancheS(idUsers);
                System.out.println("getmyidmanche "+idManche);
                System.out.println("lancio gui gioco");
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/rdf_gioco.fxml"));
                //loader.setController(this);
                AnchorPane root = (AnchorPane)loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public Gioco(String playerWinner) {
        idUsers = Partite.IDUser;
        server = ClientRDF.getServer();

        Platform.runLater(() -> {
            try {
                setWhichConstructor(0);
                if (playerWinner == nickname) {
                    text_HowWinManches.setText("Complimenti, "+playerWinner+"! Hai vinto la manche.\n La nuova manche inizierà tra 3 secondi...");
                    pane_win.setVisible(true);
                    createNewManche();
                }
                else {
                    text_HowWinManches.setText("Che peccato!\n"+playerWinner+" ha indovinato la frase e vinto la manche.\n La nuova manche inizierà tra 3 secondi...");
                    System.out.println("ha vinto "+playerWinner);
                    pane_win.setVisible(true);
                }
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/rdf_gioco.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException | InterruptedException | SQLException e) {
                e.printStackTrace();
            }

        });
    }

    private boolean createNewManche() throws IOException, SQLException, InterruptedException {
        return server.getMyGame(idPlayer).createNewManche();
    }



    public Gioco(String updPhrase, String nicknameNewPlayer) throws RemoteException, SQLException { //chiamato ogni nuova mossa
        System.out.println("ho ricevuto updPhrase"+updPhrase+" etc e nuovo player "+nicknameNewPlayer);
        idUsers = Partite.IDUser;
        server = ClientRDF.getServer();

        Platform.runLater(() -> {
            try {
                nickCurrentPlayer = nicknameNewPlayer;
                setNickCurrentPlayer(nicknameNewPlayer);
                if (updPhrase != "") {
                    setWhichConstructor(1);
                    System.out.println("updFrase0 " + updPhrase + " lettere0 ");
                    setNewLetters(updPhrase);
                    lettereUscite = (ArrayList<Character>) updPhrase.chars().mapToObj((i) -> Character.valueOf((char) i)).collect(Collectors.toList());
                    System.out.println("setto lettereUscite di" + lettereUscite.get(0) + " a updPhrase.charat" + updPhrase);
                }
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/rdf_gioco.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }


    /**
     * First method that starts, checks if the authenticated user is observer or player
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initialize");
        makeStageDragable();
        addphrase();
        addmh();
        addnh();

        server = ClientRDF.getServer();
        boolean ok = false;
        emailUser = Login.getEmailLogin();
        try {
            ok = server.isObserverS(emailUser);
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }
        if (ok) {
            pane_Spectator.setVisible(true);
            pane_Player.setVisible(false);
        } else {
            pane_Player.setVisible(true);
            pane_Spectator.setVisible(false);
        }

        try {
            nickname = server.getNickFromEmailS(emailUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        text_ManchesNumber.setText("Manche "+getnManche()+"/5");
        disableGUI(true);
        text_PlayerTurn.setText("Turno di " + getNickCurrentPlayer());
        if (getNickCurrentPlayer().equals(nickname)) {
            disableGUI(false);
        }
        text_ManchesPoint.setText(getMossaPrecPoints()+" Punti");
        text_TotalPoint.setText(getTotalPoints()+" Punti");
        text_Totaljolly.setText(getPossJolly());
        if (getPossJolly().matches("Jolly: 0")) {
            btn_jolly.setDisable(true);
        }

        displayMysteriousPhrase();

        if (getWhichConstructor() == 1) {
            System.out.println("entro in getWhichConstructor() == 1");
            riceviUpdate(String.valueOf(lettereUscite));
        }

    }

    private void addnh() {
        nh.add(text_NH1);
        nh.add(text_NH2);
        nh.add(text_NH3);
        nh.add(text_NH4);
    }

    private void addmh() {
        mh.add(text_MH1);
        mh.add(text_MH2);
        mh.add(text_MH3);
        mh.add(text_MH4);
    }

    /**
     * Timer
     */
    public void start() throws IOException, SQLException {
        text_time.textProperty().bind(timeSeconds.asString());

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();

        timeline.setOnFinished(event1 -> {
            try {
                server.endMyTurn(lettereUscite);
                endMyTurn();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    public static int getWhichConstructor() {
        return whichConstructor;
    }

    public static void setWhichConstructor(int whichConstructor) {
        Gioco.whichConstructor = whichConstructor;
    }

    public static int whichConstructor = 0; // 0 == initializeGame/frase --- 1 == riceviUpdateFrase

    public static String getNewLetters() {
        return newLetters;
    }

    public static void setNewLetters(String newLetters) {
        Gioco.newLetters = newLetters;
    }

    public static String newLetters;


    public static String getNickCurrentPlayer() {
        return nickCurrentPlayer;
    }

    public static void setNickCurrentPlayer(String nickCurrentPlayer) {
        Gioco.nickCurrentPlayer = nickCurrentPlayer;
    }

    public int getMossaPrecPoints(){
        return mossaPrecPoints;
    }

    public void setMossaPrecPoints(int p){
        this.mossaPrecPoints =p;
    }


    public int getTotalPoints(){
        return totalPoints;
    }

    public void setTotalPoints(int tp){
        this.totalPoints=tp;
    }

    /**
     * Get hint and phrase
     *
     * @return
     */
    public ArrayList<String> getHintAndPhrase() {
        return hintAndPhrase;
    }


    public void setHintAndPhrase(ArrayList<String> hintAndPhrase) {
        this.hintAndPhrase = hintAndPhrase;
    }


    public static int getnManche() {
        return nManche;
    }

    public static void setnManche(int nManche) {
        Gioco.nManche = nManche;
    }

    /**
     * Add letter of grid to arrayList
     */
    private void addphrase() {
        phrase.add(t0);
        phrase.add(t1);
        phrase.add(t2);
        phrase.add(t3);
        phrase.add(t4);
        phrase.add(t5);
        phrase.add(t6);
        phrase.add(t7);
        phrase.add(t8);
        phrase.add(t9);
        phrase.add(t10);
        phrase.add(t11);
        phrase.add(t12);
        phrase.add(t13);
        phrase.add(t14);
        phrase.add(t15);
        phrase.add(t16);
        phrase.add(t17);
        phrase.add(t18);
        phrase.add(t19);
        phrase.add(t20);
        phrase.add(t21);
        phrase.add(t22);
        phrase.add(t23);
        phrase.add(t24);
        phrase.add(t25);
        phrase.add(t26);
        phrase.add(t27);
        phrase.add(t28);
        phrase.add(t29);
        phrase.add(t30);
        phrase.add(t31);
        phrase.add(t32);
        phrase.add(t33);
        phrase.add(t34);
        phrase.add(t35);
        phrase.add(t36);
        phrase.add(t37);
        phrase.add(t38);
        phrase.add(t39);
        phrase.add(t40);
        phrase.add(t41);
        phrase.add(t42);
        phrase.add(t43);
        phrase.add(t44);
        phrase.add(t45);
        phrase.add(t46);
        phrase.add(t47);
        phrase.add(t48);
        phrase.add(t49);
        phrase.add(t50);
        phrase.add(t51);
        phrase.add(t52);
        phrase.add(t53);
        phrase.add(t54);
        phrase.add(t55);
        phrase.add(t56);
        phrase.add(t57);
        phrase.add(t58);
        phrase.add(t59);
    }

    /**
     *
     */
    public void displayMysteriousPhrase(){
        try {
            Platform.runLater(() -> {
                addphrase();
           //     System.out.println("casella di phrase "+phrase.get(1)+" tostring"+phrase.get(1).toString());
            //    hintAndPhrase = manager.getHintAndPhrase();
                text_hint.setText(getHintAndPhrase().get(0));
                System.out.println("displayMyst: " + hintAndPhrase.get(0)+ " e frase "+hintAndPhrase.get(1));
                hintAndPhrase.get(1).toUpperCase();
                charPhraseArr = hintAndPhrase.get(1).toCharArray();
                for (int i = 0; i < charPhraseArr.length; i++) {
                    phrase.get(i).setText("_");
                    System.out.println("frase get i " + phrase.get(i).toString());
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String[] getPoints(){
        return points;
    }

    public void setPoint(String[]points){
        this.points=points;
    }

    public String[] getTPoints(){
        return tPoints;
    }

    public void setTPoints(String[]tPoints){
        this.tPoints=tPoints;
    }

    public String getPossJolly(){
        return possJolly;
    }

    public void setPossJolly(String j){
        this.possJolly=j;
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
        pane_top.setOnDragDone((event) -> GUI_ClientLogin.stage.setOpacity(1.0f));
        pane_top.setOnMouseReleased((event) -> GUI_ClientLogin.stage.setOpacity(1.0f));
    }

    /**
     * Disable GUI button
     *
     * @param check
     */
    public void disableGUI(boolean check) {
        btn_consonant.setDisable(check);
        btn_spin.setDisable(check);
        btn_solution.setDisable(check);
        btn_vocal.setDisable(check);
    }

    /**
     * Disable buttons based on move
     *
     * @param chosenMove
     */
    public void newMove(String chosenMove) throws RemoteException, SQLException {
        switch (chosenMove) {
            case "GIRA":
                btn_vocal.setDisable(true);
                btn_solution.setDisable(true);
                break;

            case "SOLUZIONE":
                btn_vocal.setDisable(true);
                btn_spin.setDisable(true);
                break;

            case "VOCALE":
                btn_spin.setDisable(true);
                btn_solution.setDisable(true);
                break;

            default:
                break;
        }
    }

    /**
     * Get text hint
     *
     * @return
     */
    public Text getText_hint() {
        return text_hint;
    }

    /**
     * Set hint text
     *
     * @param text_hint
     */
    public void setText_hint(String text_hint) {
        this.text_hint.setText(text_hint);
    }


    /**
     * Icon window
     */
    public void minus() {
        GUI_ClientLogin.stage.setIconified(true);
    }
    /**
     * Close windows
     */
    public void close() {
        System.exit(0);
    }

    //STORIA
    /**
     * On mouse enter in the pane 1 set visible history 1
     */
    public void pane_H1_E() {
        label_H1.setVisible(true);
        label_H1.setVisible(true);
    }
    /**
     * On mouse exit of the pane 1 set not visible history 1
     */
    public void pane_H1_X() {
        label_H1.setVisible(false);
        label_H1.setVisible(false);
    }
    /**
     * On mouse enter in the pane 2 set visible history 2
     */
    public void pane_H2_E() {
        label_H2.setVisible(true);
        label_H2.setVisible(true);
    }
    /**
     * On mouse exit of the pane 2 set not visible history 2
     */
    public void pane_H2_X() {
        label_H2.setVisible(false);
        label_H2.setVisible(false);
    }
    /**
     * On mouse enter in the pane 3 set visible history 3
     */
    public void pane_H3_E() {
        label_H3.setVisible(true);
        label_H3.setVisible(true);
    }
    /**
     * On mouse exit of the pane 3 set not visible history 3
     */
    public void pane_H3_X() {
        label_H3.setVisible(false);
        label_H3.setVisible(false);
    }
    /**
     * On mouse enter in the pane 4 set visible history 4
     */
    public void pane_H4_E() {
        label_H4.setVisible(true);
        label_H4.setVisible(true);
    }
    /**
     * On mouse exit of the pane 4 set not visible history 4
     */
    public void pane_H4_X() {
        label_H4.setVisible(false);
        label_H4.setVisible(false);
    }

    public boolean isConsonantAlreadyPresent(ArrayList<Character> c) throws IOException, SQLException {
        return server.getMyGame(idPlayer).isConsonantAlreadyPresent(c);
    }

    public boolean veryfySolution(String mySolution) throws IOException, SQLException, InterruptedException {
        return server.getMyGame(idPlayer).isWordGuessedRight(mySolution);
    }
    /**
     * End my turn
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void endMyTurn() throws RemoteException, SQLException {
        pane_loseTurn.setVisible(true);
        //      server.getMyGame(idPlayer).endTurn(idPlayer, getNewLetters());
    }

    /**
     * Get move
     *
     * @param value
     */
    public String getReturnValue(String value) {
        chosenMove = value;
        return chosenMove;
    }

    //GIRA
    /**
     * Spin button give result of the spin
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void btn_spin() throws IOException, SQLException {
        start();
        String move = getReturnValue("GIRA");
        r = (int) (Math.random()*24);
        text_resultSpin.setText(ruota[r]);
        mossa="Gira Ruota --> "+ruota[r];
        _history();
        newMove(move);

        switch(ruota[r]){
            case "JOLLY":
                text_Totaljolly.setText("Jolly: 1");
                setPossJolly("Jolly: 1");
                btn_jolly.setDisable(false);
                disableGUI(false);
                break;

            case "PASSA":
                if(!btn_jolly.isDisable()) {
                    start();
                }
                else {
                    server.endMyTurn(lettereUscite);
                    endMyTurn();
                    mossa="Perde il Turno";
                }
                break;

            case "PERDI":
                server.endMyTurn(lettereUscite);
                endMyTurn();
                mossa="Perde il Turno";
                break;

            case  "300":
            case  "400":
            case  "500":
            case  "600":
            case  "700":
            case  "1000":
                btn_spin.setDisable(true);
                btn_consonant();
                break;

            default:
                break;

        }

    }

    //Consonante

    /**
     * Consonant button set visible edit field and confermation button
     */
    public void btn_consonant() {
        try {
            start();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        consORvocal=1;
        getReturnValue("VOCALE");
        btn_consonant.setVisible(false);
        btn_solution.setVisible(false);
        btn_vocal.setVisible(false);
        btn_jolly.setVisible(false);

        text_ConsonantORSolution.setText("Consonante");
        text_ConsonantORSolution.setVisible(true);
        edit_ConsORVocal.setVisible(true);
        btn_ConsORVocalConfermation.setVisible(true);
    }

    //Vocale

    /**
     * Vocal button set visible edit field and confermation button
     */
    public void btn_vocal() throws IOException, SQLException {
        start();
        consORvocal=2;
        getReturnValue("VOCALE");
        btn_consonant.setVisible(false);
        btn_solution.setVisible(false);
        btn_vocal.setVisible(false);
        btn_jolly.setVisible(false);

        text_ConsonantORSolution.setText("Vocale");
        text_ConsonantORSolution.setVisible(true);
        edit_ConsORVocal.setVisible(true);
        btn_ConsORVocalConfermation.setVisible(true);
    }

    //Soluzione
    /**
     * Solution button set visible edit field and confermation button
     */
    public void btn_solution(ActionEvent actionEvent) throws IOException, SQLException {
        start();
        consORvocal=0;
        getReturnValue("SOLUZIONE");
        btn_consonant.setVisible(false);
        btn_solution.setVisible(false);
        btn_vocal.setVisible(false);
        btn_jolly.setVisible(false);
        edit_ConsORVocal.setVisible(false);
        btn_ConsORVocalConfermation.setVisible(false);

        text_ConsonantORSolution.setText("Soluzione");
        text_ConsonantORSolution.setVisible(true);
        edit_Solution.setVisible(true);
        btn_SolutionConfermation.setVisible(true);
    }

    //Jolly
    /**
     * Jolly button set visible edit field and confermation button if is not disable
     */
    public void btn_jolly() throws SQLException, RemoteException {
        consORvocal=0;
        server.useJollyS(idPlayer, idManche);
        btn_jolly.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(text_Totaljolly.getText() == "1") {
                    text_Totaljolly.setText("0");
                    setPossJolly("Jolly: 0");
                }
                try {
                    start();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btn_spin.setDisable(false);
                btn_solution.setDisable(false);
                btn_vocal.setDisable(false);
                try {
                    newMove(chosenMove);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                chosenMove = null;
            }
        });
        btn_jolly.setDisable(true);
    }

    //Conferma Vocale o Consonante
    /**
     * Confermation button of vocal or consonant, write the result on history
     */
    public void btn_ConsORVocalConfermation() throws IOException, SQLException {
        start();
        btn_consonant.setVisible(true);
        btn_solution.setVisible(true);
        btn_vocal.setVisible(true);
        btn_jolly.setVisible(true);
        edit_Solution.setVisible(false);
        btn_SolutionConfermation.setVisible(false);

        text_ConsonantORSolution.setVisible(false);
        edit_ConsORVocal.setVisible(false);
        btn_ConsORVocalConfermation.setVisible(false);
        String letter = edit_ConsORVocal.getText().toUpperCase();

        if(edit_ConsORVocal.getText().equals("") && edit_ConsORVocal.getText().length()>1){
            mossa="Perde il Turno";
            server.endMyTurn(lettereUscite);
            endMyTurn();
            System.out.println("Perde Turno");
        }else {
            char l = letter.charAt(0);
            if(consORvocal==1){
                String[] vocal={"A","E","I","O","U"};
                for(int i=0; i<5; i++){
                    if(letter.equals(vocal[i])){
                        System.out.println("No vocali se segli consonante");
                        server.endMyTurn(lettereUscite);
                        endMyTurn();
                        mossa="Perde il Turno";
                        break;
                    }
                }
            }else if (consORvocal==2){
                String []vocal={"B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};
                for(int i=0; i<22; i++){
                    if(letter.equals(vocal[i])){
                        System.out.println("No consonanti se segli vocale");
                        server.endMyTurn(lettereUscite);
                        endMyTurn();
                        mossa="Perde il Turno";
                        break;
                    }
                }
            }

            String mysteryPhrase = new String(charPhraseArr);
            mysteryPhrase = mysteryPhrase.toUpperCase();
        if (consORvocal==1) {
            int count = 0; //occorenza di lettera estratta nella frase
            lettereUscite.add(0, l); // metto in testa la nuova lettera chiamata
            if (!(isConsonantAlreadyPresent(lettereUscite))) {
                for (int i = 0; i < mysteryPhrase.length(); i++) {
                    if (mysteryPhrase.charAt(i) == l) {
                        count++;
                        phrase.get(i).setText(String.valueOf(l).toUpperCase());
                    }
                }
                int partialPoint = count * Integer.valueOf(text_resultSpin.getText());
                points = text_ManchesPoint.getText().split(" ");
                //setPoint(points);
                int oldPoints = Integer.parseInt(points[0]);
                oldPoints += partialPoint;
                text_ManchesPoint.setText(oldPoints +" punti");
                setMossaPrecPoints(oldPoints);
                //TODO scegli nuova mossa sol, voc, ruota
                disableGUI(false);
            }
            else {
                System.out.println("Lettera già presente.");
                lettereUscite.remove(0); // se già presente rimuovo lettera inserita in testa.
                //TODO passa il turno
                server.endMyTurn(lettereUscite);
                endMyTurn();
                mossa="Perde il Turno";
            }

            if (text_resultSpin.getText() != "PERDE" && text_resultSpin.getText() != "PASSA" && text_resultSpin.getText() != "JOLLY") {
                int ruotaResult = Integer.parseInt(text_resultSpin.getText());
                int scoremoves = ruotaResult * count;

                mossa = "Chiama " + edit_ConsORVocal.getText().toUpperCase() + " --> " + scoremoves;
            }
        }
        else if (consORvocal==2) {
            String[] points = text_ManchesPoint.getText().split(" ");
            int actualPoints = Integer.parseInt(points[0]);
            if (actualPoints >= 1000) {
                lettereUscite.add(0, l);
                if (!(isConsonantAlreadyPresent(lettereUscite))) {
                    for (int i = 0; i < mysteryPhrase.length(); i++) {
                        if (mysteryPhrase.charAt(i) == l) {
                            phrase.get(i).setText(String.valueOf(l).toUpperCase());
                        }
                    }
                    text_ManchesPoint.setText((actualPoints-1000)+" punti");
                    setMossaPrecPoints(actualPoints-1000);
                    //TODO scegli nuova mossa sol, voc, ruota
                    disableGUI(false);
                }
            }
        }
        }
        _history();
        //tutti questi parametri sono da mandare a Partita per implementarli nei metodi
        edit_ConsORVocal.setText("");
        consORvocal=0;
    }


    public void riceviUpdate(String consNvowels) {
        System.out.println("");
        String updatedPhrase = consNvowels.toUpperCase();
        System.out.println("sono in riceviUpdate di Gioco, updatedPhrase "+updatedPhrase);
        String finalMysteryPhrase = getHintAndPhrase().get(1).toUpperCase();
        System.out.println("finalMystery char Arr "+finalMysteryPhrase+" etc");
        Platform.runLater(() -> {
            for(int i = 0; i< finalMysteryPhrase.length(); i++) {
                System.out.println("primo for lungo "+finalMysteryPhrase.length());
                for (int j = 0; j < updatedPhrase.length(); j++) {
                    System.out.println("secondo for lungo "+updatedPhrase.length()+ " lettere ricevute sono: "+updatedPhrase);
                    if (finalMysteryPhrase.charAt(i) == updatedPhrase.charAt(j)) {
                        System.out.println("finalMysteryPhrase[i] "+finalMysteryPhrase.charAt(i)+ " è uguale a updatedPhrase.charAt(j) "+updatedPhrase.charAt(j));
                        phrase.get(i).setText(updatedPhrase.charAt(j) + "");
                        System.out.println("ho settato phrase.get(i) a" +phrase.get(i));
                    }
                }
            }
        });
    }


    //Conferma soluzione

    /**
     * Confermation button of solution verify with the solution of the manches
     */
    public void btn_SolutionConfermation() throws InterruptedException, IOException, SQLException {
        start();
        btn_consonant.setVisible(true);
        btn_solution.setVisible(true);
        btn_vocal.setVisible(true);
        btn_jolly.setVisible(true);

        text_ConsonantORSolution.setVisible(false);
        edit_Solution.setVisible(false);
        edit_ConsORVocal.setVisible(false);
        btn_SolutionConfermation.setVisible(false);
        System.out.println("verify solution: "+veryfySolution(edit_Solution.getText()));
        if(edit_Solution.getText().equals("")) {
            mossa="Perde il Turno";
            endMyTurn();
        }


        else if(edit_Solution.getText().toUpperCase().equals(getHintAndPhrase().get(1).toUpperCase())) {
            charPhraseArr = hintAndPhrase.get(1).toUpperCase().toCharArray();
            for (int i=0; i<charPhraseArr.length; i++) {
                phrase.get(i).setText(String.valueOf(charPhraseArr[i]));
            }
            tPoints = text_TotalPoint.getText().split(" ");
            int totPoints = Integer.parseInt(tPoints[0]);
            String[] mPoints = text_ManchesPoint.getText().split(" ");
            int manchePoints = Integer.parseInt(mPoints[0]);
            totPoints += manchePoints;
            text_TotalPoint.setText(totPoints+" Punti");
            setTotalPoints(totPoints);
            server.winTheGame(nickname);
        }

        _history();
        edit_Solution.setText("");
    }

    public void someoneWon(String player) {
        pane_GameEnd.setVisible(true);
    }

    /**
     *  Close pane lose turn
     */
    public void btn_closeLoseTurn() {
        pane_loseTurn.setVisible(false);
    }

    //LASCIA

    /**
     * Leave the manches
     * @throws IOException
     */
    public void btn_leave() throws IOException {
        Parent logout = FXMLLoader.load(getClass().getResource("/rdf_partite.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(logout);
    }

    /**
     * Not leave the manches
     */
    public void btn_NoLeave() {
        pane_Leave.setVisible(false);
    }

    /**
     * Set visible the leave pane
     */
    public void btn_giveUp(){
        pane_Leave.setVisible(true);
    }

    //STORIA

    /**
     * History of the last four moves that players have made
     */
    private void _history() {
        h++;
        if(h==1){
            pane_H1.setVisible(true);

            text_NH1.setText(nickname);
            text_MH1.setText(mossa);
            text_number_H1.setText(String.valueOf(h));
        }else {
            if (h == 2) {
                pane_H2.setVisible(true);

                text_NH2.setText(text_NH1.getText());
                text_MH2.setText(text_MH1.getText());
                text_number_H2.setText(String.valueOf(h - 1));

                text_NH1.setText(nickname);
                text_MH1.setText(mossa);
                text_number_H1.setText(String.valueOf(h));
            } else {
                if (h == 3) {
                    pane_H3.setVisible(true);

                    text_NH3.setText(text_NH2.getText());
                    text_MH3.setText(text_MH2.getText());
                    text_number_H3.setText(String.valueOf(h - 2));

                    text_NH2.setText(text_NH1.getText());
                    text_MH2.setText(text_MH1.getText());
                    text_number_H2.setText(String.valueOf(h - 1));

                    text_NH1.setText(nickname);
                    text_MH1.setText(mossa);
                    text_number_H1.setText(String.valueOf(h));
                } else {
                    pane_H4.setVisible(true);

                    text_NH4.setText(text_NH3.getText());
                    text_MH4.setText(text_MH3.getText());
                    text_number_H4.setText(String.valueOf(h - 3));

                    text_NH3.setText(text_NH2.getText());
                    text_MH3.setText(text_MH2.getText());
                    text_number_H3.setText(String.valueOf(h - 2));

                    text_NH2.setText(text_NH1.getText());
                    text_MH2.setText(text_MH1.getText());
                    text_number_H2.setText(String.valueOf(h - 1));

                    text_NH1.setText(nickname);
                    text_MH1.setText(mossa);
                    text_number_H1.setText(String.valueOf(h));

                    if(h==100){
                        text_number_H1.setFont(Font.font("System", FontWeight.BOLD, 15));
                    }else{
                        if(h==101) {
                            text_number_H2.setFont(Font.font("System", FontWeight.BOLD, 15));
                        }else {
                            if (h==102) {
                                text_number_H3.setFont(Font.font("System", FontWeight.BOLD, 15));
                            } else {
                                if (h==103) {
                                    text_number_H4.setFont(Font.font("System", FontWeight.BOLD, 15));
                                }else{
                                    if(h==1000){
                                        text_number_H1.setFont(Font.font("System", FontWeight.BOLD, 11));
                                        text_number_H1.setLayoutY(17);
                                    }else {
                                        if (h == 1001) {
                                            text_number_H2.setFont(Font.font("System", FontWeight.BOLD, 11));
                                            text_number_H2.setLayoutY(17);
                                        } else {
                                            if (h == 1002) {
                                                text_number_H3.setFont(Font.font("System", FontWeight.BOLD, 11));
                                                text_number_H3.setLayoutY(17);
                                            } else {
                                                if (h == 1003) {
                                                    text_number_H4.setFont(Font.font("System", FontWeight.BOLD, 11));
                                                    text_number_H4.setLayoutY(17);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
