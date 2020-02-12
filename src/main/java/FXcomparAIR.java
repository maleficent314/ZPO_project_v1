import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXcomparAIR extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("fxml/ComparAIRfxml.fxml"));
        Scene scene = new Scene(root, 700, 540);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ComparAIR");
        primaryStage.show();

    }
}
