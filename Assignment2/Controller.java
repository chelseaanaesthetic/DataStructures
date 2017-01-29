import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

/**
 * @author Chelsea Hanson
 * ICS240 Assignment 2: due 6/14/16
 *
 * This program runs a GUI for interacting with the Member.java and MembershipList.java
 * classes that were designed to store a list of objects. Adding, deleting, and modifying
 * Member objects in the collection list are part of this program.
 */
public class Controller extends JFrame implements ActionListener {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 700;
    private static final int INPUT = 20;

    private MemberNode cursor;
    private MembershipLinkedList memberList;

    // Swing components for the GUI
    private JButton showAllButton, addButton, findButton, modifyButton, deleteButton,
            createNew, addMemberButton, modifyMemberButton, deleteMemberButton;

    private JTextArea displayAll, emptyBody, footerText, emptyHeader;
    private JScrollPane scroller;

    private JLabel name, firstNameLabel, lastNameLabel, usernameLabel, passwordLabel, emailLabel, scoreLabel;
    private JTextField nameSetter, firstNameField, lastNameField, usernameField, passwordField, emailField, scoreField;

    private JPanel topLevelPanel, headerPanel, footerPanel, bodyPanel,
            emptyHeaderPanel, menuPanel, createNewPanel, createListPanel,
            emptyBodyPanel, addMemberPanel,
            firstNamePanel, lastNamePanel, usernamePanel, passwordPanel, emailPanel, scorePanel,
            findMemberPanel, memberDetailsPanel, modifyDeletePanel, showAllPanel;

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

        buildComponents();
        buildSections();
        setHeaderContent(emptyHeaderPanel);
        setBodyContent(createNewPanel);
        setFooterText("Directions:  Create a new member list to use.");
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
     * This method creates the pieces that get put together to build the GUI.
     */
    private void buildComponents(){
        // buttons for directives and navigation
        showAllButton = new JButton("Show All");
        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        addMemberButton = new JButton("Add Member");
        findButton = new JButton("Find");
        modifyMemberButton = new JButton("Modify Member");
        deleteMemberButton = new JButton("Delete Member");

        // components for letting the user create a new list of members
        name = new JLabel("Name of List ");
        nameSetter = new JTextField(INPUT);
        createNew = new JButton("Create List");

        // large text areas for displaying directions, warnings, errors,
        // and for displaying all members of the list
        displayAll = new JTextArea(23, 60);
        displayAll.setEditable(false);
        emptyBody = new JTextArea(23, 60);
        emptyBody.setEditable(false);
        footerText = new JTextArea(3, 60);
        footerText.setEditable(false);
        emptyHeader = new JTextArea(3, 60);
        emptyHeader.setEditable(false);

        scroller = new JScrollPane(displayAll);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // labels for attributes of a member
        firstNameLabel = new JLabel("First Name ");
        lastNameLabel = new JLabel("Last Name ");
        usernameLabel = new JLabel("username ");
        passwordLabel = new JLabel("password ");
        emailLabel = new JLabel("email address ");
        scoreLabel = new JLabel("score ");

        // spaces for getting user input and modifying data
        firstNameField = new JTextField(INPUT);
        lastNameField = new JTextField(INPUT);
        usernameField = new JTextField(INPUT);
        passwordField = new JTextField(INPUT);
        emailField = new JTextField(INPUT);
        scoreField = new JTextField(INPUT);

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
     * This method builds the different display options for different activities in the application.
     */
    private void buildSections() {
        /* creates panels to help organize components */
        // main layout panels
        topLevelPanel = (JPanel) getContentPane();
        headerPanel = new JPanel(new BorderLayout());
        bodyPanel = new JPanel(new BorderLayout());
        footerPanel = new JPanel(new BorderLayout());

        // helper/inner panels
        firstNamePanel = new JPanel();
        lastNamePanel = new JPanel();
        usernamePanel = new JPanel();
        passwordPanel = new JPanel();
        emailPanel = new JPanel();
        scorePanel = new JPanel();

        modifyDeletePanel = new JPanel();
        createListPanel = new JPanel(new GridLayout(2, 2));

        // header options
        emptyHeaderPanel = new JPanel(new BorderLayout());
        menuPanel = new JPanel();

        // body options
        createNewPanel = new JPanel();
        emptyBodyPanel = new JPanel();
        addMemberPanel = new JPanel();
        findMemberPanel = new JPanel();
        memberDetailsPanel = new JPanel();
        showAllPanel = new JPanel();

        addMemberPanel.setLayout(new BoxLayout(addMemberPanel, BoxLayout.Y_AXIS));

        /* fills panels with components */
        emptyHeaderPanel.add(emptyHeader, BorderLayout.CENTER);

        createListPanel.add(name);
        createListPanel.add(nameSetter);
        createNewPanel.add(createListPanel);
        createNewPanel.add(createNew);

        menuPanel.add(showAllButton);
        menuPanel.add(addButton);
        menuPanel.add(modifyButton);
        menuPanel.add(deleteButton);

        emptyBodyPanel.add(emptyBody);

        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(firstNameField);
        firstNamePanel.setMaximumSize(firstNamePanel.getPreferredSize());

        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameField);
        lastNamePanel.setMaximumSize(lastNamePanel.getPreferredSize());

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        usernamePanel.setMaximumSize(usernamePanel.getPreferredSize());

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        emailPanel.setMaximumSize(emailPanel.getPreferredSize());

        scorePanel.add(scoreLabel);
        scorePanel.add(scoreField);
        scorePanel.setMaximumSize(scorePanel.getPreferredSize());

        modifyDeletePanel.add(modifyMemberButton);
        modifyDeletePanel.add(deleteMemberButton);

        showAllPanel.add(scroller);

        footerPanel.add(footerText, BorderLayout.CENTER);

        /* creates the layout of the application */
        topLevelPanel.add(headerPanel, BorderLayout.NORTH);
        topLevelPanel.add(bodyPanel, BorderLayout.CENTER);
        topLevelPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * This method creates the display panel for adding a new member.
     */
    private void buildAddPanel() {
        addMemberPanel.add(firstNamePanel);
        addMemberPanel.add(lastNamePanel);
        addMemberPanel.add(usernamePanel);
        addMemberPanel.add(passwordPanel);
        addMemberPanel.add(emailPanel);
        addMemberPanel.add(addMemberButton);
    }

    /**
     * This method creates the display panel for searching for a particular member.
     */
    private void buildFindPanel() {
        findMemberPanel.add(usernamePanel);
        findMemberPanel.add(findButton);
    }

    /**
     * This method creates the display for showing all members in the list.
     */
    private void buildDetailsPanel() {
        memberDetailsPanel.add(firstNamePanel);
        memberDetailsPanel.add(lastNamePanel);
        memberDetailsPanel.add(usernamePanel);
        memberDetailsPanel.add(passwordPanel);
        memberDetailsPanel.add(emailPanel);
        memberDetailsPanel.add(scorePanel);
        memberDetailsPanel.add(modifyDeletePanel);
    }

    /**
     * This method sets the display of the main body of the application.
     * @param content - the display panel to make active in the body of the app
     */
    private void setBodyContent(JPanel content){
        bodyPanel.removeAll();
        bodyPanel.add(content);
        bodyPanel.repaint();
        bodyPanel.revalidate();
    }

    /**
     * This method sets the display of the header section of the application.
     * @param content - the display panel to make active in the head of the app
     */
    private void setHeaderContent(JPanel content) {
        headerPanel.removeAll();
        headerPanel.add(content);
        headerPanel.repaint();
        headerPanel.revalidate();
    }

    /**
     * Displays hints to the user ie. directions, warnings, and error messages.
     * @param text - the message to be displayed to the user
     */
    private void setFooterText(String text) {
        footerText.setText(text);
        footerPanel.repaint();
        footerPanel.revalidate();
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
     * This method sets up the MembershipList to store all data to be worked with.
     */
    public void processCreateNew() {
        String listName = nameSetter.getText();
        memberList = new MembershipLinkedList();

        setTitle(listName);
        setHeaderContent(menuPanel);
        setBodyContent(emptyBodyPanel);
        setFooterText("Directions:  Select an operation to perform on the list or one of its members.");
    }

    /**
     * This method attempts to display the contents of the list in a new TextArea.
     * If there is nothing to display, a warning message is displayed.
     */
    public void processShowAll() {
        if (memberList.size() > 0) {
            displayAll.setText(memberList.toString());
            setBodyContent(showAllPanel);
            setFooterText("Directions:  Select an operation to perform on the list or one of its members.");
        }
        else {
            setBodyContent(emptyBodyPanel);
            setFooterText("Warning:  There are no members to show; try adding some and trying again.");
        }
    }

    /**
     * This method sets up the prompt to get data from the user to add a member.
     */
    public void processAddMember() {
        clearInput();
        buildAddPanel();
        setBodyContent(addMemberPanel);
        setFooterText("Directions:  Fill in the necessary data to add a new member to the list.");
    }

    /**
     * This method sets up the prompt to find a member for the user to modify.
     */
    public void processModifyMember() {
        clearInput();
        buildFindPanel();
        setBodyContent(findMemberPanel);
        setFooterText("Directions:  Enter the username of the member you wish to modify.");
    }

    /**
     * This method sets up the prompt to find a member for the user to delete.
     */
    public void processDeleteMember() {
        clearInput();
        buildFindPanel();
        setBodyContent(findMemberPanel);
        setFooterText("Directions:  Enter the username of the member you wish to delete.");
    }

    /**
     * This method checks the validity of the user's input, and attempts to add the new member to the list.
     */
    public void processConfirmAddMember() {
        if (validData(usernameField.getText(), passwordField.getText())) {
            memberList.add(new Member(firstNameField.getText(), lastNameField.getText(), usernameField.getText().toLowerCase(), passwordField.getText(), emailField.getText()));
            setFooterText("Success:  '" + usernameField.getText() + "' was added to this list.");
            clearInput();
        }
        else {
            setFooterText("Error:  Could not add member to list.");
        }
    }

    /**
     * This method gets the member from the user's search query.
     */
    public void processFind() {
        String searchQuery = usernameField.getText().toLowerCase();
        if (memberList.search(searchQuery)) {
            cursor = memberList.getCursor();
            populateFields();
            buildDetailsPanel();
            setBodyContent(memberDetailsPanel);
            setFooterText("Directions:  Change a field and select 'Modify Member' to make changes, " +
                          "or select 'Delete Member' to remove it from the list.");
        }
        else {
            setFooterText("Error:  No such username was found. Try again or add them now.");
        }
    }

    /**
     * This method checks the validity of the user's input, and attempts to modify a member in the list.
     */
    public void processConfirmModify() {
        if (cursor.getData().getUsername().equals(usernameField.getText())) {
            if (passwordField.getText().length() < 6) {
                setFooterText("Error:  A good password must be at least 6 characters long.");
            }
            else {
                memberList.modify(cursor, new Member(firstNameField.getText(),
                        lastNameField.getText(), usernameField.getText(), passwordField.getText(),
                        emailField.getText(), Integer.parseInt(scoreField.getText())));
                setFooterText("Success:  '" + usernameField.getText() + "' has been modified.");
            }
        }
        else if (validData(usernameField.getText(), passwordField.getText())) {
            memberList.modify(cursor, new Member(firstNameField.getText(), lastNameField.getText(),
                    usernameField.getText(), passwordField.getText(), emailField.getText(),
                    Integer.parseInt(scoreField.getText())));
            setFooterText("Success:  '" + usernameField.getText() + "' has been modified.");
        }
        else {
            // setFooterText("Error:  '" + usernameField.getText() + "' has not been modified.");
        }
    }

    /**
     * This method removes the specified member from the list.
     */
    public void processConfirmDelete() {
        setFooterText("Success:  '" + cursor.getData().getUsername() + "' has been removed from this list.");
        memberList.remove(cursor);
        clearInput();
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
            setFooterText("Error:  This username is already in use.");
            return false;
        }
        else if (password.length() < 6) {
            setFooterText("Error:  A good password must be at least 6 characters long.");
            return false;
        }
        return true;
    }

    /**
     * This method clears out any previous user input for clean reuse.
     */
    private void clearInput() {
        firstNameField.setText("");
        lastNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        scoreField.setText("");
    }

    /**
     * This method brings back all user input for a specific member
     * for modification and display purposes.
     */
    private void populateFields() {
        firstNameField.setText(cursor.getData().getFirstName());
        lastNameField.setText(cursor.getData().getLastName());
        usernameField.setText(cursor.getData().getUsername());
        passwordField.setText(cursor.getData().getPassword());
        emailField.setText(cursor.getData().getEmail());
        scoreField.setText((cursor.getData().getScore()) + "");
    }
}