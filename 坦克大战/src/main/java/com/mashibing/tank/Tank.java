package com.mashibing.tank;

import lombok.Data;

import java.awt.*;

@Data
public class Tank {
    private int x = 200, y = 200,SPEED=5;
    private  Dir dir = Dir.UP;
    private boolean moving = false;

    private TankFrame tf;
    public Tank(int x, int y, int SPEED,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.SPEED = SPEED;
        this.tf = tankFrame;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);

        move();

    }

    private void move() {
        if(!moving)
            return;
        switch (dir){
            case  LEFT:
                x-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
            default:
                break;
        }
    }

    public void fire() {
        tf.b = new Bullet(this.x,this.y,this.dir);
    }
}
