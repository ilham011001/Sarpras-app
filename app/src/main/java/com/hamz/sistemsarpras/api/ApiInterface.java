package com.hamz.sistemsarpras.api;

import com.hamz.sistemsarpras.UploadObject;
import com.hamz.sistemsarpras.model.GetAdmin;
import com.hamz.sistemsarpras.model.GetContact;
import com.hamz.sistemsarpras.model.GetReport;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Hamz on 02/03/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("getReport.php")
    Call<GetReport> getReport(@Field("idUser") String idUser);

    @Multipart
    @POST("upload_image.php")
    Call<UploadObject> upload(@Header("Authorization") String authorization,
                              @PartMap Map<String, RequestBody> map, @Part("id_user") RequestBody idUser,
                              @Part("title") RequestBody title, @Part("desc") RequestBody desc);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("contact.php")
    Call<GetContact> getContact(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("getReportAll.php")
    Call<GetReport> getReportAll(@Field("idUser") String sorting);

    @FormUrlEncoded
    @POST("getAdmin.php?act=projector")
    Call<GetAdmin> getProjector(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("getAdmin.php?act=list_request")
    Call<GetAdmin> getRequestBorrow(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("getAdmin.php?act=list_borrowed")
    Call<GetAdmin> getBorrowed(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("getAdmin.php?act=projector_user")
    Call<GetAdmin> getProjectorUser(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("postRequest.php")
    Call<ResponseBody> postRequest(@Field("id_projector") String id_projector,
                                   @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("postApprove.php")
    Call<ResponseBody> postApprove(@Field("id_projector") String id_pinjam);
}
