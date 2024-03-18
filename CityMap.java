import java.util.Arrays;
import java.util.Scanner;

/*
 * Kyle Huynh
 * 501250558
 */

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3
    String parts[] = getParts(address);
    String homeNumber = parts[0];
    String nth = parts[1];
    String addressType = parts[2].toLowerCase();

    if(parts.length != 3)
    {
      return false;
    }

    if(homeNumber.length() > 2 || !allDigits(homeNumber))
    {
      return false;
    }

    if(!addressType.equalsIgnoreCase("avenue") && !addressType.equalsIgnoreCase(("street")))
    {
      return false;
    }

    //Verify if the nth part of the address is "1st", "2nd", "3rd", then after it ends with "th", like "4th", "5th" up to "9th".
    if(!nth.equalsIgnoreCase("1st") && !nth.equalsIgnoreCase("2nd") && !nth.equalsIgnoreCase("3rd")
      && !nth.equalsIgnoreCase("nth") && !(nth.charAt(0) >= '4' && nth.charAt(0) <= '9')  )
      {
        return false;
      }

    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    // Fill in the code
  
      if (address == null || address.trim().isEmpty()) {
          return block;
      }
  
      //Split the address into parts
      String[] parts = address.split("\\s+");
      if (parts.length != 3) {
          return block;
      }
  
      // Validate and parse the home number
      String homePart = parts[0];
      if (!homePart.matches("\\d{2}")) {
          return block;
      }
      int homeNumber = Integer.parseInt(homePart);
      int blockNumber = homeNumber / 10;
  
      // Parse the street or avenue number, ensuring only the numeric part is parsed
      String streetAvenuePart = parts[1].replaceAll("[^0-9]", "");
      if (streetAvenuePart.isEmpty()) {
          return block;
      }

      int streetOrAvenueNumber = Integer.parseInt(streetAvenuePart);
  
      //Assign the correct block based on whether the address is an avenue or street
      String addressType = parts[2].toLowerCase();
      if (addressType.startsWith("ave")) {
          block[0] = streetOrAvenueNumber;
          block[1] = blockNumber;
      } else if (addressType.startsWith("st")) {
          block[0] = blockNumber;
          block[1] = streetOrAvenueNumber;
      }
  
      return block;
  }

  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    int[] fromBlock = getCityBlock(from);
    int[] toBlock = getCityBlock(to);

    // Ensure both addresses are valid, Invalid address then return -1
    if(fromBlock[0] == -1 || fromBlock[1] == -1 || toBlock[0] == -1 || toBlock[1] == -1)
    {
      return -1;
    }

    int distanceDiff = Math.abs(fromBlock[0] - toBlock[0]) + Math.abs(fromBlock[1] - toBlock[1]);

  
    return distanceDiff;

    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
    
    //double doubleRandomNumber = Math.random() * 17;

    // cast the double to whole number
    //int randomNumber = (int)doubleRandomNumber;
    //return (randomNumber);
  }
}
