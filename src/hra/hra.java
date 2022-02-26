package hra;


import java.sql.*;

public class hra 
{
    private String lastWord;
    private String word;
    private String guessedWord = "";
    private int lengthOfWord;
    
    public hra(String lastWord)
    {
        this.lastWord = lastWord;
        //this.word = uploadFromDatabase();
        word = uploadFromDatabase();
        if(!word.equals(""))
        {
            lengthOfWord = this.word.length();
       
        
        
            for(int i = 0; i < lengthOfWord; i++)
            {
                guessedWord += "*";
            }
        }
        
    }
    
   
    
    public void setGuessedWord(String guessedWord)
    {
        this.guessedWord = guessedWord;
    }
    
    public String getWord()
    {
        return word;
    }
    
    public String getGuessedWord()
    {
        return guessedWord;
    }
    
    public boolean check(String entered)
    {
        entered = entered.toLowerCase();
        String pomWord = this.word;
        pomWord = pomWord.toLowerCase();
        boolean match = false;
        int help = entered.length();
        if(help == 0)
        {
            throw new ArithmeticException("Nezadal jsi žádné písmeno");
        }
        if(help != lengthOfWord && help != 1)
        {
            throw new ArithmeticException("Zadán špatný počet znaků");
        }
        if(entered.equals(pomWord))
        {
            match = true;
            guessedWord = word;
        }
        else
        {
            char[] helpWord = guessedWord.toCharArray();
            for(int i = 0; i < lengthOfWord; i++)
            {
                if(entered.charAt(0) == pomWord.charAt(i))
                {
                    match = true;
                    helpWord[i] = entered.charAt(0);
                    if(i == 0)
                    {
                        helpWord[i] = Character.toUpperCase(helpWord[i]);
                    }
                }
            }
            guessedWord = String.valueOf(helpWord);
        }
        return match;
    }
    
    public boolean rightGuessed()
    {
        return word.equals(guessedWord);
    }
    
    private String uploadFromDatabase() 
    {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javaSibenice", "root", "Nerous253"))
        {
            //System.out.println("Connected");
            String slovo = "";
        
            String sql = "SELECT * FROM slova ORDER BY RAND() LIMIT 1";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            result.next();
            slovo = result.getString("slovo");
            
            //System.out.println(id + " " + slovo);
            //word = slovo;
            
            conn.close();
            return slovo;
        }
        catch(SQLException ex)
        {
            //System.out.println("Nic"); 
            ex.printStackTrace();
            
        }
        return "";
    }
    
}
