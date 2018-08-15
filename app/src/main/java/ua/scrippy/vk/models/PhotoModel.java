package ua.scrippy.vk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoModel {

    @SerializedName("response")
    @Expose
    private ResponsePhoto response;

    public ResponsePhoto getResponse() {
        return response;
    }

    public void setResponse(ResponsePhoto response) {
        this.response = response;
    }

}
