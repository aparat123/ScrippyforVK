package ua.scrippy.vk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ua.scrippy.vk.models.FriendsResponse;

public class FriendsModel {

    @SerializedName("response")
    @Expose
    private FriendsResponse friendsResponse;

    public FriendsResponse getResponse() {
        return friendsResponse;
    }

    public void setResponse(FriendsResponse friendsResponse) {
        this.friendsResponse = friendsResponse;
    }

}