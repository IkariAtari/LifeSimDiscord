package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.Database;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.command.Command;
import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.Session;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandJoinSession extends Command
{

	@Override
	public String GetCommand() 
	{
		return "join";
	}

	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		Response response = new Response(event);
		Player player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		if(Session.GetSessionByName(tokens[1]) != null)
		{
			Session session = Session.GetSessionByName(tokens[1]);
			
			if(session.JoinSession(player))
			{
				response.SendBasicMessage("Joined session: " + tokens[1]);
			}
		}
		else
		{
			response.SendBasicMessage("could not find session");
		}
	}
}