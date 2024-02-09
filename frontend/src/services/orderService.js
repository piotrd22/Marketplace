import httpClient from "./httpClient";

export default {
  placeOrder() {
    return httpClient.post("/orders");
  },
  getOdersByUserId(size = 10, page = 0, sort = "createdAt,desc") {
    const uri = `/orders/user?size=${size}&page=${page}&sort=${sort}`;
    return httpClient.get(uri);
  },
  getOrderById(id) {
    return httpClient.get(`/orders/${id}`);
  },
};
