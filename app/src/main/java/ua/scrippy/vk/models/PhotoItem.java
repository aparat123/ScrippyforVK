package ua.scrippy.vk.models;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoItem {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("album_id")
    @Expose
    private Integer albumId;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("sizes")
    @Expose
    private List<PhotoSize> sizes = new ArrayList<>();
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("date")
    @Expose
    private Integer date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public List<PhotoSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<PhotoSize> sizes) {
        this.sizes = sizes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

}