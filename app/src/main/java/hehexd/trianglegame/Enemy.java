package hehexd.trianglegame;

import static java.lang.Math.random;

/**
 * Created by James on 3/3/2017.
 * This class will contain the data for the boxes that come from the top
 * of the screen
 */

public class Enemy {
    private int yPos;  // when you make an enemy, they all have a y pos
    private int holePos; // and they will all have an x position for the hole
    private int holeSize;
    private boolean hasNew;

    public Enemy(){
        yPos = -100; // start it above the top of the screen
        //now, create a random position for the hole that is possible for the triangle to fit in
        createRandomPosition();
        hasNew = false;
    }
    private void createRandomPosition(){
        // this function will create a random position for the hole as well as the size of the hole
        // first of all, create the size of the hole
        // the hero is 100 pix wide, so the hole should be > 120 pixels and less than 500 to make
        //hard
        holeSize = 140 + (int)(260 * random()); // so, the size is just a random number
        //between 380 and 0 + 120, so its always between 120 and 500

        //now, whe hole's position is going to depend on the hole size, so that they don't appear
        // off screen
        //between 20 pix and 1060 pix, so I need random # between this
        holePos = (holeSize / 2) + 50 + (int)((940 - holeSize) * random());
        // so the center of the hole is 50 + half of the hole
        // + a random # between o and 939
    }
    public void moveEnemy(int score){
        //move the enemy depending on the score
        // the more the score, the faster the movement
        yPos += (score + 400) / 100; // this might be hella fast
        // + 100 to immediately start movement


    }
    public int getyPos(){
        return yPos;
    }
    public int getHolePos(){
        return holePos;
    }
    public int getHoleSize(){
        return holeSize;
    }
    public boolean getHasNew(){
        return hasNew;
    }
    public void setHasNew(){
        hasNew = true;
    }
}
