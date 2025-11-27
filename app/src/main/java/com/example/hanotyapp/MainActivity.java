package com.example.hanotyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanotyapp.model.Product;
import com.example.hanotyapp.ui.product.ProductAdapter;
import com.example.hanotyapp.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this, products -> adapter.setProducts(products));

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            // Exemple d'ajout de produit
            Product product = new Product("Produit Demo", 10.0, 5);
            productViewModel.insert(product);
        });
    }
}
