package cap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck 
{
    private List<Card> cards;

    public Deck() 
    {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) 
        {
            for (String rank : ranks) 
            {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public void shuffle() 
    {
        Collections.shuffle(cards);
    }

    public Card deal() 
    {
        return cards.remove(0);
    }
}