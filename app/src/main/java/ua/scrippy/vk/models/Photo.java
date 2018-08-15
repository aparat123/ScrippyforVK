package ua.scrippy.vk.models;

public class Photo {

    private int photoId;
    private String photoUrl;
    private String photoBigUrl;

    public int getPhotoId() {
        return photoId;
    }
    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoBigUrl() {
        return photoBigUrl;
    }

    public void setPhotoBigUrl(String photoBigUrl) {
        this.photoBigUrl = photoBigUrl;
    }

}
