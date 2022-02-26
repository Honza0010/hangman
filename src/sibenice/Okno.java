package sibenice;

//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.HeadlessException;

import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import hra.*;
import java.awt.FlowLayout;

public class Okno extends JFrame
{
    private final JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
    
    //private final JButton tlačítkoDalší = new JButton(new ImageIcon(ikony.Ikona.class.getResource("menu_run.png")));
    private final JButton guessButton = new JButton("Hádej");
    private final JButton newGameButton = new JButton("Nová hra");
    
    
    private final JMenuBar menuBar = new JMenuBar();
    
    private final JMenu game = new JMenu("Hra");
    private final JMenuItem newGame = new JMenuItem("Nová hra");
    private final JMenuItem guess = new JMenuItem("Hádej");
    private final JMenuItem end = new JMenuItem("Ukončit hru");
    
    private final JMenu info = new JMenu("Informace");
    private final JMenuItem aboutGame = new JMenuItem("O hře");
    
    private final DrawingPanel panel = new DrawingPanel(this);
    private final JPanel textPanel = new JPanel();
    //private final JPanel toolBarPanel = new JPanel();
   
    private JLabel word;
    
    private final JLabel mistakes = new JLabel("Maximální možný počet chyb: 11");
    
    private hra hra = null;
    
    private final guessDialog dialogGuess = new guessDialog(this);
    
    public Okno()
    {
        inicializace();
        menu();
    }

    private void inicializace() 
    {
        Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(monitor.width/2, monitor.height/2);
        setLocation(monitor.width/4, monitor.height/4);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                EndOrNot(null);
            }
        });
        setLayout(new BorderLayout());
        makeToolbar();
        panel.setPreferredSize(new Dimension(this.getWidth(), 3*this.getHeight()/4));
        //panel1.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));
        textPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));
        textPanel.setLayout(null);
        
        
        //word = new JLabel("Hello");
        word = new JLabel();
        word.setSize(this.getWidth()/2, this.getHeight()/4);
        word.setLocation(this.getWidth()/2 - 25, 0);
        word.setFont(new Font("Serif", Font.PLAIN, 30));
        textPanel.add(word);
     
        
        this.add(panel);
        this.add(textPanel, BorderLayout.SOUTH);
//
//        
        word.setText("...");
        //this.add(panel1, BorderLayout.CENTER);
        makeToolbar();
    }
    
    private void změňText(ActionEvent ae)
    {
        dialogGuess.setVisible(true);
        if(dialogGuess.isConfirmed())
        {
            try
            {
//                String text = dialogGuess.getText();
//                word.setText(text);
                panel.numOfMistakesPlus1(!hra.check(dialogGuess.getText()));
                word.setText(hra.getGuessedWord());
                dialogGuess.clearText();
                repaint();
                if(panel.getNumOfMistakes() == 11)
                {
                    Icon icon = new ImageIcon(ikony.Ikona.class.getResource("defeat.png"));
                    guess.setEnabled(false);
                    guessButton.setEnabled(false);
                    JOptionPane.showMessageDialog (this, "Konec: Prohrál jsi, protože jsi nezvládl uhodnout zadané slovo dříve, než ses oběsil", "Prohra", JOptionPane.INFORMATION_MESSAGE, icon);
                    word.setText(hra.getWord());
                }
                if(hra.rightGuessed())
                {
                    Icon icon = new ImageIcon(ikony.Ikona.class.getResource("victory.jpg"));
                    guess.setEnabled(false);
                    guessButton.setEnabled(false);
                    JOptionPane.showMessageDialog (this, "Konec: Vyhrál jsi. Uhodl jsi zadané slovo.", "Výhra", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }
            catch(Exception a)
            {
                dialogGuess.clearText();
                JOptionPane.showMessageDialog (this, "Chyba: " + a.getMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
                //a.printStackTrace();
            }
            
        }
    }
    
    private void menu()
    {
        createMenu();
    }
    
    private void createMenu()
    {
        this.setJMenuBar(menuBar);
        menuBar.add(game);
        game.add(newGame);
        game.add(guess);
        game.addSeparator();
        game.add(end);
        
        menuBar.add(info);
        info.add(aboutGame);
        
        
        
        guess.setEnabled(false);
        eventHandlers();
        
    }
    
    private void eventHandlers()
    {
        guess.addActionListener(this::změňText); //Jen zkouška
        guessButton.addActionListener(this::změňText);
        
        end.addActionListener(this::EndOrNot);
        newGame.addActionListener(this::newGame);
        newGameButton.addActionListener(this::newGame);
        
        aboutGame.addActionListener(this::aboutGame);
    }
          
    
    private void makeToolbar()
    {
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(toolbar, BorderLayout.NORTH);
        toolbar.add(newGameButton);
        //toolbar.addSeparator();
        toolbar.addSeparator(new Dimension(10,10));
        toolbar.add(guessButton);
        toolbar.addSeparator(new Dimension(10,10));
        toolbar.add(mistakes);
        
        guessButton.setEnabled(false);
    }
    
    private void EndOrNot(ActionEvent ae)
    {
        var answer = JOptionPane.showConfirmDialog(Okno.this, "Opravdu chceš ukončit program?", "Dotaz", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    private void newGame(ActionEvent ae)
    {
        String help = "";
        if(hra != null)
        {
            help = hra.getWord();
        }
        
        hra = new hra(help);
        
        help = hra.getGuessedWord();
        
        word.setText(hra.getGuessedWord());
        
        guess.setEnabled(true);
        guessButton.setEnabled(true);
        
        panel.clearNumOfMistakes();
        
        if(help.equals(""))
        {
            guess.setEnabled(false);
            guessButton.setEnabled(false);
            JOptionPane.showMessageDialog (this, "Nepovedlo se načíst slovo. \nHru načti znovu nebo restartuj aplikaci.", "Hlášení stavu", JOptionPane.PLAIN_MESSAGE);
        }
        
        repaint();
    }
    
    private void aboutGame(ActionEvent ae)
    {
        Icon ikona = new ImageIcon(ikony.Ikona.class.getResource("hangman.png"));
        JOptionPane.showMessageDialog (this, 
                "Hádáš po jednotlivých písmenech slovo, dokud ho neuhodneš nebo se neoběsíš. \nV okně na místě, "
                        + "kde vidíte 3 tečky, uvidíte hvězdičky představující počet písmen hádaného slova", 
                "Nápověda", JOptionPane.INFORMATION_MESSAGE, ikona);
    }
}
