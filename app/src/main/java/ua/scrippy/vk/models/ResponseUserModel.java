package ua.scrippy.vk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserModel {

    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;


    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
