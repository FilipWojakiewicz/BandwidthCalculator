package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bandwidth calculator");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
        root.getScene().getStylesheets().add(readFile());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String readFile() throws IOException {
        Scanner scanner = null;
        String text = null;
        try{
            scanner = new Scanner(new FileReader("Themes.txt"));
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
            return "darkTheme.css";
        }else{
            return text;
        }
    }
}
