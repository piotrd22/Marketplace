import httpClient from "./httpClient";

export default {
  searchProducts(criteria, size = 10, page = 0, sort = "createdAt,asc") {
    const uri = `/products/search?size=${size}&page=${page}&sort=${sort}`;
    return httpClient.post(uri, criteria);
  },
  getProductById(id) {
    return httpClient.get(`/products/${id}`);
  },
};
