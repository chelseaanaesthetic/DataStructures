import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * @author Chelsea Hanson
 * ICS240 Assignment 4: due 7/26/16
 *
 * This program runs a GUI that interacts with the user to build a dictionary
 * using a BinarySearchTree.java to alphabetize the individual words from the user.
 */
public class Controller extends JFrame implements ActionListener {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 700;
    private static final int INPUT = 25;

    private BinarySearchTree bst;

    private JButton chooseFileButton, addTextButton, countOccurrencesButton;
    private JTextArea inputText, sortedText, occurrenceText;
    private JLabel numberOfWords, uniqueWords;
    private JTextField customText, searchQuery;
    JScrollPane inputScrollPane, resultsScrollPane;

    /**
     * This is the constructor that sets up the window, layouts, and action listeners.
     */
    public Controller() {
        super("Alphabetizer");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        buildGUI();
        bst = new BinarySearchTree();
    }

    /**
     * This is the main method that initializes the GUI display.
     * @param args - default value for main that is unused
     */
    public static void main(String[] args) {
        Controller main = new Controller();
        main.setSize(WIDTH, HEIGHT);
        main.setResizable(false);
        main.setVisible(true);
    }

    /**
     * This method creates the pieces that get put together in panels
     * that come together to make up the GUI.
     */
    private void buildGUI() {
        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(this);

        addTextButton = new JButton("Add Text");
        addTextButton.addActionListener(this);

        countOccurrencesButton = new JButton("Count Occurrences");
        countOccurrencesButton.addActionListener(this);

        numberOfWords = new JLabel("0");
        uniqueWords = new JLabel("0");

        JLabel wordCountText = new JLabel(" total words   :  ");
        JLabel uniqueCountText = new JLabel(" unique words");
        JLabel quantityCheck = new JLabel("Check how many times a word occurs: ");
        JLabel originalLabel = new JLabel("Original Text Order: ");
        JLabel alphabetizedLabel = new JLabel("Alphabetized Text: ");

        customText = new JTextField(INPUT);
        searchQuery = new JTextField(INPUT);

        JTextArea headerText = new JTextArea(3, 60);
        headerText.setEditable(false);
        headerText.setText("Add a word or phrase to the original file and " +
                "watch it integrate its way into the alphabetized collection of words. " +
                "\nYou can also select a file to include in the collection.");

        occurrenceText = new JTextArea(2, 60);
        occurrenceText.setEditable(false);
        occurrenceText.setText("");

        inputText = new JTextArea(20, 29);
        inputText.setEditable(false);

        sortedText = new JTextArea(20, 29);
        sortedText.setEditable(false);

        inputScrollPane = new JScrollPane(inputText);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inputScrollPane.validate();

        resultsScrollPane = new JScrollPane(sortedText);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultsScrollPane.validate();

        JPanel inputTextPanel = new JPanel(new BorderLayout());
        inputTextPanel.add(originalLabel, BorderLayout.NORTH);
        inputTextPanel.add(inputScrollPane, BorderLayout.CENTER);

        JPanel sortedTextPanel = new JPanel(new BorderLayout());
        sortedTextPanel.add(alphabetizedLabel, BorderLayout.NORTH);
        sortedTextPanel.add(resultsScrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel();
        headerPanel.add(headerText);

        JPanel fileChooserPanel = new JPanel();
        fileChooserPanel.add(chooseFileButton);

        JPanel customInputPanel = new JPanel(new GridLayout(1, 2));
        customInputPanel.add(customText);
        customInputPanel.add(addTextButton);

        JPanel countOccurrencesPanel = new JPanel(new GridLayout(1, 2));
        countOccurrencesPanel.add(searchQuery);
        countOccurrencesPanel.add(countOccurrencesButton);

        JPanel statusPanel = new JPanel();
        statusPanel.add(numberOfWords);
        statusPanel.add(wordCountText);
        statusPanel.add(uniqueWords);
        statusPanel.add(uniqueCountText);

        JPanel interactivePanel = new JPanel(new BorderLayout());
        interactivePanel.add(fileChooserPanel, BorderLayout.NORTH);
        interactivePanel.add(customInputPanel, BorderLayout.CENTER);
        interactivePanel.add(statusPanel, BorderLayout.SOUTH);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(inputTextPanel, BorderLayout.WEST);
        resultsPanel.add(sortedTextPanel, BorderLayout.EAST);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.add(interactivePanel, BorderLayout.NORTH);
        bodyPanel.add(resultsPanel, BorderLayout.SOUTH);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(quantityCheck, BorderLayout.NORTH);
        footerPanel.add(countOccurrencesPanel, BorderLayout.CENTER);
        footerPanel.add(occurrenceText, BorderLayout.SOUTH);

        JPanel topLevelPanel = (JPanel) getContentPane();
        topLevelPanel.add(headerPanel, BorderLayout.NORTH);
        topLevelPanel.add(bodyPanel, BorderLayout.CENTER);
        topLevelPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * This method handles all events and processes them based on which event occurs.
     * @param event - the user interaction with the GUI to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(chooseFileButton)) {
            processChooseFileButton();
        }
        else if (event.getSource().equals(addTextButton)) {
            processAddTextButton();
        }
        else if (event.getSource().equals(countOccurrencesButton)) {
            processCountOccurrencesButton(searchQuery.getText());
        }
    }

    /**
     * This method opens a window and lets the user choose a file to
     * import words from and proceeds to import those words.
     */
    public void processChooseFileButton() {
        File inputFile;

        // Opens a window to select a text file from the same directory this program runs from.
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        File currentDirectory = new File(".").getAbsoluteFile();
        fileChooser.setCurrentDirectory(currentDirectory);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();

            try {
                BufferedReader inputStream = new BufferedReader(new FileReader(inputFile));
                breakString(inputStream);
                inputStream.close();
                bst.printTo(sortedText);
            }
            catch (FileNotFoundException e){
                System.out.println("Problem opening files.");
            }
            catch (IOException e){
                System.out.println("Error reading from " + inputFile +".");
            }
            catch (Exception e){
                // If any error occurs,
                e.printStackTrace();
            }
        }
    }

    /**
     * This method takes the user entered word(s) and proceeds to break it apart
     * into individual words to add to the BinarySearchTree and then display the
     * sorted tree to the GUI's alphabetized panel.
     */
    public void processAddTextButton() {
        breakString(customText.getText());
        customText.setText("");
        bst.printTo(sortedText);
    }

    /**
     * This method checks how many times a specific word occurs in the BinarySearchTree.
     * @param query - the user entered word to count how mnay times it occurs
     */
    public void processCountOccurrencesButton(String query) {
        if (query != null) {
            String[] tokens = query.split("[^a-zA-Z]");
            int occurrences = bst.countOccurrences(tokens[0]);
            occurrenceText.setText("The word \"" + tokens[0] + "\" occurs " + occurrences + " time(s).");
            searchQuery.setText("");
        }
    }

    /**
     * This method breaks the user input file into individual words
     * and proceeds to add those individual words to the BinarySearchTree.
     * @param inputStream - the reader on the user input file
     * @throws IOException - if there is a problem reading from the input file
     */
    public void breakString(BufferedReader inputStream) throws IOException {
        String line;
        while ((line = inputStream.readLine()) != null) {
            String[] tokens = line.split("[^a-zA-Z]");
            for (int x = 0; x < tokens.length; x++) {
                if (tokens[x].length() > 0) {
                    addWord(tokens[x].toLowerCase());
                }
            }
        }
    }

    /**
     * This method breaks the user input string into individual words
     * and proceeds to add those individual words to the BinarySearchTree.
     * @param customInput - the user entered line of input
     */
    public void breakString(String customInput) {
        if (customInput != null) {
            String[] tokens = customInput.split("[^a-zA-Z]");
            for (int x = 0; x < tokens.length; x++) {
                if (tokens[x].length() > 0) {
                    addWord(tokens[x].toLowerCase());
                }
            }
        }
    }

    /**
     * This method adds a word to the unsorted list, then sends it to the
     * BinarySearchTree.java where it is alphabetized.
     * @param word - user input to be sorted
     */
    public void addWord(String word) {
        // create an Entry to generate a key and be able to create a BTNode
        Entry newWord = new Entry(word);
        // display word to unsorted panel of GUI
        inputText.append(newWord.getKey() + ". " + newWord.getWord() + "\n");
        // increase total word count
        int wordCount = Integer.parseInt(numberOfWords.getText()) + 1;
        numberOfWords.setText(wordCount + "");
        // if it is unique, increase the unique count
        if (bst.add(newWord)){
            int unique = Integer.parseInt(uniqueWords.getText()) + 1;
            uniqueWords.setText(unique + "");
        }
    }
}