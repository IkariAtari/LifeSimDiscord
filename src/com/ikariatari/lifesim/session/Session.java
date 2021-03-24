package com.ikariatari.lifesim.session;

import java.util.ArrayList;
import java.util.List;

import com.ikariatari.lifesim.Player;
import com.ikariatari.lifesim.response.Response;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Session 
{
	public String name;
	
	public String channelID;
	
	public List<Player> players = new ArrayList<Player>();
	
	public String host = "";
	
	private List<String> validInputs = new ArrayList<String>();
	
	public boolean expectInput;
	
	private static List<Session> sessions = new ArrayList<Session>();
	
	public static Session GetSessionByName(String name)
	{
		for(Session session: Session.sessions)
		{
			System.out.println(session.name);
			
			if(session.name.equals(name))
			{
				return session;
			}
		}
		
		return null;
	}
	
	public static boolean IsPlayerInSession(Player player)
	{
		for(Session session: Session.sessions)
		{
			for(Player _player: session.players)
			{
				if(player.Name.equals(_player.Name))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Session FindSessionFromPlayer(Player player)
	{
		for(Session session: Session.sessions)
		{
			for(Player _player: session.players)
			{
				if(player.Name.equals(_player.Name))
				{
					return session;
				}
			}
		}
		
		return null;
	}
	
	public static List<Session> GetAllSessions()
	{
		return sessions;
	}
	
	public static boolean CreateSession(String name, String channelID, String type)
	{
		// If there isn't already a session with the same name
		if(Session.GetSessionByName(name) == null)
		{
			Session newSession = new Session();
			
			switch(type)
			{
				case "pesten" :
					newSession = new Pesten();
				break;
			}
			
			newSession.name = name;
			newSession.channelID = channelID;
			
			Session.sessions.add(newSession);
			
			for(Session session: Session.sessions)
			{
				System.out.println(session.name);
			}
			
			return true;
		}
		
		return false;
	}
	
	public String FindNextPlayer(String playerName, String orientation)
	{
		int index = GetPlayerIdFromName(playerName);
		
		if(orientation.equals("clockwise"))
		{
			if(index + 1 <= players.size() - 1)
			{
				return players.get(index + 1).Name;
			}
			else
			{
				return players.get(0).Name;
			}
		}
		
		if(orientation.equals("counterclockwise"))
		{
			if(index - 1 >= 0)
			{
				return players.get(index - 1).Name;
			}
			else
			{
				return players.get(players.size() - 1).Name;
			}
		}
		
		return null;
	}
	
	protected int GetPlayerIdFromName(String name)
	{
		for(Player player: players)
		{
			if(player.Name.equals(name))
			{
				return players.indexOf(player);
			}
		}
		
		return -1;
	}
	
	public void SetValidInputs(List<String> validInputs)
	{
		this.validInputs = validInputs;
		
		expectInput = true;
	}
	
	public List<String> GetValidInputs()
	{
		return validInputs;
	}
	
	public boolean isHost(String playerName)
	{
		if(playerName.equals(host))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isValidInput(String input)
	{
		for(String inputString: validInputs)
		{
			if(inputString.equals(input))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean JoinSession(Player player)
	{
		// Check if the player already exists
		for(Player _player: players)
		{
			if(_player.Name.equals(player.Name))
			{
				return false;	
			}
		}
		
		System.out.println("Hallo");
		
		if(players.size() < 1 || players == null)
		{
			host = player.Name;
			
			Response response = new Response(channelID);
			
			response.SendBasicMessage(player.Name + " is the host of session " + name);
		}
		
		players.add(player);
	
		return true;
	}
	
	public void Start() { }
	
	public void Update() { }
	
	public void Stop()
	{
		Response response = new Response(channelID);
		
		response.SendBasicMessage("Session " + name + " has stopped!");
		
		Session.sessions.remove(this);
	}
}
