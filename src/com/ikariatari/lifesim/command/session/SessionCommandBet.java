package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.session.IBettable;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommandBet extends SessionCommand
{
	@Override
	public String GetCommand() 
	{
		return "bet";
	}
	
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		super.Run(tokens, event);
		
		if(session instanceof IBettable)
		{
			IBettable bettable = (IBettable)session;
			
			bettable.Bet(Float.parseFloat(tokens[1]));
		}
		else
		{
			// Bet cannot be used in this type of session
		}
	}
}
