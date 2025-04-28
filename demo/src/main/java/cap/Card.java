package cap;

import java.io.Serializable;

public class Card implements Serializable 
{
    private String suit;  
    private String rank;  

    public Card(String suit, String rank) 
    {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() 
    {
        return suit;
    }

    public String getRank() 
    {
        return rank;
    }
    
    public int getValue() 
    {
        switch (rank) 
        {
            case "Ace": 
                return 11;
                
            case "King":
            	
            case "Queen":
            	
            case "Jack":            	
                return 10;
            default:
                return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() 
    {
        return rank + " of " + suit;
    }
}