package com.BackEcom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackEcom.model.Cart;
import com.BackEcom.model.CartItem;
import com.BackEcom.model.Order;
import com.BackEcom.model.OrderItem;
import com.BackEcom.model.Product;
import com.BackEcom.model.User;
import com.BackEcom.repository.OrderItemRepo;
import com.BackEcom.repository.OrderRepo;
import com.BackEcom.util.StatusOrder;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepository;
	@Autowired
	private OrderItemRepo orderItemRepository;
	

	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;


	// Créer une commande à partir du panier
	@Transactional
	public Order createOrder(Long userId) {

		User user = userService.getUserById(userId);
        Cart cart = cartService.getCartByUserId(userId);

    // Vérifier le stock par produit

        for (CartItem cartItem : cart.getCartItemsList()) 	{
        	Product product = cartItem.getProduct();

        	int quantityInCart = cartItem.getQuantity();
        	int quantityInStock = product.getStockQuantity();

        if(quantityInCart > quantityInStock){
        		throw new RuntimeException("la quantité commandée pour le produit " + product.getName()+
        				" dépasse la quantité en stock : " + quantityInStock );

        	}
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatusOrder(StatusOrder.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalPrice());

        // Convertir les articles du panier en articles de commande

        for (CartItem cartItem : cart.getCartItemsList()) {
        	OrderItem orderItem = new OrderItem();
        	orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            order.getOrderItems().add(orderItem);
            
            // Réserver le stock pour les produits dans la commande
            
            Product product = cartItem.getProduct();
            product.setReservedQuantity(product.getReservedQuantity() + cartItem.getQuantity());
            productService.updateProduct(product.getId(), product);
        }
       

        // Sauvegarder la commande et les articles
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        // Vider le panier après la commande
       // cartService.clearCart(userId);

        return order;
	}

	  // Récupérer les commandes d'un utilisateur
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

 // Mettre à jour le statut d'une commande
    public Order updateOrderStatus(Long orderId, StatusOrder status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
                order.setStatusOrder(status);
                
        List <OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        		
        
        
        StatusOrder Status = order.getStatusOrder();
        
        //////////////
        switch (Status){
        	
        	case SHIPPED:
            	// Réduire le stock uniquement si la commande est payée

        		for (OrderItem orderItem :orderItems) {
        			 Product product = orderItem.getProduct();
                     int quantityInOrder = orderItem.getQuantity();
                     int newStockQuantity = product.getStockQuantity() - quantityInOrder;

                     product.setStockQuantity(newStockQuantity);  // Mettre à jour le stock
                     product.setReservedQuantity(product.getReservedQuantity() - quantityInOrder);  // Annuler la réservation
                     productService.updateProduct(product.getId(), product);
        		}
        		break;
        	case CANCELED:
                // Annuler la réservation du stock si la commande est annulée
                for (OrderItem orderItem : orderItems) {
                    Product product = orderItem.getProduct();
                    int quantityInOrder = orderItem.getQuantity();

                    product.setReservedQuantity(product.getReservedQuantity() - quantityInOrder);  // Annuler la réservation
                    productService.updateProduct(product.getId(), product);
                }
                break;

            case DELIVERED:
            	for (OrderItem orderItemss : order.getOrderItems()) {
            		
            		Product productOrder = orderItemss.getProduct();
            		
            		int quantityInOrder = orderItemss.getQuantity();
                	int quantityInStock = productOrder.getStockQuantity();
                	
                	int newQuantity 	= quantityInStock - quantityInOrder;
                	
                	
                	productOrder.setName(productOrder.getName());
                	productOrder.setDescription(productOrder.getDescription());
                	productOrder.setPrice(productOrder.getPrice());
                	productOrder.setStockQuantity(newQuantity);
                	          	
                	productService.updateProduct(productOrder.getId(), productOrder);
            	}
                	break;
                	
                	default:
                		 throw new IllegalArgumentException("Statut de commande non pris en charge : " + status);
                	
                	
        }
        
        return orderRepository.save(order);
    }

    // Supprimer une commande
    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }



}
