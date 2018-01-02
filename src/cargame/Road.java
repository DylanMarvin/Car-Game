
package cargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;


public class Road {
    int roadx;
    int roady;
    int roadNum;
    private static int num;
    
    private static Image roadImage;
    
    Road(int x,int y){
        roadx = x;
        roady = y;
        num++;
        roadNum =num;
    }
    public static void initImage(){
        roadImage = Toolkit.getDefaultToolkit().getImage("assets/road.png");
    }
    public void draw(Graphics2D g,CarGame obj){
        g.drawImage(roadImage,Window.getX(roadx),Window.getY(roady),Window.getWidth2(),Window.getHeight2(),obj);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 150));
        g.drawString("Road " + roadNum, 500, roady+150);
    }
    public void tick(){
        roady+=10;
        
            if(roady >= Window.getHeight2()){
                roady = (-Window.getHeight2() * 2);
            }

    }
    

}
