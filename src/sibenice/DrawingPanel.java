package sibenice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


public class DrawingPanel extends JPanel
{
    Graphics2D plocha = null;
    Okno okno = null;
    private int numOfMistakes = 0;
    
    public DrawingPanel(Okno okno)
    {
        super();
        this.okno = okno;
    }
    
    @Override
    public void paintComponent(Graphics plocha)
    {
        this.plocha = (Graphics2D)plocha;
        plocha.setColor(Color.BLUE);
      
        switch(numOfMistakes)
        {
            case 0:
                break;
            case 1:
                drawPolygon();
                break;
            case 2, 3, 4, 5:
                drawPolygon();
                drawStructure();
                break;
            case 6:
                drawPolygon();
                drawStructure();
                drawHead();
                break;
            case 7:
                drawPolygon();
                drawStructure();
                drawHead();
                drawBody();
                break;
            case 8, 9:
                drawPolygon();
                drawStructure();
                drawHead();
                drawBody();
                drawLegs();
                break;
            case 10, 11:
                drawPolygon();
                drawStructure();
                drawHead();
                drawBody();
                drawLegs();
                drawHands();
                break;
            default:
                drawPolygon();
                drawStructure();
                drawHead();
                drawBody();
                drawLegs();
                drawHands();
                break;
        }
        
               
    }
    
    private void drawPolygon()
    {
        //dolní trojuhelník
        plocha.drawPolygon(new int[]{2*this.getWidth()/5 -50, 2*this.getWidth()/5 + 50, 2*this.getWidth()/5}, new int[]{this.getHeight() - 5, this.getHeight() - 5, this.getHeight()-40}, 3);
    }
    
    private void drawStructure()
    {
        //konstrukce
        if(numOfMistakes >= 2)
        {
            plocha.drawLine(2*this.getWidth()/5, this.getHeight()-40, 2*this.getWidth()/5, 40);
        }
        if(numOfMistakes >= 3)
        {
            plocha.drawLine(2*this.getWidth()/5, 40, 2*this.getWidth()/5 + 100, 40);
        }
        if(numOfMistakes >= 4)
        {
            plocha.drawLine(2*this.getWidth()/5, 80, 2*this.getWidth()/5+40, 40);
        }
        if(numOfMistakes >= 5)
        {
            plocha.drawLine(2*this.getWidth()/5+100, 40, 2*this.getWidth()/5+100, 80);
        }
         
    }
    
    private void drawHead()
    {
         //hlava
        plocha.drawOval(2*this.getWidth()/5+80, 80, 40, 40);
    }
    
    private void drawBody()
    {
        //tělo
        plocha.drawLine(2*this.getWidth()/5+100, 120, 2*this.getWidth()/5+100, 200);
    }
    
    private void drawLegs()
    {
        //nohy
        if(numOfMistakes >= 8)
        {
            plocha.drawLine(2*this.getWidth()/5+100, 200, 2*this.getWidth()/5+60, 240);
        }
        if(numOfMistakes >= 9)
        {
            plocha.drawLine(2*this.getWidth()/5+100, 200, 2*this.getWidth()/5+60, 240);
            plocha.drawLine(2*this.getWidth()/5+100, 200, 2*this.getWidth()/5+140, 240);
        }
        
    }
    
    private void drawHands()
    {
        //ruce
        if(numOfMistakes >= 10)
        {
            plocha.drawLine(2*this.getWidth()/5+100, 160, 2*this.getWidth()/5+70, 130);
        }
        if(numOfMistakes >= 11)
        {
            plocha.drawLine(2*this.getWidth()/5+100, 160, 2*this.getWidth()/5+130, 130);
        }
        
    }
   
    
    
    public void numOfMistakesPlus1(boolean mistake)
    {
        if(mistake)
        {
            numOfMistakes++;
        }
    }
    
    public int getNumOfMistakes()
    {
        return numOfMistakes;
    }
    
    public void clearNumOfMistakes()
    {
        numOfMistakes = 0;
    }
}


