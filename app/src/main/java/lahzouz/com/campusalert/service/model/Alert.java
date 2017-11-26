package lahzouz.com.campusalert.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import lahzouz.com.campusalert.service.database.Converters;

@Entity(tableName = "alert_table")
public class Alert {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alert_id")
    public long id;
    @ColumnInfo(name = "alert_type")
    private String type;
    @ColumnInfo(name = "alert_desc")
    private String description;
    @ColumnInfo(name = "alert_adress")
    private String adress;
    @ColumnInfo(name = "alert_lati")
    private double latitude;
    @ColumnInfo(name = "alert_long")
    private double longitude;
    @ColumnInfo(name = "alert_date")
    @TypeConverters({Converters.class})
    private Date created_at;


    /***************************************************************************************************
     * Generated
     ***************************************************************************************************/
    @Ignore
    public Alert() {
//        longitude = -1;
//        latitude = -2;
    }

    public Alert(String type, String description, String adress, double latitude, double longitude, Date created_at) {
        this.type = type;
        this.description = description;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", adress='" + adress + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", created_at=" + created_at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alert alert = (Alert) o;

        if (getType() != null ? !getType().equals(alert.getType()) : alert.getType() != null)
            return false;
        return getAdress() != null ? getAdress().equals(alert.getAdress()) : alert.getAdress() == null;
    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getAdress() != null ? getAdress().hashCode() : 0);
        return result;
    }
}
