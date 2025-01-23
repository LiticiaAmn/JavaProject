/**
 * Interface permettant d'effectuer une réservation.
 *
 * @author Mady
 * @version 1.0
 * @since Java 17
 */

interface Reservable {
    boolean reserver(int nombreDeTickets) throws PlacesInsuffisantesException;
}