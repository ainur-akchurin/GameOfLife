package ru.ak;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;


public class GameOfLife {

    private final String NAME_OF_GAME = "Conway's Game of Life";
    private final int LIFE_SIZE = 50;
    private final int POINT_RADIUS = 10;
    private final int START_LOCATION = 200;
    private final int FIELD_SIZE = (LIFE_SIZE - 1) * POINT_RADIUS -3;
    private final int BTN_PANEL_HEIGHT = 58;
    private boolean[][] lifeGeneretion = new boolean[LIFE_SIZE][LIFE_SIZE];
    private boolean[][] nextGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    private JFrame frame = new JFrame(NAME_OF_GAME);
    private Random  random = new Random();
    private Canvas canvasPanel = new Canvas() ;
    private JPanel btnPanel = new JPanel();

    //==================================================================================================================
    public static void main(String[] args) {
        new GameOfLife().go();
    }
    //==================================================================================================================

    private void go() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           //назначаем кнопку закрытия окна
        frame.setSize(FIELD_SIZE,FIELD_SIZE + BTN_PANEL_HEIGHT); //устанавливаем размеры окна
        frame.setLocation(START_LOCATION,START_LOCATION);               //устанавливаем координаты левого верхнего угла
        frame.setResizable(false);                                      //запрещаем смену размера окна

        canvasPanel.setBackground(Color.WHITE);                         // установка белого фона

        JButton fillButton = new JButton("Fill");                   //создание кнопки для заполнения
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int x = 0; x < LIFE_SIZE; x++){
                    for (int y = 0; y <LIFE_SIZE; y++){
                        lifeGeneretion[x][y] = random.nextBoolean();
                    }
                }
                canvasPanel.repaint(); }
            });

        JButton stepButton = new JButton("Step");                   //создание кнопки для показа следующего поколения
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processOfLife();
                canvasPanel.repaint(); }
            });

        btnPanel.add(fillButton);                                        // добавление кнопок
        btnPanel.add(stepButton);

        frame.getContentPane().add(BorderLayout.CENTER,canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);                                         //устаналиваем видимость окна

    }//------------------------------------------------------------------------------------------------------------------

    private void processOfLife(){
        for (int x = 0; x < LIFE_SIZE; x ++){
            for (int y = 0; y < LIFE_SIZE ; y++){
                int count = countNeighbors(x,y);
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for (int x = 0; x<LIFE_SIZE; x++){
            System.arraycopy(nextGeneration[x], 0, lifeGeneretion[x], 0, LIFE_SIZE);
        }
    }//------------------------------------------------------------------------------------------------------------------

    private int countNeighbors(int x, int y) {
        int count = 0;
            for (int dx = -1; dx<2;dx++){
                for(int dy = -1; dy<2;dy++){
                    int nX = x + dx;
                    int nY = y + dy;

                    nX = (nX<0)?LIFE_SIZE-1:nX;
                    nY = (nY<0)?LIFE_SIZE-1:nY;
                    nX = (nX>LIFE_SIZE-1)?0:nX;
                    nY = (nY>LIFE_SIZE-1)?0:nY;

                    count += (lifeGeneretion[nX][nY])?1:0;
                }
            }
            if (lifeGeneretion[x][y])
                count--;

            return count;
    }//------------------------------------------------------------------------------------------------------------------

    private class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x<LIFE_SIZE;x++){
                for (int y = 0; y<LIFE_SIZE;y++){
                    if(lifeGeneretion[x][y]){
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }
}
