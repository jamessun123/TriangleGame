package hehexd.trianglegame;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by James on 2/25/2017.
 * awww yeaaaaaaaaaaaah
 * this is mr triangle's data
 */

public class Hero {
    private int xPos; //  only need xPos, can't move y
    private boolean isDead;
    private int score;

    public Hero(){
        xPos = 540; // center of the screen
        isDead = false;
        score = 0;
    }

    public void move(float[] tilt){
        //uses the angle calculated in the sensor to move the triangle around the bottom
        // of the screen
        xPos += 50 * tilt[2];
        //uses the orientation calculated in sensormanager.get orientation
        //tilt[2] is roll

        //if the triangle moves and touches one of the side walls...
        if(xPos <= 70 || xPos >= 1010){
            isDead = true;//hero is dead
            //saveData();
        }
        //if you're not dead yet, increment the score
        // you only call this function from the sensor,
        //so each time the sensor calls this function, you get a point
        score++;
    }



    public void makeDead(){ isDead = true;}
    public int getScore(){ return score;}
    public boolean getDead(){
        return isDead;
    }
    public int getX(){
        return xPos;
    }
}
