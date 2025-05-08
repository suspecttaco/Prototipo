package uas.mtds.prototipo;

import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/7-LOGIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void stop() {
        System.exit(0);
    }

}
