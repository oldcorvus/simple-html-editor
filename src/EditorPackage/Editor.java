package EditorPackage;
/**
 * This Program Is a Rich TextEditor That Can Save Files Either As *.txt (Plain Text) Or As *.html (Web view) .
 * This Program has Multiple Shortcuts for Different Operations such as opening Files (Ctrl + O) And Saving File (Ctrl + S)
 * This Program also can Perform  Redo/Undo Actions
 *This Program follows MVC architecture
 *
 * @author Mohanna Rajabi
 * @version 1.0
 *
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


/**
 * This Class is used for Generating View  based on FxmlFile {@link  <a href="editor.fxml">FxmlFile</a>}
 */
public class Editor  extends Application {

   public static Scene scene= null;
   public static Stage stage=null;

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage)  {

        stage=primaryStage;

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("editor.fxml"));

        EditorController editorController =new EditorController(new EditorModel());

       fxmlLoader.setControllerFactory(t -> editorController);

        try {

            scene = new Scene(fxmlLoader.load());

        }
        catch (IOException e) {

            e.printStackTrace();

        }

        stage.setScene(scene);

        stage.show();

        //ShortCut For Saving The File

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

            public void handle(KeyEvent ke) {

                if (keyComb.match(ke)) {

                   editorController.onSave();

                }
            }
        });

        //Shortcut For Opening A File

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);

            public void handle(KeyEvent ke) {

                if (keyComb.match(ke)) {

                    editorController.onLoad();
                }
            }
        });



        primaryStage.setOnCloseRequest(e -> {

           editorController.onClose(e);

        });


    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}


