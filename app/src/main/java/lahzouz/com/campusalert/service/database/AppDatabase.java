package lahzouz.com.campusalert.service.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import lahzouz.com.campusalert.service.model.Alert;

/**
 * Classe abstraite qui gère la création d'une instance du DAO.
 */
@Database(entities = {Alert.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    /**
     * Création d'une instance de la base de données.
     * @param context
     * @return AppDatabase
     */
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "alert-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
//                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    /**
     * Destruction de l'instance de la base de données.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Permets de retourner l'alertModel une fois crée.
     * @return AlertDao
     */
    public abstract AlertDao AlertModel();
}
