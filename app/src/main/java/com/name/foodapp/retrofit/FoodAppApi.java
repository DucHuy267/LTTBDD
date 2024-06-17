package com.name.foodapp.retrofit;

import com.name.foodapp.model.CategoryModel;
import com.name.foodapp.model.MealDetailModel;
import com.name.foodapp.model.MealModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodAppApi {
    @GET("category.php")
    Call<CategoryModel> getCategory();

    @POST("meal.php")
    @FormUrlEncoded
    Call<MealModel> getMeals(
            @Field("idcate") int idcate
    );

    @POST("mealdetail.php")
    @FormUrlEncoded
    Call<MealDetailModel> getMealsDetail(
            @Field("id") int id
    );
}
