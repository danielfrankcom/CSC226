

/*

Rahnuma Islam Nishat - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class  KMP{

    private static String pattern;
    private static int[][] dfs;

    public static int search(String txt){

        // declarations
        int numCharsText = txt.length();
        int numCharsPattern = dfs[0].length;
        int pointer = 0;

        // loop through search text
        for(int i = 0; i < numCharsText ; i++){

            // if char at current index is next in pattern
            if((int) txt.charAt(i) == dfs[0][pointer]){
                pointer ++;  // increase location to look in pattern
            }else{
                // set location to look in pattern as dictated by DFS
                pointer = dfs[1][pointer];
            }

            // if entire pattern has been found
            if(pointer == numCharsPattern){
                return i - numCharsPattern + 1;  // return index of beginning of pattern
            }

        }

        return numCharsText;  // return length of text as pattern not found

    }

    public KMP(String pattern){

        // declarations
        int numChars = pattern.length();
        int[] characters = new int[numChars];
        int[] locations = new int[numChars];

        // populate array with char representations
        for(int i = 0; i < numChars; i++){
            characters[i] = (int) pattern.charAt(i);  // integer representation of each char
        }

        // populate array with locations if char does not match
        for(int i = 0; i < numChars; i++){

            // declare iterating string, starting at chars up until current index
            // start with highest value match, so as to keep location pointer high
            String tempStr = pattern.substring(0, i);
            int tempStrLen = i - 1;

            while(tempStrLen > 0){  // while potential match in previous string

                // check iterating string against pattern of same length
                // starting immediately before the current index
                if(pattern.substring(i - tempStrLen, i).equals(tempStr)){
                    locations[i] = tempStrLen;  // store location pointer if pattern found
                }

                // decrease string in size until only the beginning chars
                // in the pattern are reached
                tempStrLen -= 1;
                tempStr = tempStr.substring(0, tempStrLen);

            }

        }

        dfs = new int[][]{characters, locations};  // set public dfs for use

    }


  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}

			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}

			//System.out.println(text);

		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}


	}
}
