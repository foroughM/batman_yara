package testproject.yara.batman.data.datasource;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import testproject.yara.batman.data.model.VideoDetails;

import static androidx.room.OnConflictStrategy.REPLACE;
import static testproject.yara.batman.data.Constants.LOCAL_TIMEOUT_IN_MILLISEC;

@Dao()
public abstract class VideoDetailsDao {

    @Query("SELECT * FROM video_details WHERE imdbId = :imdbId")
    abstract LiveData<VideoDetails> getVideoDetails(String imdbId);

    @Insert(onConflict = REPLACE)
    abstract void saveAll(VideoDetails videoDetails);

    @Query("DELETE FROM video_details WHERE imdbId = :imdbId")
    abstract void delete(String imdbId);

    boolean isExpired(VideoDetails videoDetails) {
        if (videoDetails == null)
            return true;
        return videoDetails.getUpdateDate() <= System.currentTimeMillis() - LOCAL_TIMEOUT_IN_MILLISEC;
    }

}
