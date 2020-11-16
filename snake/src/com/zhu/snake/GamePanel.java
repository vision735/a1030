package com.zhu.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int lenth;//蛇的长度
    int[] snakeX=new int[600];//蛇的坐标
    int[] snakeY=new int[500];
    String orientation;
    Timer timer=new Timer(100,this);

    boolean isStart = false;
    boolean isFail = false;

    int foodX;//食物坐标
    int foodY;
    Random random =new Random();

    //积分
    int score;

    public GamePanel(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//动起来
    }
    //初始化
    public void init(){
        lenth=3;
        //头部坐标
        snakeX[0]=100;
        snakeY[0]=100;
        //身体坐标
        snakeX[1]=75;
        snakeY[1]=100;
        snakeX[2]=50;
        snakeY[2]=100;

        orientation ="R";

        foodX=25+25*random.nextInt(34);
        foodY=75+25*random.nextInt(24);

        score=0;
    }


    //画板：画界面，画蛇
    //Graphics:画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(Color.black);
        //绘制头部的广告栏
        //Data.header.paintIcon(this,g,25,11);
        Data.header.paintIcon(this,g,25,11);
        //绘制游戏区域
        g.fillRect(25,75,850,600);

        if (orientation.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (orientation.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (orientation.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (orientation.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for (int i=1; i<lenth;i++){
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //绘制食物
        Data.food.paintIcon(this,g,foodX,foodY);
        //花积分
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度"+lenth,750,35);
        g.drawString("分数"+score,750,50);

        //游戏提示：是否开始
        if(isStart==false){
            //画一个文字
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        if(isFail){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，按下空格重新开始",300,300);
        }
    }
    //监听键盘输入
    @Override
    public void keyTyped(KeyEvent e) {
        //键盘按下，弹起

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //键盘按下，未释放
        //获取按下的是哪个键
        int keyCode = e.getKeyCode();
        //如果按下的是空格键
        if(keyCode==KeyEvent.VK_SPACE){
            if (isFail){
                isFail= !isFail;
                init();//重新初始化
            }else {
                isStart = !isStart;
            }
            repaint();//刷新界面
        }

        if(keyCode==KeyEvent.VK_LEFT){
            orientation ="L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            orientation ="R";
        }else if (keyCode==KeyEvent.VK_UP){
            orientation ="U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            orientation ="D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //释放某个键位
    }

    //定时器
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && !isFail){
            //右移
            for (int i=lenth-1;i>0;i--){
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }

            //控制方向让头部移动
            if (orientation.equals("R"))
            {
                //头部移动
                snakeX[0]+=25;
                //边界判断
                if(snakeX[0]>850){
                    snakeX[0]=25;
                }
            }else if (orientation.equals("L")){
                snakeX[0]-=25;
                if (snakeX[0]<25){
                    snakeX[0]=850;
                }
            }else if (orientation.equals("U")){
                snakeY[0]-=25;
                if (snakeY[0]<75){
                    snakeY[0] =650;
                }
            }else if (orientation.equals("D")){
                snakeY[0]+=25;
                if (snakeY[0]>650){
                    snakeY[0] =75;
                }
            }
            //吃到食物
            if (snakeX[0]==foodX && snakeY[0]==foodY){
                lenth++;
                foodX=25+25*random.nextInt(34);
                foodY=75+25*random.nextInt(24);
                score+=10;
            }

            //结束判断
            for(int i=1;i<lenth;i++){
                if (snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail =true;
                }
            }

            repaint();//刷新
        }
        timer.start();
    }

}
