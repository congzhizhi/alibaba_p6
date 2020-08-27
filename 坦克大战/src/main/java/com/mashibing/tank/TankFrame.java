package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame  extends Frame {
    int x =200,y=200;
public TankFrame(){
    this.setSize(800,600);
    this.setResizable(false);
    this.setTitle("坦克大战");
    this.setVisible(true);
    this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    });

    this.addKeyListener(new MyKeyListener());
}
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        while (true){
            Thread.sleep(100);
            tankFrame.repaint();
        }
    }

    @lombok.SneakyThrows
    @Override
    public void paint(Graphics g) {


        g.fillRect(x,y,50,50);
    }

    class MyKeyListener extends KeyAdapter{

    boolean bl =false;
    boolean bu =false;
    boolean br =false;
    boolean bd =false;
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bl = true;
                    x-=10;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    y-=10;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    x+=10;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    y+=10;
                    break;
             default:
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bl = false;
                    x-=10;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    y-=10;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    x+=10;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    y+=10;
                    break;
                default:
                    break;
            }
        }
    }






}
