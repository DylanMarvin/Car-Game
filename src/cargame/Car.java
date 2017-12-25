
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
    
    Car(int i){
        carX = Window.getWidth2()/2;
        carY = Window.getHeight2()/3;
        carImage[i] = Toolkit.getDefaultToolkit().getImage("assets/car" + i +".png");
        carNum = i;
    }

    
    public void draw(Graphics2D g,CarGame obj){
        drawCar(g,Window.getX(carX),Window.getY(carY),angle,3,3,obj);
    }
    
    private void drawCar(Graphics2D g,double xpos,double ypos,double rot,double xscale,double yscale,CarGame obj)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        int width = carImage[carNum].getWidth(obj);
        int height = carImage[carNum].getHeight(obj);

        g.drawImage(carImage[carNum],-22/2,-28/2,width,height,obj);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    public void calcAngle(double mouseX, double mouseY){
        double centerX = carX;
        double centerY = carY;

        double radiansToMouse = (double) Math.atan2(centerX - mouseX, centerY - mouseY);

        double degreesToMouse = (57.2957795f * radiansToMouse) * -1;
        angle = degreesToMouse;
        
    }
    
    public void setCarLocation(int x,int y){
        
        System.out.println(carX-x);
        if((carX - x) > 15 && carX > x)
            carX-=15;
        else if((carX + x) > 15 && carX < x)
            carX+=15;
        //carX =x;
        carY = y+50;
    }
    
}
