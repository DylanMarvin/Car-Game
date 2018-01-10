
package cargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;


public class HighScore {
    private static HighScore HighScores[] = new HighScore[10];
    private static Image highscore = Toolkit.getDefaultToolkit().getImage("assets/highScore.png");
    private static boolean setHigh;
    private int highScore;
    private String name;
    private int val;
    
    HighScore(String _name,int score){
        highScore = score;
        name = _name;
    }
    
    public static void Draw(Graphics2D g, CarGame obj){
        g.drawImage(Menu.menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),obj);
       
        g.drawImage(highscore,Window.getX(750),Window.getY(100),447,88,obj);
        
        for(int i = 7;i<12;i++){
            g.drawLine(Window.getX(i*105), Window.getY(415), Window.getX((i*105)+60), Window.getY(415));           
        }
        
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
