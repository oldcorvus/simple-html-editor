package EditorPackage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.Optional;
import java.util.Stack;

/**
 *This is a Controller Class For FxmlFile {@link  <a href="editor.fxml">FxmlFile</a>}
 */
public class EditorController {

    @FXML
    public HTMLEditor htmlEditor;

    private final Stack<String> stackUndo = new Stack<String>();

    private final Stack<String> stackRedo = new Stack<String>();

    private final EditorModel model;

    //Constructor For EditorController

    public EditorController(EditorModel model) {

        this.model = model;

    }


    /**
     * This Method Saves any Key Strokes in StackUndo
     */
    @FXML
     public void onKeyPressed() {

        if(htmlEditor.getHtmlText().length()+808-Editor.scene.getWidth()>0 && htmlEditor.getWidth()<4050){

            htmlEditor.setPrefWidth(htmlEditor.getWidth()+18);
        }

       stackUndo.push(htmlEditor.getHtmlText());

     }


    /**
     *This Method Invokes When Button SaveAs Is Clicked
     * For Saving Editor Content Either As *.html Or *.txt
     */
    @FXML
     public void onSaveAs() {

            FileChooser chooser = new FileChooser();

            //Getting Desired File Extension And Name

            chooser.getExtensionFilters().addAll(

                    new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html"),

                    new FileChooser.ExtensionFilter("Plain Text (*.txt)", "*.txt"),

                    new FileChooser.ExtensionFilter("All files (*.*)", "*.*")
            );

            File file = chooser.showSaveDialog(null);

            String content = "";

            if(file != null) {


                //Getting Chosen Extension

                String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

                //Getting Proper Content Based On File Extension

                if (fileExtension.equals("txt")) {

                    //Deleting Html Tags

                    content = htmlEditor.getHtmlText().replaceAll("<br>", "\n");

                    content = content.replaceAll("</p>", "\n");

                    content = content.replaceAll("\\<.*?\\>", "");

                    content = content.replaceAll("&nbsp;", " ");

                } else {

                    content = htmlEditor.getHtmlText();

                }

                model.Save(file, content);
            }
            else {

                //Showing Alert For Error Accrued While  Opening The File

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(null);

                alert.setTitle("Error");

                alert.setContentText("Error in Opening the  File ! ");

                alert.show();
            }

    }


    /**
     *  This Method Invokes When Button Save Is Clicked
     *  For Saving Editor Content  As *.html
     */
    @FXML
    public void onSave() {

            String content ;

            //Getting Content In The Editor

            content = htmlEditor.getHtmlText();

            model.Save(content);

    }



    /**
     *   This Method Invokes When Button Load Is Clicked
     *   For Loading Files With Different Extensions
     */
    @FXML
     public void onLoad()  {

            //Choosing File

            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("./"));

            File file = fileChooser.showOpenDialog(null);

            htmlEditor.setHtmlText(model.Load(file));

        }



    /**
     *  This Method Invokes When Button Undo Is Clicked
     *  To Perform Undo Action
     */
    @FXML
    public void onUndo(){

        if(!stackUndo.isEmpty()) {

            String last = stackUndo.pop();

            htmlEditor.setHtmlText(last);

            stackRedo.push(last);

        }
    }




    /**
     *  This Method Invokes When Button Redo Is Clicked
     *  To Perform Redo Action
     */
    @FXML
    public void onRedo(){

        if(!stackRedo.isEmpty()) {

            String last = stackRedo.pop();

            htmlEditor.setHtmlText(last);

            stackUndo.push(last);
        }

    }



    /**
     * This Method is Called When Event Close Happens
     * @param ev Event
     */
    @FXML
     void onClose(Event ev){

        //Setting Alert For Confirmation for Saving File before Exiting

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText(null);

        alert.setTitle("Exit?");

        alert.setContentText("Do you Want To Save Before Exit");

        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE,ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        //When Button Close  Is Clicked .Closing the App

        if(result.get() == ButtonType.CLOSE){

            System.exit(0);

        }

        //When Button Ok Is Clicked. Saving File As Either *.html Or *.txt

        else if(result.get() == ButtonType.OK) {

            onSaveAs();

            Alert alertFile = new Alert(Alert.AlertType.INFORMATION);

            alertFile.setHeaderText(null);

            alertFile.setTitle("Alert");

            alertFile.setContentText("File Saved ");

            alertFile.showAndWait();

            System.exit(0);

        }

        //When Button Cancel Is Clicked . Consuming The Event And Closing The Alert

        else if(result.get() == ButtonType.CANCEL){

            alert.close();

            ev.consume();

        }
    }



    /**
     * When Button About Is Clicked
     * For Showing Information About The Programmer And Application
     */
    @FXML
    private void onAbout(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setTitle("About");

        alert.setContentText("This is a simple text editor EditorApp Created by Mohanna Rajabi"+"\n\n"+
                String.format("Java version: %s, %s, JavaFX version: %s",System.getProperty("java.version")
                        ,System.getProperty("java.vendor"), System.getProperty("javafx.version")));

         alert.show();
    }



    /**
     * When Button New Tab Is Clicked
     * For Creating New Instance Of Controller And Model Class
     *
     * @throws IOException when loading fxml file
     */
    @FXML
    public void onNewTab() throws IOException{

        //Creating New Instance Of Program

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editor.fxml"));

        EditorController editorController = new EditorController(new EditorModel());

        fxmlLoader.setControllerFactory(t -> editorController);


        Stage primaryStage = new Stage();

        primaryStage.setScene(new Scene(fxmlLoader.load()));

        primaryStage.show();

    }

}
