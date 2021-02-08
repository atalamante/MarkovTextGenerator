/**
 * Name: Aaron Talamante
 * Date: April 18, 2020
 * Class: CS251L-002
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NGram {
    /**
     * This method takes the book that was scanned in and turns it into array.
     * It is turned into an array by splitting by space. This means that it splits
     * every word. Punctuation is removed and replaced with nothing. Multiple new lines
     * are replaced with one space. I check to see if the file is "sonnet.txt" because
     * my loop starts different for that file. The rest of this method is just looping through
     * the arrays, adding them to a list if they meet the criteria, then adding to another list to remove
     * the space, and finally returning the last list as an array.
     * @param s This is the scanned in book that is in one long string.
     * @param filename The name says it all. This is the file name so "hamlet-noHeader.txt" or "sonnet.txt".
     * @return A string array that holds each word in the book as one individual element in the array.
     */
    public static String[] stringToArray (String s, String filename) {
        String newInput = s.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("\n+", " ");
        String[] words = newInput.replaceFirst("", "").split(" ", -1);
        List<String> fixed = new ArrayList<>();
        List<String> spaceFix = new ArrayList<>();
        int y = 0;
        int holder = 1;

        if (Character.isLetter(newInput.charAt(0))) {
            holder = 0;
        }
        for (int x = 0; x < words.length; x++) {
            if ((words[x] != null) && (words[x] != "")){
                fixed.add(words[x]);
            }
        }
        for (y = holder; y < fixed.size(); y++) {
            if ((fixed.get(y).length() > 0)) {
                spaceFix.add(fixed.get(y));
            }
        }
        return spaceFix.toArray(new String[0]);
    }

    /**
     * This method is used for small individual strings. The main difference between this method
     * and stringToArray is that the second for loop starts at 0 rather than 1. It does nearly the exact same
     * thing as stringToArray, but starts at 0. This is for when I use need to split up a string of 2 or 3 words
     * into an array. If I start at 1 in the last method, it would skip the first word. That is why this method
     * is needed.
     * @param small The string that will be turned into an array.
     * @return A string array with each word in the string being an individual element in the array.
     */
    public static String[] smallUseString (String small) {
        String[] words = small.replaceFirst("", "").split(" ", -1);
        List<String> fixed = new ArrayList<>();
        List<String> spaceFix = new ArrayList<>();
        for (int x = 0; x < words.length; x++) {
            if ((words[x] != null) && (words[x] != "")){
                fixed.add(words[x]);
            }
        }
        for (int y = 0; y < fixed.size(); y++) {
            if ((fixed.get(y).length() > 0)) {
                spaceFix.add(fixed.get(y));
            }
        }
        return spaceFix.toArray(new String[0]);
    }


    /**
     * makeHash is one of the most important methods in this project. This method, as the
     * name says, makes the HashMap which is vital to the project. It loops through the entire
     * book and for each loop, it loops over the order size. For n = 2, it loops over 2 words at a time.
     * For n = 3, it loops through 3 words at a time. It puts together a string of these words and
     * makes the HashMap with the return value of findNextWords in TransitionRule. As stated in the
     * TransitionRule class, findNextWords uses the string made in the for loop, and finds the words following
     * it with the same n size.
     * @param bookOriginal This is the book as an array. This will be the array from stringToArray.
     * @param value The n size so 1,2,3,etc. This parameter creates the size of the strings and
     *              the size of the string for the words that follow
     * @return This methods returns a LinkedHashMap with the keys being a String and the values being
     *          a List of Strings.
     */
    public static LinkedHashMap makeHash (String[] bookOriginal, int value) {

        LinkedHashMap<String, List<String>> myMap = new LinkedHashMap<>();
        String empty = "";
        int placeHolder = 0;
        int holder = value;
        int subtractLength = value -1;
        int editedValue = value - subtractLength;
        if (value == 1){
            editedValue = value;
        }
        for (int x = 0; x < bookOriginal.length; x+=editedValue) {
            for (int y = placeHolder; y < holder; y++) {
                if ((y + 1) > bookOriginal.length) {
                    break;
                }
                empty += bookOriginal[y] + " ";
            }
            String newEmpty = empty.trim();
            myMap.put(newEmpty, TransitionRule.findNextWords(newEmpty, value, bookOriginal));       //Changing all the += value to +=(value - 1).
            empty = "";
            placeHolder += editedValue;
            holder += editedValue;
        }
        int count = 0;
//        for (Map.Entry<String, List<String>> entry : myMap.entrySet()) {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
        return myMap;

    }


    /**
     * This main is just used for testing. Nothing is ran through main in this class, but the methods will be called
     * @param args This main has 3 arguments. The first being the order size (1,2,3,etc.).
     *      *             The second being the limit(how many words of output).
     *      *             The third being the text file(Hamlet, Moby Dick, Sonnets, etc.)
     * @throws FileNotFoundException This is thrown when a file can not be found.
     */
    public static void main (String[] args) throws FileNotFoundException {

        if (args.length != 3) {
            System.out.println("Incorrect Number of Parameters!");
        } else {
            System.out.println(args.length + " command line arguments:");
            for (String x : args) {
                System.out.println(" " + x);
            }
            int nsize = Integer.parseInt(args[0]);
            int limit = Integer.parseInt(args[1]);
            String filename = args[2];
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            String fileContent = "";
            while (scan.hasNext()) {
                fileContent = fileContent.concat(scan.next() + "\n");

            }
            String[] book = stringToArray(fileContent, filename);
        }

    }


}
