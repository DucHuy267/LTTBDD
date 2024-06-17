package com.name.foodapp.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.name.foodapp.R;
import com.name.foodapp.Utils.Utils;
import com.name.foodapp.adapters.CartAdapter;
import com.name.foodapp.databinding.ActivityCartBinding;
import com.name.foodapp.listener.ChangeNumListener;
import com.name.foodapp.model.Cart;
import java.util.ArrayList;
import java.util.List;
import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        Paper.init(this);
        initView();
        initData();
        totalPrice();
    }

    private void initData() {
        List<Cart> carts = Paper.book().read("Cart");
        if (carts == null) {
            carts = new ArrayList<>();
        }
        Utils.cartList = carts;

        CartAdapter adapter = new CartAdapter(this, Utils.cartList, new ChangeNumListener() {
            @Override
            public void change() {
                totalPrice();
            }
        });

        binding.recyclercart.setAdapter(adapter);
    }

    private void totalPrice() {
        if (Utils.cartList == null || Utils.cartList.isEmpty()) {
            binding.txtitem.setText("0");
            binding.txtprice.setText("0đ");
            return;
        }
            int item = 0;
            for (int i = 0; i < Utils.cartList.size(); i++) {
                item = item + Utils.cartList.get(i).getAmount();
            }
            binding.txtitem.setText(String.valueOf(item));
            double price = 0.0;
            for (int i = 0; i < Utils.cartList.size(); i++) {
                price = price + (Utils.cartList.get(i).getAmount() * Utils.cartList.get(i).getMealDetail().getPrice());
            }
            binding.txtprice.setText("đ" + String.valueOf(price));

    }

    private void initView() {
        binding.recyclercart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclercart.setLayoutManager(layoutManager);
    }
}
