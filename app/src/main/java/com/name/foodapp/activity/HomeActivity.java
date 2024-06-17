package com.name.foodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.name.foodapp.R;
import com.name.foodapp.adapters.CategoryAdapter;
import com.name.foodapp.adapters.ImageAdapter;
import com.name.foodapp.adapters.PopularAdapter;

import com.name.foodapp.databinding.ActivityHomeBinding;

import com.name.foodapp.listener.CategoryListener;
import com.name.foodapp.listener.EventClickListener;
import com.name.foodapp.model.Category;
import com.name.foodapp.model.Meals;
import com.name.foodapp.viewModel.HomeViewModel;

public class HomeActivity extends AppCompatActivity implements CategoryListener, EventClickListener {
    ActivityHomeBinding binding;
    HomeViewModel homeViewModel;
    //Image
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_home);
        initData();
        initView();

        int[] images = {R.drawable.img_3, R.drawable.img_4, R.drawable.img}; // Thêm các hình ảnh cần hiển thị
        // Image
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new ImageAdapter(this, images);
        binding.recyclerView.setAdapter(mAdapter); // Sử dụng binding.recyclerView thay vì mRecyclerView
    }
    private void initView() {
        binding.rcCategories.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcCategories.setLayoutManager(layoutManager);

        binding.rcPopular.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        binding.rcPopular.setLayoutManager(layoutManager1);

        binding.floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(cart);
            }
        });
    }

    private void initData() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.categoryModelMutableLiveData().observe(this, categoryModel -> {
            if (categoryModel.isSuccess()){
                CategoryAdapter adapter = new CategoryAdapter(categoryModel.getResult(), this);
                binding.rcCategories.setAdapter(adapter);

            }
        });
        homeViewModel.mealsModelMutableLiveData(1).observe(this,mealsModel -> {
            if (mealsModel.isSuccess()) {
                PopularAdapter adapter = new PopularAdapter(mealsModel.getResult(),this);
                binding.rcPopular.setAdapter(adapter);
            }
        });
    }
    @Override
    public void onCategoryClick(Category category) {
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        intent.putExtra("idcate", category.getId());
        intent.putExtra("namecate", category.getCategory());
        startActivity(intent);
    }

    @Override
    public void onPopularClick(Meals meals) {
        Intent intent = new Intent(getApplicationContext(), ShowDetailActivity.class);
        intent.putExtra("id", meals.getIdMeal());
        intent.putExtra("strMeal", meals.getIdMeal());
        startActivity(intent);
    }
}