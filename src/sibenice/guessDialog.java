package sibenice;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class guessDialog extends JDialog
{
    private final JPanel confirmPanel = new JPanel();
    private final JButton confirm = new JButton("Potvrdit");
    private final JButton cancel = new JButton("Zrušit");
    
    private final JPanel textPanel = new JPanel();
    private final JTextField textField = new JTextField(10);
    private final JLabel guessLabel = new JLabel("Vlož písmeno:");
    
    private boolean confirmed = false;
    
    public guessDialog(Okno okno)
    {
        super(okno, "Hádání", true);
        inicializace();
    }
    
    private void inicializace()
    {
        var display = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(display.width/5, display.height/5);
        this.setLocation(2*display.width/5, 2*display.height/5);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        this.add(textPanel, BorderLayout.NORTH);
        textPanel.add(guessLabel);
        textPanel.add(textField);
        
        this.add(confirmPanel, BorderLayout.SOUTH);
        confirmPanel.add(confirm);
        confirmPanel.add(cancel);
        eventHandlers();
    }
    
    private void eventHandlers()
    {
        confirm.addActionListener(this::pushConfirm);
        cancel.addActionListener(this::pushCancel);
    }
    
    public boolean isConfirmed()
    {
        return confirmed;
    }
    
    
    private void pushConfirm(ActionEvent ae)
    {
        setVisible(false);
        confirmed = true;
    }
    
    private void pushCancel(ActionEvent ae)
    {
        setVisible(false);
        confirmed = false;
    }
    
    public String getText()
    {
        return textField.getText();
    }
    
    public void clearText()
    {
        textField.setText("");
    }
}
