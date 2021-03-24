package com.ikariatari.lifesim;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Database 
{
	private static Database INSTANCE;
	
	public static final String DATABASE_FOLDER = "C:/LifeSim/Database";
	public static final String DATABASE_USER_EXTENSION = ".pbj";
	
	public static Database getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new Database();
		}
		
		return INSTANCE;
	}
	
	public static boolean UserExists(String user)
	{
		File folder = new File(DATABASE_FOLDER);
		File[] users = folder.listFiles();
		
		for(int i = 0; i < users.length; i++)
		{
			if(users[i].isFile())
			{
				if(users[i].getName().split(Pattern.quote("."))[0].equals(user))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Player GetUser(String user, String userID)
	{
		if(UserExists(user))
		{
			return GetPlayerFromFile(user);
		}
		else
		{
			try 
			{
				if(AddNewUser(user, userID))
				{
					
				}
				else
				{
				
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Adding new user " + user);
			
			return GetPlayerFromFile(user);
		}
	}
	
	public static boolean AddNewUser(String user, String userID) throws IOException
	{
		File userFile = new File(DATABASE_FOLDER + "/" + user + DATABASE_USER_EXTENSION);
		
		try 
		{
			if(userFile.createNewFile())
			{
				System.out.println("Successfully created file");
			}
			else
			{
				System.out.println("Can't create file");
				
				return false;
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			
			return false;
		}
		
		FileWriter writer = new FileWriter(userFile);
		
		writer.write("userid:" + userID);
		writer.write("\n");
		writer.write("money:0");
		writer.write("\n");
		writer.write("xp:0");
		writer.write("\n");
		writer.write("wordcoins:0");
		
		writer.close();
		
		return true;
		
	}
	
	private static List<String> ParseTokens(String user)
	{
		File playerFile = new File(DATABASE_FOLDER + "/" + user + DATABASE_USER_EXTENSION);
		Scanner scanner;
		
		try 
		{
			scanner = new Scanner(playerFile);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
		List<String> tokens = new ArrayList<String>();
		
		while(scanner.hasNextLine())
		{
			tokens.add(scanner.nextLine());
		}
		
		scanner.close();
		
		return tokens;
		
	}
	
	private static Player GetPlayerFromFile(String user)
	{
		List<String> tokens;
	
		tokens = ParseTokens(user);
		
		Player player = new Player();
		
		for(String token : tokens)
		{
			switch(token.split(":")[0])
			{
				case "money" :
					player.Money = Float.parseFloat(token.split(":")[1]);
				break;
				case "level" :
					player.XP = Integer.parseInt(token.split(":")[1]);
				break;
				case "wordcoins" :
					player.WordCoins = Float.parseFloat(token.split(":")[1]);
				break;
				case "userid" :
					player.UserID = token.split(":")[1];
				break;
			}
		}
		
		player.Name = user;
		
		return player;
	}
	
	public static void UpdatePlayer(Player player)
	{
		List<String> tokens = ParseTokens(player.Name);
		
		File playerFile = new File(DATABASE_FOLDER + "/" + player.Name + DATABASE_USER_EXTENSION);
		
		FileWriter writer;
		
		try 
		{
			writer = new FileWriter(playerFile);
			
			writer.write("userid:" + player.UserID);
			writer.write("\n");
			writer.write("money:" + player.Money);
			writer.write("\n");
			writer.write("xp:" + player.XP);
			writer.write("\n");
			writer.write("wordcoins:" + player.WordCoins);
			
			writer.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}	
	}
}