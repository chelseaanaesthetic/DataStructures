import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Chelsea Hanson
 * ICS240 Project 1: due 5/31/16
 *
 * This program runs a GUI for interacting with the Member.java and MembershipList.java
 * classes that were designed to store a list of objects. Adding, deleting, and modifying
 * Member objects in the collection list are part of this program.
 */
public class Controller extends JFrame implements ActionListener {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;
    private static final int INPUT = 20;
    private int activeIndex;
    private Member activeMember;
    private MembershipList memberList;

    private JButton showAllButton = new JButton("Show All");
    private JButton addButton = new JButton("Add");
    private JButton findButton = new JButton("Find");
    private JButton modifyButton = new JButton("Modify");
    private JButton deleteButton = new JButton("Delete");

    private JButton addMemberButton = new JButton("Add Member");
    private JButton modifyMemberButton = new JButton("Modify Member");
    private JButton deleteMemberButton = new JButton("Delete Member");

    private JLabel name = new JLabel("Name of List ");
    private JTextField nameSetter = new JTextField(INPUT);
    private JLabel size = new JLabel("Initial Member Size ");
    private JTextField sizeSetter = new JTextField(INPUT);
    private JButton createNew = new JButton("Create List");

    private JTextArea displayAll = new JTextArea(20, 400);

    private JLabel fnameLabel = new JLabel("First Name ");
    private JLabel lnameLabel = new JLabel("Last Name ");
    private JLabel usernameLabel = new JLabel("username ");
    private JLabel passwordLabel = new JLabel("password ");
    private JLabel emailLabel = new JLabel("email address ");
    private JLabel scoreLabel = new JLabel("score ");

    private JTextField fnameField = new JTextField(INPUT);
    private JTextField lnameField = new JTextField(INPUT);
    private JTextField usernameField = new JTextField(INPUT);
    private JTextField passwordField = new JTextField(INPUT);
    private JTextField emailField = new JTextField(INPUT);
    private JTextField scoreField = new JTextField(INPUT);

    private JTextArea directionsTextArea = new JTextArea(3, 100);

    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridLayout(2, 1));
    JPanel startPanel = new JPanel(new FlowLayout());
    JPanel directionsPanel = new JPanel(new BorderLayout());
    JPanel createPanel = new JPanel(new GridLayout(2, 2));
    JPanel menuPanel = new JPanel(new FlowLayout());
    JPanel contentPanel = new JPanel();

    /**
     * This is the constructor that sets up the window, layouts, and action listeners.
     */
    public Controller() {
        super("Membership");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        getContentPane().add(topPanel);
        topPanel.add(mainPanel, BorderLayout.CENTER);
        topPanel.add(directionsPanel, BorderLayout.SOUTH);
        directionsPanel.add(directionsTextArea, BorderLayout.CENTER);

        setMainPanel("create");
        setDirectionsPanel("Directions:  Create a new member list to use.");

        //listeners for user input

        //listeners for button clicks
        createNew.addActionListener(this);
        showAllButton.addActionListener(this);
        addButton.addActionListener(this);
        findButton.addActionListener(this);
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);
        addMemberButton.addActionListener(this);
        modifyMemberButton.addActionListener(this);
        deleteMemberButton.addActionListener(this);

    }

    /**
     * This is the main method that initializes the GUI display.
     * @param args - default value for main
     */
    public static void main(String[] args) {
        Controller main = new Controller();
        main.setSize(WIDTH, HEIGHT);
        main.setResizable(false);
        main.setVisible(true);
    }

    /**
     * This method handles all events and processes them based on which event occurs.
     *
     * @param event - the user interaction with the GUI to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(createNew)) {
            processCreateNew();
        }
        else if (event.getSource().equals(showAllButton)) {
            processShowAll();
        }
        else if (event.getSource().equals(addButton)) {
            processAddMember();
        }
        else if (event.getSource().equals(findButton)) {
            processFind();
        }
        else if (event.getSource().equals(modifyButton)) {
            processModifyMember();
        }
        else if (event.getSource().equals(deleteButton)) {
            processDeleteMember();
        }
        else if (event.getSource().equals(addMemberButton)) {
            processConfirmAddMember();
        }
        else if (event.getSource().equals(modifyMemberButton)) {
            processConfirmModify();
        }
        else if (event.getSource().equals(deleteMemberButton)) {
            processConfirmDelete();
        }
    }

    /**
     * Sets the content and components of the main panel
     * based on what actions are performed.
     */
    public void setMainPanel(String state) {
        // for creating a new list to work with
        if (state.equals("create")) {
            mainPanel.add(startPanel);
            startPanel.add(createPanel);
            createPanel.add(name);
            createPanel.add(nameSetter);
            createPanel.add(size);
            createPanel.add(sizeSetter);
            startPanel.add(createNew);
        }

        // for getting menu options to show what you can do
        else if (state.equals("menu")) {
            mainPanel.removeAll();

            mainPanel.add(menuPanel);
            mainPanel.add(contentPanel);
            menuPanel.add(showAllButton);
            menuPanel.add(addButton);
            menuPanel.add(modifyButton);
            menuPanel.add(deleteButton);

            setDirectionsPanel("Directions:  Select an operation to perform " +
                    "on the list or one of its members.");
        }

        // for adding new members to the list
        else if (state.equals("add")) {
            contentPanel.removeAll();

            contentPanel.add(fnameLabel);
            contentPanel.add(fnameField);

            contentPanel.add(lnameLabel);
            contentPanel.add(lnameField);

            contentPanel.add(usernameLabel);
            contentPanel.add(usernameField);

            contentPanel.add(passwordLabel);
            contentPanel.add(passwordField);

            contentPanel.add(emailLabel);
            contentPanel.add(emailField);
        }

        // for displaying the details of a single member
        else if (state.equals("display")) {
            contentPanel.removeAll();

            contentPanel.add(fnameLabel);
            contentPanel.add(fnameField);
            fnameField.setText(activeMember.getFirstName());

            contentPanel.add(lnameLabel);
            contentPanel.add(lnameField);
            lnameField.setText(activeMember.getLastName());

            contentPanel.add(usernameLabel);
            contentPanel.add(usernameField);
            usernameField.setText(activeMember.getUsername());

            contentPanel.add(emailLabel);
            contentPanel.add(emailField);
            emailField.setText(activeMember.getEmail());

            contentPanel.add(scoreLabel);
            contentPanel.add(scoreField);
            scoreField.setText((activeMember.getScore()) + "");

            contentPanel.add(modifyMemberButton);
            contentPanel.add(deleteMemberButton);
        }

        // for searching a specific member
        else if (state.equals("find")) {
            contentPanel.removeAll();

            contentPanel.add(usernameLabel);
            contentPanel.add(usernameField);
            contentPanel.add(findButton);
        }

        // for clearing the main panel after deleting a member
        else if (state.equals("confirm")) {
            contentPanel.removeAll();

        }

        // to update the display
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * Displays the directions based on the status of the program.
     * @param direction - a String telling the user what action they should do next
     */
    public void setDirectionsPanel(String direction) {
        directionsTextArea.setText(direction);
        directionsPanel.repaint();
        directionsPanel.revalidate();
    }

    /**
     * This method sets up the MembershipList to store all data to be worked with.
     */
    public void processCreateNew() {
        String listName = nameSetter.getText();
        if (sizeSetter.getText() == null) {
            memberList = new MembershipList();
        }
        else {
            int initialSize = Integer.parseInt(sizeSetter.getText());
            memberList = new MembershipList(initialSize);
        }
        setMainPanel("menu");
    }

    /**
     * This method attempts to display the contents of the list in a new TextArea.
     * If there is nothing to display, a warning message is displayed.
     */
    public void processShowAll() {
        if (memberList.getMemberCount() > 0) {
            contentPanel.add(new JTextArea(memberList.toString()));
        }
        else {
            setDirectionsPanel("Warning:  There are no members to show; try adding some and trying again.");
        }
    }

    /**
     * This method sets up the prompt to get data from the user to add a member.
     */
    public void processAddMember() {
        setMainPanel("add");
        contentPanel.add(addMemberButton);
        setDirectionsPanel("Directions:  Fill in the necessary data to add a new member to the list.");
    }

    /**
     * This method sets up the prompt to find a member for the user to modify.
     */
    public void processModifyMember() {
        setMainPanel("find");
        setDirectionsPanel("Directions:  Enter the username of the member you wish to modify.");
    }

    /**
     * This method sets up the prompt to find a member for the user to delete.
     */
    public void processDeleteMember() {
        setMainPanel("find");
        setDirectionsPanel("Directions:  Enter the username of the member you wish to delete.");
    }

    /**
     * This method checks the validity of the user's input, and attempts to add the new member to the list.
     */
    public void processConfirmAddMember() {
        setMainPanel("menu");
        if (validData(usernameField.getText(), passwordField.getText())) {
            memberList.add(new Member(fnameField.getText(), lnameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText()));
        }
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    /**
     * This method gets the member from the user's search query.
     */
    public void processFind() {
        String searchQuery = usernameField.getText().toLowerCase();
        if (memberList.search(searchQuery)) {
            activeIndex = memberList.indexOf(searchQuery);
            activeMember = memberList.get(activeIndex);
            setMainPanel("display");
        }
        else {
            setDirectionsPanel("Error:  No such username was found. Try again or add them now.");
        }
    }

    /**
     * This method checks the validity of the user's input, and attempts to modify a member in the list.
     */
    public void processConfirmModify() {
        if (validData(usernameField.getText(), passwordField.getText())) {
            memberList.modify(activeIndex, new Member(fnameField.getText(), lnameField.getText(), usernameField.getText(), passwordField.getText(), emailField.getText(), Integer.parseInt(scoreField.getText())));
        }
    }

    /**
     * This method removes the specified member from the list.
     */
    public void processConfirmDelete() {
        memberList.setMemberCount(memberList.getMemberCount() - 1);
        memberList.set(activeIndex, memberList.get(memberList.getMemberCount()));
    }

    /**
     * This method checks the validity of the data that makes up a member.
     * @param username - The member's chosen username
     * @param password - The member's chosen password
     *
     * @return false if the data is invalid, and true if the data is valid
     */
    public boolean validData(String username, String password) {
        if (memberList.search(username)) {
            setDirectionsPanel("Error:  This username is already in use.");
            return false;
        }
        else if (password.length() < 6) {
            setDirectionsPanel("Error:  A good password must be at least 6 characters long.");
            return false;
        }
        return true;
    }
}