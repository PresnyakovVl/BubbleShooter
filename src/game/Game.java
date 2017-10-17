package game;

import Display.Display;
import io.Input;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import utils.Time;

public class Game implements Runnable{
    
    public static final int         WIDTH               = 600;
    public static final int         HEIGHT              = 600;
    public static final String      TITLE               = "BubbleShooter";
    public static final int         CLEAR_COLOR         = 0xff0000f0;
    public static final int         NUM_BUFFERS         = 4;
    //Game loop
    public static final float       UPDATE_RATE         = 60.0f;
    public static final float       UPDATE_INTERVAL     = Time.SECOND / UPDATE_RATE;
    public static final long        IDLE_TIME           = 1;
    //Player
    public static final float       PLAYER_START_X            = WIDTH /2;   
    public static final float       PLAYER_START_Y            = HEIGHT /2;  
    public static final int         PLAYER_START_RADIUS       = 5; 
    public static final Color         PLAYER_START_COLOR       =  Color.WHITE;
    //public static final Color         PLAYER_START_COLOR2       =  Color.WHITE;
    public static final float         PLAYER_START_SPEED       =  3;
    public static final int         PLAYER_START_STROKE       = 3;
    public static final byte        PLAYER_START_SHOOTING_SCALE =1;
    //Bullet
    public static final int         BULLET_RADIUS           = 2;
    public static final Color         BULLET_COLOR           = Color.WHITE;
    public static final float         BULLET_SPEED           = 5;
    public static final long          SHOOTING_RATE          = 100000000l;
    
    
    private boolean running;
    private Graphics2D              graphics;
    private Input                   input;
    private Thread                  gameThread;
    private Player                  player;
    
    public static ArrayList<Bullet> bullets;
    
    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, 
                PLAYER_START_RADIUS,PLAYER_START_STROKE, PLAYER_START_COLOR
                ,PLAYER_START_SPEED,PLAYER_START_SHOOTING_SCALE);
        bullets = new ArrayList<Bullet>();
    }
    public synchronized void start(){
        if(running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public synchronized void stop(){
        
        if(!running)
            return;
        running = false;
        
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        cleanUp();
    }
    
    //Fuctions
    public void run(){
         int fps = 0;
        int upd = 0;
        int updl = 0;
        
        long count = 0;
        
        float delta = 0;
        long lastTime = Time.get();
        while(running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;
            
            count+=elapsedTime;
            
            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while(delta>1){
                update();
                upd++;
                delta --;
                if(render){
                    updl++;
                }else{
                    render = true;
                }
            }
            if(render){
                render();
                fps++;
            }else{
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if(count>= Time.SECOND){
                Display.setTitle(TITLE+" || Fps: "+fps+" | Upd: "+ upd+" | Updl: "+updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count =0;
            }
        }
    }
    private void update(){
        player.update(input);
        //Bullets Update
        Iterator<Bullet> i = bullets.iterator();
        while(i.hasNext()){
            Bullet bullet = i.next();
            bullet.update();
            if(bullet.removeFlag()){
                i.remove();
            }
        }
    }
    private void render(){
        //Display.clear();
        Display.renderBackground();
        player.render(graphics);
        for(Bullet bullet : bullets){
            bullet.render(graphics);
        }
        Display.swapBuffers();
    }
    
    private void cleanUp(){//очистка ресурсов
        Display.destroy();
    }
}
