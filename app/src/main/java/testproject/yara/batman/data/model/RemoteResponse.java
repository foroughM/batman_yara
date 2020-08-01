package testproject.yara.batman.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoteResponse {

    @SerializedName("Search")
    private List<Video> responseData;

    public List<Video> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<Video> responseData) {
        this.responseData = responseData;
    }
}
