import httpClient from "./httpClient";

export default {
  getCart() {
    return httpClient.get("/cart");
  },
  addProductToCart(body) {
    return httpClient.post("/cart/cart-product", body);
  },
  removeProductFromCart(id) {
    const uri = `/cart/cart-product/${id}`;
    return httpClient.delete(uri);
  },
  deleteCart() {
    return httpClient.delete("/cart");
  },
  addAddressToCart(body) {
    return httpClient.put("/cart/address", body);
  },
  addPaymentToCart(body) {
    return httpClient.put("/cart/payment", body);
  },
  updateProductQuantityInCart(id, newQuantity) {
    const uri = `/cart/cart-product/${id}`;
    return httpClient.put(uri, { newQuantity });
  },
  getCartProductLengthByUserId() {
    return httpClient.get("/cart/length");
  },
};
