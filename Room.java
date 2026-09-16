package hotelsystem;

import java.util.*;

/**
 * A hotel room with a room number, type, price, and responsible Manager.
 * @author jevongilliam
 */
public class Room implements Comparable<Room> {

    public enum RoomType {STANDARD, DOUBLE, KING, CONNECTED}
    private int roomNum;
    private RoomType roomTy;
    private float price;
    private int resManager;

    /**
     * A hotel room with a room number, type, price, and responsible Manager.
     * @param rn The Room Number
     * @param rt The Room Type
     * @param p The Room Price
     * @param rm The Room Manager badge number
     * @throws HotelSystemException if the room type is not recognized,
     *         or the room number/price are not valid positive values
     */
    public Room(int rn, String rt, float p, int rm) throws HotelSystemException
    {
        if (rn <= 0)
        {
            throw new HotelSystemException("Room number must be a positive number: " + rn);
        }
        if (p <= 0)
        {
            throw new HotelSystemException("Room price must be a positive number: " + p);
        }
        try
        {
            roomTy = RoomType.valueOf(rt.toUpperCase().trim());
        }
        catch (IllegalArgumentException ex)
        {
            throw new HotelSystemException("Invalid room type: " + rt);
        }
        roomNum = rn;
        price = p;
        resManager = rm;
    }

    /**
     * Gets the room number.
     * @return the room number
     */
    public int getRoomNum() {return roomNum;}

    /**
     * Gets the room type.
     * @return the room type
     */
    public RoomType getRoomType() {return roomTy;}

    /**
     * Gets the room price.
     * @return the price
     */
    public float getPrice() {return price;}

    /**
     * Gets the badge number of the manager responsible for this room.
     * @return the manager's badge number
     */
    public int getResManager() {return resManager;}

    /**
     * A String representation of the Room.
     * @return the formatted room details
     */
    public String toString()
    {
        return String.format("Room Number: %d\n "
                + "Room Type: %s\n"
                + "Room Price: %.2f\n"
                + "Room Manager: %d\n", roomNum, roomTy, price, resManager);
    }

    /**
     * A compact, single-line representation of the Room, used for
     * indented listings such as a manager's assigned rooms.
     * @return a one-line summary of the room
     */
    public String toSummaryString()
    {
        return String.format("Room %d (%s) - $%.2f", roomNum, roomTy, price);
    }

    /**
     * Rooms compared by price.
     * @param otherRoom a room to compare
     * @return a negative, positive, or zero value
     */
    public int compareTo(Room otherRoom)
    {
        return Float.compare(price, otherRoom.price);
    }
}
