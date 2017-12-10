package lahzouz.com.campusalert.service.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Classe qui permet d'applatir les objets de type complexe afin de les stockés en base de données.
 */
public class Converters {
    /**
     * Convertie un Long vers une Date.
     * @param value
     * @return Date
     */
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Convertie une date vers un long.
     * @param date
     * @return Long
     */
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
