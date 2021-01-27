package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.stage.Stage;
import org.w3c.dom.events.EventException;

import java.awt.Desktop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class Controller {
    @FXML private Button calculateButton;
    @FXML private Button helpButton;
    @FXML private Button aboutButton;
    @FXML private MenuBar menuBar;
    @FXML private Menu settingsMenu;
    @FXML private Label label;
    @FXML private MenuItem learnItem;
    @FXML private MenuItem darkTheme;
    @FXML private MenuItem lightTheme;
    @FXML private MenuItem blueTheme;
    private final Menu themeMenu = new Menu("Theme");
    private OpenScene openScene;


    public void initialize() throws Exception{
        initButtons();
        //initMenu();
        initCalculateScene();
        initHelpScene();
        initAboutScene();
        initHomeScene();
    }

    public void onLearnItemClick(){
        try {
            Desktop.getDesktop().browse(new URL("https://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html").toURI());
        } catch (Exception e) {}
    }

    @FXML public void onCalculateButtonClick() throws Exception{
        setCalculateScene();
    }

    @FXML public void onHelpButtonClick() throws Exception{
        setHelpScene();
    }

    @FXML public void onAboutButtonClick() throws Exception{
        setAboutScene();
    }

    @FXML public void handleMouseEnter(Event event){
        if (event.getSource().equals(calculateButton)){
            calculateButton.setScaleY(1.2);
            calculateButton.setScaleX(1.2);
        }else if (event.getSource().equals(helpButton)){
            helpButton.setScaleY(1.2);
            helpButton.setScaleX(1.2);
        }else if (event.getSource().equals(aboutButton)){
            aboutButton.setScaleY(1.2);
            aboutButton.setScaleX(1.2);
        }
    }

    @FXML public void handleMouseExit(Event event){
        if (event.getSource().equals(calculateButton)){
            calculateButton.setScaleY(1.0);
            calculateButton.setScaleX(1.0);
        }else if (event.getSource().equals(helpButton)){
            helpButton.setScaleY(1.0);
            helpButton.setScaleX(1.0);
        }else if (event.getSource().equals(aboutButton)){
            aboutButton.setScaleY(1.0);
            aboutButton.setScaleX(1.0);
        }
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

    public void initButtons(){
        calculateButton.setEffect(new Reflection());
        helpButton.setEffect(new Reflection());
        aboutButton.setEffect(new Reflection());
    }

    public void setCalculateScene() throws Exception{
        Stage stage = (Stage) calculateButton.getScene().getWindow();
        openScene.startCalcScene(stage);
    }

    public void initCalculateScene() throws Exception{
        PrintWriter writer = new PrintWriter("calculateWindow.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void setHomeScene() throws Exception{
        Stage stage = (Stage) calculateButton.getScene().getWindow();
        openScene.startMenuScene(stage);
    }

    public void initHomeScene() throws Exception{
        PrintWriter writer = new PrintWriter("sample.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void setHelpScene() throws Exception{
        Stage stage = (Stage) helpButton.getScene().getWindow();
        openScene.startHelpScene(stage);
    }

    public void initHelpScene() throws Exception{
        PrintWriter writer = new PrintWriter("helpWindow.fxml") ;
        openScene = new OpenScene(writer);
    }
    public void setAboutScene() throws Exception{
        Stage stage = (Stage) helpButton.getScene().getWindow();
        openScene.startAboutScene(stage);
    }

    public void initAboutScene() throws Exception{
        PrintWriter writer = new PrintWriter("aboutWindow.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void onThemeModeButtonClick(Event event) throws Exception{
        if (event.getSource().equals(darkTheme)){
            writeFile("darkTheme.css");
            setHomeScene();
        }else if (event.getSource().equals(lightTheme)){
            writeFile("lightTheme.css");
            setHomeScene();
        }else if (event.getSource().equals(blueTheme)){
            writeFile("blueTheme.css");
            setHomeScene();
        }
    }

    public void writeFile(String text) throws IOException {
        try(FileWriter fileWriter = new FileWriter("Themes.txt")){
            fileWriter.write(text);
        }
    }

    public void closeApp() {
        Platform.exit();
    }

    public void setHome() throws  Exception{
        setHomeScene();
    }
}
