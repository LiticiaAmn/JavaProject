/**
 * Cette classe permet d'effectuer un paiement.
 *
 * @author Liticia
 * @version 1.0
 * @since Java 17
 */
public class Paiement {

    public static void effectuerPaiement(String nom, String numeroCarte) throws PaiementInvalideException {
        // Vérification des champs vides
        if (nom == null || nom.isEmpty() || numeroCarte == null || numeroCarte.isEmpty()) {
            throw new PaiementInvalideException("Les informations de paiement sont incomplètes.");
        }

        // Vérification basique du numéro de carte (longueur, uniquement des chiffres)
        if (!numeroCarte.matches("\\d{16}")) {
            throw new PaiementInvalideException("Le numéro de carte est invalide. Il doit contenir 16 chiffres.");
        }

        // Simuler une validation du paiement (par exemple avec un service externe)
        System.out.println("Paiement validé pour : " + nom);
    }
}
