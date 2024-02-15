<template>
  <div v-if="isEmpty">
    <v-row justify="center">
      <v-col class="text-center">
        <h1>Your cart is empty :(</h1>
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

        <v-row v-else-if="cart">
          <v-col cols="12" mdi="12">
            <h1>
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

        <v-row v-if="cart" justify="center" class="text-center">
          <v-col cols="12" mdi="12">
            <v-btn
              @click="deleteCart"
              color="warning"
              append-icon="mdi-cart-remove"
              >Delete cart</v-btn
            >
          </v-col>
        </v-row>
      </template>

      <template v-slot:item.2>
        <v-row v-if="cart">
          <v-col cols="12" mdi="12">
            <h1>
              <v-icon>mdi-home-edit-outline</v-icon>
              Add Address To Your Order
            </h1>
          </v-col>

          <v-col cols="12" mdi="12">
            <add-address-to-cart-form
              :cart="cart"
              :update-cart-from-response="updateCartFromResponse"
            ></add-address-to-cart-form>
          </v-col>
        </v-row>
      </template>

      <template v-slot:item.3>
        <v-row v-if="cart">
          <v-col cols="12" mdi="12">
            <h1>
              <v-icon>mdi-cash-multiple</v-icon>
              Add Payment Method To Your Order
            </h1>
          </v-col>

          <v-col cols="12" mdi="12">
            <add-payment-to-cart-form
              :cart="cart"
              :update-cart-from-response="updateCartFromResponse"
            ></add-payment-to-cart-form>
          </v-col>
        </v-row>
      </template>

      <template v-slot:item.4>
        <v-row v-if="cart">
          <v-col cols="12" mdi="12">
            <h1>
              <v-icon>mdi-bookmark-check-outline</v-icon>
              Summary
            </h1>
          </v-col>

          <v-col cols="12" mdi="12">
            <h3>
              <v-icon>mdi-cart-outline</v-icon> Total price
              {{ cart?.cartPrice.toFixed(2) }}$
            </h3>
          </v-col>
          <v-col
            v-for="product in cart?.cartProducts"
            :key="product.id"
            cols="12"
            md="4"
          >
            <cart-order-product
              :product="product"
              :is-summary="true"
            ></cart-order-product>
          </v-col>

          <v-col cols="12" mdi="12">
            <h3>
              <v-icon>mdi-home-edit-outline</v-icon>
              Address
            </h3>
            <div class="mt-2 ml-2" v-if="cart?.address">
              <p>Address: {{ cart?.address?.address }}</p>
              <p>City: {{ cart?.address?.city }}</p>
              <p>Zip / Postal Code: {{ cart?.address?.zipCode }}</p>
              <p>State/Province/Region: {{ cart?.address?.state }}</p>
              <p>Country: {{ cart?.address?.country }}</p>
            </div>
            <div class="mt-2 ml-2" v-else>
              Please complete the delivery address and save it.
            </div>
          </v-col>

          <v-col cols="12" mdi="12">
            <h3>
              <v-icon>mdi-cash-multiple</v-icon>
              Payment Method
            </h3>
            <div class="mt-2 ml-2" v-if="cart?.payment">
              <p>Payment Method: {{ cart?.payment?.paymentMethod }}</p>
            </div>
            <div class="mt-2 ml-2" v-else>
              <p>Please complete your payment method and save it.</p>
            </div>
          </v-col>
        </v-row>

        <v-row justify="center" class="text-center">
          <v-col cols="12" mdi="12">
            <v-btn
              @click="placeOrder"
              color="secondary"
              append-icon="mdi-cart-arrow-down"
              :loading="loadingDialog"
              :disabled="!cart?.address || !cart?.payment || loadingDialog"
            >
              Place Order
            </v-btn>

            <v-dialog
              v-model="loadingDialog"
              :scrim="false"
              persistent
              width="auto"
            >
              <v-card color="secondary">
                <v-card-text>
                  Order processing. Please stand by.
                  <v-progress-linear
                    indeterminate
                    color="white"
                    class="mb-0"
                  ></v-progress-linear>
                </v-card-text>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
      </template>
    </v-stepper>
  </div>
</template>

<script>
import orderService from "../services/orderService";
import cartService from "../services/cartService";
import CartOrderProduct from "../components/CartOrderProduct.vue";
import AddAddressToCartForm from "../components/cart/AddAddressToCartForm.vue";
import AddPaymentToCartForm from "../components/cart/AddPaymentToCartForm.vue";
import { useCartStore } from "../store";

export default {
  data() {
    return {
      step: 1,
      isEmpty: false,
      cart: null,
      stepItems: ["Cart", "Address", "Payment Method", "Submit"],
      loading: false,
      loadingDialog: false,
      cartStore: useCartStore(),
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
    async deleteCart() {
      try {
        await cartService.deleteCart();
        this.cartStore.setCartSize(0);
        this.$toast.success("Succesfully deleted cart.");
        this.$router.push({ name: "Home" });
      } catch (error) {
        console.error("deleteCart() Cart.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Deleting cart failed.";
        this.$toast.error(errorMessage);
      }
    },
    async placeOrder() {
      try {
        this.loadingDialog = true;
        setTimeout(async () => {
          const res = await orderService.placeOrder();
          this.loadingDialog = false;
          this.cartStore.setCartSize(0);
          this.$toast.success("Succesfully placed order.");
          this.$router.push({ name: "OrderInfo", params: res?.data });
        }, 4000);
      } catch (error) {
        console.error("placeOrder() Cart.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Placing order failed.";
        this.$toast.error(errorMessage);
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
