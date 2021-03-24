package com.ikariatari.lifesim.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.*;

public class CommandTest extends Command 
{
	@Override
	public String GetCommand() 
	{
		return "test";
	}

	@Override
	public void Run(String[] tokens, MessageReceivedEvent event)
	{
		System.out.println(event.getMember().getId());
		System.out.println(event.getMember().getUser().getId());
		
		event.getMember().getUser().openPrivateChannel().queue(channel -> {
			channel.sendMessage("hoi").queue();
		});
	}
}
