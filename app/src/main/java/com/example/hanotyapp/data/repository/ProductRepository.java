package com.example.hanotyapp.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hanotyapp.data.local.AppDatabase;
import com.example.hanotyapp.data.local.ProductDao;
import com.example.hanotyapp.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {

    private final ProductDao productDao;
    private final ExecutorService executorService;

    public ProductRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        productDao = db.productDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // LiveData pour observer la liste des produits
    private final MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();

    public LiveData<List<Product>> getProductsLiveData() {
        loadProducts(); // charger les produits à l'initialisation
        return productsLiveData;
    }

    private void loadProducts() {
        executorService.execute(() -> {
            List<Product> products = productDao.getAllProducts();
            productsLiveData.postValue(products);
        });
    }

    public void insert(Product product) {
        executorService.execute(() -> {
            productDao.insert(product);
            loadProducts();
        });
    }

    public void update(Product product) {
        executorService.execute(() -> {
            productDao.update(product);
            loadProducts();
        });
    }

    public void delete(Product product) {
        executorService.execute(() -> {
            productDao.delete(product);
            loadProducts();
        });
    }
}
