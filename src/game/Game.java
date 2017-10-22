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
    public static final int         NUM_BUFFERS         = 1;
    //Game loop
    public static final float       UPDATE_RATE         = 60.0f;
    public static final float       UPDATE_INTERVAL     = Time.SECOND / UPDATE_RATE;
    public static final long        IDLE_TIME           = 1;
    //Player
    public static final float       PLAYER_START_X            = WIDTH /2;   
    public static final float       PLAYER_START_Y            = HEIGHT /2;  
    public static final int         PLAYER_START_RADIUS       = 5; 
    public static final Color         PLAYER_START_COLOR       =  Color.WHITE;
    public static final byte         PLAYER_START_HEALTH       =  3;
    //public static final Color         PLAYER_START_COLOR2       =  Color.WHITE;
    public static final float         PLAYER_START_SPEED       =  9;
    public static final int         PLAYER_START_STROKE       = 3;
    public static final byte        PLAYER_START_SHOOTING_SCALE =1;
    //Bullet
    public static final int         BULLET_RADIUS           = 3;
    public static final Color         BULLET_COLOR           = Color.WHITE;
    public static final float         BULLET_SPEED           = 7;
    public static final long          SHOOTING_RATE          = 100000000l;
    
    
    private boolean running;
    private Graphics2D              graphics;
    private Input                   input;
    private Thread                  gameThread;
    private Player                  player;
    
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    
    private static Wave wave;
    
    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, 
                PLAYER_START_RADIUS,PLAYER_START_STROKE, PLAYER_START_COLOR
                ,PLAYER_START_SPEED,PLAYER_START_SHOOTING_SCALE,PLAYER_START_HEALTH);
        bullets = new ArrayList<Bullet>();
        //enemy = new Enemy((byte)5,(byte)3,Color.RED,(byte)1,(byte)1,(byte)5);
        //enemy2 = new Enemy((byte)10,(byte)3,Color.GREEN,(byte)1,(byte)1,(byte)5);
        enemies = new ArrayList<Enemy>();
        
       // enemies.add(new Enemy((int)20,(byte)3,Color.RED,(byte)1,(byte)1,(float)1, (float)6));
       // for (int i =0; i< 5; i++)
       //     enemies.add(new Enemy((int)(Math.random()*20+7),(byte)3,Color.GREEN,(byte)1,(byte)1,(float)(Math.random()*2+1), (float) 5));
        //enemies.add(new Enemy(1,1));
        
        wave = new Wave();
        
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
        BulletEnemyCollision();
        PlayerEnemyCollision();
        //Bullets Update
        Iterator<Bullet> bulletIt = bullets.iterator();
        while(bulletIt.hasNext()){
            Bullet bullet = bulletIt.next();
            bullet.update();
            if(bullet.GetRemoveFlag()){
                bulletIt.remove();
            }
        }
        //Enemy Update
        Iterator<Enemy> enemyIt = enemies.iterator();
        while(enemyIt.hasNext()){
            Enemy enemy = enemyIt.next();
            enemy.update();
            if(enemy.GetRemoveFlag()){
                enemyIt.remove();
            }
        }
        for(Enemy enemy : enemies){
            enemy.update();
        }
        wave.update();
        //System.out.println(wave.showWave());
        //enemy.update();
        //enemy2.update();
    }
    private void render(){
        //Display.clear();
        Display.renderBackground();
        player.render(graphics);
        for(Bullet bullet : bullets){
            bullet.render(graphics);
        }
        for(Enemy enemy : enemies){
            enemy.render(graphics);
        }
        if(wave.showWave())
            wave.render(graphics);
        //enemy.render(graphics);
        //enemy2.render(graphics);
        Display.swapBuffers();
    }
    
    private void cleanUp(){//очистка ресурсов
        Display.destroy();
    }
    
    private void BulletEnemyCollision(){
        for(Enemy enemy : enemies){
            float ex = enemy.GetX();
            float ey = enemy.GetY();
            int er = enemy.GetR();
            for(Bullet bullet : bullets){
                float dx = ex - bullet.GetX();
                float dy = ey - bullet.GetY();
                double dist = Math.sqrt(dx*dx+dy*dy);
                if((int)dist< (er + bullet.GetR())){
                    enemy.hit();
                    bullet.SetRemoveFlag(true);
                    if(enemy.GetRemoveFlag())
                        break;
                }
            }
        }
    }
    
    private void PlayerEnemyCollision(){
        float px = player.GetX();
        float py = player.GetY();
        int pr = player.GetR();
        for(Enemy enemy : enemies){
            float dx = px - enemy.GetX();
            float dy = py - enemy.GetY();
            double dist = Math.sqrt(dx*dx+dy*dy);
            if((int)dist< (pr + enemy.GetR())){
                player.hit();
                enemy.hit();
                if(enemy.GetRemoveFlag())
                    break;
            }
        }
    }
}
