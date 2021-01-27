package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math.*;
import java.net.URL;
import java.text.DecimalFormat;

import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;

public class CalculateWindowController {
    @FXML private ComboBox<String> codecComboBox;
    @FXML private ComboBox<String> packetComboBox;
    @FXML private ComboBox<String> mediaComboBox;
    @FXML private ComboBox<String> tunnelingComboBox;

    @FXML private MenuItem darkTheme;
    @FXML private MenuItem lightTheme;
    @FXML private MenuItem blueTheme;

    @FXML private TextField calls;
    @FXML private TextField blocking;

    @FXML private Button backButton;
    @FXML private Button calculateButton;

    @FXML private Menu settingsMenu;
    @FXML private MenuBar menuBar;

    @FXML private RadioButton radioButton1;
    @FXML private RadioButton radioButton2;

    @FXML private ToggleGroup compresionGroup;
    @FXML private Label resultLabel;
    private final Menu themeMenu = new Menu("Theme");

    private OpenScene openScene;
    private double codecBandwidth;
    private int packetPeriod;
    private int mediaHeader;
    private int tunnelingHeader;
    private int ipOverhead = 40;
    private int numberOfCalls;
    private double blockingProbability;



    public void initialize() throws Exception{
        initComboBoxes();
        initHomeScene();
        initMenuScene();
        initCalculateScene();
        calculateButton.setDisable(true);
    }

    public void closeApp() {
        Platform.exit();
    }

    public void setHomeScene() throws Exception{
        Stage stage = (Stage) calculateButton.getScene().getWindow();
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
        PrintWriter writer = new PrintWriter("calculateWindow.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void initComboBoxes(){
        codecComboBox.getItems().addAll("G.711 (64kbps)", "G.711 (56kbps)", "G.711 (48kbps)","G.721 (32kbps)",
                "G.722 (48kbps)","G.722 (56kbps)","G.722 (64kbps)","G.722.1 (16kbps)","G.722.1 (24kbps)",
                "G.723.1 (5.3kbps)","G.723.1 (6.4kbps)","G.726 (32kbps)","G.729 (8kbps)","G.728 (12.8kbps)","G.729e (11.8kbps)");
        packetComboBox.getItems().addAll("10 ms", "20 ms","30 ms","40 ms","50 ms","60 ms","70 ms","80 ms","90 ms","100 ms");
        mediaComboBox.getItems().addAll("Ethernet", "PPP","MPPP","Frame Relay 7-byte","Frame Relay 8-byte", "Frame Relay 9-byte");
        tunnelingComboBox.getItems().addAll("No Tunneling","4 Bytes","6 Bytes","8 Bytes","10 Bytes","12 Bytes","16 Bytes","24 Bytes","32 Bytes",
                "40 Bytes","48 Bytes","56 Bytes","64 Bytes","72 Bytes","80 Bytes");
    }

    public void onCodecComboboxChange(){
        String codec = codecComboBox.getValue();
        switch (codec) {
            case "G.711 (64kbps)" -> codecBandwidth = 64;
            case "G.711 (56kbps)" -> codecBandwidth = 56;
            case "G.711 (48kbps)" -> codecBandwidth = 48;
            case "G.721 (32kbps)" -> codecBandwidth = 32;
            case "G.722 (48kbps)" -> codecBandwidth = 48;
            case "G.722 (56kbps)" -> codecBandwidth = 56;
            case "G.722 (64kbps)" -> codecBandwidth = 64;
            case "G.722.1 (16kbps)" -> codecBandwidth = 16;
            case "G.722.1 (24kbps)" -> codecBandwidth = 24;
            case "G.723.1 (5.3kbps)" -> codecBandwidth = 5.3;
            case "G.723.1 (6.4kbps)" -> codecBandwidth = 6.4;
            case "G.726 (32kbps)" -> codecBandwidth = 32;
            case "G.729 (8kbps)" -> codecBandwidth = 8;
            case "G.728 (12.8kbps)" -> codecBandwidth = 12.8;
            case "G.729e (11.8kbps)" -> codecBandwidth = 11.8;

        }
        System.out.println("codec: "+codecBandwidth);
    }

    @FXML public void onBackButtonClick() throws Exception{
        setMenuScene();
    }

    @FXML
    public void onCalculateButtonClick(){

        int counter = 0;
        char[] arrayB = blocking.getText().toCharArray();
        for (int i =0; i<arrayB.length; i++){
            if (!Character.isDigit(arrayB[i]) && arrayB[i] != '.'){
                counter++;
            }
        }
        char[] arrayC = calls.getText().toCharArray();
        for (int i =0; i<arrayC.length; i++){
            if (!Character.isDigit(arrayC[i]) && arrayC[i] != '.'){
                counter++;
            }
        }

        if (counter > 0){
            resultLabel.setText("Invalid input");
            return;
        }
        numberOfCalls =(int) Double.parseDouble(calls.getText());
        System.out.println("numberOfCalls: " + numberOfCalls);
        //int numberN = calculateFinalResult(numberOfCalls);
        double payloadSize = ((codecBandwidth * 1000 * packetPeriod) / 1000) / 8;
        System.out.println("payload size: "+payloadSize);
        double totalFrameSize = payloadSize + ipOverhead + mediaHeader + tunnelingHeader;
        System.out.println("total frame size: "+totalFrameSize);
        double packetRate = 1.0 / ((double) packetPeriod / 1000);
        System.out.println("packet rate: "+packetRate);
        double bandwidthPerCall = (totalFrameSize * 8 * packetRate) / 1000;


        double block = Double.parseDouble(blocking.getText());
        System.out.println(block);

        if (numberOfCalls == 1){
            System.out.println("numberofcalls = 1");
            resultLabel.setText("" + round(bandwidthPerCall, 3) + " kbps");
        }else if (block == 0){
            resultLabel.setText("" + round(bandwidthPerCall*numberOfCalls, 2) + " kbps");
            System.out.println("block == 0");
        }else if (Double.parseDouble(blocking.getText()) > 0){
            double bandwidthOverall = bandwidthPerCall * calculateFinalResult(numberOfCalls );
            resultLabel.setText("" + round(bandwidthOverall, 2) + " kbps");
        }
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public int calculateFinalResult(int n){
        int numberOfCalls;
        numberOfCalls = n;
        double result;
        double A = (double)numberOfCalls * (1.0/10.0);
        int s = 0;

        for (int N = 1; N <= numberOfCalls; N++){
            result = ((pow(A, N))/factorial(N)) / sum(N,A);
            System.out.println("result "+ (N) + " "+result);
            s = N;

            if (result < Double.parseDouble(blocking.getText())){
                break;
            }
        }
        return s;
    }

    public double sum(int stop, double a){
        double sum = 0;
        for(int i=0; i<=stop;i++){
            sum += pow(a,i)/factorial(i);
            //System.out.println(""+i+" sum= " +sum);
        }
        return sum;
    }

    public static long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    @FXML
    public void handleMouseEnter(Event event){
        if (event.getSource().equals(calculateButton)){
            calculateButton.setScaleY(1.2);
            calculateButton.setScaleX(1.2);
            onCallsTextAction();
            onBlockingTextAction();
        }else if (event.getSource().equals(backButton)){
            backButton.setScaleY(1.2);
            backButton.setScaleX(1.2);
        }
    }

    @FXML public void handleMouseExit(Event event){
        if (event.getSource().equals(calculateButton)){
            calculateButton.setScaleY(1.0);
            calculateButton.setScaleX(1.0);
        }else if (event.getSource().equals(backButton)){
            backButton.setScaleY(1.0);
            backButton.setScaleX(1.0);
        }
    }

    public void onPacketComboboxChange(){
        String packet = packetComboBox.getValue();
        switch (packet) {
            case "10 ms" -> packetPeriod = 10;
            case "20 ms" -> packetPeriod = 20;
            case "30 ms" -> packetPeriod = 30;
            case "40 ms" -> packetPeriod = 40;
            case "50 ms" -> packetPeriod = 50;
            case "60 ms" -> packetPeriod = 60;
            case "70 ms" -> packetPeriod = 70;
            case "80 ms" -> packetPeriod = 80;
            case "90 ms" -> packetPeriod = 90;
            case "100 ms" -> packetPeriod = 100;
        }
        System.out.println("packet: "+packetPeriod);
    }

    public void onMediaComboboxChange(){
        String media = mediaComboBox.getValue();
        switch (media){
            case "Ethernet" -> mediaHeader = 18;
            case "PPP" -> mediaHeader = 8;
            case "Frame Relay 7-byte" -> mediaHeader = 7;
            case "Frame Relay 8-byte" -> mediaHeader = 8;
            case "Frame Relay 9-byte" -> mediaHeader = 9;
            case "MPPP" -> mediaHeader = 6;
        }
        System.out.println("media: "+mediaHeader);
    }

    public void onTunnelingComboboxChange(){
        String tunneling = tunnelingComboBox.getValue();
        switch (tunneling){
            case "No Tunneling" -> tunnelingHeader = 0;
            case "4 Bytes" -> tunnelingHeader = 4;
            case "6 Bytes" -> tunnelingHeader = 6;
            case "8 Bytes" -> tunnelingHeader = 8;
            case "10 Bytes" -> tunnelingHeader = 10;
            case "12 Bytes" -> tunnelingHeader = 12;
            case "16 Bytes" -> tunnelingHeader = 16;
            case "24 Bytes" -> tunnelingHeader = 24;
            case "32 Bytes" -> tunnelingHeader = 32;
            case "40 Bytes" -> tunnelingHeader = 40;
            case "48 Bytes" -> tunnelingHeader = 48;
            case "56 Bytes" -> tunnelingHeader = 56;
            case "64 Bytes" -> tunnelingHeader = 64;
            case "72 Bytes" -> tunnelingHeader = 72;
            case "80 Bytes" -> tunnelingHeader = 80;
        }
        System.out.println("tunneling: "+tunnelingHeader);
    }

     public void onRadioButton1Click(){
         if (radioButton2.isSelected()){
             radioButton2.setSelected(false);
         }
         if (radioButton1.isSelected()){
             ipOverhead = 2;
         }else if (!radioButton1.isSelected()){
             ipOverhead = 40;
         }

        System.out.println(""+ipOverhead);
    }

    public void onRadioButton2Click(){
        if (radioButton1.isSelected()){
            radioButton1.setSelected(false);
        }
        if (radioButton2.isSelected()){
            ipOverhead = 4;
        }else if (!radioButton2.isSelected()){
            ipOverhead = 40;
        }
        System.out.println(""+ipOverhead);
    }

    public void onMouseMoved(){
        if (!codecComboBox.getSelectionModel().isEmpty() && !packetComboBox.getSelectionModel().isEmpty() && !mediaComboBox.getSelectionModel().isEmpty() && !tunnelingComboBox.getSelectionModel().isEmpty() && !calls.getText().isEmpty() && !blocking.getText().isEmpty()){
            calculateButton.setDisable(false);
        }
    }

    public void onBlockingTextAction(){
        char[] array = blocking.getText().toCharArray();
        for (int i =0; i<array.length; i++){
            if (array[i] == ','){
                array[i] = '.';
            }
        }
        String string = new String(array);
        blocking.setText(string);
        for (int i =0;i<array.length; i++){
            if (!Character.isDigit(array[i])){
                return;
            }
        }
        if (Double.parseDouble(blocking.getText()) > 1.0){
            blocking.setText("0.0");
        }else if (Double.parseDouble(blocking.getText()) < 0.0){
            blocking.setText("0.0");
        }
    }

    public void onCallsTextAction(){
        char[] array = calls.getText().toCharArray();
        for (int i =0; i<array.length; i++){
            if (array[i] == ','){
                array[i] = '.';
            }
        }
        String string = new String(array);
        calls.setText(string);

        for (int i =0;i<array.length; i++){
            if (!Character.isDigit(array[i])){
                return;
            }
        }

        int num =(int) Double.parseDouble(calls.getText());
        if (num < 1){
            calls.setText(""+1);
        }else if (num > 1000){
            calls.setText(""+1000);
        }
        else calls.setText(""+num);

    }

    public void onThemeModeButtonClick(Event event) throws Exception{
        if (event.getSource().equals(darkTheme)){
            writeFile("darkTheme.css");
            setCalculateScene();
        }else if (event.getSource().equals(lightTheme)){
            writeFile("lightTheme.css");
            setCalculateScene();
        }else if (event.getSource().equals(blueTheme)){
            writeFile("blueTheme.css");
            setCalculateScene();
        }
    }

    public void writeFile(String text) throws IOException {
        try(FileWriter fileWriter = new FileWriter("Themes.txt")){
            fileWriter.write(text);
        }
    }

    public void setCalculateScene() throws Exception{
        Stage stage = (Stage) calculateButton.getScene().getWindow();
        openScene.startCalcScene(stage);
    }

    public void initCalculateScene() throws Exception{
        PrintWriter writer = new PrintWriter("calculateWindow.fxml") ;
        openScene = new OpenScene(writer);
    }

    public void onLearnItemClick(){
        try {
            Desktop.getDesktop().browse(new URL("https://www.cisco.com/c/en/us/support/docs/voice/voice-quality/7934-bwidth-consume.html").toURI());
        } catch (Exception e) {}
    }
}
