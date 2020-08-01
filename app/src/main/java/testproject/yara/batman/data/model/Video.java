package testproject.yara.batman.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "videos")
public class Video {

    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @PrimaryKey
    @NonNull
    @SerializedName("imdbID")
    private String imdbId = "";
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;
    private Long updateDate;

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @NonNull
    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(@NonNull String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
