
package cargame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Obstacles {
    private static ArrayList<Obstacles> obstacles = new ArrayList<Obstacles>();
    private static Image sprites[] = new Image[2];
    private int xpos;
    private int ypos;
    private Image image;
    private Type type;
    private double angle;
    private double scale;
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
        else{
            xpos = (int) (Math.random()*1310+320);
            angle = 180;
            scale = 2;
        }
        
        ypos = 300;
        type = _type;
        image = _image;
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
        sprites[1] = Toolkit.getDefaultToolkit().createImage("assets/obstacle1.png");
    }
    
    public void draw(Graphics2D g,CarGame obj){
        drawObstacle(g,Window.getX(xpos),Window.getY(ypos),angle,scale,scale,obj);
    }
    
    public boolean tick(){
        //ypos += 15;
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
    public static boolean HitBox(int x,int y){
        boolean loop = false;
        for(int i = 0;i<obstacles.size();i++){
            loop = obstacles.get(i).hitBox(x,y);
            
            if(loop == true)
                break;
        }
        return(loop);
    }    
    public boolean hitBox(int carx,int cary){
        if(type == Type.Car){
            if( (carx + 50) > xpos-30 && (carx - 50) < xpos + 30  && (cary + 50) > ypos - 70 && (cary - 50) < ypos -45){
                return true;
            }
        }
        else if(type == Type.TrashCan){
            
        }
        
        return false;
    }
}
