package com.ikariatari.lifesim;

import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Words 
{
	public static final File WORDS = new File("C:/LifeSim/Words/Words.txt");
	public static final File CURRENT_DICTIONARY = new File("C:/LifeSim/Words/CurrentWords.txt");
	public static int PlaySize = 20;
	public static float currentPrice = 0;
	
	public List<String> AllWords = new ArrayList<String>();
	
	public Words()
	{
		Init();
	}
	
	public void Init()
	{
		Scanner allWordsScanner;
		
		try 
		{
			allWordsScanner = new Scanner(WORDS);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}
		
		while(allWordsScanner.hasNextLine())
		{
			AllWords.add(allWordsScanner.nextLine());
		}
		
		Scanner currentWordsScanner;
		
		try 
		{
			currentWordsScanner = new Scanner(CURRENT_DICTIONARY);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}
		
		if(currentWordsScanner.hasNextLine())
		{
			return;
		}
		else
		{
			// Initialize new words list
			String[] currentWords = new String[Words.PlaySize];
			
			for(int i = 0; i < Words.PlaySize; i++)
			{
				Random random = new Random();
				
				int index = random.nextInt(AllWords.size());
				
				currentWords[i] = AllWords.get(index);
			}
			
			SetWordList(currentWords);
		}
	}
	
	private void SetWordList(String[] currentWords)
	{
		FileWriter writer;
		
		try 
		{
			writer = new FileWriter(CURRENT_DICTIONARY);
		
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}
		
	
		for(String word: currentWords)
		{
			try 
			{
				writer.write(word);
				writer.write("\n");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return;
			}
		}
		
		try 
		{
			writer.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return;
		}
	}
	
	public String NewWord()
	{
		Random random = new Random();
		
		int index = random.nextInt(AllWords.size());
		
		String newWord = AllWords.get(index);
		
		List<String> words = GetWords();
	
		for(int i = 0; i < words.size() - 1; i++)
		{
			words.set(i, words.get(i + 1));
		}
		
		words.set(words.size() - 1, newWord);

		SetWordList(words.toArray(new String[words.size()]));
		
		UpdatePrice();
		
		return newWord;
	}
	
	public List<String> GetWords()
	{
		Scanner scanner;
		
		try 
		{
			scanner = new Scanner(CURRENT_DICTIONARY);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
		List<String> words = new ArrayList<String>();
		
		while(scanner.hasNextLine())
		{
			words.add(scanner.nextLine());
		}
		
		return words;
	}
	
	public void UpdatePrice()
	{
		List<String> words = GetWords();
		
		float average = 0;
		
		for(String word: words)
		{
			average += (float)word.length();
		}
		
		average /= words.size();
		
		float difference = (float)words.get(words.size() - 1).length() - words.get(words.size() - 2).length();
		
		/*if(difference < 0)
		{
			difference /= 1.03;
		}*/
		
		currentPrice += difference * average / 10;
		
		System.out.println(currentPrice + " " + average);
		
		if(currentPrice < 0)
		{
			currentPrice = 0;
		}
	}
	
	public float ReturnPrice()
	{
		return currentPrice;
	}
}
