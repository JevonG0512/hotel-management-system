package hotelsystem;

import  static hotelsystem.Manager.nextBadgeNum;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * The main menu window for the Hotel System. Holds the shared lists of
 * Managers and Rooms and launches the Upload, Add Manager, Add Room, and
 * Download Statistics windows.
 * @author jevongilliam
 */
public class HotelSystemWindow extends JFrame{

    protected ArrayList<Manager> managers = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();

    private JButton butt1, butt2, butt3, butt4;

    /**
     * Builds and displays the main Hotel System window.
     */
    public HotelSystemWindow()
    {
        setTitle("Hotel System");
        setSize(250,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        JOptionPane.showMessageDialog(null, "Welcome to CSCU's Hotel System. "
                + "Please choose from the following options");
        setVisible(true);
    }

    /**
     * Routes each main-menu button click to the corresponding sub-window.
     */
    public class ButtonHandler implements ActionListener
    {
        /**
         * Opens the sub-window matching the clicked button.
         * @param e the button click event
         */
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton) e.getSource();

            if(button == butt1)
            {
                UploadFileWindow ufw = new UploadFileWindow(managers, rooms);
            }
            if(button == butt2)
            {
                AddManagerWindow amw = new AddManagerWindow(managers);
            }
            if(button == butt3)
            {
                AddRoomWindow arm = new AddRoomWindow(rooms, managers);
            }
            if(button == butt4)
            {
                DownloadStatsWindow dsw = new DownloadStatsWindow(managers, rooms);
            }
        }
    }

    /**
     * Lays out the four main-menu buttons.
     */
    public void buildPanel()
    {
        JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4,1));
                    Color myPurple = new Color(240, 225, 255);
                        panel.setBackground(myPurple);

        ButtonGroup bg = new ButtonGroup();

        butt1 = new JButton("1. Upload Database from File");
                bg.add(butt1);
                    butt1.addActionListener(new ButtonHandler());
                        panel.add(butt1);

        butt2 = new JButton("2. Add New Manager");
            bg.add(butt2);
                butt2.addActionListener(new ButtonHandler());
                    panel.add(butt2);

        butt3 = new JButton("3. Add New Room");
            bg.add(butt3);
                butt3.addActionListener(new ButtonHandler());
                    panel.add(butt3);

        butt4 = new JButton("4. Download Statistics");
            bg.add(butt4);
                butt4.addActionListener(new ButtonHandler());
                    panel.add(butt4);

        add(panel);
    }

    /**
     * Adds a manager to the shared managers list.
     * @param m the manager to add
     */
    public void addManager(Manager m)
    {
        managers.add(m);
    }

    /**
     * Adds a room to the shared rooms list.
     * @param r the room to add
     * @throws HotelSystemException never thrown here; declared for
     *         compatibility with callers that construct a Room
     */
    public void addRoom(Room r) throws HotelSystemException
    {
        rooms.add(r);
    }

    /**
     * Gets the next badge number that will be assigned to a new manager.
     * @return the next available badge number
     */
    public int getNextBadgeNum()
    {
        return nextBadgeNum;
    }

    /**
     * Launches the Hotel System application.
     * @param args not used
     */
    public static void main(String[] args) {

        HotelSystemWindow hsw = new HotelSystemWindow();

    }


}
