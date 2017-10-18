package hehexd.trianglegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by James on 2/23/2017.
 * This class has all the data for the view
 * Also has multithread collision watcher
 */

public class WriterView extends View{

    private Paint bobRoss;
    private Hero pogChamp;
    private boolean isMenu;
    private ArrayList<Enemy> wutFace;
    private Thread collisionWatcher;
    private Context context;

    public WriterView(Context context) {
        super(context);
        bobRoss = new Paint();
        isMenu = true; //start the game in the menu
        pogChamp = new Hero();
        wutFace = new ArrayList<Enemy>();
        wutFace.add(new Enemy());
        this.context = context;

        collisionWatcher = new Thread(){
            @Override
            public void run(){
                //this thread will check for the collisions between the enemy closest to the bottom
                //of the screen and the hero
                //first, checks if the top of the triangle collides with the enemy
                //(pogChamp.getX, 1500) == top of triangle
                /*if(wutFace.get(0).getyPos() == 1350){
                    /
                    //so, if the enemy is in range...
                    if(wutFace.get(0).getyPos() <= 1600){
                        //enemy isn't past you, the correct y pos


                    }
                }
                if(pogChamp.getX() >= wutFace.get(0).getHolePos() + wutFace.get(0).getHoleSize()){
                    //so, if the x pos is to the right of the right box
                    if()
                }*/

                if((pogChamp.getX() >= wutFace.get(0).getHolePos() + wutFace.get(0).getHoleSize()
                        &&wutFace.get(0).getyPos() > 1350 && wutFace.get(0).getyPos() < 1600)
                        || (pogChamp.getX() <= wutFace.get(0).getHolePos() - wutFace.get(0).getHoleSize()
                && wutFace.get(0).getyPos() > 1350 && wutFace.get(0).getyPos() < 1600)){
                    endGame();
                }
            }
        }; // make the new thread
    }

    @Override
    public void onDraw(Canvas canvas){
        bobRoss.setColor(Color.BLACK);
        bobRoss.setTextSize(50);
        canvas.drawText("The roll is " + SensorActivity.tilt[2], 100, 200, bobRoss);
        if(isMenu){ // when you're in the menu
            drawButton(canvas, "Start!", 540, 960);
        }
        else { // when you're not in the menu
            drawGame(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        //this is what happens when you tap on the screen
        float xVal = motionEvent.getX();
        float yVal = motionEvent.getY();
        if(isMenu){
            // if its in the menu screen, when you tap on the start "button" you can start the game
            if(xVal > 400 && xVal < 680 && yVal > 860 && yVal < 1060){
                //if the tap is inside the box, then
                startGame();// this is a method which changes the variables so that the game starts
            }
        }
        return true;
    }

    private void drawGame(Canvas canvas){
        //start the game
        drawScore(canvas);
        drawHero(canvas);
        drawEnemy(canvas);
        drawSides(canvas);
        //on top of this, if you've lost, draw the end message on top of you
        if(pogChamp.getDead()){
            //if he's dead...
            canvas.drawText("You died!", 100, 100, bobRoss);
            //this is just test, just to see if it works
            //time to draw the scores
            drawEndScores(canvas);
        }
    }
    private void drawEnemy(Canvas canvas){
        // this function will be called when drawing the rectangles in the sccreen
        bobRoss.setColor(Color.GRAY);
        for(int i = 0; i < wutFace.size(); i++){
            //for each element in the list, draw them out
            canvas.drawRect(wutFace.get(i).getHolePos() + wutFace.get(i).getHoleSize()
                    , wutFace.get(i).getyPos()
                , 1080 , wutFace.get(i).getyPos() + 150, bobRoss);
            canvas.drawRect(0, wutFace.get(i).getyPos(),
                    wutFace.get(i).getHolePos() - wutFace.get(i).getHoleSize(),
                    wutFace.get(i).getyPos() + 150, bobRoss);
            // this is just to display data
            canvas.drawText("" + wutFace.get(i).getyPos() +" \n" +
                wutFace.get(i).getHoleSize() + "  " + wutFace.get(i).getHolePos(),
                    40, 400, bobRoss);
        }

    }
    private void drawButton(Canvas canvas, String text, float xCoord, float yCoord){
        //draw a button (just a box) with the text inside of it
        // the box will have a size of 100 pixels
        bobRoss.setColor(Color.BLACK);
        bobRoss.setStrokeWidth(10);
        canvas.drawRect(xCoord- 140, yCoord - 100, xCoord + 140, yCoord + 100, bobRoss);
        bobRoss.setColor(Color.WHITE); // make the inside
        canvas.drawRect(xCoord - 120, yCoord- 80, xCoord + 120, yCoord + 80, bobRoss);
        bobRoss.setColor(Color.BLACK);
        canvas.drawText(text, xCoord - 55, yCoord, bobRoss);
    }
    private void drawScore(Canvas canvas){
        // this function just draws the score from the hero on the top of the screen
        canvas.drawText("Score: " +pogChamp.getScore(), 400, 100, bobRoss);
    }
    private void drawHero(Canvas canvas){
        // draws the triangle at the bottom of the screen
        //take data from Hero to find location of triangle
        bobRoss.setColor(Color.GREEN);
        bobRoss.setStrokeWidth(20);
        canvas.drawLine(pogChamp.getX() - 50, 1600, pogChamp.getX(), 1500, bobRoss);
        canvas.drawLine(pogChamp.getX(), 1500, pogChamp.getX() + 50, 1600, bobRoss);
        canvas.drawLine(pogChamp.getX() - 50, 1600, pogChamp.getX() + 50, 1600, bobRoss);
        //fill it in
        canvas.drawCircle(pogChamp.getX(), 1550, 30, bobRoss);
        canvas.drawRect(pogChamp.getX()- 35, 1570, pogChamp.getX()+ 35, 1600, bobRoss);
        //drew the triangle at the bottom of the screen
    }
    private void drawSides(Canvas canvas){
        //draws the walls on the sides of the screen
        bobRoss.setColor(Color.RED);
        canvas.drawRect(0, 0, 20, 1920, bobRoss);
        canvas.drawRect(1060, 0, 1080, 1920, bobRoss);
    }
    private void drawEndScores(Canvas canvas){
        //this function will draw the scores onto your screen
        int[] saveData = getData();
        String currNum = "";
        bobRoss.setColor(Color.BLUE);
        for(int i = 0; i < 4; i++){
            //go through the entire array and draw each one on the screen
            currNum += Integer.toString(saveData[i]);
            canvas.drawText(currNum, 100, i*200 + 200, bobRoss);
            currNum = ""; //reset the currNum
        }
    }

    private void startGame(){
        // this function will start the timer for the rectangles to move down and paint the triangle
        isMenu = false;
        pogChamp = new Hero(); // create a new instance of the hero

        //create new enemies
        invalidate(); // then repaint
    }
    private void endGame(){
        // this function ends the game when called
        pogChamp.makeDead();
        saveData(pogChamp.getScore());//save the score
    }
    public void moveGame(){
        pogChamp.move(SensorActivity.tilt);
        //moves the hero whichever direction you tilt it
        for(int i = 0; i < wutFace.size(); i++){
            //for each element in the list,
            wutFace.get(i).moveEnemy(pogChamp.getScore());
            //move the enemy down toward the character
            if(wutFace.get(i).getyPos() >= 400 &&
                    !wutFace.get(i).getHasNew()){
                wutFace.add(new Enemy()); // when you pass 200y on the screen
                wutFace.get(i).setHasNew(); // then mention that this one doesn't need any more
                //add a new enemy
            }
            else if(wutFace.get(i).getyPos() >= 1920){
                //if its off the screen, remove it
                wutFace.remove(i);
            }
        }
        //TODO: check for collisions here
        collisionWatcher.start();
        invalidate();//then, repaint the game
    }
    public boolean isEnded(){
        //call this method if the game is over (hero has touched walls or has touched "enemy"
            return pogChamp.getDead();
    }

    public void saveData(int score){
        //this function saves data into the savefile
        // if there is no save file, then it creates one
        int[] dataArr = getData(); // puts the data into this array
        sort(dataArr);
        dataArr[0] = score; // most recent score is just first one
        if(score >= dataArr[1]){ //shift scores down by 1
            dataArr[3] = dataArr[2];
            dataArr[2] = dataArr[1];
            dataArr[1] = score;
        }
        else if(score >= dataArr[2]){
            dataArr[3] = dataArr[2];
            dataArr[2] = score;
        }
        else if(score > dataArr[3]){
            dataArr[3] = score;
        }
        String scoreString ="" + dataArr[0] + "\n" + dataArr[1] + "\n" + dataArr[2] + "\n"
                + dataArr[3] + "\n"; // put in # and then newline

        try {
            FileOutputStream FOut = new FileOutputStream("saveData.txt", false); // try to open the file
            FOut.write(scoreString.getBytes());
            FOut.close();
        }catch(Exception e){
            //if the file is not found, then
            e.printStackTrace();
        }
        //then, once the stream is open
    }
    public int[] getData(){
        //this function gets the array of values from the file
        int[] dataArr = new int[4]; //this array will contain the values for your score
        //if there was no score, then it will have a -1
        //char[] inputBuffer = new char[];
        String fileOutput = "";
        try {
            InputStream inputStream = context.openFileInput("saveData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                fileOutput = stringBuilder.toString();
            }
            else{
                //then the input stream is null, and the file doesn't exist
                File saveData = new File(context.getFilesDir(), "saveData.txt");
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        int index= 0;
        for(int i = 0; i < fileOutput.length(); i++){
            //go through the string and put each # into the array
            String currNum = "";
            if(fileOutput.substring(i, i+1) != "\n"){
                //if the current char isn't a newline
                currNum += fileOutput.substring(i, i+1); //add the char in
            }
            else if(fileOutput.substring(i, i+1) == "\n"){
                //if its a newline
                dataArr[index] = Integer.parseInt(currNum);
                index++;
                currNum = "";//clear currnum
            }
        }
        if(index != 4){
            //then it didn't get to the end
            for(int i = index + 1; i < 4; i++){
                //make them all -1
                dataArr[i] = -1;
            }
        }
        return dataArr;
    }

    private void sort(int[] dataArr){
        //im going to sort this array, but only #s 1, 2, 3 matter
        //just using bubble sort
        //1 is largest, 3 is smallest
        if(dataArr[3] > dataArr[2]){
            int temp = dataArr[2];
            dataArr[2] = dataArr[3];
            dataArr[3] = temp;
        }
        if(dataArr[2] > dataArr[1]){
            int temp = dataArr[1];
            dataArr[1] = dataArr[2];
            dataArr[2] = temp;
        }
        if(dataArr[3] > dataArr[2]){
            int temp = dataArr[2];
            dataArr[2] = dataArr[3];
            dataArr[3] = temp;
        }
    }
}
