import httpClient from "./httpClient";
import authHeader from "./authHeader";

export default {
  getCart() {
    return httpClient.get("/cart", { headers: authHeader() });
  },
  addProductToCart(body) {
    return httpClient.post("/cart/cart-product", body, {
      headers: authHeader(),
    });
  },
  removeProductFromCart(id) {
    const uri = `/cart/cart-product/${id}`;
    return httpClient.delete(uri, { headers: authHeader() });
  },
  deleteCart() {
    return httpClient.delete("/cart", { headers: authHeader() });
  },
  addAddressToCart(body) {
    return httpClient.put("/cart/address", body, { headers: authHeader() });
  },
  addPaymentToCart(body) {
    return httpClient.put("/cart/payment", body, { headers: authHeader() });
  },
  updateProductQuantityInCart(id, newQuantity) {
    const uri = `/cart/cart-product/${id}`;
    return httpClient.put(uri, { newQuantity }, { headers: authHeader() });
  },
  getCartProductLengthByUserId() {
    return httpClient.get("/cart/length", { headers: authHeader() });
  },
};
