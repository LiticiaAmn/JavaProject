class Reservation {
    private Evenement evenement;
    private int nombreDeTickets;

    public Reservation(Evenement evenement, int nombreDeTickets) {
        this.evenement = evenement;
        this.nombreDeTickets = nombreDeTickets;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public int getNombreDeTickets() {
        return nombreDeTickets;
    }
}
