package hotelsystem;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A window for bulk-uploading Managers or Rooms from a comma-separated
 * text file. Each line is parsed and validated independently: successful
 * records are added, and any bad records are collected and reported back
 * to the user (with the reason) rather than silently skipped or allowed
 * to crash the upload.
 * @author jevongilliam
 */
public class UploadFileWindow extends JFrame{
    private ArrayList<Manager> managers;
    private ArrayList<Room> rooms;

    private JTextField text1;
    private JRadioButton rButt1, rbutt2;
    private JButton butt1;

    /**
     * Builds and displays the Upload window.
     * @param m the shared list of managers to upload into
     * @param r the shared list of rooms to upload into
     */
    public UploadFileWindow(ArrayList<Manager> m, ArrayList<Room> r)
    {
        managers = m;
        rooms = r;
        setTitle("Upload Database from File");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        setVisible(true);
    }

    /**
     * Lays out the file name field, file type selector, and Upload button.
     */
    public void buildPanel()
    {
        JPanel panel = new JPanel();
            Color myPurple2 = new Color(204, 195, 255);
                panel.setBackground(myPurple2);

        JLabel label1 = new JLabel("Enter File Name:");
            panel.add(label1);

        text1 = new JTextField(20);
            panel.add(text1);

        JLabel label2 = new JLabel("File Type");
            panel.add(label2);

        ButtonGroup bg = new ButtonGroup();

        rButt1 = new JRadioButton("Room");
            rButt1.setSelected(true);
                bg.add(rButt1);
                    panel.add(rButt1);

        rbutt2 = new JRadioButton("Manager");
            bg.add(rbutt2);
                panel.add(rbutt2);

        butt1 = new JButton("Upload");
            butt1.addActionListener(new ButtonHandler());
                panel.add(butt1);

        add(panel);

    }

    /**
     * Handles the Upload button click: reads the selected file line by
     * line, attempts to parse each line as either a Room or a Manager
     * depending on the selected file type, and reports both the count
     * and the specific reason for every record that failed to parse.
     */
    public class ButtonHandler implements ActionListener
    {
        /**
         * Reads and processes the selected file.
         * @param e the button click event
         */
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() != butt1)
            {
                return;
            }

            String input = text1.getText().trim();
            if(input.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please enter a file name.");
                return;
            }

            File f = new File(input);
            int successCount = 0;
            ArrayList<String> badRecords = new ArrayList<>();

            try (Scanner fileInput = new Scanner(f))
            {
                while(fileInput.hasNextLine())
                {
                    String rawLine = fileInput.nextLine();
                    if(rawLine.trim().isEmpty())
                    {
                        continue;
                    }

                    // Data is comma-separated and prices are written with a
                    // leading "$" (e.g. "100, Standard, $98.25, 109001"), so
                    // split on commas and strip "$" before parsing numbers.
                    String[] line = rawLine.split(",");
                    for(int i = 0; i < line.length; i++)
                    {
                        line[i] = line[i].trim().replace("$", "");
                    }

                    try
                    {
                        if(rButt1.isSelected())
                        {
                            if(line[1].equalsIgnoreCase("Connected"))
                            {
                                // Format: roomNum, Connected, connectedRoomNum, price, managerBadge[, amenities...]
                                int rN = Integer.parseInt(line[0]);
                                int cRN = Integer.parseInt(line[2]);
                                float rP = Float.parseFloat(line[3]);
                                int mR = Integer.parseInt(line[4]);

                                rooms.add(new ConnectedRoom(rN, "CONNECTED", rP, mR, cRN));
                            }
                            else
                            {
                                // Format: roomNum, type, price, managerBadge[, amenities...]
                                int rN = Integer.parseInt(line[0]);
                                String rT = line[1];
                                float rP = Float.parseFloat(line[2]);
                                int mR = Integer.parseInt(line[3]);

                                rooms.add(new Room(rN, rT, rP, mR));
                            }
                        }
                        else if(rbutt2.isSelected())
                        {
                            // Format: lastName, firstName, username, password, badgeNum
                            String ln = line[0];
                            String fn = line[1];
                            String un = line[2];
                            String pw = line[3];
                            int bn = Integer.parseInt(line[4]);

                            managers.add(new Manager(ln, fn, un, pw, bn));
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Please select a file type (Room or Manager).");
                            return;
                        }
                        successCount++;
                    }
                    catch(NumberFormatException ex)
                    {
                        badRecords.add(rawLine + "  ->  invalid number: " + ex.getMessage());
                    }
                    catch(ArrayIndexOutOfBoundsException ex)
                    {
                        badRecords.add(rawLine + "  ->  missing field(s)");
                    }
                    catch(HotelSystemException ex)
                    {
                        badRecords.add(rawLine + "  ->  " + ex.getMessage());
                    }
                }

                showReport(successCount, badRecords);
            }
            catch (FileNotFoundException ex)
            {
                JOptionPane.showMessageDialog(null, "File Not Found");
            }
        }
    }

    /**
     * Displays a summary of the upload, including the count of records
     * successfully added and a scrollable, itemized list of every record
     * that was rejected along with the reason it failed.
     * @param successCount number of records successfully uploaded
     * @param badRecords the raw text and failure reason for each rejected record
     */
    private void showReport(int successCount, ArrayList<String> badRecords)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(successCount).append(" record(s) uploaded successfully.\n");
        sb.append(badRecords.size()).append(" record(s) rejected.\n");

        if(!badRecords.isEmpty())
        {
            sb.append("\nRejected records:\n");
            for(String bad : badRecords)
            {
                sb.append(bad).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 250));

        JOptionPane.showMessageDialog(null, scrollPane, "Upload Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
