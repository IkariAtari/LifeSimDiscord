package com.ikariatari.lifesim.session;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.card.Card;
import com.ikariatari.lifesim.card.CardDeck;
import com.ikariatari.lifesim.card.CardRank;
import com.ikariatari.lifesim.card.CardSuit;
import com.ikariatari.lifesim.response.ColorContent;
import com.ikariatari.lifesim.response.FieldContent;
import com.ikariatari.lifesim.response.Response;

public class Pesten extends CardGame implements IDrawCard, ICardPlay, IInput
{
	private CardDeck cardPile = new CardDeck();
	
	private CardRank[] specialRanks = new CardRank[] {
			CardRank.ACE,
			CardRank.EIGHT,
			CardRank.TWO,
			CardRank.SEVEN,
			CardRank.JACK,
			CardRank.JOKER
	};
	
	private CardSuit currentSuit;

	@Override
	public void Start()
	{
		deck = new CardDeck();
		
		deck.PopulatecardsWithJokers();
		
		Collections.shuffle(deck.cards);
		
		// give everyone a hand of cards
		for(int i = 0; i < 5; i++)
		{
			for(CardDeck playerHand: playerHands)
			{
				playerHand.cards.add(deck.DrawCard());
			}
		}
		
		// send message to display hand
		for(int i = 0; i < players.size(); i++)
		{
			SendPlayerHand(i);
		}
		
		// draw one card to the pile as a start TODO: need to check for special cards as you can't start with them
		cardPile.cards.add(deck.DrawCard());
		
		Update();
	}
	
	@Override
	public void Update()
	{
		Response response = new Response(channelID);
		
		String[] card = Card.ResolveCard(cardPile.cards.get(cardPile.cards.size() - 1));
		
		// TODO: exceptinput needs to be implemented differently
		if(!expectInput)
		{
			if(!(cardPile.cards.get(cardPile.cards.size() - 1).rank == CardRank.JACK))
			{
				currentSuit = cardPile.cards.get(cardPile.cards.size() - 1).suit;
			}
		}
		else
		{
			expectInput = false;
		}
		
		response.content.add(new FieldContent(card[0] + "\n" + card[1], "", false));
		response.content.add(new FieldContent("Current Suit", currentSuit.name, false));
		
		String allPlayers = "";
		int i = 0;
		
		for(Player player: players)
		{
			allPlayers += player.Name + " with ID: " + String.valueOf(i);
			
			if(playerHands.get(GetPlayerIdFromName(player.Name)).cards.size() < 2)
			{
				response.content.add(new ColorContent(Color.red));
				allPlayers += " > LastCard";
			}
			
			allPlayers += "\n";
			
			i++;
		}
		
		response.content.add(new FieldContent("Players", allPlayers, false));
		response.Send(players.get(turn).Name + "'s turn | Turn: " + String.valueOf(turns));
	}
	
	public void Win(String playerName)
	{
		Response response = new Response(channelID);
		
		response.SendBasicMessage(playerName + " won the game!");
		
		super.Stop();
	}
	
	private boolean canPlay(String playerName)
	{
		CardDeck _deck = playerHands.get(GetPlayerIdFromName(playerName));
		
		for(Card card: _deck.cards)
		{
			if(CheckPlay(card, cardPile.cards.get(cardPile.cards.size() - 1), playerHands.get(GetPlayerIdFromName(playerName)).cards.size()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isSpecial(Card card)
	{
		for(int i = 0; i < specialRanks.length; i++)
		{
			if(card.rank.equals(specialRanks[i]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean CheckPlay(Card playedCard, Card pileCard, int playerHandSize)
	{
		if(isSpecial(playedCard))
		{
			System.out.println("Played Card is special");
			
			if(playerHandSize > 1)
			{
				if(playedCard.rank == CardRank.JACK || playedCard.rank == CardRank.JOKER)
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		
		if(playedCard.suit == currentSuit)
		{
			return true;
		}
		else if(playedCard.rank == pileCard.rank)
		{
			return true;
		}
		
		return false;
	}
	
	private void EatCards(int amount, String playerName)
	{
		
	}

	@Override
	public void onCardPlay(int cardId, String playerName) 
	{
		if(expectInput)
		{
			return;
		}
		
		if(!isPlayersTurn(playerName))
		{
			// return message that it isn't the player's turn
			return;
		}
		
		CardDeck playerDeck = playerHands.get(GetPlayerIdFromName(playerName));
		
		System.out.println("CardID: " + cardId + " PlayerDeck size: " + String.valueOf(playerDeck.cards.size() - 1));
		
		// check if the player put in a valid number
		if(cardId > playerDeck.cards.size() - 1)
		{
			System.out.println("CardID " + cardId + " is not correct, PlayerDeck size: " + String.valueOf(playerDeck.cards.size() - 1));
			return;
		}
		
		// check if card played is valid
		if(CheckPlay(playerDeck.cards.get(cardId), cardPile.cards.get(cardPile.cards.size() - 1), playerHands.get(GetPlayerIdFromName(playerName)).cards.size()))
		{
			String nextPlayer = FindNextPlayer(playerName, turnType);
			
			int index = GetPlayerIdFromName(nextPlayer);
			
			System.out.println(index);
			
			switch(playerDeck.cards.get(cardId).rank)
			{
				case TWO :	
					playerHands.get(index).cards.add(deck.DrawCard());
					playerHands.get(index).cards.add(deck.DrawCard());
						
					SendPlayerHand(index);
					
				break;
				
				case SEVEN :
					if(turnType.equals("clockwise"))
					{
						turn--;
					}
					if(turnType.equals("counterclockwise"))
					{
						turn++;
					}
				break;
				
				case EIGHT :
					turn = index;
				break;
				
				case JACK :
					Response response = new Response(channelID);
					List<String> inputs = new ArrayList<String>();
					
					inputs.add("diamonds");
					inputs.add("hearts");
					inputs.add("spades");
					inputs.add("clubs");
					
					SetValidInputs(inputs);
					
					response.SendBasicMessage(playerName + " you can change the suit!");
					
					cardPile.cards.add(playerDeck.cards.get(cardId));
					
					playerDeck.cards.remove(cardId);
				return;
		
				case ACE :
					if(turnType.equals("clockwise"))
					{
						turnType = "counterclockwise";
					}
					else if(turnType.equals("counterclockwise"))
					{
						turnType = "clockwise";
					}
				break;
				
				case JOKER :
					playerHands.get(index).cards.add(deck.DrawCard());
					playerHands.get(index).cards.add(deck.DrawCard());
					playerHands.get(index).cards.add(deck.DrawCard());
					playerHands.get(index).cards.add(deck.DrawCard());
					playerHands.get(index).cards.add(deck.DrawCard());
					
					CardSuit[] randomSuits = new CardSuit[] {
							CardSuit.DIAMONDS,
							CardSuit.HEARTS,
							CardSuit.CLUBS,
							CardSuit.SPADES
					};
					
					Random random = new Random();
					
					int randomInt = random.nextInt(3);
					
					currentSuit = randomSuits[randomInt];
						
					SendPlayerHand(index);
				break;
				
				default:
					
				break;
			}
			
			cardPile.cards.add(playerDeck.cards.get(cardId));
			
			playerDeck.cards.remove(cardId);
			
			// player has won
			if(playerDeck.cards.size() <= 0)
			{
				
				System.out.println(playerName + " won!");
				
				Win(playerName);
				
				return;
			}
			
			SendPlayerHand(GetPlayerIdFromName(playerName));
			
			NextTurn();
			Update();
		}
		else
		{
			System.out.println("Something is wrong");
		}
	}

	@Override
	public void onCardDraw(String playerName) 
	{
		if(expectInput)
		{
			return;
		}
		
		if(!isPlayersTurn(playerName))
		{
			// return message that it isn't the player's turn
			return;
		}
		
		if(!canPlay(playerName))
		{
			if(deck.cards.size() > 0)
			{
				// take one card from the deck into the hand
				CardDeck _deck = playerHands.get(GetPlayerIdFromName(playerName));
				
				_deck.cards.add(deck.DrawCard());
				SendPlayerHand(GetPlayerIdFromName(playerName));
				
				// advance turns
				NextTurn();
				Update();
			}
			else
			{
				// shuffle the card pile into the card keeping the last card
				Card lastCard = cardPile.cards.get(cardPile.cards.size() - 1);
				
				cardPile.cards.remove(lastCard);
				
				deck = cardPile;
				Collections.shuffle(deck.cards);
				
				cardPile.cards.add(lastCard);
			}
		}
		else
		{
			// notify the player that he can play
			Response response = new Response(channelID);
			
			response.SendBasicMessage(playerName + " you can play ;)");
		}
	}

	@Override
	public void onUserInput(String input, String playerName) 
	{
		if(!expectInput)
		{
			return;
		}
		
		if(!isPlayersTurn(playerName))
		{
			// return message that it isn't the player's turn
			return;
		}
		
		// TODO: Bad way of doing it, buy hey
		switch(input)
		{
			case "hearts" :
				currentSuit = CardSuit.HEARTS;
			break;
			
			case "spades" :
				currentSuit = CardSuit.SPADES;
			break;
			
			case "clubs" :
				currentSuit = CardSuit.CLUBS;
			break;
			
			case "diamonds" :
				currentSuit = CardSuit.DIAMONDS;
			break;
		}
		
		NextTurn();
		Update();
	}
}