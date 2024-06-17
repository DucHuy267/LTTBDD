package com.name.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.name.foodapp.model.MealModel;
import com.name.foodapp.repository.MealRepository;

public class CategoryViewModel extends ViewModel {
    private MealRepository mealRepository;

    public CategoryViewModel(){
        mealRepository = new MealRepository();
    }
    public MutableLiveData<MealModel> mealModelMutableLiveData(int idcate){
        return mealRepository.getMeals(idcate);
    }
}
