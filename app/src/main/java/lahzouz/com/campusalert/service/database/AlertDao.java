package lahzouz.com.campusalert.service.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import lahzouz.com.campusalert.service.model.Alert;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by sparow on 10/31/17.
 */

@Dao
public interface AlertDao {
    @Query("SELECT * FROM Alert")
    LiveData<List<Alert>> getAll();

    @Query("SELECT *FROM Alert WHERE alert_name = :alertName LIMIT 1")
    LiveData<Alert> findOneProject(String alertName);

    @Query("SELECT * FROM Alert WHERE id IN (:alertIds)")
    List<Alert> loadAllByIds(int[] alertIds);

    @Insert(onConflict = REPLACE)
    void insertOne(Alert alert);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Alert> alerts);

    @Delete
    void delete(Alert alert);

    @Query("DELETE FROM Alert")
    int deleteAll();
}