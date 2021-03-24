package com.ikariatari.lifesim.command;

import com.ikariatari.lifesim.Database;
import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.response.FieldContent;
import com.ikariatari.lifesim.response.Response;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandProfile extends Command
{
	@Override
	public void Run(String[] tokens, MessageReceivedEvent event) 
	{ 
		Response response = new Response(event);
		
		Player player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		Database.UpdatePlayer(player);
		
		response.content.add(new FieldContent("Stats", "Balance: $" + player.Money + "\n" + "Experience: " + player.XP, false));
		response.Send(player.Name);
	}

	@Override
	public String GetCommand() 
	{
		return "profile";
	}
}
