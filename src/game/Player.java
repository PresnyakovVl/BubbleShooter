/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import io.Input;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import utils.Time;

/**
 *
 * @author Владимир
 */
public class Player extends Entity{
    
    private float speed;
    private int Stroke;
    private long lastShoot;
    private byte shootingScale;
    private byte health;
    private static final double ANGLE_45 = Math.toRadians(45);
    
    protected Player(float x, float y,int r, int Stroke, Color color1, float speed, byte shootingScale, byte health){
        super(EntityType.Player,x , y, r,  color1);
        this.speed = speed;
        this.Stroke = Stroke;
        this.lastShoot = Time.get();
        this.shootingScale = shootingScale;
        this.health = health;
    }
    
    @Override
    public void update(){
        System.out.println("Неправильный вызов Player.update");
    }
    
    public void update(Input input){
        float dx = 0;
        float dy = 0;
        float newX = x;
        float newY = y;
        
        boolean up = input.getKey(KeyEvent.VK_UP) || input.getKey(KeyEvent.VK_W);
        boolean down = input.getKey(KeyEvent.VK_DOWN)|| input.getKey(KeyEvent.VK_S);
        boolean left = input.getKey(KeyEvent.VK_LEFT)|| input.getKey(KeyEvent.VK_A);
        boolean right = input.getKey(KeyEvent.VK_RIGHT)|| input.getKey(KeyEvent.VK_D);
        
        if(up){
            dy = -speed;
        }if(down){
            dy = speed;
        }if(left){
            dx = -speed;
        }if(right){
            dx = speed;
        }if((up && left) || (up && right) || (down && left) || (down && right)){
            
            dy = dy*(float)Math.sin(ANGLE_45);
            dx = dx*(float)Math.sin(ANGLE_45);
        }
        
        //shoot
        if(input.getKey(KeyEvent.VK_SPACE)){
            fire();
        }
       
            
        
        newX +=dx;
        newY +=dy;
        
        if(newX<=0+r){
            newX = 0+r;
        }else if(newX >= Game.WIDTH-r){
            newX = Game.WIDTH-r;
        }
        
        if(newY<=0+r){
            newY = 0+r;
        }else if(newY >= Game.HEIGHT-r){
            newY = Game.HEIGHT-r;
        }
        
        x=newX;
        y = newY;
    }
    
    @Override
    public void render(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
        g.setStroke(new BasicStroke(Stroke));
        g.setColor(color.darker());
        g.drawOval((int)(x-r), (int)(y-r), 2*(r), 2*(r));
        g.setStroke(new BasicStroke(1));
        
    }
    
    public void fire(){
        
        if((Time.get()-lastShoot)>(Game.SHOOTING_RATE/shootingScale)){
            Game.bullets.add(new Bullet(x,y+r,Game.BULLET_RADIUS,Game.BULLET_COLOR, Game.BULLET_SPEED));
            lastShoot=Time.get();
        }
        
    }
    
    public void hit(){
        health--;
    }
    
    public byte GetHealth(){
        return health;
    }
    
    
}
