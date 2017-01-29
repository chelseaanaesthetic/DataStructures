/**
 * @author Chelsea Hanson
 * ICS240 Assignment 2: due 6/14/16
 *
 * This class outlines the structure and functionality of a Member of some group.
 * It is to be used with the MembershipList.java class to organize multiple Members.
 */
public class Member implements Comparable<Member> {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private int score;

    /**
     * This constructor creates a member with the minimum required fields,
     * and leaves the rest of the fields blank for now.
     *
     * @param username - the username of the member
     * @param password - the password of the member
     * @param email - the email address of the member
     */
    public Member(String username, String password, String email) {
        setFirstName("");
        setLastName("");
        setUsername(username.toLowerCase());
        setPassword(password);
        setEmail(email);
        setScore(0);
    }

    /**
     * This constructor creates a member with all fields minus the score given by the user.
     * The score is initialized to zero.
     *
     * @param fName - the first name of the member
     * @param lName - the last name of the member
     * @param username - the username of the member
     * @param password - the password of the member
     * @param email - the email address of the member
     */
    public Member(String fName, String lName, String username, String password, String email) {
        setFirstName(fName);
        setLastName(lName);
        setUsername(username.toLowerCase());
        setPassword(password);
        setEmail(email);
        setScore(0);
    }

    /**
     * This constructor creates a member with all fields given by the user.
     *
     * @param fName - the first name of the member
     * @param lName - the last name of the member
     * @param username - the username of the member
     * @param password - the password of the member
     * @param email - the email address of the member
     * @param score - the score of the member
     */
    public Member(String fName, String lName, String username, String password, String email, int score) {
        setFirstName(fName);
        setLastName(lName);
        setUsername(username.toLowerCase());
        setPassword(password);
        setEmail(email);
        setScore(score);
    }

    /**
     * This method sets the value of the member's first name.
     * @param fName - the first name to be assigned to the member
     */
    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    /**
     * This method sets the value of the member's last name.
     * @param lName - the last name to be assigned to the member
     */
    public void setLastName(String lName) {
        this.lastName = lName;
    }

    /**
     * This method sets the value of the member's username.
     * @param username - the username to be assigned to the member
     */
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    /**
     * This method sets the value of the member's password.
     * @param password - the password to be assigned to the member
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method sets the value of the member's email address.
     * @param email - the email address to be assigned to the member
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method sets the value of the member's score.
     * @param score - the score to be assigned to the member
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method checks for and returns the member's first name.
     * @return the member's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method checks for and returns the member's last name.
     * @return the member's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method checks for and returns the member's username.
     * @return the member's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method checks for and returns the member's password.
     * @return the member's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method checks for and returns the member's email address.
     * @return the member's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method checks for and returns the member's score.
     * @return the member's score
     */
    public int getScore() {
        return score;
    }

    /**
     * This method alphabetically compares the usernames of two Members
     * @param m - the member being compared to the calling member
     * @return 0 if the username's are the same.
     *         >0 if the calling member's username comes after m alphabetically
     *         <0 if the calling member's username comes before m alphabetically
     */
    @Override
    public int compareTo(Member m) {
        if (this.getUsername().equals(m.getUsername())) {
            return 0;
        }

        return ( this.getUsername().compareTo(m.getUsername()) );
    }

    /**
     * This method checks if the calling Member is the same as the given member
     * based solely on username.
     * @param m - the member to check equality against
     * @return true if the two are equal, else false
     */
    public boolean equals(Member m) {
        if (this.username.equals(m.getUsername())) {
            return true;
        }

        return false;
    }

    /**
     * This method formats a member's attributes for organized display to the user.
     * @return a formatted string representing the member and all of their attributes
     */
    @Override
    public String toString() {
        String memberString = "\n\nFirst Name: " + firstName + "\nLast Name: " + lastName +
                              "\nUsername: " + username + "\nPassword: " + password +
                              "\nEmail: " + email + "\nScore: " + score;

        return memberString;
    }
}