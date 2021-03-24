package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.response.Response;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommandStop extends SessionCommand
{
	@Override
	public String GetCommand() 
	{
		return "stop";
	}
	
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		super.Run(tokens, event);
		
		if(session.host.equals(player.Name))
		{
			session.Stop();
		}
		else
		{
			Response response = new Response(event);
			
			response.SendBasicMessage(player.Name + "You are not the host of the session");
		}
	}
}
