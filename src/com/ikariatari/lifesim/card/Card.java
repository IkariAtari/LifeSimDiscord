package com.ikariatari.lifesim.card;

public class Card 
{
	public CardSuit suit;
	public CardRank rank;
	
	public Card(CardSuit suit, CardRank rank)
	{
		this.suit = suit;
		this.rank = rank;
	}

	public static String[] ResolveCard(Card card)
	{
		String[] cardEmotes = new String[2];
		
		if(card.rank == CardRank.JOKER)
		{
			cardEmotes[0] = card.rank.blackEmote;
			cardEmotes[1] = card.rank.redEmote;
			
			return cardEmotes;
		}
		
		if(card.suit == CardSuit.CLUBS || card.suit == CardSuit.SPADES)
		{
			cardEmotes[0] = card.rank.blackEmote;
			cardEmotes[1] = card.suit.emote;
		}
		else if(card.suit == CardSuit.DIAMONDS || card.suit == CardSuit.HEARTS)
		{
			cardEmotes[0] = card.rank.redEmote;
			cardEmotes[1] = card.suit.emote;
		}
		
		return cardEmotes;
	}
}
