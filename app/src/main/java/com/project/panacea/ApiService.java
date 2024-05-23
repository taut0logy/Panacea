package com.project.panacea;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer sk-proj-ps9oypJH3qrJIT2t4lS0T3BlbkFJ3cwQD2kcmcpEfX57TjPw"})
    @POST("g/g-JNKJcECyp-doctor-finder")
    Call<ResponseBody> getCompletion(@Body CompletionRequest request);

}
