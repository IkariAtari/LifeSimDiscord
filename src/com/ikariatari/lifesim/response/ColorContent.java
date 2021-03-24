package com.ikariatari.lifesim.response;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;

public class ColorContent extends Content
{
	Color color;
	
	public ColorContent(Color color)
	{
		this.color = color;
	}
	
	public void AddContent(EmbedBuilder builder)
	{
		builder.setColor(color);
	}
}
