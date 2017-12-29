
package cargame;

import java.util.ArrayList;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.awt.Font;


public class Fonts {
    private static ArrayList<Fonts> fontList = new ArrayList<Fonts>();
    
    private static String fontPath;
    
    public Fonts(String filePath){
        Fonts.fontPath = "assets/" + filePath;
        registerFont();
    }
    private void registerFont(){
        try{
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File(fontPath)));
        }
        catch(Exception e){
            e.printStackTrace();
     
        }
    }
    public static void addFont(Fonts font){
        fontList.add(font);
    }
}
