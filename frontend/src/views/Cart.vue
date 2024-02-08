<template>
  <div v-if="isEmpty">
    <v-row justify="center">
      <v-col class="text-center">
        <h1 class="display-1">Your cart is empty :(</h1>
        <div class="subtitle-1">
          Return to the home page and add something to your cart.
        </div>
      </v-col>
    </v-row>
  </div>
  <div v-else>
    <v-stepper v-model="step" :items="stepItems" show-actions>
      <template v-slot:item.1>
        <v-row>
          <v-col cols="12" mdi="12">
            <h1 class="display-1">
              <v-icon>mdi-cart-outline</v-icon> Total price
              {{ cart?.cartPrice.toFixed(2) }}$
            </h1>
          </v-col>
          <v-col
            v-for="product in cart?.cartProducts"
            :key="product.id"
            cols="12"
            md="4"
          >
            <cart-order-product :product="product"></cart-order-product>
          </v-col>
        </v-row>
      </template>
    </v-stepper>
  </div>
</template>

<script>
import cartService from "../services/cartService";
import CartOrderProduct from "../components/CartOrderProduct.vue";

export default {
  data() {
    return {
      step: 1,
      isEmpty: false,
      cart: null,
      stepItems: ["Cart", "Address", "Payment Method", "Submit"],
    };
  },
  mounted() {
    this.getCart();
  },
  methods: {
    async getCart() {
      try {
        const res = await cartService.getCart();
        this.cart = res?.data;
        if (this.cart.cartProducts.length > 0) {
          this.isEmpty = false;
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
  components: {
    CartOrderProduct,
  },
};
</script>
