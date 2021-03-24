package com.ikariatari.lifesim.command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command 
{
	public abstract String GetCommand();
	
	public abstract void Run(String[] tokens, MessageReceivedEvent event);
	
	// This may change in the future
	public static HashMap<String, Command> commands = new HashMap<>();
	
	public static void Init()
	{
		Reflections reflections = new Reflections("com.ikariatari.lifesim.command");
		
		Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
		
		for(Class<? extends Command> command : commands)
		{
			Command commandInstance;
			
			try 
			{
				commandInstance = command.getConstructor().newInstance();
				
				Command.commands.put(commandInstance.GetCommand(), commandInstance);
				
				System.out.println(commandInstance.GetCommand());
			}
			catch (InstantiationException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalArgumentException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InvocationTargetException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (NoSuchMethodException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (SecurityException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
