package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OpenScene {
    //private final PrintWriter writer ;
    private File file;

    public OpenScene(PrintWriter writer) {
        //this.writer = writer ;
    }


    public void startCalcScene(Stage window) throws Exception {
        file = new File("calculateWindow.fxml");
        Parent root = FXMLLoader.load(getClass().getResource(file.getPath()));
        Scene scene =  new Scene(root, 500 ,600);
        scene.getStylesheets().add(readFile());
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public void startHelpScene(Stage window) throws Exception {
        file = new File("helpWindow.fxml");
        Parent root = FXMLLoader.load(getClass().getResource(file.getPath()));
        Scene scene =  new Scene(root, 500 ,600);
        scene.getStylesheets().add(readFile());
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public void startAboutScene(Stage window) throws Exception {
        file = new File("aboutWindow.fxml");
        Parent root = FXMLLoader.load(getClass().getResource(file.getPath()));
        Scene scene =  new Scene(root, 500 ,600);
        scene.getStylesheets().add(readFile());
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public void startMenuScene(Stage window) throws Exception {
        file = new File("sample.fxml");
        Parent root = FXMLLoader.load(getClass().getResource(file.getPath()));
        Scene scene =  new Scene(root, 500 ,600);
        scene.getStylesheets().add(readFile());
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public static String readFile() throws IOException {
        Scanner scanner = null;
        String text = null;
        try{
            File file = new File("Themes.txt");
            scanner = new Scanner(new FileReader(file.getPath()));
            text = scanner.nextLine();
            //System.out.println(text);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (scanner != null){
                scanner.close();
            }
        }
        if (text.isEmpty()){
            return "blueTheme.css";
        }else{
            return text;
        }
    }
}
