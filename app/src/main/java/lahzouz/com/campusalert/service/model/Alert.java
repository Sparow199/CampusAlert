package lahzouz.com.campusalert.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import lahzouz.com.campusalert.service.database.Converters;

/**
 * Classe modèle, permets de persister l'objet Alert.
 */
@Entity(tableName = "alert_table")
public class Alert {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alert_id")
    public Long id;
    @ColumnInfo(name = "alert_type")
    private String type;
    @ColumnInfo(name = "alert_desc")
    private String description;
    @ColumnInfo(name = "alert_address")
    private String address;
    @ColumnInfo(name = "alert_lati")
    private double latitude;
    @ColumnInfo(name = "alert_Long")
    private double Longitude;
    @ColumnInfo(name = "alert_date")
    @TypeConverters({Converters.class})
    private Date created_at;

    /**
     * Constructeur par défault
     */
    @Ignore
    public Alert() {
        Longitude = -404;
        latitude = -404;
    }

    /**
     * Constructeur avec paramètres.
     * @param type
     * @param description
     * @param address
     * @param latitude
     * @param Longitude
     * @param created_at
     */
    public Alert(String type, String description, String address, double latitude, double Longitude, Date created_at) {
        this.type = type;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.Longitude = Longitude;
        this.created_at = created_at;
    }

    /**
     * Getter 
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter
     * @return double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter
     * @return Double
     */
    public double getLongitude() {
        return Longitude;
    }

    /**
     * Setter
     * @param Longitude
     */
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    /**
     * Getter
     * @return Date
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Setter
     * @param created_at
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * Getter
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * ToString
     * @return String
     */
    @Override
    public String toString() {
        return "Alert{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", Longitude=" + Longitude +
                ", created_at=" + created_at +
                '}';
    }

    /**
     * Equals
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alert alert = (Alert) o;

        return getType().equals(alert.getType()) && getAddress().equals(alert.getAddress());
    }

    /**
     * HashCode
     * @return int
     */
    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
