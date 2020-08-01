package testproject.yara.batman.data.datasource.localdatasource;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import testproject.yara.batman.data.model.Video;
import testproject.yara.batman.data.model.VideoDetails;

import static testproject.yara.batman.data.Constants.ROOM_DB_NAME;
import static testproject.yara.batman.data.Constants.ROOM_DB_VERSION;

@Database(entities = {Video.class, VideoDetails.class},
        version = ROOM_DB_VERSION,
        exportSchema = false)
@TypeConverters(RoomConverter.class)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB instance;

    public static RoomDB getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, ROOM_DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract VideoDao videoDao();

    public abstract VideoDetailsDao videoDetailsDao();

}
