
package cargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;


public class Road {
    private static ArrayList<Road> roads = new ArrayList<Road>();
    int roadx;
    int roady;
    int roadNum;
     
 
    private static int num;
    
    private static Image roadImage;
    
        private static Image finishLine;
    
    public static void Create(){
        for(int i = 0;i<50;i++){
            Road obj = new Road(0, (-Window.getHeight2()) *i);
            roads.add(obj);
        }
    }
    
    Road(int x,int y){
        roadx = x;
        roady = y;
        num++;
        roadNum =num;
    }
    public static void initImage(){
        roadImage = Toolkit.getDefaultToolkit().getImage("assets/road.png");
        finishLine = Toolkit.getDefaultToolkit().getImage("assets/finish_line.png");
    }
    
    public static void Tick(){
        for(int i = 0;i<roads.size();i++)
            roads.get(i).tick();
    }
    
    public static void Draw(Graphics2D g,CarGame obj){
        for(int i = 0;i<roads.size();i++)
            roads.get(i).draw(g,obj);
    }
    
    public void draw(Graphics2D g,CarGame obj){
        if(roady >= -Window.getHeight2()*3){
            g.drawImage(roadImage,Window.getX(roadx),Window.getY(roady),Window.getWidth2(),Window.getHeight2(),obj);
            if(roadNum == 5){
                g.drawImage(finishLine,316,Window.getY(roady),1314,150,obj);
            }
        }
        
           // g.setColor(Color.white);
           // g.setFont(new Font("Arial", Font.PLAIN, 65));
           // g.drawString("Road " + roadNum,Window.getX(300),Window.getY(roady+50));
    }
    public void tick(){
        roady+=10;
        
        if(roady >= Window.getHeight2()*2){
            roads.remove(this);

        }

    }
    

}
