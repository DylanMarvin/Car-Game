
package cargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;


public class HighScore {
    private static HighScore HighScores[] = new HighScore[3];
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
        
        if(setHigh == false){
            for(int i = 0;i<HighScores.length;i++){
                g.setColor(Color.white);
//                g.drawLine(Window.getX(i*105), Window.getY(415), Window.getX((i*105)+60), Window.getY(415));
                    if(HighScores[i] != null)
                        g.drawString((i+1) + ": " + HighScores[i].highScore, Window.getX(800), Window.getY( 615 + (i*100)  ));
                    else
                        g.drawString((i+1) + ": " + "Empty", Window.getX(800), Window.getY( 615 + (i*100)  ));
            }
        }
        else {
            for(int i = 0;i<HighScores.length;i++){
                //g.drawString(""+HighScores[i].name, Window.getX(i),Window.getY(i));
                //g.drawString(""+HighScores[i].highScore,Window.getX(i),Window.getY(i));
            }
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
             System.out.println("newScore");
            
            
        }
        HighScores[val] = new HighScore(_name,score);
    }
    
    public int getHighScore(){
        return highScore;
    }
    public static void setHighScore(int score){
        
        int num = -1;
        if(score > HighScores[2].highScore){
            num = 3;
        }
        else if(score > HighScores[1].highScore){
            num = 2;
        }
        else if(score > HighScores[0].highScore){
            num = 1;
        } 
        
        if(num == 3){
            HighScores[2] = new HighScore("",score);
        }
        else if(num == 2){
            HighScores[2] = HighScores[1];
            HighScores[1] = new HighScore("",score);
        }
        else if(num == 1){
            HighScores[2] = HighScores[1];
            HighScores[1] = HighScores[0];
            HighScores[0] = new HighScore("",score);
        }
    
}
}
