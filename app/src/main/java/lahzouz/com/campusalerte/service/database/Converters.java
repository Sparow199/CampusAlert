package lahzouz.com.campusalerte.service.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Classe qui permet d'aplatir les objets du type complexes afin de les stocker en base de données.
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
