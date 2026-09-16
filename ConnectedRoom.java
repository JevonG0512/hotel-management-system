package hotelsystem;

import java.util.*;

/**
 * A Room that is physically connected to another room by room number.
 * @author jevongilliam
 */
public class ConnectedRoom extends Room {
    private int connectedRoomNum;

    /**
     * A Room that is physically connected to another room.
     * @param rn the room number
     * @param rt the room type (expected to be "CONNECTED")
     * @param p the room price
     * @param rm the responsible manager's badge number
     * @param cRN the room number this room connects to
     * @throws HotelSystemException if inherited Room validation fails,
     *         or the connected room number is not a valid positive value
     */
    public ConnectedRoom(int rn, String rt, float p, int rm, int cRN) throws HotelSystemException
    {
        super(rn, rt, p, rm);
        if (cRN <= 0)
        {
            throw new HotelSystemException("Connected room number must be a positive number: " + cRN);
        }
        connectedRoomNum = cRN;
    }

    /**
     * Gets the room number this room is connected to.
     * @return the connected room number
     */
    public int getConnectedRoomNum() {return connectedRoomNum;}

    /**
     * A String representation of the ConnectedRoom, including which room
     * it connects to.
     * @return the formatted room details
     */
    @Override
    public String toString()
    {
        return super.toString() + " Connected Room Number: " + connectedRoomNum + "\n";
    }

    /**
     * A compact, single-line representation of the ConnectedRoom, including
     * which room it connects to.
     * @return a one-line summary of the room
     */
    @Override
    public String toSummaryString()
    {
        return super.toSummaryString() + " [Connected to Room " + connectedRoomNum + "]";
    }
}
