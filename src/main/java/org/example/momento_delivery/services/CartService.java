package org.example.momento_delivery.services;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.AddToCartRequestDTO;
import org.example.momento_delivery.dtos.CartItemDTO;
import org.example.momento_delivery.entities.*;
import org.example.momento_delivery.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductVariantRepository variantRepo;
    private final UserRepository userRepo;
    private final IngredientRepository ingredientRepo;
    private final CartItemIngredientRepository cartItemIngredientRepo;

    public Cart getOrCreateCart(Integer userId) {
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepo.findById(userId).orElseThrow());
                    return cartRepo.save(newCart);
                });
    }

    public CartItemDTO addItem(AddToCartRequestDTO dto) {
        Cart cart = getOrCreateCart(dto.getUserId());
        ProductVariant variant = variantRepo.findById(dto.getVariantId()).orElseThrow();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProductVariant(variant);
        item.setQuantity(dto.getQuantity());
        cartItemRepo.save(item);

        if (dto.getIngredientIds() != null) {
            for (Integer ingId : dto.getIngredientIds()) {
                Ingredient ing = ingredientRepo.findById(ingId).orElseThrow();
                CartItemIngredient link = new CartItemIngredient(item, ing);
                cartItemIngredientRepo.save(link);
            }
        }

        CartItemDTO itemDTO = new CartItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setProductName(variant.getProduct().getName());
        itemDTO.setVariantLabel(variant.getLabel());
        itemDTO.setPrice(variant.getPrice());
        itemDTO.setQuantity(item.getQuantity());

        List<String> ingredientNames = dto.getIngredientIds() == null
                ? List.of()
                : dto.getIngredientIds().stream()
                .map(id -> ingredientRepo.findById(id).orElseThrow().getName())
                .toList();

        itemDTO.setIngredientNames(ingredientNames);

        return itemDTO;
    }



    public void updateItemQuantity(Integer itemId, int newQuantity) {
        CartItem item = cartItemRepo.findById(itemId).orElseThrow();
        if (newQuantity <= 0) cartItemRepo.delete(item);
        else {
            item.setQuantity(newQuantity);
            cartItemRepo.save(item);
        }
    }

    public void removeItem(Integer itemId) {
        cartItemRepo.deleteById(itemId);
    }

    public List<CartItemDTO> getItems(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow();
        List<CartItem> items = cartItemRepo.findByCart(cart);

        return items.stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setId(item.getId());
            dto.setProductName(item.getProductVariant().getProduct().getName());
            dto.setVariantLabel(item.getProductVariant().getLabel());
            dto.setPrice(item.getProductVariant().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setIngredientNames(
                    cartItemIngredientRepo.findIngredientsByCartItem(item).stream()
                            .map(Ingredient::getName)
                            .collect(Collectors.toList())
            );
            return dto;
        }).collect(Collectors.toList());
    }


    public BigDecimal calculateTotal(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow();
        List<CartItem> items = cartItemRepo.findByCart(cart);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {
            BigDecimal variantPrice = item.getProductVariant().getPrice();

            List<Ingredient> ingredients = cartItemIngredientRepo.findIngredientsByCartItem(item);
            BigDecimal extraIngredientsPrice = ingredients.stream()
                    .map(Ingredient::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalForItem = (variantPrice.add(extraIngredientsPrice))
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(totalForItem);
        }

        return total;
    }


}

