package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.IBettable;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommandStart extends SessionCommand
{
	@Override
	public String GetCommand() 
	{
		return "start";
	}
	
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		super.Run(tokens, event);
		
		if(session.host.equals(player.Name))
		{
			session.Start();
		}
		else
		{
			Response response = new Response(event);
			
			response.SendBasicMessage(player.Name + "You are not the host of the session");
		}
	}
}
