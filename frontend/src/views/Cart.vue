<template>
  <div v-if="isEmpty">Your Cart is Empty back to the home page</div>
  <div v-else>Basket</div>
</template>

<script>
import cartService from "../services/cartService";

export default {
  data() {
    return {
      isEmpty: true,
      cart: null
    };
  },
  mounted() {
    this.getCart();
  },
  methods: {
    async getCart() {
      try {
        const res = await cartService.getCart();
        this.cart = res?.data
        if (this.cart.cartProducts.length > 0) {
            this.isEmpty = false
        } else {
            this.isEmpty = true;
        }
        console.log(res);
      } catch (error) {
        if (error?.response?.data?.statusCode === 404) {
          this.isEmpty = true;
        } else {
          console.error(error);
        }
      }
    },
  },
};
</script>
