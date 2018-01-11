package cargame;



class Window {
    
    
    
    static final int XBORDER = 0; 
    static final int YBORDER = 0; 
    
    static final int YTITLE = 30; //30
//    static final int WINDOW_BORDER = 0; //8
//    static final int WINDOW_WIDTH = 1920;
    static final int WINDOW_WIDTH = 920;
//    static final int WINDOW_HEIGHT = 10;
    static final int WINDOW_HEIGHT = 610;
    
        
    static int xsize = -1;
    static int ysize = -1;
    
/////////////////////////////////////////////////////////////////////////
    public static int getX(int x) {
        return (x + XBORDER );
    }
    public static int getY(int y) {
        return (y + YBORDER + YTITLE );
    }
    public static int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER));
    }
    public static int getHeight2() {
        return (ysize - 2 * YBORDER  - YTITLE);
    }  
    
 
}