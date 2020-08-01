package testproject.yara.batman.data.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import testproject.yara.batman.data.model.RemoteResponse;
import testproject.yara.batman.data.model.VideoDetails;

public interface BatmanApi {

    @GET("/")
    Call<RemoteResponse> getVideos(@Query("apikey") String apiKey, @Query("s") String serviceName);

    @GET("/")
    Call<VideoDetails> getVideoDetails(@Query("apikey") String apiKey,
                                             @Query("i") String imdbId);

}
