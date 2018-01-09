
package cargame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Obstacles {
    private static ArrayList<Obstacles> obstacles = new ArrayList<Obstacles>();
    private static Image sprites[] = new Image[4];
    private static Image onehundred;
    private int xpos;
    private int ypos;
    private Image image;
    private Type type;
    private double angle;
    private double scale;
    private boolean active;
    private int timecount;
    public static enum Type{
        Tree,TrashCan,Car
    };
    Obstacles(Image _image,Type _type){
        if(_type == Type.TrashCan){
            if((int) (Math.random()*2+1) == 1){
                xpos = (int) (Math.random()*60+320);
            }
            else{
                xpos = (int) (Math.random()*60+1550);
            }
            angle = 0;
            scale = 3;
            
        }
        else if(_type == Type.Tree){
            if((int) (Math.random()*2+1) == 1){
                xpos = (int) (Math.random()*60+320);
                angle = 90;
            }
            else{
                xpos = (int) (Math.random()*60+1650);
                angle = -90;
            }
            
            scale = 1;
         }
        else{
            xpos = (int) (Math.random()*1060+440);
            angle = 180;
            scale = 2;
        }
        
        ypos = -50;
        type = _type;
        image = _image;
        active = true;
        
    }
    public static void Draw(Graphics2D g,CarGame obj){
        for(int i =0;i<obstacles.size();i++){
            obstacles.get(i).draw(g, obj);
        }
    }
    
    public static void Tick(){
       for(int i =0;i<obstacles.size();i++){
            obstacles.get(i).tick();
            
        }
    }
    
    public static void Create(int i,Type type){
        Obstacles obj = new Obstacles(sprites[i],type);
        
        obstacles.add(obj);
    }
    
    public static void initSprites(){
        sprites[0] = Toolkit.getDefaultToolkit().createImage("assets/trashCan.png");
        sprites[1] = Toolkit.getDefaultToolkit().createImage("assets/redCar.png");
        sprites[2] = Toolkit.getDefaultToolkit().createImage("assets/truck.png");
        sprites[3] = Toolkit.getDefaultToolkit().createImage("assets/greenCar.png");
        onehundred = Toolkit.getDefaultToolkit().createImage("assets/100.png");
        
    }
    
    public void draw(Graphics2D g,CarGame obj){
        if(active == true)
        drawObstacle(g,Window.getX(xpos),Window.getY(ypos),angle,scale,scale,obj);
        else if(active == false){
            g.drawImage(onehundred,Window.getX(xpos),Window.getY(ypos),216,65,obj);
        }
    }
    
    public boolean tick(){
        
        ypos += 15;
        
            
        if(ypos >= Window.getHeight2()+50)
            obstacles.remove(this);
        return false;
    }
        private void drawObstacle(Graphics2D g,double xpos,double ypos,double rot,double xscale,double yscale,CarGame obj)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        int width = image.getWidth(obj);
        int height = image.getHeight(obj);

        g.drawImage(image,-width/2,-height/2,width,height,obj);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    public static int HitBox(int x,int y){
        int loop = 0;
        for(int i = 0;i<obstacles.size();i++){
            loop = obstacles.get(i).hitBox(x,y);
            
            if(loop > 0)
                break;
        }
        return(loop);
    }    
    
    public int hitBox(int carx,int cary){
        if(type == Type.Car){
            if( (carx + 30) > xpos-30 && (carx - 30) < xpos + 30  && (cary + 40) > ypos - 40 && (cary - 30) < ypos + 45){
                obstacles.remove(this);
                return 1;
            }
        }
        else if(type == Type.TrashCan){
             if( (carx + 30) > xpos-30 && (carx - 30) < xpos + 30  && (cary + 40) > ypos - 40 && (cary - 30) < ypos + 20 && active == true){
                 
                 active = false;
                 timecount = 3;
                  //obstacles.remove(this);   
                 
                return 2;
             }
        }
        
        return 0;
    }
}
