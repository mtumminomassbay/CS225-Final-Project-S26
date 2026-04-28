//Joey Barton

/*
How to use:
    Navigator.getInstance().navigateTo(Screen.Dashboard)
*/

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Navigator {

    private static Navigator instance;

    private AnchorPane content;

    private Navigator() {}

    public static Navigator getInstance() {
        if(instance == null) {
            instance = new Navigator();
        }

        return instance;
    }

    public void setContentArea(AnchorPane content) {
        this.content = content;
    }

    public void navigateTo(Screen screen) {
        try {
            String path = "/" + screen.getFxmlFile();
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(path));
            Node view = loader.load();
            content.getChildren().setAll(view);
        } catch(IOException e) {
            System.err.println("Failed to load screen: " + screen);
            e.printStackTrace();
        }
    }
    
}
