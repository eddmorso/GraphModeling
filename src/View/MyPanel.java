package View;

import javax.swing.*;
import java.awt.*;

class MyPanel extends JPanel {
    private boolean able = false, clear = false;
    private int initialX, initialY, endX, endY, connectionWeight;

    public void setAble(boolean able) {
        this.able = able;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public void setAble(boolean able, int initialX, int initialY, int endX, int endY, int connectionWeight){
        this.able = able;
        this.initialX = initialX;
        this.initialY = initialY;
        this.endX = endX;
        this.endY = endY;
        this.connectionWeight = connectionWeight;
    }

    public boolean getAble(){
        return able;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(clear){
            g.setColor(Color.white);
            g.fillRect(0,0, this.getWidth(), this.getHeight());
            clear = false;
        }
        if(able) {
            g.setColor(Color.red);

            g.drawLine(initialX + 25, initialY + 25, endX + 25, endY + 25);
            if(initialX > endX){
                g.drawString(String.valueOf(connectionWeight), endX + 30, endY + 25);
            }else {
                g.drawString(String.valueOf(connectionWeight), endX - 20, endY + 25);
            }
            able = false;
        }
    }
}
