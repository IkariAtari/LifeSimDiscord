package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.Database;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.session.IDrawCard;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommandDrawCard extends SessionCommand
{
	public String GetCommand() 
	{
		return "dc";
	}
	
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		super.Run(tokens, event);
		
		Player player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		if(session instanceof IDrawCard)
		{
			IDrawCard cardPlay = (IDrawCard) session;
			
			cardPlay.onCardDraw(player.Name);
		}
	}
}
