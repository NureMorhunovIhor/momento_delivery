package org.example.momento_delivery.services;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.CreateOrderRequestDTO;
import org.example.momento_delivery.dtos.OrderDTO;
import org.example.momento_delivery.dtos.OrderResponseDTO;
import org.example.momento_delivery.entities.*;
import org.example.momento_delivery.repositories.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final AddressRepository addressRepo;
    private final DeliveryZoneRepository zoneRepo;
    private final CartItemIngredientRepository cartItemIngRepo;
    private final OrderItemIngredientRepository orderItemIngRepo;
    private final UserRepository userRepo; // ✅ добавлено
    private final JavaMailSender mailSender;

    public OrderResponseDTO placeOrder(CreateOrderRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId()).orElseThrow();
        Address address = addressRepo.findById(dto.getAddressId()).orElseThrow();

        Cart cart = cartRepo.findByUserId(dto.getUserId()).orElseThrow();
        List<CartItem> cartItems = cartItemRepo.findByCart(cart);

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setPaymentStatus("pending");
        order.setComment(dto.getComment());
        order.setStatus("new");

        Order savedOrder = orderRepo.save(order);

        for (CartItem cItem : cartItems) {
            OrderItem oItem = new OrderItem();
            oItem.setOrder(savedOrder);
            oItem.setProductVariant(cItem.getProductVariant());
            oItem.setQuantity(cItem.getQuantity());

            BigDecimal variantPrice = cItem.getProductVariant().getPrice();
            if (variantPrice == null) variantPrice = BigDecimal.ZERO;

            oItem.setUnitPrice(variantPrice);

            OrderItem savedItem = itemRepo.save(oItem);

            List<Ingredient> ingredients = cartItemIngRepo.findIngredientsByCartItem(cItem);
            for (Ingredient ing : ingredients) {
                orderItemIngRepo.save(new OrderItemIngredient(savedItem, ing));
            }

            BigDecimal ingPrice = ingredients.stream()
                    .map(ing -> ing.getPrice() != null ? ing.getPrice() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal itemTotal = (variantPrice.add(ingPrice))
                    .multiply(BigDecimal.valueOf(cItem.getQuantity()));

            total.set(total.get().add(itemTotal));
        }


        zoneRepo.findByLocationName(address.getCity()).ifPresent(zone -> {
            if (total.get().compareTo(zone.getFreeDeliveryThreshold()) < 0) {
                total.set(total.get().add(zone.getDeliveryPrice()));
            }
        });

        savedOrder.setTotalPrice(total.get());
        orderRepo.save(savedOrder);

        cartItemRepo.deleteAll(cartItems);

        sendConfirmationEmail(user.getEmail(), savedOrder.getId());

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(savedOrder.getId());
        response.setStatus(savedOrder.getStatus());
        response.setTotalPrice(savedOrder.getTotalPrice());
        response.setPaymentMethod(savedOrder.getPaymentMethod());
        response.setComment(savedOrder.getComment());
        response.setCity(address.getCity());
        response.setStreet(address.getStreet());
        response.setHouseNumber(address.getHouseNumber());

        return response;
    }

    private void sendConfirmationEmail(String email, Integer orderId) {
        if (email == null || email.isEmpty()) return;
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject("Підтвердження замовлення");
            msg.setText("Ваше замовлення №" + orderId + " прийнято в обробку.");
            mailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(Integer orderId, String status) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setComment(order.getComment());
            dto.setUserName(order.getUser().getName());
            return dto;
        }).toList();
    }


    public List<Order> getUserOrders(Integer userId) {
        return orderRepo.findByUserId(userId);
    }

    public void sendTestMail(String to) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Тест");
            msg.setText("Привет из MomentoDelivery!");
            mailSender.send(msg);
            System.out.println("Письмо успешно отправлено");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
