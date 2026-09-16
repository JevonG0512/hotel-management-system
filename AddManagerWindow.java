package hotelsystem;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A window for adding a new Manager to the hotel system. Validates all
 * fields, enforces password rules (must not contain the manager's name,
 * must contain a digit/uppercase/lowercase letter, must match its
 * confirmation, and must be 8-10 characters), and flashes any invalid
 * field(s) red before reverting to their normal color.
 * @author jevongilliam
 */
public class AddManagerWindow extends JFrame{
    private JButton addMgButt;
    private JTextField fNameText, lNameText, uNameText, pwText, pwComText;
    private JLabel iDText;
    protected ArrayList<Manager> managers;
    private static final Color DEFAULT_FIELD_BG = Color.white;

    /**
     * Builds and displays the Add Manager window.
     * @param m the shared list of managers to add a new manager to
     */
    public AddManagerWindow(ArrayList<Manager> m)
    {
        managers = m;

        setTitle("Add Manager");
        setSize(350,250);
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
     * Handles the Add Manager button click: validates every field and
     * password rule before creating and storing a new Manager.
     */
    public class ButtonHandler implements ActionListener
    {
        /**
         * Validates input and, if valid, adds a new Manager to the list.
         * @param e the button click event
         */
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton) e.getSource();

            if(button != addMgButt)
            {
                return;
            }

            String fn = fNameText.getText().trim();
            String ln = lNameText.getText().trim();
            String un = uNameText.getText().trim();
            String pw = pwText.getText();
            String pw2 = pwComText.getText();

            // Required fields
            boolean missing = false;
            if(fn.isEmpty()) {flashError(fNameText); missing = true;}
            if(ln.isEmpty()) {flashError(lNameText); missing = true;}
            if(un.isEmpty()) {flashError(uNameText); missing = true;}
            if(missing)
            {
                JOptionPane.showMessageDialog(null,
                        "First name, last name, and username are required.",
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Password must match its confirmation and be 8-10 characters
            if(!Manager.isValidPassword(pw, pw2))
            {
                flashError(pwText);
                flashError(pwComText);
                JOptionPane.showMessageDialog(null,
                        "Passwords must match and be 8-10 characters long.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Password must not contain the manager's first or last name
            if(Manager.containsName(pw, fn, ln))
            {
                flashError(pwText);
                flashError(pwComText);
                JOptionPane.showMessageDialog(null,
                        "Password cannot contain your first or last name.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Password must contain a digit, an uppercase letter, and a lowercase letter
            if(!Manager.hasRequiredComplexity(pw))
            {
                flashError(pwText);
                flashError(pwComText);
                JOptionPane.showMessageDialog(null,
                        "Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                        "Invalid Password", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // All checks passed - only now is a Manager (and badge number) created
            Manager m1 = new Manager(ln, fn, un, pw);
            managers.add(m1);

            JOptionPane.showMessageDialog(null,
                    "New Manager Added\nBadge Number: " + m1.getBadgeNum());

            fNameText.setText("");
            lNameText.setText("");
            uNameText.setText("");
            pwText.setText("");
            pwComText.setText("");
            iDText.setText("" + Manager.nextBadgeNum);
        }
    }

    /**
     * Lays out all labels, text fields, and the Add Manager button.
     */
    public void buildPanel()
    {
        JPanel panel = new JPanel();
            Color myPurple3 = new Color(165, 115, 220);
                    panel.setBackground(myPurple3);
                        panel.setLayout(new GridLayout(7,2));

        JLabel fNameLabel = new JLabel("Enter First Name:");
            panel.add(fNameLabel);

        fNameText = new JTextField(20);
            panel.add(fNameText);

        JLabel lNameLabel = new JLabel("Enter Last Name:");
            panel.add(lNameLabel);

        lNameText = new JTextField(20);
            panel.add(lNameText);

        JLabel uNameLabel = new JLabel("Enter Username:");
            panel.add(uNameLabel);

        uNameText = new JTextField(20);
            panel.add(uNameText);

        JLabel pwLabel = new JLabel("Enter Password:");
            panel.add(pwLabel);

        pwText = new JTextField(20);
            panel.add(pwText);

        JLabel pwComLabel = new JLabel("Confirm Password:");
            panel.add(pwComLabel);

        pwComText = new JTextField(20);
            panel.add(pwComText);

        JLabel iDLabel = new JLabel("ID Number");
            panel.add(iDLabel);

        iDText = new JLabel("" + Manager.nextBadgeNum);
            panel.add(iDText);

        addMgButt = new JButton("Add Manager");
            addMgButt.addActionListener(new ButtonHandler());
                panel.add(addMgButt);

        add(panel);
    }
}
