package com.BackEcom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackEcom.model.Cart;
import com.BackEcom.model.CartItem;
import com.BackEcom.model.Product;
import com.BackEcom.model.User;
import com.BackEcom.repository.CartItemRepo;
import com.BackEcom.repository.CartRepo;
import com.BackEcom.repository.UserRepo;

@Service
public class CartService {

	@Autowired
	private CartRepo cartRepository;

	@Autowired
	private CartItemRepo cartItemRepository;

	@Autowired
    private ProductService productService;

	@Autowired
    private UserService userService;

	 @Autowired
	    private UserRepo userRepository;


	public Cart getCartByUserId(Long userId) {
	 return cartRepository.findByUserId(userId)
	  .orElseThrow(()-> new RuntimeException("Panier non trouvé"));

	}

    // Créer un nouveau panier pour un utilisateur
    public Cart createCart(Long user_id) {

    	User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    	// Vérifier si l'utilisateur a déjà un panier
    	if (user.getCart() != null) {
    		throw new RuntimeException("l'utilisateur a déja panier");
    	}
    	// Créer un nouveau panier
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
     // Associer le panier à l'utilisateur
        user.setCart(cart);
     // Sauvegarder le panier et mettre à jour l'utilisateur
        cartRepository.save(cart);
        userRepository.save(user);

        return cart;
    }


    // Mettre à jour le prix total du panier
    private void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItemsList().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }
 // Supprimer un produit du panier
    public Cart removeFromCart(Long user_id, Long product_id) {
        Cart cart = getCartByUserId(user_id);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product_id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé dans le panier"));

        cartItemRepository.delete(cartItem);
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    // Vider le panier
    public void clearCart(Long user_id) {
        Cart cart = getCartByUserId(user_id);
        cartItemRepository.deleteByCart_id(cart.getId());
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

	// Ajouter un produit au panier
    public Cart addToCart(Long user_id, Long product_id, int quantity) {
        Cart cart = cartRepository.findByUserId(user_id)
                .orElseGet(() -> createCart(user_id));
        Product product = productService.getProductById(product_id);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product_id)
                .orElse(new CartItem());

        if (cartItem.getCartItem_id() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }

    // Mettre à jour la quantité d'un produit dans le panier
    public Cart updateCartItemQuantity(Long user_id, Long product_id, int quantity) {
        Cart cart = getCartByUserId(user_id);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product_id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé dans le panier"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        updateCartTotalPrice(cart);
        return cartRepository.save(cart);
    }


}
