package EditorPackage;
import javafx.scene.control.Alert;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This Class is Model Class For EditorApplication
 *
 */
public class EditorModel {

    private File file;
    private String Content;


    /**
     * This Method Saves Content In The Editor In The Specified File
     * @param file files loction
     * @param content content in editor
     */
    public void Save(File file,String content) {


            PrintWriter outputFile = null;

            try {

                //Writing Into File

                outputFile = new PrintWriter(file);

                outputFile.println(content);

                outputFile.close();

                this.file = file;

            } catch (FileNotFoundException e) {

                e.printStackTrace();

                //Setting Alert To SaveAs File First

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(null);

                alert.setTitle("Error");

                alert.setContentText("Error in Saving File ! Please read log for more information ");

                alert.show();

            }


    }



    /**
     * This Method Saves Content In The Editor In The Same File That  Already Been Saved
     * @param content content in the editor
     */
    public void Save(String content){

        PrintWriter outputFile = null;

        try {

            //Writing Into File

            outputFile = new PrintWriter(file);

            outputFile.println(content);

            outputFile.close();

            //Setting Alert For Action Saving The File Successfully

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setTitle("File Saved");

            alert.setContentText("File Saved Successfully");

            alert.show();

        }
        catch (Exception e) {

            System.out.println(e.getMessage());

            //Setting Alert For SavingAs The File First

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setTitle("Error");

            alert.setContentText("Error Please SaveAs File First  ");

            alert.show();
        }
    }


    /**
     * This Method Loads Content Of The File And Then Converts Content of The File To Proper Format
     * @param file files location
     * @return Content content of the editor
     */

    public String Load(File file) {

        Content="";

        if (file != null) {

            //Getting File Extension

            String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

            if (fileExtension.equals("txt")){

                //Reading  From txt File

                try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getPath()))) {

                    for (String line = null; (line = br.readLine()) != null; ) {

                        Content += line+"\n";

                    }
                }
                catch (Exception e){

                    System.out.println(e.getMessage());
                }


                Content = Content.replaceAll(" ","&nbsp;");

                //Deleting Html Tangs

                Content = Content.replaceAll("\n","<br>");

                Content=Content.replaceAll("\r","");

            }

            // File Extension Is .Html

            else {

                //Reading From File

                try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getPath()))) {

                    for (String line = null; (line = br.readLine()) != null; ) {

                        Content += line;

                    }

                    this.file = file;

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }

        else {

            //Showing Alert For Error Accrued While  Opening The File

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setTitle("Error");

            alert.setContentText("Error in Opening the  File ! ");

            alert.show();

        }

        return Content;
    }
}
