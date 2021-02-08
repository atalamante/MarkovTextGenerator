/**
 * Name: Aaron Talamante
 * Date: April 18, 2020
 * Class: CS251L-002
 */
import java.util.*;

public class TransitionRule {
    /**
     * This method is used to find the possible transitions. It looks ahea
     * @param sample This is the string that is trying to be found in the text along with finding
     *               the transitions of.
     * @param ahead This is how much we are looking ahead. This is the same as the order from the
     *              command line.
     * @param originalText This is the array of the book that was made from stringToArray in NGram.
     * @return A List<String> that holds all the possible transitions for the sample string.
     */
    public static List<String> findNextWords(String sample, int ahead, String[] originalText) {
        String[] sampleArr = NGram.smallUseString(sample);
        String[] arr = originalText;
        boolean flag = false;
        String empty = "";
        int index;
        int next;
        String secondCheck = "";
        List<String> myList = new LinkedList<String>();
        int subtractLength = (ahead - 1);
        if (ahead == 1) {
            return findTransitionForOne(sample, ahead, originalText);
        }
        for (int x = 0; x < arr.length; x++) {   //Look at arr.length -1?
            if ((sampleArr.length > 0) && (sampleArr.length == ahead)) {
                if (arr[x].equals(sampleArr[0])) {
                    secondCheck += arr[x] + " ";
                }
                for (next = 1; next < ahead; next++) {
                    if ((x+next) >= arr.length) {
                        break;
                    } else if (arr[x + next].equals(sampleArr[next])) {
                        flag = true;
                        secondCheck += arr[x + next] + " ";
                    }
                }
                String newSecond = secondCheck.trim();
                if (flag && newSecond.equals(sample)) {
                    index = x + (ahead-subtractLength);
                    for (int check = 0; check < ahead; check++) {
                        if (index > (arr.length -1)) {
                            break;
                        } else {
                            empty += arr[index] + " ";
                            index++;
                        }
                    }
                    String newEmpty = empty.trim();
                    myList.add(newEmpty);
                    empty = "";
                }
            }
            secondCheck = "";
        }
        return myList;
    }

    /**
     * This method checks if the possible transitions for each key in the HashMap is actually
     * possible. For example, there are times where the transitions include a transition that doesn't have a key.
     * This method checks to see if each transition has a key. If it does, it is kept. If it doesn't, it is removed. If
     * the transition doesn't have a key, then it is useless because during the output, it won't have a transition to
     * follow it.
     * @param map This is the LinkedHashMap that was made in makeHash. This would initially be the final HashMap used in the
     *            output, but this method returns a new, edited LinkedHashMap.
     * @return A new LinkedHashMap that only includes transitions that have a key.
     */
    public static LinkedHashMap checkTransitionUsage (LinkedHashMap map, int increment) {
        Set setKeys = map.keySet();
        Object[] objArr = setKeys.toArray();
        String[] arrayKeys = new String[objArr.length];
        for (int index = 0; index < objArr.length; index++) {
            arrayKeys[index] = String.valueOf(objArr[index]);
        }
        Collection values = map.values();
        Object[] objValues = values.toArray();

        String[] arrayValues = new String[objValues.length];

        for (int x = 0; x < objValues.length; x++) {
            arrayValues[x] = String.valueOf(objValues[x]);
        }
        boolean flag = false;
        LinkedHashMap<String, List<String>> myMap = new LinkedHashMap<String, List<String>>();
        String check = "";
        for (int index = 0; index < arrayValues.length; index++) {     //Changing valuesArr to arrayValues for all!!!
            if (arrayValues[index].contains(",")) {
                String[] newArray = arrayValues[index].split(",");
                String[] returnArray = new String[newArray.length];
                for (int x = 0; x < newArray.length; x++) {
                    check = newArray[x].replace("[", "").replace("]", "").trim();
                    if ((checkLengthOfValue(check, increment)) || increment == 1){
                        if (map.containsKey(check)) {
                            returnArray[x] = newArray[x].replace("[", "").replace("]", "").trim();
                        }
                    }
                }
                int range = 0;
                for (int removeNull = 0; removeNull < returnArray.length; removeNull++) {
                    if (returnArray[removeNull] != null) {
                        range++;
                    }
                }
                String[] noNull = new String[range];
                int indexHolder = 0;
                for (int transfer = 0; transfer < returnArray.length; transfer++) {
                    if (returnArray[transfer] != null) {
                        noNull[indexHolder] = returnArray[transfer];
                        indexHolder++;
                    }
                }
                    List<String> linkList = new LinkedList<String>(Arrays.asList(noNull));
                    myMap.put((arrayKeys[index]), linkList);
                } else {
                check = arrayValues[index].replace("[", "").replace("]", "").trim();
                String[] returnArray = new String[1];
                if ((checkLengthOfValue(check, increment)) || increment ==1) {
                    if ((map.containsKey(check))) {
                        returnArray[0] = check;
                        List<String> linkList = new LinkedList<String>(Arrays.asList(returnArray));
                        myMap.put((arrayKeys[index]), linkList);
                    }
                } else {
                    returnArray[0] = "";
                }
            }
        }
//        for (Map.Entry<String, List<String>> entry : myMap.entrySet()) {
//            System.out.println(entry.getKey()+" : "+entry.getValue());
//        }
        return myMap;
    }

    public static boolean checkLengthOfValue (String s, int increment) {
        if (increment > 1) {
            return s.contains(" ");
        }
        return false;
    }

    /**
     * This method is used when the n size is 1. The n size is the first command line argument. This is a
     * separate method because an n of 1 is putting the down the word ahead so it is much simpler.
     * @param sample The string that is trying to be found along with finding the transition for it. In this case,
     *               the string is just 1 word.
     * @param ahead This is how much we are looking ahead. This is the same as the order from the
     *              command line.
     * @param originalText This is the array of the book that was made from stringToArray in NGram.
     * @return A List<String> that holds the transition for that sample string.
     */
    public static List<String> findTransitionForOne (String sample, int ahead, String[] originalText) {
        List<String> myList = new LinkedList<String>();
        String[] sampleArr = NGram.smallUseString(sample);
        String[] arr = originalText;

        for (int x = 0; x < arr.length-1; x++) {
            if (arr[x].equals(sampleArr[0])) {
                myList.add(arr[x+1]);
            }
        }
        return myList;
    }

    /**
     * This is the method that is for all the output. It takes all the keys and turns them into an array. It takes all
     * the values and turns them into an array. For my output, I start with a random first word/words. Then, I find the
     * next word/words by using randomness for the list of values. To find the randomness, I call my method randomWord.
     * Once the next word/words is found, it is put into an array. Then, I put each word in one by one to make sure it
     * doesn't go over the limit. Using my find nextIndex method, I find the index of the just added string so I
     * can find the transitions for it the next go around.
     * @param words This is the LinkedHashMap that has been made with all the keys and corresponding values. This HashMap
     *              has been put through checkTransitionUsage.
     * @param limit Limit is how many words of output there can be. This is the second command line argument.
     * @param increment This is the n size or order size. This is the first command line argument.
     * @return A string of text that is the length of the limit. This text will appear normal but be totally gibberish.
     */
    public static String formSentences(LinkedHashMap words, int limit, int increment) {
        Set keys = words.keySet();
        Object[] objArr = keys.toArray();
        String[] keysArr = (new String[objArr.length]);
        for (int index = 0; index < objArr.length; index++) {
            keysArr[index] = String.valueOf(objArr[index]);
        }
        Collection values = words.values();
        Object[] objArray = values.toArray();
        String[] valuesArray = new String[objArray.length];
        for (int x = 0; x < objArray.length; x++) {
            valuesArray[x] = String.valueOf(objArray[x]);
        }
        boolean result = checkLastValue(valuesArray);
        int randomStart;
        if (result) {
            randomStart = (int) (Math.random() * (keysArr.length-1));
        } else {
            randomStart = (int) (Math.random() * keysArr.length);
        }
        String[] checkLength = NGram.smallUseString(keysArr[randomStart]);
        String rv = keysArr[randomStart] + " ";
        if ((valuesArray[randomStart].equals("[]")) || (checkLength.length != increment)) {
            randomStart = (int) (Math.random() * keysArr.length);
            if (rv.trim().equals(keysArr[randomStart])) {
                randomStart = (int) (Math.random() * keysArr.length);
            }
            rv = keysArr[randomStart] + " ";
        }
        int index = randomStart;
        int count = increment;
        int increase = 1;
        boolean check = false;
        for (int x = 0; x < limit; x++) {
            String next = randomWord(valuesArray, index).replace("[", "").replace("]", "").trim();   //Changing valuesArr to valuesArray
            String[] nextCheck = NGram.smallUseString(next);
            if (next.equals("[]") || (nextCheck.length != increment)) {
                int newRandom; //(int)(Math.random()*keysArr.length);
                do {
                    if (result) {
                        newRandom = (int)(Math.random()*keysArr.length-1);
                    } else {
                        newRandom = (int) (Math.random() * keysArr.length);
                    }
                    next = randomWord(valuesArray, newRandom).replace("[", "").replace("]", "").trim();
                    check = true;
                }
                while ((newRandom == index) || (next.equals("[]")) || (nextCheck.length == increment));

            }
            String newNext = next.replace("[", "").replace("]", "").replace(",", "").trim();
            String[] newNextArray = NGram.smallUseString(newNext);
            increase = (increment - 1);
            if (check) {
                increase = 0;
            }

            for (int word = increase; word < increment; word++) {
                if ((count < limit) && ((newNextArray[word] != null))) {
                    rv += newNextArray[word] + " ";
                    count++;
                }
            }
            if ((("["+next+"]").equals(valuesArray[valuesArray.length-1])) && (!words.containsKey(next))) {
                index = (int)(Math.random()*keysArr.length);
                increase = 0;
            } else {
                index = findIndex(keysArr, next);
                increase = 1;
            }
            if (((increment == 1 || increment == 2) && (count % 10 == 0)) || ((increment == 3) && (count % 9 == 0))) {
                rv += "\n";
            }
            if (count >= limit) {
                break;
            }
            check = false;
        }
        return rv;
    }

    /**
     * This method is solely used when called by the findNextWords method. It looks through the
     * entire array of keys and tries to find the word/words that have been passed in. When it finds them,
     * it returns the value of x. This value is the index.
     * @param keys This is an array of all the keys from the LinkedHashMap.
     * @param words This is is the word/words that we are trying to find the index of.
     * @return An int which represents the index of the words in the keys array.
     */
    public static int findIndex (String[] keys, String words) {
        for (int x = 0; x < keys.length; x++) {
            if (keys[x].equals(words)) {
                return x;
            }
        }
        return 0;
    }

    /**
     * This method finds the random transition that will be used. It loops through all the values and when x equals
     * the index, it stores all the values into one array. It then splits up the array into more than one element by
     * splitting at ",". Next, I count how many elements there are. This is the range. Finally, I use Math.random() and
     * multiply it by the range to find the random index. Then the word/words at that random index in the array of
     * values for this transition is the word that will be returned and added to the output.
     * @param values An array of strings of all the values from the LinkedHashMap.
     * @param index The index of the values that we are trying to find the transition for.
     * @return A string which is added to the output. In the next go around, the code will find the transition for this
     * string.
     */
    public static String randomWord (String[] values, int index) {
        String[] change = new String[values.length];
        for (int x = 0; x < values.length; x++) {
            if (x == index) {
                change[0] = values[x];
                break;
            }
        }
        String[] newChange = change[0].split(",");
        int range = 0;
        for (int each = 0; each < newChange.length; each++) {
            if (newChange[each] != null) {
                range++;
            } else {
                break;
            }
        }
        if (range == 1) {
            return newChange[0];
        } else {
            int randomIndex = (int) (Math.random() * range);
            if (randomIndex < 0) {
                randomIndex =0;
            }
            return newChange[randomIndex];
        }
    }


    public static boolean checkLastValue (String[] s) {
        if (s[s.length-1].equals("[]")) {
            return true;
        }
        return false;
    }

    /**
     * Nothing is really ran through TransitionRule but rather the methods are called from the main class for the
     * project, MarkovTextGenerator. This is more of test code in this main method. I was practicing on
     * "Mary had a little lamb.."
     * @param args Command-line arguments are ignored for this file.
     */
    public static void main (String[] args) {
        String example = "Mary had a little lamb, little lamb, little lamb. Mary had a little lamb whose fleece was white as snow. " +
                "Everywhere that Mary went, Mary went, Mary went, everywhere that Mary went, the lamb was sure to go.";
        String[] words = NGram.stringToArray(example,"sonnet.txt");
        LinkedHashMap thisMap;
        thisMap = NGram.makeHash(words,2);
        LinkedHashMap usageMap;
        usageMap = checkTransitionUsage(thisMap, 2);
        System.out.println(thisMap);
        System.out.println(usageMap);

    }

}
