package lahzouz.com.campusalert.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import lahzouz.com.campusalert.service.database.Converters;

import java.util.Date;

@Entity
public class Alert {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "alert_name")
    public String name;
    public String description;
    @TypeConverters({Converters.class})
    public Date created_at;
    @TypeConverters({Converters.class})
    public Date updated_at;
    public String git_url;
    @ColumnInfo(name = "programing_langage")
    public String language;
    public int open_issues;
    public int watchers;
    public String clone_url;

    public Alert(String name, String description, Date created_at, Date updated_at, String git_url, String language, int open_issues, int watchers, String clone_url) {
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.git_url = git_url;
        this.language = language;
        this.open_issues = open_issues;
        this.watchers = watchers;
        this.clone_url = clone_url;
    }

    @Ignore
    public Alert() {
    }
    @Ignore
    public Alert(String name) {
        this.name = name;
    }
}
