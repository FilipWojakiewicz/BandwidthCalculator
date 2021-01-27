package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class AboutWindowController {
    private OpenScene openScene;
    @FXML private TextArea textArea;
    @FXML private MenuBar menuBar;
    @FXML private Menu settingsMenu;
    @FXML private Button backButton;

    @FXML private MenuItem darkTheme;
    @FXML private MenuItem lightTheme;
    @FXML private MenuItem blueTheme;

    private final Menu themeMenu = new Menu("Theme");


    public void initialize() throws Exception{
        initMenuScene();
        initHomeScene();
        initTextArea();
        //initMenu();
        initAboutScene();
        setTextArea();
    }

    public void closeApp() {
        Platform.exit();
    }

    public void setHomeScene() throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        openScene.startMenuScene(stage);
    }

    public void initHomeScene() throws Exception{
        PrintWriter writer = new PrintWriter("sample.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void setMenuScene() throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        openScene.startMenuScene(stage);
    }

    public void initMenuScene() throws Exception{
        PrintWriter writer = new PrintWriter("sample.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void initTextArea(){
        textArea.setEditable(false);
    }

    @FXML public void onBackButtonClick() throws Exception{
        setMenuScene();
    }

    public void initMenu(){
        MenuItem sub1 = new MenuItem("Dark");
        MenuItem sub2 = new MenuItem("Light");
        MenuItem sub3 = new MenuItem("Blue");

        themeMenu.getItems().addAll(sub1,sub2,sub3);
        settingsMenu.getItems().add(themeMenu);
        menuBar = new MenuBar();
        menuBar.getMenus().add(settingsMenu);
    }

    @FXML
    public void handleMouseEnter(Event event){
        if (event.getSource().equals(backButton)){
            backButton.setScaleY(1.2);
            backButton.setScaleX(1.2);
        }
    }

    @FXML public void handleMouseExit(Event event){
        if (event.getSource().equals(backButton)){
            backButton.setScaleY(1.0);
            backButton.setScaleX(1.0);
        }
    }

    public void onThemeModeButtonClick(Event event) throws Exception{
        if (event.getSource().equals(darkTheme)){
            writeFile("darkTheme.css");
            setAboutScene();
        }else if (event.getSource().equals(lightTheme)){
            writeFile("lightTheme.css");
            setAboutScene();
        }else if (event.getSource().equals(blueTheme)){
            writeFile("blueTheme.css");
            setAboutScene();
        }
    }

    public void writeFile(String text) throws IOException {
        try(FileWriter fileWriter = new FileWriter("Themes.txt")){
            fileWriter.write(text);
        }
    }

    public void setAboutScene() throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        openScene.startAboutScene(stage);
    }

    public void initAboutScene() throws Exception{
        PrintWriter writer = new PrintWriter("calculateWindow.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void onLearnItemClick(){
        try {
            Desktop.getDesktop().browse(new URL("https://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html").toURI());
        } catch (Exception e) {}
    }

    public void setTextArea (){
        String text;
        text = "About the project:\n\n" +
                "The project was created during a Traffic Engineering course at Wrocław University of Technology and was made by Filip Wojakiewicz. " +
                "It was created to calculate the necessary bandwidth for a VoIP network. For information's about the calculation process please go to the Help section.";
        textArea.setText(text);
    }
}
