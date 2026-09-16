package hotelsystem;
import java.util.*;

/**
 * A hotel manager with a last name, first name, username, password, and badge number.
 * @author jevongilliam
 */
public class Manager implements Comparable<Manager> {
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private int badgeNum;
    public static int nextBadgeNum = 1;

    private ArrayList<Room> roomsManaged;

    /**
     * A hotel manager with a last name, first name, username, password, and badge number.
     * @param l last name
     * @param f first name
     * @param u user name
     * @param p password
     * @param bn badge number
     */
    public Manager(String l, String f, String u, String p, int bn)
    {
        lastName = l;
        firstName = f;
        username = u;
        password = p;
        badgeNum = bn;
    }

    /**
     * A hotel manager with a last name, first name, username, and password.
     * Automatically assigns the next available badge number.
     * @param l last name
     * @param f first name
     * @param u user name
     * @param p password
     */
    public Manager(String l, String f, String u, String p)
    {
        lastName = l;
        firstName = f;
        username = u;
        password = p;
        badgeNum = nextBadgeNum++;
    }

    /**
     * Gets the manager's first name.
     * @return the first name
     */
    public String getFirstName() {return firstName;}

    /**
     * Gets the manager's last name.
     * @return the last name
     */
    public String getLastName() {return lastName;}

    /**
     * Gets the manager's username.
     * @return the username
     */
    public String getUsername() {return username;}

    /**
     * Gets the manager's badge number.
     * @return the badge number
     */
    public int getBadgeNum() {return badgeNum;}

    /**
     * Returns a Manager String.
     * @return String
     */
    public String toString()
    {
        return String.format("Manager: %s %s"
                + "\nUsername: %s\nID Number: %d\n"
                , lastName, firstName, username, badgeNum);
    }

    /**
     * Compares two managers by last name.
     * @param m a manager
     * @return a negative, positive, or zero value
     */
    public int compareTo(Manager m)
    {
        return lastName.compareTo(m.lastName);
    }

    /**
     * Determines whether two entered passwords match and meet the
     * required length (8-10 characters). Static so it can be checked
     * BEFORE a Manager object (and badge number) is created.
     * @param p1 password
     * @param p2 password confirmation
     * @return true if both are non-null, match, and are 8-10 characters
     */
    public static boolean isValidPassword(String p1, String p2)
    {
        if (p1 == null || p2 == null)
        {
            return false;
        }
        if (!p1.equals(p2))
        {
            return false;
        }
        return p1.length() >= 8 && p1.length() <= 10;
    }

    /**
     * Checks whether a password contains the manager's first or last name
     * (case-insensitive). Names shorter than 2 characters are ignored to
     * avoid trivial false positives.
     * @param password the password to check
     * @param firstName the manager's first name
     * @param lastName the manager's last name
     * @return true if the password contains the first or last name
     */
    public static boolean containsName(String password, String firstName, String lastName)
    {
        if (password == null)
        {
            return false;
        }
        String pwLower = password.toLowerCase();
        if (firstName != null && firstName.trim().length() >= 2
                && pwLower.contains(firstName.trim().toLowerCase()))
        {
            return true;
        }
        if (lastName != null && lastName.trim().length() >= 2
                && pwLower.contains(lastName.trim().toLowerCase()))
        {
            return true;
        }
        return false;
    }

    /**
     * Checks whether a password contains at least one digit, one uppercase
     * letter, and one lowercase letter.
     * @param password the password to check
     * @return true if all three character classes are present
     */
    public static boolean hasRequiredComplexity(String password)
    {
        if (password == null)
        {
            return false;
        }
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        for (char c : password.toCharArray())
        {
            if (Character.isDigit(c)) hasDigit = true;
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
        }
        return hasDigit && hasUpper && hasLower;
    }

    /**
     * Sets the manager's password.
     * @param pw the new password
     */
    public void setPassword(String pw) {password = pw;}

}
