package com.ikariatari.lifesim.response;

import net.dv8tion.jda.api.EmbedBuilder;

public class ImageContent extends Content
{
	public String imageUrl;
	
	public ImageContent(String imageURL)
	{
		this.imageUrl = imageURL;
	}
	
	public void AddContent(EmbedBuilder builder)
	{
		builder.setImage("attachment://" + imageUrl);
	}
}
