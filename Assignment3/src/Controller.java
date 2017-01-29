import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chelsea Hanson
 * ICS240 Assignment 3: due 5/5/16
 *
 * This program runs a GUI for simulating an airport runway with planes
 * sharing a single runway for landings and takeoffs.
 */
public class Controller extends JFrame implements ActionListener, Serializable {

    private static final int HEIGHT = 330;
    private static final int WIDTH = 700;
    private static final int INPUT = 10;

    private JTextField setArrivalRate, setDepartureRate, setTimeToLand, setTimeToTakeoff, setFuelRemain, setSimTime;
    private JButton runSimulatorButton, clearAllButton;
    private JTextArea displayResults, footerText;
    private JPanel footerPanel, mainPanel, inputPanel, buttonPanel, outputPanel;

    private double arrivalLandProb, arrivalTakeOffProb;
    private int simulationTime, landTime, takeOffTime, fuelRemaining;

    private Runway runway;
    private BooleanSource landArrival, takeOffArrival;
    private Queue<Integer> arrivalLandTimes, arrivalTakeOffTimes;
    private Averager avgLandWait, avgTakeOffWait, crashes;

    private static BufferedWriter output;

    /**
     * This is the constructor that sets up the window, layouts, and action listeners.
     */
    public Controller() {
        super("Project Runway Simulator");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                try {
                    output.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            }
        });
        buildGUI();

        setFooterText("Fill in the values for a simulation of the runway." +
                "\nAll raw data will be saved to a file in this directory.");
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

        setupResultFile();
    }

    /**
     * This method creates the pieces that get put together in panels
     * that come together to make up the GUI.
     */
    private void buildGUI() {
        JLabel arrivalRateLabel = new JLabel("Arrival Rate");
        JLabel departureRateLabel = new JLabel("Departure Rate");
        JLabel timeToLandLabel = new JLabel("Time to Land");
        JLabel timeToTakeoffLabel = new JLabel("Time to Takeoff");
        JLabel fuelRemainLabel = new JLabel("Minutes of fuel remaining");
        JLabel simTimeLabel = new JLabel("Simulation Time");

        setArrivalRate = new JTextField(INPUT);
        setDepartureRate = new JTextField(INPUT);
        setTimeToLand = new JTextField(INPUT);
        setTimeToTakeoff = new JTextField(INPUT);
        setFuelRemain = new JTextField(INPUT);
        setSimTime = new JTextField(INPUT);

        runSimulatorButton = new JButton("Run Simulator");
        clearAllButton = new JButton("Clear All");
        runSimulatorButton.addActionListener(this);
        clearAllButton.addActionListener(this);

        displayResults = new JTextArea(15, 30);
        displayResults.setEditable(false);
        JScrollPane scroller = new JScrollPane(displayResults);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        footerText = new JTextArea(3, 60);
        footerText.setEditable(false);

        inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(arrivalRateLabel);
        inputPanel.add(setArrivalRate);
        inputPanel.add(departureRateLabel);
        inputPanel.add(setDepartureRate);
        inputPanel.add(timeToLandLabel);
        inputPanel.add(setTimeToLand);
        inputPanel.add(timeToTakeoffLabel);
        inputPanel.add(setTimeToTakeoff);
        inputPanel.add(fuelRemainLabel);
        inputPanel.add(setFuelRemain);
        inputPanel.add(simTimeLabel);
        inputPanel.add(setSimTime);

        buttonPanel = new JPanel();
        buttonPanel.add(clearAllButton);
        buttonPanel.add(runSimulatorButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        outputPanel = new JPanel();
        outputPanel.add(displayResults);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.add(mainPanel, BorderLayout.WEST);
        bodyPanel.add(outputPanel, BorderLayout.EAST);

        footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(footerText, BorderLayout.CENTER);

        JPanel topLevelPanel = (JPanel) getContentPane();
        topLevelPanel.add(bodyPanel, BorderLayout.CENTER);
        topLevelPanel.add(footerPanel, BorderLayout.SOUTH);
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
     * This method attempts to display the input and output of the simulation in
     * a TextArea for the user.
     * @param text - the data to display to the user about the simulation
     */
    private void setOutputText(String text) {
        displayResults.setText(displayResults.getText() + text);
        outputPanel.repaint();
        outputPanel.revalidate();
    }

    /**
     * This method handles all events and processes them based on which event occurs.
     * @param event - the user interaction with the GUI to be processed
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(runSimulatorButton)) {
            try {
                processRunSimulator();
            }
            catch (IllegalArgumentException e) {
                setFooterText(e.getMessage() +
                        "\nFill in valid values to run a simulation." +
                        "\nRemember that rates are a decimal between 0.0 and 1.0.");
            }
        }
        else if (event.getSource().equals(clearAllButton)) {
            processClearInput();
        }
    }

    /**
     * This method prepares to run the simulation by setting up and initializing
     * the components as well as check for valid input.
     */
    public void processRunSimulator() {
        arrivalLandProb = Double.parseDouble(setArrivalRate.getText());
        arrivalTakeOffProb = Double.parseDouble(setDepartureRate.getText());
        simulationTime = Integer.parseInt(setSimTime.getText());
        landTime = Integer.parseInt(setTimeToLand.getText());
        takeOffTime = Integer.parseInt(setTimeToTakeoff.getText());
        fuelRemaining = Integer.parseInt(setFuelRemain.getText());

        // Check the precondition
        if (landTime <= 0 || takeOffTime <= 0 || simulationTime < 0 ||
                arrivalLandProb < 0 || arrivalTakeOffProb < 0 ||
                arrivalLandProb > 1 || arrivalTakeOffProb > 1 ) {

            throw new IllegalArgumentException("Error: Values out of range.");
        }

        runway = new Runway(landTime, takeOffTime);

        arrivalLandTimes = new LinkedList<Integer>();
        arrivalTakeOffTimes = new LinkedList<Integer>();

        landArrival = new BooleanSource(arrivalLandProb);
        takeOffArrival = new BooleanSource(arrivalTakeOffProb);

        avgLandWait = new Averager();
        avgTakeOffWait = new Averager();
        crashes = new Averager();

        displayResults.setText("");

        setFooterText("");

        // write parameters to output or file
        printParams();

        // simulate the passage of one second of time
        runSimulator();

        setFooterText("Simulation complete!  Look to the right for results." +
                "\nClose this simulator and open the runwayResults.txt file to view raw data.");

        // write results to output or file
        printResults();
    }

    /**
     * This method clears out all user input as well as previous simulation results.
     */
    public void processClearInput() {
        setArrivalRate.setText("");
        setDepartureRate.setText("");
        setTimeToLand.setText("");
        setTimeToTakeoff.setText("");
        setFuelRemain.setText("");
        setSimTime.setText("");

        inputPanel.repaint();
        inputPanel.revalidate();

        displayResults.setText("");
        outputPanel.repaint();
        outputPanel.revalidate();

        setFooterText("Fill in the values for another simulation of the runway.");
    }

    /**
     * This method runs the actual simulation of the runway. It almost uses a priority
     * queue to determine which plane gets to use the runway at a given time.
     */
    private void runSimulator() {
        int next;
        int currentSecond;

        // Simulate the passage of one second of time
        for (currentSecond = 0; currentSecond < simulationTime; currentSecond++) {
            // Check if a new plane is waiting to land
            if (landArrival.query()) {
                arrivalLandTimes.add(currentSecond);
            }
            // Check if a new plane is waiting to take off
            if (takeOffArrival.query()) {
                arrivalTakeOffTimes.add(currentSecond);
            }
            // Check whether a new plane can use the runway
            if (!runway.isBusy()) {
                if (!arrivalLandTimes.isEmpty()) {
                    next = arrivalLandTimes.remove();
                    if (currentSecond - next > fuelRemaining) {
                        crashes.addNumber(next);
                    }
                    else {
                        avgLandWait.addNumber(currentSecond - next);
                        runway.startLand();
                    }
                }
                else if (!arrivalTakeOffTimes.isEmpty()) {
                    next = arrivalTakeOffTimes.remove();
                    avgTakeOffWait.addNumber(currentSecond - next);
                    runway.startTakeOff();
                }
            }
            // Subtract one second from the runway wait time
            runway.reduceRemainingTime();
        }

        while (!arrivalLandTimes.isEmpty()) {
            next = arrivalLandTimes.remove();
            if (currentSecond - next > fuelRemaining) {
                crashes.addNumber(next);
            }
        }
    }

    /**
     * This helper method builds a file to save results to and adds the format
     * of the raw data to the beginning of the file.
     */
    private static void setupResultFile() {
        try {
            File file = new File("runwayResults.txt");
            output = new BufferedWriter(new FileWriter(file));

            output.write("\nInput:" +
                    "\nArrival Rate" +
                    "\nDeparture Rate" +
                    "\nTime to Land" +
                    "\nTime to Takeoff" +
                    "\nMinutes of Fuel Left" +
                    "\nSimulation Time" +
                    "\n");

            output.write("\nOutput:" +
                    "\nNumber of planes that took off" +
                    "\nNumber of planes that landed" +
                    "\nPlanes that crashed waiting" +
                    "\nAverage time spent in takeoff queue" +
                    "\nAverage time spent in landing queue" +
                    "\n\n\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method displays the input parameters from the user.
     */
    private void printParams() {
        // print params to file
        try {
            output.write("\n" + arrivalLandProb);
            output.write("\n" + arrivalTakeOffProb);
            output.write("\n" + landTime);
            output.write("\n" + takeOffTime);
            output.write("\n" + fuelRemaining);
            output.write("\n" + simulationTime);
            output.write("\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        // print params to window display
        setOutputText("\nArrival Rate:          " + arrivalLandProb +
                      "\nDeparture Rate:        " + arrivalTakeOffProb +
                      "\nTime to Land:          " + landTime +
                      "\nTime to Takeoff:       " + takeOffTime +
                      "\nMinutes of Fuel Left:  " + fuelRemaining +
                      "\nSimulation Time:       " + simulationTime +
                      "\n");
    }

    /**
     * This method displays the results from the simulation.
     */
    private void printResults() {
        // print results to file
        try{
            output.write("\n" + avgTakeOffWait.howManyNumbers());
            output.write("\n" + avgLandWait.howManyNumbers());
            output.write("\n" + crashes.howManyNumbers());
            output.write("\n" + avgTakeOffWait.average());
            output.write("\n" + avgLandWait.average());
            output.write("\n\n\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // print results to window display
        setOutputText("\nNumber of planes that took off:      " + avgTakeOffWait.howManyNumbers() +
                      "\nNumber of planes that landed:        " + avgLandWait.howManyNumbers() +
                      "\nPlanes that crashed waiting:         " + crashes.howManyNumbers() +
                      "\nAverage time spent in takeoff queue: " + avgTakeOffWait.average() +
                      "\nAverage time spent in landing queue: " + avgLandWait.average() +
                      "\n");
    }
}
