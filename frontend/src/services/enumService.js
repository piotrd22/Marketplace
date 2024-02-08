import httpClient from "./httpClient";

export default {
  getPaumentMethods() {
    return httpClient.get("/enums/payment-methods");
  },
};
