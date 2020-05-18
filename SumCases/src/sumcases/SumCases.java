package sumcases;

/**
 *
 * @author Aicha nesrine
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class SumCases {


    private final static Logger LOGGER = Logger.getLogger(SumCases.class.getName());
    private static ArrayList<Integer> negativeNumbers = new ArrayList<>();
    private static boolean negativeExists = false;

    private static int sumCalcul(String[] numbersArray) {

        int theSum =0;
        for(int i = 0; i < numbersArray.length; i++){
            //Parsing to Integers
            int aNumber = Integer.parseInt(numbersArray[i]);
            //Checking conditions and adding to the sum
            if (aNumber > 0 && aNumber <= 1000) {
                theSum += aNumber;
            } else if (aNumber < 0){
                negativeExists = true;
                negativeNumbers.add(aNumber);
            }
        }
        return theSum;
    }

    /**
     * @param str delimited string
     * @return sum of the values of str
     */
    private static int add(String str) throws Exception {

        int theSum = 0;

        //Empty string returns the value 0
        if (str.trim().isEmpty()) {
            return theSum;
        }

        if(str.startsWith("//")){
            String[] splittedString = str.split("\n");
            String delimiter =  splittedString[0].replace("//","") ;
            if (delimiter.matches("\\[\\s*\\]")) {
                int index = delimiter.lastIndexOf("]");
                delimiter = delimiter.substring(0, index);
                delimiter = delimiter.replaceFirst("\\[", "");
            }



            //if delimiter contains ","
            if (!delimiter.matches("\\s*,\\s*")) {
                String tempoStr = splittedString[1].replaceFirst("\n", "");
                //Splitting the string and putting the values in an Array
                String[] numbersArray = tempoStr.split("\\s*,\\s*|\\s*" + delimiter +"\\s*");
                theSum = sumCalcul(numbersArray);
                if (negativeExists) {
                    String negativeNumbersStr = "";
                    for (Integer number : negativeNumbers){
                        negativeNumbersStr += number + " , ";
                    }
                    int index = negativeNumbersStr.lastIndexOf(",");
                    negativeNumbersStr = negativeNumbersStr.substring(0, index);
                    throw new Exception(negativeNumbersStr + " is a negative number");
                }
                negativeExists = false;
                negativeNumbers.clear();

                return theSum;
            }
            //if delimiter contains "\n"
            else if (!delimiter.matches("\\r?\\n")) {
                //Splitting the string and putting the values in an Array
                String[] numbersArray = splittedString[1].split("\\r?\\n|\\s*" + delimiter + "\\s*");
                theSum = sumCalcul(numbersArray);
                if (negativeExists) {
                    String negativeNumbersStr = "";
                    for (Integer number : negativeNumbers){
                        negativeNumbersStr += number + " , ";
                    }
                    int index = negativeNumbersStr.lastIndexOf(",");
                    negativeNumbersStr = negativeNumbersStr.substring(0, index);
                    throw new Exception(negativeNumbersStr + " is a negative number");
                }
                negativeExists = false;
                negativeNumbers.clear();

                return theSum;
            }
            // if delimiter contains neither
            else {
                String tempoStr = splittedString[1].replaceFirst("\n", "");
                //Splitting the string and putting the values in an Array
                String[] numbersArray = tempoStr.split(delimiter);
                theSum = sumCalcul(numbersArray);
                if (negativeExists) {
                    String negativeNumbersStr = "";
                    for (Integer number : negativeNumbers){
                        negativeNumbersStr += number + " , ";
                    }
                    int index = negativeNumbersStr.lastIndexOf(",");
                    negativeNumbersStr = negativeNumbersStr.substring(0, index);
                    throw new Exception(negativeNumbersStr + " is a negative number");
                }
                negativeExists = false;
                negativeNumbers.clear();

                return theSum;
            }
        }

        ArrayList<Integer> intArrayList = new ArrayList<>();

        //Splitting the string and putting the values in an Array
        String[] numbersArray = str.split("\\s*,\\s*|\\r?\\n");

        //Calculating the sum
        theSum = sumCalcul(numbersArray);
        if (negativeExists) {
            String negativeNumbersStr = "";
            for (Integer number : negativeNumbers){
                negativeNumbersStr += number + " , ";
            }
            int index = negativeNumbersStr.lastIndexOf(",");
            negativeNumbersStr = negativeNumbersStr.substring(0, index);
            throw new Exception(negativeNumbersStr + " is a negative number");
        }
        negativeExists = false;
        negativeNumbers.clear();

        return theSum;
    }


    @Test
    public void emptyStringShouldReturnZero() {
        try {
            Assert.assertEquals(0, add(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void singleNumberReturnsTheValue() {
        try {
            Assert.assertEquals(13, add("13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void commaDelimited() {
        try {
            Assert.assertEquals(20, add("7,13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newLineDelimited() {
        try {
            Assert.assertEquals(32, add("13\n19"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threeNumbersEitherWay() {
        try {
            Assert.assertEquals(35, add("5,20\n10"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ignoringNegativeNumbers() {
        try {
            Assert.assertEquals(-1, add("5,20,-5,-20,1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ignoringNumbersGreaterThan1000() {
        try {
            Assert.assertEquals(36, add("5,20\n10\n1530\n1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void stringWithDelimiters() {
        try {
            Assert.assertEquals(30, add("//[###]\n20###10"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {}
}
