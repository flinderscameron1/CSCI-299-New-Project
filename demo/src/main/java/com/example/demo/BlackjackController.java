package com.example.demo;

import cap.Blackjack;
import cap.Card;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blackjack")
public class BlackjackController 
{
    private static Blackjack game = new Blackjack(); 

    @PostMapping("/start")
    public Map<String, Object> startGame() 
    {
        Map<String, Object> response = new HashMap<>();

        if (!game.canPlay()) 
        {
            response.put("error", "You have no points left to play!");
            return response;
        }

        game.startGame(); 

        response.put("playerHand", formatHand(game.getPlayerHand()));
        response.put("dealerHand", formatHand(game.getDealerHand().subList(1, game.getDealerHand().size())));
        response.put("points", Blackjack.getPoints());

        return response;
    }

    @PostMapping("/hit")
    public Map<String, Object> playerHits() 
    {
        game.playerHit();

        Map<String, Object> response = new HashMap<>();
        int playerValue = game.calculatePlayerHandValue();

        if (playerValue > 21) 
        {
            response.put("result", "Player busts! Dealer wins!");
            game.updatePoints("Dealer wins!");
        } 
        else 
        {
            response.put("playerHand", formatHand(game.getPlayerHand()));
            response.put("playerValue", playerValue);
        }

        response.put("points", Blackjack.getPoints());
        return response;
    }

    @PostMapping("/stay")
    public Map<String, Object> playerStays() 
    {
        while (game.calculateDealerHandValue() < 17) 
        {
            game.dealerHit();
        }

        int dealerValue = game.calculateDealerHandValue();
        int playerValue = game.calculatePlayerHandValue();
        String result = determineWinner(playerValue, dealerValue);

        System.out.println("Result: " + result);
        System.out.println("Bet before updatePoints: " + Blackjack.getPoints());
        game.updatePoints(result);
        System.out.println("Updated points: " + Blackjack.getPoints());

        Map<String, Object> response = new HashMap<>();
        response.put("dealerHand", formatHand(game.getDealerHand()));
        response.put("dealerValue", dealerValue);
        response.put("result", result);
        response.put("points", Blackjack.getPoints());

        return response;
    }

    @PostMapping("/bet")
    public Map<String, Object> placeBet(@RequestBody Map<String, Integer> request) {
        int bet = request.get("bet");

        Map<String, Object> response = new HashMap<>();
        if (!game.canPlay()) 
        {
            response.put("error", "You have no points left to play!");
        } 
        else if (bet > Blackjack.getPoints()) 
        {
            response.put("error", "You cannot bet more points than you have!");
        } 
        else 
        {
            game.setBet(bet);
            response.put("bet", bet);
            response.put("points", Blackjack.getPoints());
        }
        return response;
    }

    @GetMapping("/points")
    public Map<String, Object> getPoints() 
    {
        Map<String, Object> response = new HashMap<>();
        response.put("points", Blackjack.getPoints());
        return response;
    }

    private List<String> formatHand(List<Card> hand) 
    {
        List<String> formattedHand = new ArrayList<>();
        for (Card card : hand) 
        {
            formattedHand.add(card.toString());
        }
        return formattedHand;
    }

    private String determineWinner(int playerValue, int dealerValue) 
    {
        if (dealerValue > 21 || playerValue > dealerValue) 
        {
            return "Player wins!";
        } 
        else if (playerValue == dealerValue) 
        {
            return "It's a tie!";
        }
        return "Dealer wins!";
    }
}