import { defineStore } from "pinia";

export const useCartStore = defineStore("cart", {
  state: () => ({
    cartSize: 0,
  }),
  actions: {
    setCartSize(val) {
      this.cartSize = val;
    },
  },
});
