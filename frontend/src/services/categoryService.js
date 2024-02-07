import httpClient from "./httpClient";

export default {
  getCategories() {
    return httpClient.get("/categories");
  },
};
