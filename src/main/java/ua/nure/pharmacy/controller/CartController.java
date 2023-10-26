package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.Cart;
import ua.nure.pharmacy.entity.CartPack;
import ua.nure.pharmacy.entity.Pack;
import ua.nure.pharmacy.service.impl.CartPackService;
import ua.nure.pharmacy.service.impl.CartService;
import ua.nure.pharmacy.service.impl.PackService;

import java.util.List;
@Controller
public class CartController {
    private final CartPackService cartProductService;
    private final PackService packService;
    public final CartService cartService;
    private LoginController loginController;
    public static Cart cartCurrent = new Cart();

    public CartController(CartPackService cartProductService, PackService packService,
                          CartService cartService, LoginController loginController) {
        this.cartProductService = cartProductService;
        this.packService = packService;
        this.cartService = cartService;
        this.loginController = loginController;
    }

    @PostMapping("/add_to_cart")
    public String addToCart(@RequestParam(required = false) Integer pack_id, Model model) {
        cartCurrent = cartService.findByUserId((loginController.currentCustomer.getId()));

        List<CartPack> itemsByCart = cartProductService.findByIdCartId(cartCurrent.getId());

        CartPack cartProduct = null;
        for (CartPack item : itemsByCart) {
            if (item.getPack().getId().equals(pack_id)) {
                cartProduct = item;
                break;
            }
        }
        Pack byId = packService.findById(pack_id);

        if (cartProduct != null) {
            cartProduct.setAmount(cartProduct.getAmount() + 1);
            cartProductService.update(cartProduct);

            byId.setPacksAmount(byId.getPacksAmount() - cartProduct.getAmount());
        } else {
            cartProduct = new CartPack();
            cartProduct.setCart(cartCurrent);
            cartProduct.setPack(packService.findById(pack_id));
            cartProduct.setAmount(1);
            cartProductService.insert(cartProduct);

            byId.setPacksAmount(byId.getPacksAmount() - 1);
        }

        packService.update(byId);


        List<CartPack> itemsByCartUpdated =
                cartProductService.findByIdCartId(cartCurrent.getId());
        if (itemsByCartUpdated.isEmpty()) {
            cartCurrent.setPrice(0.0);
        } else {
            Double price = 0.0;
            for (CartPack item : itemsByCartUpdated) {
                price += item.getPack().getPrice() * item.getAmount();
            }
            cartCurrent.setPrice(Math.round(price * 100.0) / 100.0);
            cartService.update(cartCurrent);
        }

        return cart(cartCurrent.getCustomer().getId(), model);
    }


    @GetMapping("/cart")
    public String cart(@RequestParam Integer id, Model model) {
        cartCurrent = cartService.findByUserId(id);

        List<CartPack> itemsByCart = cartProductService.findByIdCartId(cartCurrent.getId());

        model.addAttribute("cartCurrent",  cartCurrent);
        model.addAttribute("cartCurrentProduct", itemsByCart);
        model.addAttribute("user", loginController.currentCustomer);
        if (itemsByCart.isEmpty()) {
            return "/client/cart_is_empty";
        } else {
            return "/client/cart";
        }
    }

    @PostMapping("submit_cart")
    public String submitCart(@RequestParam("count") Integer count,
                             @RequestParam("id") Integer id, Model model) {
        Pack pack = packService.findById(id);
        if (count != 0) {
            CartPack cartProduct = new CartPack();
            cartProduct.setCart(cartCurrent);
            cartProduct.setPack(pack);
            cartProduct.setAmount(count);
            cartProductService.update(cartProduct);

            pack.setPacksAmount(pack.getPacksAmount() - count + 1);
            packService.update(pack);
        } else {
            CartPack cartProduct = cartProductService.findByIdCartIdAndPackId(id, cartCurrent.getId());
            pack.setPacksAmount(pack.getPacksAmount());
            packService.update(pack);
            cartProductService.update(cartProduct);
        }

        List<CartPack> itemsByCart = cartProductService.findByIdCartId(cartCurrent.getId());
        if (itemsByCart.isEmpty()) {
            cartCurrent.setPrice(0.0);
            cartService.update(cartCurrent);
        } else {
            double price = 0.0;
            for (CartPack item : itemsByCart) {
                price += item.getPack().getPrice() * item.getAmount();
            }
            cartCurrent.setPrice(Math.round(price * 100.0) / 100.0);
            cartService.update(cartCurrent);
        }

        if (itemsByCart.isEmpty()) {
            return "/client/cart_is_empty";
        } else {
            return cart(loginController.currentCustomer.getId(), model);
        }
    }

    @PostMapping("/add_to_cart_product")
    public String addToCartOneProduct(@RequestParam Integer packId, @RequestParam Integer doseId, Model model) {
        cartCurrent = cartService.findById(cartCurrent.getId());
        List<CartPack> itemsByCart = cartProductService.findByIdCartId(cartCurrent.getId());

        Pack byId = packService.findById(packId);

        CartPack cartProduct = new CartPack();
        cartProduct.setCart(cartCurrent);
        cartProduct.setPack(packService.findById(packId));
        boolean found = false;
        for (CartPack item : itemsByCart) {
            if (item.getPack().getId().equals(packId)) {
                cartProduct.setAmount(item.getAmount() + 1);
                cartProductService.update(cartProduct);
                byId.setPacksAmount(byId.getPacksAmount() - cartProduct.getAmount());
                found = true;
                break;
            }
        }
        if (!found) {
            cartProduct.setAmount(1);
            cartProductService.insert(cartProduct);
            byId.setPacksAmount(byId.getPacksAmount() - 1);
        }

        packService.update(byId);
        List<CartPack> itemsByCartUpdated = cartProductService.findByIdCartId(cartCurrent.getId());
        if (itemsByCartUpdated.isEmpty()) {
            cartCurrent.setPrice(0.0);
            cartService.update(cartCurrent);
        } else {
            Double price = 0.0;
            for (CartPack item : itemsByCartUpdated) {
                price += item.getPack().getPrice() * item.getAmount();
            }
            cartCurrent.setPrice(Math.round(price * 100.0) / 100.0);
            cartService.update(cartCurrent);
        }

        return cart(cartCurrent.getCustomer().getId(), model);
    }

}