//Joey Barton

/*
    Navigator is a Singleton design that manages screen transitions.

    It holds a reference to the background AnchorPane and swaps the 
    foreground with navigateTo(). This ensures only one screen is shown
    at a time, and navigation is consistent.
*/

/*
HOW TO USE:
    navigateTo(Screen.Dashboard)
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
