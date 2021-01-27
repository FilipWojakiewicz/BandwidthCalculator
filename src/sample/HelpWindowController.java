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

public class HelpWindowController {
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
        setTextArea();
        initHelpScene();
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
            setHelpScene();
        }else if (event.getSource().equals(lightTheme)){
            writeFile("lightTheme.css");
            setHelpScene();
        }else if (event.getSource().equals(blueTheme)){
            writeFile("blueTheme.css");
            setHelpScene();
        }
    }

    public void writeFile(String text) throws IOException {
        try(FileWriter fileWriter = new FileWriter("Themes.txt")){
            fileWriter.write(text);
        }
    }

    public void setHelpScene() throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        openScene.startHelpScene(stage);
    }

    public void initHelpScene() throws Exception{
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
        text = "total packet size = (Layer 2 header) + (IP/UDP/RTP header) + (voice payload size)\n" +
                "packets per second (PPS) = (codec bit rate) ÷ (voice payload size)\n" +
                "bandwidth = total packet size * PPS\n\n" +
                "The approximate protocol header sizes used in these calculations are as follows:\n" +
                "●" +
                " 40 bytes for IP (20 bytes) / User Datagram Protocol (UDP) (8 bytes) / Real-Time Transport Protocol (RTP) (12 bytes)\n\n" +
                "●" +
                " Compressed Real-Time Transport Protocol (cRTP) reduces the IP/UDP/RTP headers to 2 or 4 bytes.\n\n" +
                "●" +
                " 6 bytes Layer 2 Header for MP or FRF\n\n" +
                "●" +
                " 1 byte for the end-of-frame flag on MP and Frame Relay frames\n\n" +
                "●" +
                " 18 bytes for Ethernet Layer 2 headers, including 4 bytes of Frame Check Sequence (FCS) or Cyclic Redundancy Check (CRC)\n\n" +
                "If blocking probability equals 0 the total bandwidth required is calculated based on number of channels the user provided.\n\n" +
                "Otherwise the bandwidth is calculated based on Erlang's B model. Knowing the blocking probability and assuming one user is producing "+
                "1/10 Erlang's of traffic load, the number of lines is calculated and used instead of the number of channels user provided.";

        textArea.setText(text);

    }

}
