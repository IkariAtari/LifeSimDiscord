package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.Database;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.command.Command;
import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.Session;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SessionCommand extends Command
{
	public Session session;
	protected Player player;
	
	@Override
	public String GetCommand() 
	{
		return null;
	}

	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		if(!Session.IsPlayerInSession(player))
		{
			Response response = new Response(event);
			
			response.SendBasicMessage("You're not in a session");
			
			return;
		}
		
		session = Session.FindSessionFromPlayer(player);
	}

}
