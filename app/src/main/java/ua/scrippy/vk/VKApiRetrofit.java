package ua.scrippy.vk;

import com.vk.sdk.VKAccessToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.scrippy.vk.models.*;

public interface VKApiRetrofit {
    @GET("/method/users.get")
    Call<UsersModel> getUsers(@Query("user_ids") String user_ids,
                              @Query("access_token") String accessToken,
                              @Query("v") String version);

    @GET("/method/photos.get")
    Call<PhotoModel> getPhotos(@Query("owner_id") String owner_id,
                                     @Query("album_id") String album_id,
                                     @Query("rev") String rev,
                                     @Query("extended") String extended,
                                     @Query("photo_sizes") String photo_sizes,
                                     @Query("count") String count,
                                     @Query("access_token") String accessToken,
                                     @Query("v") String version);
}
