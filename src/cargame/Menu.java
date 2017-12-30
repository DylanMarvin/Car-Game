
package cargame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;


public class Menu {
    static Image menuImage;
    static Image play;
    static Image quit;
    static Image title;
    static Image road;
    static Image explosion;
    static Image rArrow;
    static Image lArrow;
    static Image title2;
    static Image Continue;
    
    boolean pressPlay;
    boolean pressQuit;
    
    Color color1;
    Color color2;
    
    static int carNum;
    
    public static void initImages(){
        menuImage = Toolkit.getDefaultToolkit().getImage("assets/menuImage2.png");
        play = Toolkit.getDefaultToolkit().getImage("assets/Play.png");
        quit = Toolkit.getDefaultToolkit().getImage("assets/Quit.png");
        title = Toolkit.getDefaultToolkit().getImage("assets/title.png");
        title2 = Toolkit.getDefaultToolkit().getImage("assets/title2.png");
        road = Toolkit.getDefaultToolkit().getImage("assets/road.png");
        explosion = Toolkit.getDefaultToolkit().getImage("assets/explosion.gif");
        rArrow = Toolkit.getDefaultToolkit().getImage("assets/rArrow.png");
        lArrow = Toolkit.getDefaultToolkit().getImage("assets/lArrow.png");
        Continue = Toolkit.getDefaultToolkit().getImage("assets/continue.png");
        
        carNum = 0;
    }
    public void draw(Graphics2D g,CarGame obj,GameState gameState){
        if(gameState == GameState.Menu){
            g.drawImage(menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),obj);
            g.setColor(color1);
            g.fillRoundRect(565, 335, 300, 100, 10, 10);
            g.setColor(color2);
            g.fillRoundRect(1065, 335, 300, 100, 10, 10);
            g.setColor(Color.white);
            g.drawImage(play,Window.getX(600),Window.getY(320),196,81,obj);
            g.drawImage(quit,Window.getX(1100),Window.getY(310),196,81,obj);
            g.drawImage(title,Window.getX(725),Window.getY(120),476,81,obj);
        }
        else if(gameState == GameState.CarSelect){
            g.drawImage(menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),obj);

            g.drawImage(title2,Window.getX(585),Window.getY(120),750,65,obj);
            g.drawImage(lArrow,Window.getX(600),Window.getY(460),72,126,obj);
            g.drawImage(rArrow,Window.getX(1225),Window.getY(460),72,126,obj);
            g.drawImage(Continue,Window.getX(765),Window.getY(820),390,73,obj);
            
            g.drawImage(Car.getSprite(carNum),Window.getX(850),Window.getY(400),220,280,obj);
        }

    }
    
    public boolean getpressPlay(){
        return pressPlay;
    }
    public boolean getpressQuit(){
        return pressQuit;
    }
     public void setpressPlay(boolean play){
         pressPlay = play;
    }
    public void setpressQuit(boolean quit){
         pressQuit = quit;
    }
    
    
    public Color getColor1(){
        return color1;
    }
    public Color getColor2(){
        return color2;
    }
     public void setColor1(Color color){
         color1 = color;
    }
    public void setColor2(Color color){
         color2 = color;
    }
    
    
    public Image getRoadImage(){
        return road;
    }
    public Image getExplosion(){
        return explosion;
    }
    
    public void addCarNum(){
        carNum++;
        if(carNum > 3){
            carNum = 0;
        }        
    }
    public void subtractCarNum(){
        carNum--;
        if(carNum < 0)
            carNum = 3;
    }
    
   
}
