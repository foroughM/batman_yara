package testproject.yara.batman.data.datasource.localdatasource;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import testproject.yara.batman.data.model.VideoRate;

public class RoomConverter {

    public RoomConverter() {
    }

    @TypeConverter
    public static List<VideoRate> stringToVideoRateList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<VideoRate>>() {
        }.getType();
        Gson gson = new Gson();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String videoRateListToString(List<VideoRate> videoRates) {
        Gson gson = new Gson();
        return gson.toJson(videoRates);
    }
}
