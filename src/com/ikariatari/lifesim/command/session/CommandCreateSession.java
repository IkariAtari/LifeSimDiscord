package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.command.Command;
import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.Session;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandCreateSession extends Command
{

	@Override
	public String GetCommand() 
	{	
		return "createsession";
	}

	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		String sessionName = tokens[1];
		
		Response response = new Response(event);
		
		if(Session.CreateSession(sessionName, event.getChannel().getId(), tokens[2]))
		{
			response.SendBasicMessage("Session created with name: " + sessionName);
		}
		else
		{
			response.SendBasicMessage("could not create session");
		}
	}

}