package com.example.restaurants.data.retrofit

import com.example.restaurants.data.response.DetailRestaurantResponse
import com.example.restaurants.data.response.ListRestaurantResponse
import com.example.restaurants.data.response.PostReviewResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("list")
    fun getRestaurants(): Call<ListRestaurantResponse>

    @GET("detail/{id}")
    fun getRestaurantDetail( @Path("id") id: String ): Call<DetailRestaurantResponse>

    @GET("search")
    fun getSearchRestaurants(@Query("q") query: String): Call<ListRestaurantResponse>

    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewResponse>
}