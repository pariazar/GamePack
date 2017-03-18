package com.de_coder.hamedpa.gamepack.snake;
//Developed by HamedPa

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.TextView;

public class Game extends View {

  private boolean setupComplete = false;
  private int pxSquare, squaresWidth, squaresHeight,sqBorder=0,padding=0;
  private ArrayList<Block> walls;
  public Snake snake;
  private Food food;
  private Random random;
  private TextView scoreView;
  private GameScreen mActivity;
  public boolean gameOver=false;
  private int score,frameRate;
  private boolean darkTheme,classicMode,snakeOriented;

  public Game(Context context,GameScreen activity,TextView scoreView,boolean darkTheme,boolean classicMode,boolean snakeOriented,int speed) {
    super(context);
    mActivity = activity;
    random = new Random();
    this.scoreView = scoreView;
    this.darkTheme = darkTheme;
    this.classicMode = classicMode;
    this.snakeOriented = snakeOriented;
    this.frameRate = 5*(speed+1);
  }

  private void score(){
    score++;
    scoreView.setText(Integer.toString(this.score));
  }

  @SuppressLint("DrawAllocation")
  protected void onDraw(Canvas canvas){
    if(!setupComplete) {
      setup();
      this.invalidate();
      return;
    }

    for(Block block:walls){
      block.draw(canvas);
    }

    snake.draw(canvas);

    food.draw(canvas);


    final View parent = this;
    if(!snake.stopped){
      new Thread(new Runnable() {
        public void run() {
          parent.postDelayed(new Runnable() {
            public void run() {
              parent.invalidate();
            }
          },1000/frameRate);
        }
      }).start();
    }else if(gameOver){
      new Thread(new Runnable() {
        public void run() {
          parent.postDelayed(new Runnable() {
            public void run() {
              mActivity.gameOver();
            }
          },500);
        }
      }).start();
    }
  }

  public void setup(){
    score = -1;
    this.score();
    gameOver=false;

    int pxWidth = getWidth();
    int pxHeight = getHeight();
    int dpi = getResources().getDisplayMetrics().densityDpi;
    float inWidth = ((float) pxWidth) / dpi;
    float inHeight = ((float) pxHeight) / dpi;

    squaresWidth  = (int) (inWidth * 10.0);
    squaresHeight = (int) (inHeight * 10.0);
    if(squaresHeight < 15) squaresHeight = 15;
    if(squaresWidth < 15)  squaresWidth = 15;

    int pxSquareWidth = pxWidth / squaresWidth;
    int pxSquareHeight = pxHeight / squaresHeight;
    if(pxSquareWidth > pxSquareHeight)
      pxSquare = pxSquareHeight;
    else
      pxSquare = pxSquareWidth;  //Extra Space on Top

    padding = (pxWidth - squaresWidth * pxSquare)/2;
    if(classicMode) sqBorder = pxSquare / 20;

    walls = new ArrayList<Block>();
    for(int j=0;j<squaresWidth;j++){
      walls.add(new Block(j,0,0));  //Top Walls
      walls.add(new Block(j,squaresHeight-1,0));  //Bottom Walls
    }for(int j=1;j<(squaresHeight-1);j++){ //Left Walls
      walls.add(new Block(0,j,0));  //Left Walls
      walls.add(new Block(squaresWidth-1,j,0)); //Right Walls
    }

    //Create Snake
    snake = new Snake();

    //Create Food
    food = new Food(snake,walls);

    setupComplete = true;
  }


  public class Snake{

    public ArrayList<Block> blocks;
    private int direction,length;
    public boolean stopped=false;

    public Snake(){

      blocks = new ArrayList<Block>();
      blocks.add(new Block(squaresWidth/2,squaresHeight/2,1));
      length=3;

      direction = random.nextInt(4);
      switch(direction){
        case 0: //Going Right
          blocks.add(new Block(squaresWidth/2-1,squaresHeight/2,1));
          blocks.add(new Block(squaresWidth/2-2,squaresHeight/2,1));
          break;
        case 1: //Going Down
          blocks.add(new Block(squaresWidth/2,squaresHeight/2-1,1));
          blocks.add(new Block(squaresWidth/2,squaresHeight/2-2,1));
          break;
        case 2: //Going Left
          blocks.add(new Block(squaresWidth/2+1,squaresHeight/2,1));
          blocks.add(new Block(squaresWidth/2+2,squaresHeight/2,1));
          break;
        case 3: //Going Up
          blocks.add(new Block(squaresWidth/2,squaresHeight/2+1,1));
          blocks.add(new Block(squaresWidth/2,squaresHeight/2+2,1));
      }
    }

    // Move & Draw Snake
    public void draw(Canvas canvas){
      if(!stopped) move();
      for(Block block:blocks) block.draw(canvas);
    }


    public void turnLeft(){
      if(snakeOriented){
        this.direction -= 1;
        if(this.direction < 0) this.direction = 3;
      }else if(this.direction != 0 && this.direction != 2)
        this.direction = 2;
    }


    public void turnRight(){
      if(snakeOriented){
        this.direction += 1;
        if(this.direction > 3) this.direction = 0;
      }else if(this.direction != 0 && this.direction != 2)
        this.direction = 0;
    }


    public void turnDown(){
      if(!snakeOriented && this.direction != 1 && this.direction != 3)
        this.direction = 1;
    }


    public void turnUp(){
      if(!snakeOriented && this.direction != 1 && this.direction != 3)
        this.direction = 3;
    }

    public void move(){

      Block frontBlock = blocks.get(0);

      Block newBlock;
      switch(direction){
        case 0: //Going Right
          newBlock = new Block(frontBlock.x+1,frontBlock.y,1);
          break;
        case 1: //Going Down
          newBlock = new Block(frontBlock.x,frontBlock.y+1,1);
          break;
        case 2: //Going Left
          newBlock = new Block(frontBlock.x-1,frontBlock.y,1);
          break;
        default:  //Going Up
          newBlock = new Block(frontBlock.x,frontBlock.y-1,1);
      }

      if(this.collides(newBlock) || newBlock.collides(walls)){
        stopped = true;
        for(Block block:blocks){
          block.setType(3);
        }
        newBlock.setType(0);
        gameOver=true;

      }else{

        blocks.add(0,newBlock);

        if(this.collides(food)){
          food.move(this,walls);
          length++;
          score();

        }else
          blocks.remove(length);
      }
    }

    public boolean collides(Block block){
      for(Block oneBlock:this.blocks)
        if(block.collides(oneBlock)) return true;
      return false;
    }

  }

  public class Block {
    public int x=0,y=0;
    ShapeDrawable shape;

    public Block(){}

    public Block(int x,int y,int type){
      this.x = x;
      this.y = y;

      shape = new ShapeDrawable(new RectShape());
      shape.setBounds(padding+x*pxSquare+sqBorder,padding+y*pxSquare+sqBorder,padding+(x+1)*pxSquare-sqBorder,padding+(y+1)*pxSquare-sqBorder);

      this.setType(type);
    }

    public void draw(Canvas canvas){
      shape.draw(canvas);
    }

    public boolean collides(Block block){
      return block.x == this.x && block.y == this.y;
    }

    public boolean collides(ArrayList<Block> blocks){
      for(Block block:blocks){
        if(this.collides(block)) return true;
      }
      return false;
    }

    public void setType(int type){
      switch(type){
        case 0:
          if(darkTheme) shape.getPaint().setColor(Color.parseColor("#fff3f3f3"));
          else shape.getPaint().setColor(Color.BLACK);
          break;
        case 1:
          shape.getPaint().setColor(Color.parseColor("#ff33b5e5"));
          break;
        case 2:
          shape.getPaint().setColor(Color.GRAY);
          break;
        case 3:
          shape.getPaint().setColor(Color.RED);
      }
    }

  }

  class Food extends Block {

    public Food(Snake snake, ArrayList<Block> blocks){
      shape = new ShapeDrawable(new RectShape());
      this.setType(2);
      this.move(snake,blocks);
    }

    public void move(Snake snake, ArrayList<Block> blocks){
      while(true){
        this.x = random.nextInt(squaresWidth-3)+1;
        this.y = random.nextInt(squaresHeight-3)+1;
        if(!snake.collides(this) && !this.collides(blocks)) break;
      }
      shape.setBounds(padding+x*pxSquare+sqBorder,padding+y*pxSquare+sqBorder,padding+(x+1)*pxSquare-sqBorder,padding+(y+1)*pxSquare-sqBorder);
    }

  }
}
