package lahzouz.com.campusalerte.service.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import lahzouz.com.campusalerte.service.model.Alert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Interface pour définir les fonctions du DAO.
 */
@Dao
public interface AlertDao {
    /**
     * LiveData qui surveille la liste des alertes disponible dans la base de données.
     * @return List<Alert>
     */
    @Query("SELECT * FROM alert_table")
    LiveData<List<Alert>> getAll();

    /**
     * LiveData qui surveille une alerte en utilisant un id.
     * @param alertId
     * @return Alert
     */
    @Query("SELECT * FROM alert_table WHERE alert_id = :alertId LIMIT 1")
    LiveData<Alert> findOneAlert(Long alertId);

    /**
     * Retourner une alerte  en utilisant un id.
     * @param alertId
     * @return Alert
     */
    @Query("SELECT * FROM alert_table WHERE alert_id = :alertId LIMIT 1")
    Alert getAlert(Long alertId);

    /**
     * Inserer une nouvelle alerte.
     * @param alert
     */
    @Insert(onConflict = REPLACE)
    void insertOne(Alert alert);

    /**
     * Insérer une nouvelle liste d'alertes.
     * @param alerts
     */
    @Insert(onConflict = REPLACE)
    void insertAll(List<Alert> alerts);

    /**
     * Supprimer une alerte.
     * @param alert
     */
    @Delete
    void delete(Alert alert);

    /**
     * Supprimer toutes les alertes.
     * @return int
     */
    @Query("DELETE FROM alert_table")
    int deleteAll();
}