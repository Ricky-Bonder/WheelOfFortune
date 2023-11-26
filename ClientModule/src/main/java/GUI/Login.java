package GUI;

import RdF.Client.ClientRDF;
import RdF.Server.ServerInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public AnchorPane parent,pane_fisso,pane_mobile,pane_top;
    public Text text_signup,text_signupText,text_loginText,text_welcome,text_error,text_backForbidden,text_passwordForbidden,text_passF,text_signup1,text_login,text_login1;
    public JFXTextField edit_signupName,edit_emailForbidden,edit_signupSurname,edit_signupUsername,edit_signupEmail,edit_emailLogin;
    public JFXButton btn_signup,btn_signupTrans,btn_LoginTans,btn_login,btn_passwordForbidden;
    public JFXPasswordField edit_passwordLogin;

    private double xOffSet = 0;
    private double yOffSet = 0;
    String[] SignUpFields = new String[5];
    static String emailLogin;
    String passwordLogin;
    static ServerInterface server;

    /*static String[] passaValori;
    private InterfaceClientRDF client;
    static Boolean isLogOK = false;*/

    /**
     * super Login
     */
    public Login() {
        super();
    }

    @Override
    /**
     * First method that start
     */
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
    }

    /**
     * Get email of current user
     * @return
     */
    public static String getEmailLogin(){
        return emailLogin;
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
     * Transition from login to signup
     */
    public void btn_LoginTans() {
        //Transizione
        TranslateTransition slide=new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane_mobile);
        slide.setToX(0);
        slide.play();
        pane_fisso.setTranslateX(0);
        signinShow();
        text_error.setVisible(false);
    }
    /**
     * Transition from signup to login
     */
    public void btn_signupTrans() {
        //Transizione
        TranslateTransition slide=new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane_mobile);
        slide.setToX(620);
        slide.play();
        pane_fisso.setTranslateX(-311);
        signupShow();
        text_error.setVisible(false);
    }

    /**
     * Set visible key of login
     */
    public void backF() {
        signinShow();
        text_error.setVisible(false);
    }

    /**
     * Set visible key of forbidden password
     */
    public void pass_forb() {
        passfShow();
        text_error.setVisible(false);
    }


    public boolean areEmailAndNickValid(String email, String nickname) throws SQLException, RemoteException {
        server = ClientRDF.getServer();
        boolean validation = false;
        String EmailOk = server.getIDUsersS(email);
        String NickOk = server.getIDUsersFromNickS(nickname);

        if (EmailOk == null && NickOk == null)
        {
            //puoi registrartti
            validation = true;
        }
        else {
            text_error.setVisible(true);
        }
        return validation;
    }

    /**
     * SignUp user
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void btn_signup() throws Exception {
        SignUpFields[0] = edit_signupUsername.getText();
        SignUpFields[1] = edit_signupName.getText();
        SignUpFields[2] = edit_signupSurname.getText();
        SignUpFields[3] = edit_signupEmail.getText();
        //TODO controllare se esiste già email o username
        if(SignUpFields[0].equals("") || SignUpFields[1].equals("") || SignUpFields[2].equals("") || SignUpFields[3].equals("") || !(isValidEmailAddress(SignUpFields[3]))){
            if (!(areEmailAndNickValid(SignUpFields[3], SignUpFields[0]))) {
                text_error.setVisible(true);
            }
        }else {
            server = ClientRDF.getServer();
            server.newUsersS(SignUpFields[0], SignUpFields[1], SignUpFields[2], SignUpFields[3], false, false);

            edit_signupName.setText("");
            edit_signupUsername.setText("");
            edit_signupSurname.setText("");
            edit_signupEmail.setText("");
            btn_LoginTans();
        }
    }



    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


    /**
     * Login User if successful go to games GUI
     *
     * @throws IOException
     * @throws SQLException
     */
    public void btn_login() throws Exception {
        emailLogin = edit_emailLogin.getText();
        passwordLogin = edit_passwordLogin.getText();
        boolean ok;

        server = ClientRDF.getServer();
        ok = server.loginUsersS(emailLogin, passwordLogin);
        if (ok){
            Parent gioco = FXMLLoader.load(getClass().getResource("/rdf_partite.fxml"));
            parent.getChildren().removeAll();
            parent.getChildren().setAll(gioco);
        }
        else{
            text_error.setText("email o password errata");
            text_error.setVisible(true);
        }
    }

    /**
     * Send email for password forbidden
     *
     * @throws SQLException
     * @throws RemoteException
     */
    public void btn_passwordForbidden() throws Exception {
        //TODO Eamil errata
        if(edit_emailForbidden.getText().equals("")){
            text_error.setVisible(true);
        }else {
            String emailForgotten = edit_emailForbidden.getText();
            server = ClientRDF.getServer();
            server.resetPasswordS(emailForgotten);
        }
    }

    /**
     * Icon window
     */
    public void minus() { GUI_ClientLogin.stage.setIconified(true); }
    /**
     * Close windows
     */
    public void close() { System.exit(0); }

    /**
     * Show password forbidden field
     */
    private void passfShow() {
        text_login.setVisible(false);
        text_login1.setVisible(false);
        edit_emailLogin.setVisible(false);
        edit_passwordLogin.setVisible(false);
        btn_login.setVisible(false);
        text_passwordForbidden.setVisible(false);

        edit_emailForbidden.setVisible(true);
        btn_passwordForbidden.setVisible(true);
        text_backForbidden.setVisible(true);
        text_passF.setVisible(true);
    }

    /**
     * Show Login field
     */
    private void signinShow() {
        text_login.setVisible(true);
        text_login1.setVisible(true);
        edit_emailLogin.setVisible(true);
        edit_passwordLogin.setVisible(true);
        btn_login.setVisible(true);
        text_passwordForbidden.setVisible(true);
        btn_signupTrans.setVisible(true);

        btn_LoginTans.setVisible(false);
        btn_signup.setVisible(false);
        text_signup1.setVisible(false);
        text_signup.setVisible(false);
        edit_signupName.setVisible(false);
        edit_signupSurname.setVisible(false);
        edit_signupEmail.setVisible(false);
        edit_signupUsername.setVisible(false);

        edit_emailForbidden.setVisible(false);
        btn_passwordForbidden.setVisible(false);
        text_backForbidden.setVisible(false);
        text_passF.setVisible(false);
    }

    /**
     * Show SignUp field
     */
    private void signupShow() {
        text_login.setVisible(false);
        text_login1.setVisible(false);
        edit_emailLogin.setVisible(false);
        edit_passwordLogin.setVisible(false);
        btn_login.setVisible(false);
        text_passwordForbidden.setVisible(false);
        btn_signupTrans.setVisible(false);

        btn_LoginTans.setVisible(true);
        btn_signup.setVisible(true);
        text_signup1.setVisible(true);
        text_signup.setVisible(true);
        edit_signupName.setVisible(true);
        edit_signupSurname.setVisible(true);
        edit_signupEmail.setVisible(true);
        edit_signupUsername.setVisible(true);

        edit_emailForbidden.setVisible(false);
        btn_passwordForbidden.setVisible(false);
        text_backForbidden.setVisible(false);
        text_passF.setVisible(false);
    }
}