
package cargame;


public class Spawner {
    private int scoreKeep;
    private int timeKeep;
    private int distance;
    private int level = 1;
    
    Spawner(){
        scoreKeep = 0;
        timeKeep = 0;
        distance = 0;
        level = 1;
    }
    
    public void Reset(){
        scoreKeep = 0;
        timeKeep = 0;
        distance = 0;
        level = 1;
    }
    
    public void tick(){
        if(timeKeep % 5 == 1){
                scoreKeep++;
            }
        
        if(timeKeep % 250 == 1){
            Obstacles.Create(0, Obstacles.Type.TrashCan);
        }
        
        if(level == 1){
            if(timeKeep % 100 == 1){              
                Obstacles.Create((int) (Math.random()*3+1), Obstacles.Type.Car);
            }
        }
        else if(level == 2){
            if(timeKeep % 75 == 1){              
                Obstacles.Create((int) (Math.random()*3+1), Obstacles.Type.Car);
            }
        }
        else if(level == 3){
            if(timeKeep % 50 == 1){              
                Obstacles.Create((int) (Math.random()*3+1), Obstacles.Type.Car);
            }
            if(timeKeep % 40 == 1){              
                Obstacles.Create((int) (Math.random()*3+1), Obstacles.Type.Car);
            }
        }

        else if(level == 4){
            
        }
        
        if(scoreKeep == 250){
            level = 2;
        }
        else if(scoreKeep == 500){                   
            level = 3;        
        }
        else if(scoreKeep == 1000){
            level = 4;
        }
        
        timeKeep++;
    }

}
