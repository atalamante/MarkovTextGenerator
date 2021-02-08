/**
 * Name: Aaron Talamante
 * Date: April 18, 2020
 * Class: CS251L-002
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MarkovTextGenerator {
    /**
     * The main class of the project. This class takes in the command line arguments
     * and stores them properly. Using a scanner, I read in the file and make a string
     * variable to hold it all. Then, I put it into a string array through the use
     * of the NGram class. After, I call NGram and TransitionRule accordingly. The output
     * is a certain amount of text.
     * @param args There should be 3 arguments. The first being the order size (1,2,3,etc.).
     *             The second being the limit(how many words of output).
     *             The third being the text file(Hamlet, Moby Dick, Sonnets, etc.)
     * @throws FileNotFoundException Used when reading in the file. If the file is not found,
     *                               throws an exception.
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 3) {
            System.out.println("Incorrect Number of Parameters!");
        } else {
            System.out.println(args.length + " command line arguments:");
            for (String x : args) {
                System.out.println(x);
            }
            int nGram = Integer.parseInt(args[0]);
            int wordLimit = Integer.parseInt(args[1]);
            String filename = args[2];
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            String fileContent = "";
            while (scan.hasNext()) {
                fileContent = fileContent.concat(scan.next() + "\n");

            }
            String[] book = NGram.stringToArray(fileContent, filename);
            LinkedHashMap thisMap;
            LinkedHashMap newMap;
            thisMap = NGram.makeHash(book, nGram);
            newMap = TransitionRule.checkTransitionUsage(thisMap, nGram);
            if (thisMap.isEmpty()) {
                System.out.println("Try using a different NGram size! This size doesn't have any values! ");
            } else {
                String test = TransitionRule.formSentences(newMap, wordLimit, nGram);
                System.out.println(test);
            }
        }
    }

}
