package testproject.yara.batman.data.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import testproject.yara.batman.data.datasource.localdatasource.RoomDB;
import testproject.yara.batman.data.datasource.localdatasource.VideoDao;
import testproject.yara.batman.data.datasource.localdatasource.VideoDetailsDao;

@Module
public class RoomModule {

    private RoomDB roomDB;

    public RoomModule(Context context) {
        roomDB = RoomDB.getDatabase(context);
    }

    @Provides
    @Singleton
    RoomDB providesRoomDatabase() {
        return roomDB;
    }

    @Provides
    @Singleton
    VideoDao providesVideoDao(RoomDB roomDB) {
        return roomDB.videoDao();
    }

    @Provides
    @Singleton
    VideoDetailsDao providesVideoDetailsDao(RoomDB roomDB) {
        return roomDB.videoDetailsDao();
    }

}
