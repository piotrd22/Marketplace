import httpClient from "./httpClient";
import authHeader from "./authHeader";

export default {
  placeOrder() {
    return httpClient.post("/orders", {}, { headers: authHeader() });
  },
  getOdersByUserId(size = 10, page = 0, sort = "createdAt,desc") {
    const uri = `/orders/user?size=${size}&page=${page}&sort=${sort}`;
    return httpClient.get(uri, { headers: authHeader() });
  },
  getOrderById(id) {
    return httpClient.get(`/orders/${id}`, { headers: authHeader() });
  },
};
