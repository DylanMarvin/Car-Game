
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
    
    static Image demo;
    static Image selDifficulty;
    static Image normal;
    

    boolean pressDemo;
    boolean pressNormal;
    

    Color color3;
    Color color4;
    
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
        demo = Toolkit.getDefaultToolkit().getImage("assets/Demo.png");
        selDifficulty = Toolkit.getDefaultToolkit().getImage("assets/selDifficulty.png");
        normal = Toolkit.getDefaultToolkit().getImage("assets/Normal.png");
        
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
        } else if(gameState == GameState.DifficultySel){
            g.drawImage(menuImage,Window.getX(0),Window.getY(0),Window.getWidth2(),Window.getHeight2(),obj);
            
            g.drawImage(selDifficulty,Window.getX(600),Window.getY(120),764,88,obj);
            
            g.setColor(color3);
            g.fillRoundRect(Window.getX(695), Window.getY(395),235, 80,10,10);
            g.setColor(color4);
            g.fillRoundRect(Window.getX(995), Window.getY(395),235, 80,10,10);
            
            g.drawImage(demo,Window.getX(700),Window.getY(400),225,65,obj);
            g.drawImage(normal,Window.getX(1000),Window.getY(400),225,65,obj);
            

            
        }

    }
    public void Reset(){
        pressPlay = false;
        pressQuit = false;
        color1 = Color.BLACK;
        color2 = Color.BLACK;
        
        pressDemo = false;
        pressNormal = false;

        color3 = Color.BLACK;
        color4 = Color.BLACK;
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
    
    public int getCarNum(){
        return carNum;
    }
        public boolean getpressDemo(){
        return pressDemo;
    }
    public boolean getpressNormal(){
        return pressNormal;
    }
     public void setpressDemo(boolean yes){
         pressDemo = yes;
    }
    public void setpressNormal(boolean no){
         pressNormal = no;
    }
    public Color getColor3(){
        return color3;
    }
    public Color getColor4(){
        return color4;
    }
     public void setColor3(Color color){
         color3 = color;
    }
    public void setColor4(Color color){
         color4 = color;
    }

   
}
