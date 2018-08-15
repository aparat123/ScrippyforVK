package ua.scrippy.vk.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UsersModel {

    @SerializedName("response")
    private ArrayList<ResponseUserModel> response = new ArrayList<ResponseUserModel>(
    );
    public ArrayList<ResponseUserModel> getResponse() {
        return response;
    }
    public void setResponse(ArrayList<ResponseUserModel> response) {
        this.response = response;
    }


}
