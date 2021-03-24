package com.ikariatari.lifesim.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ikariatari.lifesim.Main;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.card.Card;
import com.ikariatari.lifesim.card.CardDeck;
import com.ikariatari.lifesim.response.FieldContent;
import com.ikariatari.lifesim.response.Response;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class CardGame extends Session
{
	protected CardDeck deck;
	
	protected List<CardDeck> playerHands = new ArrayList<CardDeck>();
	
	protected int turn = 0;
	
	protected int turns = 0;
	
	protected String turnType = "clockwise";
	
	@Override
	public boolean JoinSession(Player player)
	{
		if(super.JoinSession(player))
		{
			playerHands.add(new CardDeck());
	
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void Start()
	{
		deck = new CardDeck();
		
		deck.Populatecards();
		
		Collections.shuffle(deck.cards);
	}
	
	public void NextTurn()
	{
		if(turnType.equals("clockwise"))
		{
			turn++;
		}
		else if(turnType.equals("counterclockwise"))
		{
			turn--;
		}
		
		if(turn > players.size() - 1)
		{
			turn = 0;
		}
		if(turn < 0)
		{
			turn = players.size() - 1;
		}
		
		turns++;
	}
	
	protected boolean isPlayersTurn(String playerName)
	{
		if(players.get(GetPlayerIdFromName(playerName)).Name.equals(players.get(turn).Name))
		{
			return true;
		}
		
		return false;
	}
	
	protected void SendPlayerHand(int user)
	{
		String userID = players.get(user).UserID;
		User userObject = Main.jda.retrieveUserById(userID).complete();
		//List<Message> messages = channel.getHistory().retrievePast(10).complete();
		
		userObject.openPrivateChannel().queue(channel -> {
			
			//channel.purgeMessages(messages);
			
			Response response = new Response(channel);
			
			String topCards = "";
			String bottomCards = "";
			String numbers = "";
			
			for(Card card: playerHands.get(user).cards)
			{
				String[] cardEmotes = Card.ResolveCard(card);

				topCards += cardEmotes[0] + " ";
				bottomCards += cardEmotes[1] + " ";
				numbers += playerHands.get(user).cards.indexOf(card) + 1 + "   ";
				
			}
			
			response.content.add(new FieldContent("", topCards + "\n" + bottomCards + "\n" + numbers, false));
			
			response.Send("Your hand");
		});
	}
}