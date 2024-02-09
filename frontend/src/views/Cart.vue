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
        <v-row v-if="loading" justify="center" class="text-center">
          <v-col cols="12" mdi="12">
            <v-progress-circular indeterminate></v-progress-circular>
          </v-col>
        </v-row>

        <v-row v-else>
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
            <cart-order-product
              :product="product"
              :update-cart-from-response="updateCartFromResponse"
            ></cart-order-product>
          </v-col>
        </v-row>
      </template>

      <template v-slot:item.2>
        <v-col cols="12" mdi="12">
          <h1 class="display-1">
            <v-icon>mdi-home-edit-outline</v-icon>
            Add Address To Your Order
          </h1>
        </v-col>

        <v-col cols="12" mdi="12" v-if="cart">
          <add-address-to-cart-form
            :cart="cart"
            :update-cart-from-response="updateCartFromResponse"
          ></add-address-to-cart-form>
        </v-col>
      </template>

      <template v-slot:item.3>
        <v-col cols="12" mdi="12">
          <h1 class="display-1">
            <v-icon>mdi-cash-multiple</v-icon>
            Add Payment Method To Your Order
          </h1>
        </v-col>

        <v-col cols="12" mdi="12" v-if="cart">
          <add-payment-to-cart-form
            :cart="cart"
            :update-cart-from-response="updateCartFromResponse"
          ></add-payment-to-cart-form>
        </v-col>
      </template>

      <template v-slot:item.4> hehehe </template>
    </v-stepper>
  </div>
</template>

<script>
import cartService from "../services/cartService";
import CartOrderProduct from "../components/CartOrderProduct.vue";
import AddAddressToCartForm from "../components/AddAddressToCartForm.vue";
import AddPaymentToCartForm from "../components/AddPaymentToCartForm.vue";

export default {
  data() {
    return {
      step: 1,
      isEmpty: false,
      cart: null,
      stepItems: ["Cart", "Address", "Payment Method", "Submit"],
      loading: false,
    };
  },
  async mounted() {
    await this.getCart();
  },
  methods: {
    async getCart() {
      try {
        this.loading = true;
        const res = await cartService.getCart();
        this.cart = res?.data;
        if (this.cart.cartProducts.length > 0) {
          this.isEmpty = false;
        } else {
          this.isEmpty = true;
        }
        this.sortByCreatedAt();
        this.loading = false;
      } catch (error) {
        this.loading = false;
        if (error?.response?.data?.statusCode === 404) {
          this.isEmpty = true;
        } else {
          console.error(error);
        }
      }
    },
    updateCartFromResponse(cart) {
      this.cart = cart;
      this.sortByCreatedAt();
      if (this.cart.cartProducts.length === 0) {
        this.$router.push({ name: "Home" });
      }
    },
    sortByCreatedAt() {
      this.cart.cartProducts = this.cart.cartProducts.sort(
        (a, b) => new Date(b.createdAt) - new Date(a.createdAt),
      );
    },
  },
  components: {
    CartOrderProduct,
    AddAddressToCartForm,
    AddPaymentToCartForm,
  },
};
</script>
