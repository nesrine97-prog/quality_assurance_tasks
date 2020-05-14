package passwordverifier;

/**
 *
 * @author Aicha nesrine
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class PasswordVerifier
{
        /**
	 * This function checks if there are spaces in the password.
	 * It also checks if the password matches the required minimum of 8 characters.
	 */
        @Test
	public static int lengthChecker(String s)
	{
		if (s.contains(" "))
		{
			return 0;
		}
		else 
		{
			if (s.length() >= 8)
				return 1;
			else
				return 0;
		}	
	}
	
	/**
	 * This function checks if the password entered has a special character in it.
	 * I made sure that a space does not count as a special character.
	 */
	public static int specialCharacter(String s)
	{
		/**
		 * The regex pattern I created for special characters is basically any 
		 * character that is not an alphabetical letter, a number, or a space.
		 */
		Pattern spChar = Pattern.compile("[^A-Za-z0-9 ]");
		Matcher m = spChar.matcher(s);
		if (m.find())
			return 1;
		else 
			return 0;
		
	}	
	
	/**
	 * This function uses a regex expression [A-Z] that checks to see if there is an 
	 * uppercase letter in the password.
	 */
	public static int upperCase(String s)
	{
		Pattern upper = Pattern.compile("[A-Z]");
		Matcher m = upper.matcher(s);
		if (m.find())
			return 1;
		else 
			return 0;
	}
	
	/**
	 * This function uses a regex expression [a-z] that checks to see if there is a 
	 * lowercase letter in the password.
	 */
	public static int lowerCase(String s)
	{
		Pattern lower = Pattern.compile("[a-z]");
		Matcher m = lower.matcher(s);
		if (m.find())
			return 1;
		else
			return 0;
	}
	
	/**
	 * This function uses a regex expression [0-9] that checks to see if there 
	 * is a number in the password.
	 */
	public static int digitize(String s)
	{
		Pattern digit = Pattern.compile("[0-9]");
		Matcher m = digit.matcher(s);
		if (m.find())
			return 1;
		else
			return 0;
	}
	
	/** 
	* Calls on functions to validate each rule
	* if the password followed the rule, the function would return 1
	 * if not, the function would return 0.
	 */
        
	
	
	/**
	 * This is the main function that calls on the frame that we created to display a user-
	 * friendly window to the user.
	 */
	public static void main( String [] args )
	{
	Scanner in = new Scanner(System.in);
        String password = in.nextLine();
	int length, spChar, lowCase, upCase, digit;
	length = lengthChecker(password);
	spChar = specialCharacter(password);
	upCase = upperCase(password);
	lowCase = lowerCase(password);
	digit = digitize(password);
			
			//Initiate pStrength, and add 1 or 0 depending on what the function returned.
			//the pStrength is the strength out of 5 for the password. 
			int pStrength = 0;
			pStrength = length + spChar + upCase + lowCase + digit;
			
	
			System.out.println("Your password has a strength of: " + pStrength
				+ " out of 5.");
			
			//Define various outcomes for different strengths.
			if (pStrength == 5)
			{ System.out.println("Congratulations, you are a password pro!"); }

			else if (pStrength == 0 )
			{ System.out.println("Are you serious?! you violated every single rule!"); }
			else
			{
				System.out.println("Your password violated the following rule(s):\n");
				
				/**
				 * These rules will appened to the list of brokenRules if any of the functions
				 * returned 0. making it possible for multiple rules to be broken, while also 
				 * allowing any possible combination of broken rules.
				 */
				if (length == 0)
				{
					System.out.println("  Rule1: It must have at least 8 characters, "
						+ "and it must not contain any space character.\n");
				}
				if (spChar == 0)
				{
					System.out.println("  Rule2: It must contain at least one special character, "
						+ "which is not a letter or digit.\n");
				}
				if (upCase == 0)
					System.out.println("  Rule3: It must contain at least one upper-case letter.\n");
				if (lowCase == 0)
					System.out.println("  Rule4: It must contain at least one lower-case letter.\n");
				if (digit == 0)
					System.out.println("  Rule5: It must contain at least one digit.\n");
			}	
	}
	
	/** 
	 * It should be noted that all of the following functions will return a 1 or a
	 * 0 depending on if the password followed the rule or not. 
	 * If the password followed the rule, a 1 will be returned adding to the strength
	 * of the password.
	 * If the password failed to fulfill a rule, a 0 will be returned, not adding
	 * any strength to the password. 
	 */
}
