package com.ikariatari.lifesim.card;

public enum CardSuit
{
	CLUBS("<:aclubs:817159364364075028>", "Clubs"),
	SPADES("<:aspades:817159516336029756>", "Spades"),
	DIAMONDS("<:adiamonds:817159370659463181>", "Diamonds"),
	HEARTS("<:ahearts:817159378151014471>", "Hearts");
	
	public String emote;
	public String name;
	
	private CardSuit(String emote, String name)
	{
		this.emote = emote;
		this.name = name;
	}
}
