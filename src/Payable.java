/**
 * Interface permettant d'effectuer le paiement.
 *
 * @author Julie
 * @version 1.0
 * @since Java 17
 */

interface Payable {
    void effectuerPaiement(String detailsPaiement) throws PaiementInvalideException;
}
