package com.ikariatari.lifesim.command.session;

import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.command.Command;
import com.ikariatari.lifesim.response.FieldContent;
import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.Session;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandViewSession extends Command
{

	@Override
	public String GetCommand() 
	{
		return "viewsession";
	}

	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{
		Response response = new Response(event);
		
		if(Session.GetSessionByName(tokens[1]) != null)
		{
			Session session = Session.GetSessionByName(tokens[1]);
			
			String playerList = "";
			
			for(Player sessionPlayer: session.players)
			{
				playerList += sessionPlayer.Name + "\n";
			}
			
			response.content.add(new FieldContent("Players", playerList, false));
			
			response.Send(session.name);
		}
		else
		{
			response.SendBasicMessage("could not find session");
		}
	}
}