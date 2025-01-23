/**
 * Cette classe est une exception lorsqu'un paiement est invalide.
 *
 * @author Liticia
 * @version 1.0
 * @since Java 17
 */
class PaiementInvalideException extends Exception {
    public PaiementInvalideException(String message) {
        super(message);
    }
}
