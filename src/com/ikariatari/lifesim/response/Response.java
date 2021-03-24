package com.ikariatari.lifesim.response;

import java.util.ArrayList;
import java.util.List;

import com.ikariatari.lifesim.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite.Channel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Response 
{
	private MessageReceivedEvent event;
	
	public List<Content> content = new ArrayList<Content>();
			
	private EmbedBuilder embedBuilder = new EmbedBuilder();
	
	private MessageChannel channel;
	
	public Response(MessageReceivedEvent event)
	{
		this.channel = event.getChannel();
		embedBuilder = new EmbedBuilder();
	}
	
	public Response(String channelID)
	{
		channel = (MessageChannel) Main.jda.getGuildChannelById(channelID);
	}
	
	public Response(MessageChannel channel)
	{
		this.channel = channel;
	}
	
	public void Send(String title)
	{
		embedBuilder.setTitle(title);
		
 		for(int i = 0; i < content.size(); i++)
		{
 			content.get(i).AddContent(embedBuilder);
		}
		
		channel.sendMessage(embedBuilder.build()).queue();
	}
	
	public void SendBasicMessage(String message)
	{
		channel.sendMessage(message).queue();
	}
}