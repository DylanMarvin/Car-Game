
package cargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class HighScore {
    private static HighScore HighScores[] = new HighScore[10];
    private int highScore;
    private String name;
    
    HighScore(String _name,int score){
        highScore = score;
        name = _name;
    }
    
    public static void Draw(Graphics2D g, CarGame obj){

        g.setColor(Color.white);
        g.drawString("HighScore", Window.getX(900) , Window.getY(100));
    }
   
    public static int checkHighScore(int score){
        for(int i = 0;i<HighScores.length;i++){
            if(HighScores[i].getHighScore() < score){
                return i;
            }
        }
        return 0;
    }
    public static void setNewHigh(String _name,int score,int val){
        HighScore tempScore;
        for(int i = HighScores.length;i>val;i--){
           
            tempScore = HighScores[i-1];
            HighScores[i] = tempScore;
            
        }
        HighScores[val] = new HighScore(_name,score);
    }
    public int getHighScore(){
        return highScore;
    }
    
}
