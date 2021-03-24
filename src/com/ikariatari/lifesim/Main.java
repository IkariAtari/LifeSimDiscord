package com.ikariatari.lifesim;

import javax.security.auth.login.LoginException;

import com.ikariatari.lifesim.card.CardDeck;
import com.ikariatari.lifesim.command.Command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main 
{
	public static final String TOKEN = "";
	public static Words words;
	public static CardDeck deck = new CardDeck();
	public static JDA jda;
	
	public static void main(String[] args) throws LoginException
	{
		jda = JDABuilder.createDefault(TOKEN).build();
		jda.addEventListener(new Listener());
		
		Command.Init();
		
		words = new Words();
		
		//Runnable wordRunnable = new Runnable() { public void run() { words.NewWord(); }};
		
		//ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		//exec.scheduleAtFixedRate(wordRunnable, 0, 10, TimeUnit.MILLISECONDS);
	}
}
