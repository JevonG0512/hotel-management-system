package hotelsystem;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A window for generating a statistics report file. The report lists
 * managers sorted alphabetically by last name, with each manager's
 * assigned rooms indented beneath them, followed by all rooms sorted by
 * price with the most expensive room listed first.
 * @author jevongilliam
 */
public class DownloadStatsWindow extends JFrame {

    private JButton addButt;
    private JTextField fileNameText;
    private ArrayList<Manager> managers;
    private ArrayList<Room> rooms;

    /**
     * Builds and displays the Download Statistics window.
     * @param m the shared list of managers to report on
     * @param r the shared list of rooms to report on
     */
    public DownloadStatsWindow(ArrayList<Manager> m, ArrayList<Room> r)
    {
        managers = m;
        rooms = r;
        setTitle("Download Statistics");
        setSize(300,75);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        setVisible(true);
    }

    /**
     * Handles the Download Stats File button click and writes the report.
     */
    public class ButtonHandler implements ActionListener
    {
        /**
         * Writes the statistics file to the entered file name.
         * @param e the button click event
         */
        public void actionPerformed(ActionEvent e)
        {
            JButton butt = (JButton) e.getSource();

            if(butt != addButt)
            {
                return;
            }

            String outFile = fileNameText.getText().trim();
            if(outFile.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please enter a file name.");
                return;
            }

            try (PrintWriter pw = new PrintWriter(outFile))
            {
                writeManagersWithRooms(pw);
                pw.println();
                writeRoomsByPriceDescending(pw);

                JOptionPane.showMessageDialog(null, "Statistics file saved to " + outFile);
            }
            catch(IOException ex)
            {
                JOptionPane.showMessageDialog(null,
                        "Could not write to that file name.",
                        "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Writes managers sorted alphabetically by last name, with each
     * manager's assigned rooms listed indented directly beneath them.
     * @param pw the writer to output the report to
     */
    private void writeManagersWithRooms(PrintWriter pw)
    {
        ArrayList<Manager> sortedManagers = new ArrayList<>(managers);
        Collections.sort(sortedManagers);

        pw.println("Sorted Managers (with assigned rooms):");
        if(sortedManagers.isEmpty())
        {
            pw.println("(none)");
            return;
        }

        for(Manager m : sortedManagers)
        {
            pw.print(m);
            boolean hasRooms = false;
            for(Room r : rooms)
            {
                if(r.getResManager() == m.getBadgeNum())
                {
                    pw.println("    " + r.toSummaryString());
                    hasRooms = true;
                }
            }
            if(!hasRooms)
            {
                pw.println("    (no rooms assigned)");
            }
            pw.println();
        }
    }

    /**
     * Writes all rooms sorted by price with the most expensive room first.
     * @param pw the writer to output the report to
     */
    private void writeRoomsByPriceDescending(PrintWriter pw)
    {
        ArrayList<Room> sortedRooms = new ArrayList<>(rooms);
        Collections.sort(sortedRooms, Collections.reverseOrder());

        pw.println("Sorted Rooms by Price (most expensive first):");
        if(sortedRooms.isEmpty())
        {
            pw.println("(none)");
            return;
        }

        for(Room r : sortedRooms)
        {
            pw.println(r.toSummaryString());
        }
    }

    /**
     * Lays out the file name field and the Download Stats File button.
     */
    public void buildPanel()
    {
        JPanel panel = new JPanel();
            Color myPurple5 = new Color(73, 0, 114);
                panel.setBackground(myPurple5);

        JLabel fileNameLabel = new JLabel("Enter File Name:");
             fileNameLabel.setForeground(Color.white);
                panel.add(fileNameLabel);

        fileNameText = new JTextField(10);
            panel.add(fileNameText);

        addButt = new JButton("Download Stats File");
            addButt.addActionListener(new ButtonHandler());
                panel.add(addButt);

        add(panel);
    }
}
