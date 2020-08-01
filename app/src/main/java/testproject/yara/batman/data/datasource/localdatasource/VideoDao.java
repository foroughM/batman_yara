package testproject.yara.batman.data.datasource.localdatasource;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import testproject.yara.batman.data.model.Video;

import static androidx.room.OnConflictStrategy.REPLACE;
import static testproject.yara.batman.data.Constants.LOCAL_TIMEOUT_IN_MILLISEC;

@Dao()
public abstract class VideoDao {

    @Query("SELECT * FROM videos")
    public abstract LiveData<List<Video>> getVideos();

    @Insert(onConflict = REPLACE)
    public abstract void saveAll(List<Video> videos);

    @Query("DELETE FROM videos")
    public abstract void deleteAll();

    public boolean isExpired(List<Video> videos) {
        if (videos == null || videos.isEmpty())
            return true;
        return videos.get(0).getUpdateDate() <= System.currentTimeMillis() - LOCAL_TIMEOUT_IN_MILLISEC;
    }

}
