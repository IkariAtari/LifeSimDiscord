package com.ikariatari.lifesim.response;

import net.dv8tion.jda.api.EmbedBuilder;

public class FieldContent extends Content
{
	public String title;
	public String content;
	public boolean inline;
	
	public FieldContent(String title, String content, boolean inline)
	{
		this.title = title;
		this.content = content;
		this.inline = inline;
	}
	
	public void AddContent(EmbedBuilder builder)
	{
		builder.addField(title, content, inline);
	}
}
