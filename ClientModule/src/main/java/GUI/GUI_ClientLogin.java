package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI_ClientLogin extends Application {
    public static Stage stage = null;
    @Override
    /**
     * Start method for the GUI
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/rdf_login.fxml"));
        Scene scene = new Scene(root, 931, 548);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        this.stage = stage;
        stage.show();
    }
}
