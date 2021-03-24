package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.Database;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.session.ICardPlay;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommandPlayCard extends SessionCommand
{
	@Override
	public String GetCommand() 
	{
		return "pc";
	}
	
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		super.Run(tokens, event);
		
		Player player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		if(session instanceof ICardPlay)
		{
			ICardPlay cardPlay = (ICardPlay) session;
			
			cardPlay.onCardPlay(Integer.parseInt(tokens[1]) - 1, player.Name);
		}
	}
}
