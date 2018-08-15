package ua.scrippy.vk.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePhoto {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<PhotoItem> items = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }

}