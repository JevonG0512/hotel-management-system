package hotelsystem;

/**
 * Custom checked exception used to signal invalid hotel-system data,
 * such as an unrecognized room type or an invalid numeric value.
 * @author jevongilliam
 */
public class HotelSystemException extends Exception {

    /**
     * Creates a new HotelSystemException with a descriptive message.
     * @param msg a description of what data was invalid
     */
    public HotelSystemException(String msg)
    {
        super(msg);
    }

}
