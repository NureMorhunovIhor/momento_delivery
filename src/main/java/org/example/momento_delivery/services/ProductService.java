package org.example.momento_delivery.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.ProductRequestDTO;
import org.example.momento_delivery.entities.*;
import org.example.momento_delivery.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductVariantRepository variantRepo;
    private final IngredientRepository ingredientRepo;
    private final ProductIngredientRepository productIngredientRepo;

    @Transactional
    public Product createProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getBasePrice());
        product.setImageUrl(dto.getImageUrl());
        product.setIsActive(true);

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорія не знайдена"));
        product.setCategory(category);

        productRepo.save(product);

        // Варіації
        if (dto.getVariants() != null) {
            for (String variant : dto.getVariants()) {
                String[] parts = variant.split(":");
                if (parts.length == 2) {
                    ProductVariant pv = new ProductVariant();
                    pv.setProduct(product);
                    pv.setLabel(parts[0].trim());
                    pv.setPrice(new BigDecimal(parts[1].trim()));
                    variantRepo.save(pv);
                }
            }
        }

        // Інгредієнти
        if (dto.getIngredientIds() != null) {
            for (Integer ingId : dto.getIngredientIds()) {
                Ingredient ing = ingredientRepo.findById(ingId)
                        .orElseThrow(() -> new RuntimeException("Інгредієнт не знайдено"));
                productIngredientRepo.save(new ProductIngredient(product, ing));
            }
        }

        return product;
    }
    @Transactional
    public Product updateProduct(Integer id, ProductRequestDTO dto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не знайдено"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getBasePrice());
        product.setImageUrl(dto.getImageUrl());

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорія не знайдена"));
        product.setCategory(category);

        // Оновлення варіацій
        variantRepo.deleteAllByProduct(product);
        if (dto.getVariants() != null) {
            for (String variant : dto.getVariants()) {
                String[] parts = variant.split(":");
                if (parts.length == 2) {
                    ProductVariant pv = new ProductVariant();
                    pv.setProduct(product);
                    pv.setLabel(parts[0].trim());
                    pv.setPrice(new BigDecimal(parts[1].trim()));
                    variantRepo.save(pv);
                }
            }
        }

        // Оновлення інгредієнтів
        productIngredientRepo.deleteAllByProduct(product);
        if (dto.getIngredientIds() != null) {
            for (Integer ingId : dto.getIngredientIds()) {
                Ingredient ing = ingredientRepo.findById(ingId)
                        .orElseThrow(() -> new RuntimeException("Інгредієнт не знайдено"));
                productIngredientRepo.save(new ProductIngredient(product, ing));
            }
        }

        return product;
    }
    @Transactional
    public void deleteProduct(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не знайдено"));
        productRepo.delete(product);
    }

}
