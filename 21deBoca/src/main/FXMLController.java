/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class FXMLController implements Initializable {
    Random random = new Random();
    ObservableList list = FXCollections.observableArrayList();
    private int round= 1;
    private int score=0;
    private int p1wins=0;
    private int p2wins=0;
    private int cpuWins=0;
    private String txtPlayer="";
    
    @FXML
    private Label lblPlayer;  //player
    @FXML 
    private Label lblScore;
    @FXML
    private Button btnPlay;
    @FXML
    private Label lblWinner;
    @FXML
    private Label lblP1Wins;
    @FXML
    private Label lblP2Wins;
    @FXML
    private Label lblCpuWins;
    @FXML
    private ChoiceBox<String> selectRivals;
    @FXML
    private Button btnLance1;
    @FXML
    private Button btnLance2;
    @FXML
    private Button btnLance3;
    @FXML
    private Button btnRestart;
    
    @FXML
    private Button btnCpuPlay;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideLancesButtons();
        hideRestartBtn();
        hideBtnCpu();
        loadData();
        
    }    

    @FXML
    private void playGame(ActionEvent event) {
        
        String rival = selectRivals.getValue();
        if(rival == null){
            lblPlayer.setText("Por favor, escolha um rival");
        }else{
            changeTextRound();
            btnPlay.setVisible(false);
            showLancesButtons();
            showScore();
            attStatus();
            
        }
    }
    
    private void loadData(){
        list.remove(list);
        String p2 = "JOGADOR 2";
        String cpu = "COMPUTADOR";
        list.addAll(p2,cpu);
        selectRivals.getItems().addAll(list);
    }
    
    private boolean verifyRound(){
        if(round%2==1){
            return true; //impar retorna true
        }else{
            return false;
        }
    }
    
    private boolean verifyRival(){
        if("COMPUTADOR".equals(selectRivals.getValue())){
            return true;
        }else{
           return false;  
        }
        
    }
    
    private void changeTextRound(){
        if(score<21){
            
            if(verifyRound()==false){ //se for par
                if(verifyRival()){
                    txtPlayer ="Computador jogando...";  
                   
                }else{
                    txtPlayer ="Vez do jogador 2";
                }
            }else{
                if(verifyRival()){
                    txtPlayer ="Sua vez";  
                }else{
                    txtPlayer ="Vez do jogador 1";  
                }
            }    
        }else{
            txtPlayer="Fim de Jogo"; 
        }
       attStatus();
    }
    
    private void playerMove(int numBtn){
        score+=numBtn;
        if(score<21){
            round++;
        }
            
    }
    
    private void cpuMove() {
        if(verifyRound()==false){ //se for par  
            
           int comMove = random.nextInt(3)+1;
           score += comMove;
           changeTextRound(); 
            if(score<21){
                round++;
            }
        }
      
    }
    
    private void playVersus(int numBtn)  {
        if(verifyRival()){ //se for cpu
            playerMove(numBtn);
            hideLancesButtons();
            showBtnCpu();
           
           verifyGame();
           
        }else{
            playerMove(numBtn);
            verifyGame();
        }
        
        
    }
    
    private void attStatus(){
        // numero btn 1
        // numero btn 2
        // numero btn 3
        lblPlayer.setText(txtPlayer);
        lblScore.setText(""+score);
        lblP1Wins.setText(""+p1wins);
        lblP2Wins.setText(""+p2wins);
        lblCpuWins.setText(""+cpuWins);
    }
    
    private void verifyGame(){
        if(score>=21){
            score = 21;
           
            hideLancesButtons();
            
            if(verifyRound()){ //se for impar
                lblWinner.setText("Parabéns Jogador 1, você ganhou!"); 
                p1wins+=1;
            }else{
                if(verifyRival()){
                    lblWinner.setText("O computador ganhou!");
                    hideBtnCpu();
                    cpuWins+=1;
                }else{
                    lblWinner.setText("Parabéns Jogador 2, você ganhou!");
                    p2wins+=1;
                }
            }
            lblPlayer.setText("Fim de jogo");
            // mostrar botao de restart
            showRestartBtn();
            showWinner();
            hideLancesButtons();
            hideBtnCpu();
        }
        attStatus();
    }
    
    
    
    @FXML
    private void restartGame(ActionEvent event){
        score=0;
        round=1;
        hideRestartBtn();
        showLancesButtons();
        txtPlayer="Faça sua jogada";
        hideWinner();
        attStatus();
        
    }
    
    @FXML
    private void lancar1(ActionEvent event) throws InterruptedException {
        playVersus(1); 
        changeTextRound();
       
        
    }
    @FXML
    private void lancar2(ActionEvent event) throws InterruptedException{
        playVersus(2);
        changeTextRound();
        
    }
    @FXML
    private void lancar3(ActionEvent event) throws InterruptedException {
        playVersus(3);
        changeTextRound();
        
    }
    
    @FXML
    private void cpuPlay(ActionEvent event){
        cpuMove();
        hideBtnCpu();
        showLancesButtons();
        changeTextRound();
        verifyGame(); //tirar
    }
    
    private void hideBtnCpu(){
        btnCpuPlay.setVisible(false);
    }
    
    private void showBtnCpu(){
        btnCpuPlay.setVisible(true);
    }
    
    private void hideScore(){
       lblScore.setVisible(false);
    }
   
    private void showScore(){
       lblScore.setText(""+score);
       lblScore.setVisible(true);
    }
   
    private void hideRestartBtn(){
       btnRestart.setVisible(false);
    }
    
    private void showRestartBtn(){
       btnRestart.setVisible(true);
    }
    
    private void hideLancesButtons(){
        btnLance1.setVisible(false);
        btnLance2.setVisible(false);
        btnLance3.setVisible(false);
    }
    
    private void showLancesButtons(){
        btnLance1.setVisible(true);
        btnLance2.setVisible(true);
        btnLance3.setVisible(true);
    }
    
    private void hideWinner(){
        lblWinner.setVisible(false);
    }
    
    private void showWinner(){
        lblWinner.setVisible(true);
    }
    
    
}
    

