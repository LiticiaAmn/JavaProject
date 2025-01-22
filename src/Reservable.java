interface Reservable {
    boolean reserver(int nombreDeTickets) throws PlacesInsuffisantesException;
}