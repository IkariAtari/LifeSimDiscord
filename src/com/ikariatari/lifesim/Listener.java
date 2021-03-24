package com.ikariatari.lifesim;

import com.ikariatari.lifesim.command.Command;
import com.ikariatari.lifesim.response.FieldContent;
import com.ikariatari.lifesim.response.Response;
import com.ikariatari.lifesim.session.IInput;
import com.ikariatari.lifesim.session.Session;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter
{
	public static final String COMMAND_PREFIX = ">";
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if(event.getAuthor().isBot())
		{
			return;
		}
		
		String content = event.getMessage().getContentRaw();
		
		Player player = Database.GetUser(event.getAuthor().getName(), event.getAuthor().getId());
		
		if(content.contains(">"))
		{
			String[] CommandTokens = ParseCommand(content);
			String CommandIndentifier = CommandTokens[0];
			
			if(Command.commands.containsKey(CommandIndentifier))
			{
					Command.commands.get(CommandIndentifier).Run(CommandTokens, event);
			}
			else
			{
				Response response = new Response(event);
					
				response.content.add(new FieldContent("", "Incorrect command", false));
					
				response.Send("Errort");
			}
			
			
			
			/*switch(CommandIndentifier)
			{
				case "p" :
					response.content.add(new FieldContent("Balance", String.valueOf(player.Money)));
					response.content.add(new FieldContent("Experience", String.valueOf(player.XP)));
					
					response.Send("Stats");
				break;
				case "gm" :
					player.Money += 100;
					
					Database.UpdatePlayer(player);
					
					response.content.add(new FieldContent("Balance", String.valueOf(player.Money)));
					response.content.add(new FieldContent("Experience", String.valueOf(player.XP)));
					response.Send("Stats");
				break;
				case "lozeput" :
					Random random = new Random();
					
					float multiplier = (float)random.nextInt(200) / 100;
					
					player.Money *= multiplier;
					
					Database.UpdatePlayer(player);
					
					DecimalFormat decimalFormat = new DecimalFormat("#.##");
					
					String[][] messageContent = new String[][] {{"You rolled multiplier", String.valueOf(multiplier)}, {"Your Money", String.valueOf(decimalFormat.format(player.Money) + "$")}};
					
					response.content.add(new FieldContent("Balance", String.valueOf(player.Money)));
					response.content.add(new FieldContent("Your Money", String.valueOf(decimalFormat.format(player.Money) + "$")));
					
					response.Send("Lozeput");
				break;
				case "words" :
					List<String> words = Main.words.GetWords();
					float value = Main.words.ReturnPrice();
					
					String[][] wordsContent = new String[3][2];
						
					String wordsString = "";
					for(String word: words)
					{
						wordsString += word + "\n";
					}
					
					response.content.add(new FieldContent("Current Words", wordsString));
					response.content.add(new FieldContent("Value", String.valueOf(value) + "$"));
					response.content.add(new FieldContent(player.Name + "s amount", String.valueOf(player.WordCoins) + " = " + String.valueOf(value * player.WordCoins) + "$"));
					
					response.Send("Words");
				break;
				
				case "wbuy" :
					int amount = Integer.parseInt(CommandTokens[1]);
					
					System.out.println(amount);
					
					if(player.Money - amount > 0)
					{
						player.WordCoins += amount / Main.words.ReturnPrice();
						player.Money -= amount;
					}
					else
					{
						return;
					}
					
					Database.UpdatePlayer(player);
				break;
				case "wsell" :
					int sellAmount = Integer.parseInt(CommandTokens[1]);
					
					if(player.WordCoins - sellAmount > 0)
					{
						player.Money += sellAmount * Main.words.ReturnPrice();
						player.WordCoins -= sellAmount;
					}
					else
					{
						return;
					}
					
					Database.UpdatePlayer(player);
				break;
				case "refreshdeck" :
					Main.deck.cards.clear();
					Main.deck.Populatecards();
					
				break;
				case "shuffle" :
					Collections.shuffle(Main.deck.cards);
				break;
				case "drawcard" :
					//int amount = Integer.parseInt(CommandTokens[1]);
					
					Card drawnCard = Main.deck.DrawCard();
					
					String[] cardEmotes = new String[2];
					
					if(drawnCard != null)
					{
						cardEmotes = Card.ResolveCard(drawnCard);
					}
					else
					{
						cardEmotes[0] = "The deck is empty";
						cardEmotes[1] = "";
					}
					
					response.SendBasicMessage(cardEmotes[0] + "\n" + cardEmotes[1]);
				break;
			}*/
		}
		else
		{
			// If the player is in a session a word response can be send here, although this doesn't check whether it's the right time to do so
			if(Session.IsPlayerInSession(player))
			{
				Session session = Session.FindSessionFromPlayer(player);
				
				if(session.expectInput)
				{
					if(session.isValidInput(content))
					{
						IInput input = (IInput) session;
						
						input.onUserInput(content.trim(), player.Name);
						
						return;
					}
					else
					{
						Response response = new Response(event);
						
						String validInputs = "";
						
						for(String input: session.GetValidInputs())
						{
							validInputs += input + "\n";
						}
						
						response.SendBasicMessage("That doesn't look like a valid input :/ \nChoose one the following: \n" + validInputs);
						
						return;
					}
				}
			}
		}
	}
	
	private String[] ParseCommand(String command)
	{
		String sterializedCommand = command.split(">")[1];
		
		return sterializedCommand.split(" ");
	}
}
