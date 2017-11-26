package lahzouz.com.campusalert.service.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import lahzouz.com.campusalert.service.model.Alert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by sparow on 10/31/17.
 */

@Dao
public interface AlertDao {
    @Query("SELECT * FROM alert_table")
    LiveData<List<Alert>> getAll();

    @Query("SELECT * FROM alert_table WHERE alert_id = :alertId LIMIT 1")
    LiveData<Alert> findOneAlert(Long alertId);

    @Query("SELECT * FROM alert_table WHERE alert_id = :alertId LIMIT 1")
    Alert getAlert(Long alertId);

    @Insert(onConflict = REPLACE)
    void insertOne(Alert alert);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Alert> alerts);

    @Delete
    void delete(Alert alert);

    @Query("DELETE FROM alert_table")
    int deleteAll();
}