package lahzouz.com.campusalert.view.callback;

import lahzouz.com.campusalert.service.model.Alert;

/**
 * Interface qui définit les différents clics sur l'application.
 */
public interface AlertClickCallback {
    /**
     * Afficher le détail d'une alerte.
     * @param alert
     */
    void onClick(Alert alert);

    /**
     * Supprimer une alerte.
     * @param alert
     */
    void onDeleteClick(Alert alert);

    /**
     * Enregistrer une alerte.
     * @param alert
     */
    void onSaveClick(Alert alert);

    /**
     * Ouvrir le fragment d'ajout de nouvelle alerte.
     */
    void onAddClick();
}
