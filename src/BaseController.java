//Joey Barton

import javafx.fxml.FXML;

/*
    BaseController is the parent class for every screen controller.
*/

public abstract class BaseController {
    
    protected final Navigator navigator = Navigator.getInstance();


    /*  Our subclasses will no longer override initialize()
        Override onLoad() instead. This ensures the base setup always
        runs first.
    */
    @FXML
    public final void initialize() {
        onLoad();
    }

    //Override this in each controller for screen specific initialization
    protected void onLoad() {}

    /*
        Navigates to the given screen.

        HOW TO USE:
            navigateTo(Screen.GROUP_STAGE);
    */
    protected void navigateTo(Screen screen) {
        navigator.navigateTo(screen);
    }

}
