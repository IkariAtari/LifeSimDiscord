package com.ikariatari.lifesim.card;

import java.util.ArrayList;
import java.util.List;

public class CardDeck
{
	public List<Card> cards = new ArrayList<Card>();
	
	public Card DrawCard()
	{
		if(cards.size() > 0)
		{
			Card card = cards.get(cards.size() - 1);
			
			cards.remove(cards.size() - 1);
			
			return card;
		}
		else
		{
			return null;
		}	
	}
	
	public void Populatecards()
	{
		// Don't know how to do it otherwise, maybe I will find a method later
		cards.add(new Card(CardSuit.CLUBS, CardRank.ACE));
		cards.add(new Card(CardSuit.CLUBS, CardRank.TWO));
		cards.add(new Card(CardSuit.CLUBS, CardRank.THREE));
		cards.add(new Card(CardSuit.CLUBS, CardRank.FOUR));
		cards.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
		cards.add(new Card(CardSuit.CLUBS, CardRank.SIX));
		cards.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
		cards.add(new Card(CardSuit.CLUBS, CardRank.EIGHT));
		cards.add(new Card(CardSuit.CLUBS, CardRank.NINE));
		cards.add(new Card(CardSuit.CLUBS, CardRank.TEN));
		cards.add(new Card(CardSuit.CLUBS, CardRank.JACK));
		cards.add(new Card(CardSuit.CLUBS, CardRank.QUEEN));
		cards.add(new Card(CardSuit.CLUBS, CardRank.KING));
		
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.ACE));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.TWO));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.THREE));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.FOUR));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.SIX));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.SEVEN));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.NINE));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.TEN));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.JACK));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.QUEEN));
		cards.add(new Card(CardSuit.DIAMONDS, CardRank.KING));
		
		cards.add(new Card(CardSuit.HEARTS, CardRank.ACE));
		cards.add(new Card(CardSuit.HEARTS, CardRank.TWO));
		cards.add(new Card(CardSuit.HEARTS, CardRank.THREE));
		cards.add(new Card(CardSuit.HEARTS, CardRank.FOUR));
		cards.add(new Card(CardSuit.HEARTS, CardRank.FIVE));
		cards.add(new Card(CardSuit.HEARTS, CardRank.SIX));
		cards.add(new Card(CardSuit.HEARTS, CardRank.SEVEN));
		cards.add(new Card(CardSuit.HEARTS, CardRank.EIGHT));
		cards.add(new Card(CardSuit.HEARTS, CardRank.NINE));
		cards.add(new Card(CardSuit.HEARTS, CardRank.TEN));
		cards.add(new Card(CardSuit.HEARTS, CardRank.JACK));
		cards.add(new Card(CardSuit.HEARTS, CardRank.QUEEN));
		cards.add(new Card(CardSuit.HEARTS, CardRank.KING));
		
		cards.add(new Card(CardSuit.SPADES, CardRank.ACE));
		cards.add(new Card(CardSuit.SPADES, CardRank.TWO));
		cards.add(new Card(CardSuit.SPADES, CardRank.THREE));
		cards.add(new Card(CardSuit.SPADES, CardRank.FOUR));
		cards.add(new Card(CardSuit.SPADES, CardRank.FIVE));
		cards.add(new Card(CardSuit.SPADES, CardRank.SIX));
		cards.add(new Card(CardSuit.SPADES, CardRank.SEVEN));
		cards.add(new Card(CardSuit.SPADES, CardRank.EIGHT));
		cards.add(new Card(CardSuit.SPADES, CardRank.NINE));
		cards.add(new Card(CardSuit.SPADES, CardRank.TEN));
		cards.add(new Card(CardSuit.SPADES, CardRank.JACK));
		cards.add(new Card(CardSuit.SPADES, CardRank.QUEEN));
		cards.add(new Card(CardSuit.SPADES, CardRank.KING));
	}
	
	public void PopulatecardsWithJokers()
	{
		Populatecards();
		
		cards.add(new Card(CardSuit.CLUBS, CardRank.JOKER));
		cards.add(new Card(CardSuit.CLUBS, CardRank.JOKER));
	}
}
