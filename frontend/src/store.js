import { defineStore } from "pinia";

const user = JSON.parse(localStorage.getItem("user"));
const userInitialState = user || null;

export const useUserStore = defineStore("user", {
  state: () => ({
    user: userInitialState,
  }),
  actions: {
    setUser(val) {
      this.user = val;
    },
  },
});

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
