package hotelsystem;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A window for adding a new Room to the hotel system. Validates that the
 * price is a valid number and that the entered manager badge number
 * corresponds to an existing manager, flashing the offending field red
 * before reverting it to its normal color.
 * @author jevongilliam
 */
public class AddRoomWindow extends JFrame{
    private ArrayList<Manager> managers;
    private ArrayList<Room> rooms;
    private JTextField rMBNText, rPText, rCNText, rNText;
    private JButton addRoomButt;
    private JRadioButton rTButt1, rTButt2, rTButt3, rTButt4;
    private Color myPurple4 = new Color(112, 68, 158);
    private static final Color DEFAULT_FIELD_BG = Color.white;

    /**
     * Builds and displays the Add Room window.
     * @param r the shared list of rooms to add a new room to
     * @param m the shared list of managers, used to validate badge numbers
     */
    public AddRoomWindow(ArrayList<Room> r, ArrayList<Manager> m)
    {
        rooms = r;
        managers = m;
        setTitle("Add Room");
        setSize(1050,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        setVisible(true);
    }

    /**
     * Briefly turns a field's background red, then reverts it to the
     * default background color, to visually flag invalid input.
     * @param field the field to flash
     */
    private void flashError(JTextField field)
    {
        field.setBackground(Color.red);
        javax.swing.Timer timer = new javax.swing.Timer(1200, e -> field.setBackground(DEFAULT_FIELD_BG));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Enables the Connected Room Number field only when the "Connected"
     * room type is selected. Wired to fire the moment a radio button is
     * selected, not only when Add Room is clicked.
     */
    private void updateConnectedFieldState()
    {
        if(rTButt4.isSelected())
        {
            rCNText.setEnabled(true);
            rCNText.setBackground(DEFAULT_FIELD_BG);
        }
        else
        {
            rCNText.setEnabled(false);
            rCNText.setBackground(myPurple4);
            rCNText.setText("");
        }
    }

    /**
     * Refreshes the Connected Room Number field's enabled state whenever
     * the selected room type changes.
     */
    public class RoomTypeHandler implements ItemListener
    {
        /**
         * Called whenever a room type radio button is selected or deselected.
         * @param e the item selection event
         */
        public void itemStateChanged(ItemEvent e)
        {
            updateConnectedFieldState();
        }
    }

    /**
     * Handles the Add Room button click: validates every field (including
     * that the price is numeric and the manager badge number exists)
     * before creating and storing a new Room.
     */
    public class ButtonHandler implements ActionListener
    {
        /**
         * Validates input and, if valid, adds a new Room to the list.
         * @param e the button click event
         */
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton) e.getSource();

            if(button != addRoomButt)
            {
                return;
            }

            int rNum;
            try
            {
                rNum = Integer.parseInt(rNText.getText().trim());
            }
            catch(NumberFormatException ex)
            {
                flashError(rNText);
                JOptionPane.showMessageDialog(null,
                        "Room Number must be a valid number.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float rPrice;
            try
            {
                rPrice = Float.parseFloat(rPText.getText().trim());
            }
            catch(NumberFormatException ex)
            {
                flashError(rPText);
                JOptionPane.showMessageDialog(null,
                        "Room Price must be a valid number.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int mBN;
            try
            {
                mBN = Integer.parseInt(rMBNText.getText().trim());
            }
            catch(NumberFormatException ex)
            {
                flashError(rMBNText);
                JOptionPane.showMessageDialog(null,
                        "Manager Badge Number must be a valid number.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!isValidManager(mBN))
            {
                flashError(rMBNText);
                JOptionPane.showMessageDialog(null,
                        "No manager with badge number " + mBN + " exists. Add the manager first.",
                        "Unknown Manager", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try
            {
                if(rTButt1.isSelected())
                {
                    rooms.add(new Room(rNum, "STANDARD", rPrice, mBN));
                }
                else if(rTButt2.isSelected())
                {
                    rooms.add(new Room(rNum, "DOUBLE", rPrice, mBN));
                }
                else if(rTButt3.isSelected())
                {
                    rooms.add(new Room(rNum, "KING", rPrice, mBN));
                }
                else if(rTButt4.isSelected())
                {
                    int cRN;
                    try
                    {
                        cRN = Integer.parseInt(rCNText.getText().trim());
                    }
                    catch(NumberFormatException ex)
                    {
                        flashError(rCNText);
                        JOptionPane.showMessageDialog(null,
                                "Connected Room Number must be a valid number.",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    rooms.add(new ConnectedRoom(rNum, "CONNECTED", rPrice, mBN, cRN));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please select a room type.");
                    return;
                }

                JOptionPane.showMessageDialog(null, "Room Successfully Added");
                rNText.setText("");
                rPText.setText("");
                rMBNText.setText("");
                rCNText.setText("");
            }
            catch(HotelSystemException ex)
            {
                JOptionPane.showMessageDialog(null,
                        "Room Not Added: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Checks whether a manager with the given badge number has already
     * been added to the system.
     * @param badgeNum the badge number to look up
     * @return true if a manager with that badge number exists
     */
    private boolean isValidManager(int badgeNum)
    {
        for(Manager m : managers)
        {
            if(m.getBadgeNum() == badgeNum)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Lays out all labels, text fields, radio buttons, and the Add Room button.
     */
    public void buildPanel()
    {
        JPanel panel = new JPanel();
            myPurple4 = new Color(112, 68, 158);
                panel.setBackground(myPurple4);
                    panel.setLayout(new GridLayout(6,6));

        JLabel rNLabel = new JLabel("Enter Room Number:");
            panel.add(rNLabel);

        rNText = new JTextField(20);
            panel.add(rNText);
                panel.add(new JPanel()).setBackground(myPurple4);
                panel.add(new JPanel()).setBackground(myPurple4);
                panel.add(new JPanel()).setBackground(myPurple4);

        JLabel rTLabel = new JLabel("Choose Room Type:");
            panel.add(rTLabel);

        ButtonGroup rTBg = new ButtonGroup();

        rTButt1 = new JRadioButton("Standard");
            rTButt1.setSelected(true);
                rTBg.add(rTButt1);
                    rTButt1.addItemListener(new RoomTypeHandler());
                        panel.add(rTButt1);

        rTButt2 = new JRadioButton("Double");
            rTBg.add(rTButt2);
                rTButt2.addItemListener(new RoomTypeHandler());
                    panel.add(rTButt2);

        rTButt3 = new JRadioButton("King");
            rTBg.add(rTButt3);
                rTButt3.addItemListener(new RoomTypeHandler());
                    panel.add(rTButt3);

        rTButt4 = new JRadioButton("Connected");
            rTBg.add(rTButt4);
                rTButt4.addItemListener(new RoomTypeHandler());
                    panel.add(rTButt4);

        JLabel rCNLabel = new JLabel("Enter Connected Room Number:");
            panel.add(rCNLabel);

        rCNText = new JTextField(10);
            panel.add(rCNText);

            panel.add(new JPanel()).setBackground(myPurple4);
            panel.add(new JPanel()).setBackground(myPurple4);
            panel.add(new JPanel()).setBackground(myPurple4);

        JLabel rPLabel = new JLabel("Enter Room Price:");
            panel.add(rPLabel);

        rPText = new JTextField(10);
            panel.add(rPText);
            panel.add(new JPanel()).setBackground(myPurple4);
            panel.add(new JPanel()).setBackground(myPurple4);
            panel.add(new JPanel()).setBackground(myPurple4);

        JLabel rMBNLabel = new JLabel("Enter Manager Badge Number:");
            panel.add(rMBNLabel);

        rMBNText = new JTextField(10);
            panel.add(rMBNText);
                panel.add(new JPanel()).setBackground(myPurple4);
                panel.add(new JPanel()).setBackground(myPurple4);
                panel.add(new JPanel()).setBackground(myPurple4);

        addRoomButt = new JButton("Add Room");
            addRoomButt.addActionListener(new ButtonHandler());
                panel.add(addRoomButt);

        add(panel);

        updateConnectedFieldState();
    }
}
