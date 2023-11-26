package GUI;

import RdF.Server.ServerInterface;
import RdF.Server.ServerRDF;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Server implements Initializable {
    public AnchorPane parent, pane_top;
    public Pane pane_noAdmin;
    public ProgressIndicator progressbar;
    public Text text_serverStartStop, text_errorField;
    public FontAwesomeIcon btn_closePane;
    public JFXTextField edit_databaseName, edit_hostName, edit_portName, edit_emailAdminLogin;
    public JFXButton btn_startDatabase, btn_signupAdmin, btn_loginAdmin;
    public JFXButton btn_stopServer, btn_startServer;
    public JFXTextField edit_nameAdmin, edit_surnameAdmin, edit_usernameAdmin, edit_emailAdmin;
    public JFXPasswordField edit_passwordAdmin, edit_passwordAdminLogin;

    private double xOffSet = 0;
    private double yOffSet = 0;
    ServerInterface server;
    String database, hostname, port, name, surname, nickname, passw, email;

    @Override
    /**
     * Inizialize GUI
     */
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
    }

    /**
     * Move pane
     */
    private void makeStageDragable() {
        pane_top.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        pane_top.setOnMouseDragged((event) -> {
            GUI_Server.stage.setX(event.getScreenX() - xOffSet);
            GUI_Server.stage.setY(event.getScreenY() - yOffSet);
            GUI_Server.stage.setOpacity(0.8f);
        });
        pane_top.setOnDragDone((event) -> GUI_Server.stage.setOpacity(1.0f));
        pane_top.setOnMouseReleased((event) -> GUI_Server.stage.setOpacity(1.0f));
    }

    /**
     * Minimize pane
     */
    public void minus() {
        GUI_Server.stage.setIconified(true);
    }

    /**
     * Close pane
     */
    public void close() {
        System.exit(0);
    }

    /**
     * Stop server
     */
    public void btn_stop() {
        //TODO stop server
        text_serverStartStop.setText("server stopped...");
        text_serverStartStop.setVisible(true);
        progressbar.setVisible(false);
    }

    /**
     * Start server
     */
    public void btn_start() {
        //TODO start server
        text_serverStartStop.setText("server started...");
        text_serverStartStop.setVisible(true);
        progressbar.setVisible(true);
    }

    /**
     * Login in the databese
     *
     * @throws RemoteException
     * @throws SQLException
     */
    public void btn_startA() throws RemoteException, SQLException {
        hostname = edit_hostName.getText();
        port = edit_portName.getText();
        database = edit_databaseName.getText();

        boolean connection = false;
        boolean okany = false;

        server = new ServerRDF();

        try {
            connection = server.startConnectionS(hostname, port, database);
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        }

        if (connection) {
            edit_databaseName.setVisible(false);
            edit_hostName.setVisible(false);
            edit_portName.setVisible(false);
            btn_startDatabase.setVisible(false);
            try {
                okany = server.anyAdminS();
            } catch (RemoteException | SQLException e) {
                e.printStackTrace();
            }
            if (okany) {
                edit_emailAdminLogin.setVisible(true);
                edit_passwordAdminLogin.setVisible(true);
                btn_loginAdmin.setVisible(true);
                btn_startDatabase.setVisible(false);
            } else {
                pane_noAdmin.setVisible(true);
                btn_startDatabase.setVisible(false);
            }
            text_errorField.setVisible(false);
        } else {
            text_errorField.setText("Dati database errati!");
            text_errorField.setVisible(true);
        }
    }

    /**
     * SignUp Admin
     *
     * @throws SQLException
     * @throws RemoteException
     */
    public void btn_signupA() throws Exception {
        name = edit_nameAdmin.getText();
        surname = edit_surnameAdmin.getText();
        email = edit_emailAdmin.getText();
        nickname = edit_usernameAdmin.getText();

        server.newUsersS(nickname, name, surname, email, true, false);

        btn_startDatabase.setVisible(true);
        btn_stopServer.setVisible(false);
        edit_nameAdmin.setVisible(false);
        edit_surnameAdmin.setVisible(false);
        edit_emailAdmin.setVisible(false);
        edit_usernameAdmin.setVisible(false);
        edit_passwordAdmin.setVisible(false);
        btn_signupAdmin.setVisible(false);
    }

    /**
     * Login Admin
     */
    public void btn_loginA() {
        boolean ok = false;
        boolean user = false;
        email = edit_emailAdminLogin.getText();
        passw = edit_passwordAdminLogin.getText();

        try {
            ok = server.isAdminS(email);
            user = server.loginUsersS(email, passw);
        } catch (RemoteException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((ok) && (user)) {
            edit_emailAdminLogin.setVisible(false);
            edit_passwordAdminLogin.setVisible(false);
            btn_loginAdmin.setVisible(false);
            btn_startServer.setVisible(true);
            btn_stopServer.setVisible(true);
            text_errorField.setVisible(false);
        } else {
            text_errorField.setText("utente o password errato");
            text_errorField.setVisible(true);
        }
    }

    /**
     * Close pane if no Admin
     */
    public void btn_closePane() {
        pane_noAdmin.setVisible(false);
        edit_nameAdmin.setVisible(true);
        edit_surnameAdmin.setVisible(true);
        edit_emailAdmin.setVisible(true);
        edit_usernameAdmin.setVisible(true);
        btn_signupAdmin.setVisible(true);
    }
}
