package testproject.yara.batman.data.datasource;


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
    abstract LiveData<List<Video>> getVideos();

    @Insert(onConflict = REPLACE)
    abstract void saveAll(List<Video> videos);

    @Query("DELETE FROM videos")
    abstract void deleteAll();

    boolean isExpired(List<Video> videos) {
        if (videos == null || videos.isEmpty())
            return true;
        return videos.get(0).getUpdateDate() <= System.currentTimeMillis() - LOCAL_TIMEOUT_IN_MILLISEC;
    }

}
