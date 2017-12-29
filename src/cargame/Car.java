
package cargame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;


public class Car {
    Image carImage[] = new Image[5];
    int carNum;
    
    int carX;
    int carY;
    double angle;
    
    double xMov;
    double yMov;
    
    int life;
    
    Car(int i){
        carX = Window.getWidth2()/2;
        carY = 870;
        carImage[i] = Toolkit.getDefaultToolkit().getImage("assets/car" + i +".png");
        carNum = i;
        life = 3;

    }

    
    public void draw(Graphics2D g,CarGame obj){
            drawCar(g,Window.getX(carX),Window.getY(carY),angle,3,3,obj);
        
    }
    public void tick(int mouseX,int mouseY){
        calcAngle(mouseX, mouseY);
        
        if( (carX + xMov) > 1600){
             carX = 1600;
        }
        else if((carX + xMov) < 350){
            carX = 350;
        }
            
        if(angle >= 90){
            angle = 90;
        }
        else if(angle < -90){
            angle = -90;
        }
        
        if(moveValid(carX,carY,mouseX,mouseY,30)){
            carX += xMov;       
            
        }
        
        
        
    }
    
    private void drawCar(Graphics2D g,double xpos,double ypos,double rot,double xscale,double yscale,CarGame obj)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        int width = carImage[carNum].getWidth(obj);
        int height = carImage[carNum].getHeight(obj);

        g.drawImage(carImage[carNum],-width/2,-height/2,width,height,obj);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    public void calcAngle(double mouseX, double mouseY){
        if(moveValid(carX,carY,mouseX,mouseY,30)){
            double centerX = carX;
            double centerY = carY;

            double radiansToMouse = (double) Math.atan2(centerX - mouseX, centerY - mouseY);

            double degreesToMouse = (57.2957795f * radiansToMouse) * -1;
            angle = degreesToMouse;

            mov(mouseX,mouseY);
            
        }
        
    }
    
    public void mov(double mouseX,double mouseY){
        int dist = (int)Math.hypot((mouseX-carX), (mouseY-carY));
        dist/=10;

        xMov = (mouseX-carX)/dist;   
        
    }
    
    public boolean moveValid(double pX,double pY,double mX, double mY,double pSize){
        if(pX+pSize > mX && pX-pSize < mX && pY+pSize > mY && pY-pSize < mY)
            return false;
        else
            return true;
    }
    
    
    public int getX(){
        return carX;
    }
    public int getY(){
        return carY;
    }
    public int getLife(){
        return life;
    }
    public void substractLife(){
        life--;
    }


    
}
