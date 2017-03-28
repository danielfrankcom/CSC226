

/*

Rahnuma Islam Nishat - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP{
    private static String pattern;


    public KMP(String pattern){
        DFS(pattern);
    }
    public static int search(String txt){
    	return 0;
    }

    public int[][] DFS(String pattern){

        int numChars = pattern.length();
        int[] characters = new int[numChars];
        for(int i = 0; i < numChars; i++){
            characters[i] = (int) pattern.charAt(i);
        }

        int[] locations = new int[numChars];
        for(int i = 0; i < numChars; i++){
            String tempStr = pattern.substring(0, i);
            int tempStrLen = i;
            while(tempStrLen > 0){
                System.out.println(tempStr);
                tempStrLen -= 1;
                tempStr = tempStr.substring(0, tempStrLen);
            }
            System.out.println();

        }

        return null;

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
