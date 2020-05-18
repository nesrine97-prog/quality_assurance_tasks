package passwordverifier;

/**
 *
 * @author Aicha nesrine
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordVerifier {

    public static ArrayList<String> errors;

    /**
     * This function checks if the password is empty
     */
    public static int isPasswordNotEmpty(String s) throws Exception {
        if (s.trim().isEmpty()) {
            throw new Exception("Password should not be empty");
        } else
            return 1;
    }

    /**
     * This function checks if the password
     * matches the required minimum of 8 characters.
     */
    public static int lengthChecker(String s) throws Exception {
        if (s.length() >= 8)
            return 1;
        else
            throw new Exception("Password length should be more than 8");
    }

    /**
     * This function uses a regex expression [A-Z]
     * that checks to see if there is an
     * uppercase letter in the password.
     */
    public static int upperCase(String s) throws Exception {
        Pattern upper = Pattern.compile("[A-Z]");
        Matcher m = upper.matcher(s);
        if (m.find())
            return 1;
        else
            throw new Exception("Password should contain at least one uppercase letter");
    }

    /**
     * This function uses a regex expression [a-z]
     * that checks to see if there is a
     * lowercase letter in the password.
     */
    public static int lowerCase(String s) throws Exception {
        Pattern lower = Pattern.compile("[a-z]");
        Matcher m = lower.matcher(s);
        if (m.find())
            return 1;
        else
            throw new Exception("Password should contain at least one lowercase letter");
    }

    /**
     * This function uses a regex expression [0-9]
     * that checks to see if there
     * is a number in the password.
     */
    public static int digitize(String s) throws Exception {
        Pattern digit = Pattern.compile("[0-9]");
        Matcher m = digit.matcher(s);
        if (m.find())
            return 1;
        else
            throw new Exception("Password should contain at least one digit");
    }

    public static boolean verify(String password) {

        try {
            return (lowerCase(password) == 1 && isPasswordNotEmpty(password) + lengthChecker(password)
                    + upperCase(password) + digitize(password) >= 2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    @Test
    public final void passwordShouldBeAtLeast8Chars() {
        try {
            Assert.assertEquals(1, lengthChecker("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void passwordMustNotBeNull() {
        try {
            Assert.assertEquals(1, isPasswordNotEmpty("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void passwordHaveOneUpperAtLeast() {
        try {
            Assert.assertEquals(1, upperCase("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void passwordHaveOneLowerAtLeast() {
        try {
            Assert.assertEquals(1, lowerCase("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void passwordHaveOneNumberAtLeast() {
        try {
            Assert.assertEquals(1, digitize("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void passwordIsCorrect() {
        try {
            Assert.assertTrue(verify("HelloWorld13"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
