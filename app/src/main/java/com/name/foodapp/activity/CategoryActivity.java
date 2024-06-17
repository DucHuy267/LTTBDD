package com.name.foodapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.name.foodapp.R;
import com.name.foodapp.adapters.MealAdapter;
import com.name.foodapp.databinding.ActivityCategoryBinding;
import com.name.foodapp.listener.CategoryItemListener;
import com.name.foodapp.model.Meals;
import com.name.foodapp.viewModel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    CategoryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_category);
        initData();
        initView();

        // Sử dụng View Binding để tham chiếu và đặt sự kiện OnClickListener cho nút tvback
        binding.tvback.setOnClickListener(v -> {
            // Quay lại
            finish();
        });

    }
    private void initView() {
        binding.rcCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcCategory.setLayoutManager(layoutManager);
    }
    private void initData() {
        int idcate = getIntent().getIntExtra("idcate", 1);
        String namecate = getIntent().getStringExtra("namecate");
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.mealModelMutableLiveData(idcate).observe(this, mealModel -> {
            if (mealModel.isSuccess()){
                MealAdapter adapter = new MealAdapter(mealModel.getResult(),this::onMealClick);
                binding.rcCategory.setAdapter(adapter);
                binding.tvname.setText(namecate + ": " + mealModel.getResult().size());
            }
        });
    }

    private void onMealClick(Meals meals) {
        Intent intent = new Intent(getApplicationContext(), ShowDetailActivity.class);
        intent.putExtra("id", meals.getIdMeal());
        startActivity(intent);
    }
}