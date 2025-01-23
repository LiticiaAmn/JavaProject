/**
 * Cette classe est une exception lorsqu'il n'y a plus de place.
 *
 * @author Liticia
 * @version 1.0
 * @since Java 17
 */
public class PlacesInsuffisantesException extends Exception {
    public PlacesInsuffisantesException(String message) {
        super(message);
    }
}
