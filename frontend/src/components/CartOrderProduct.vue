<template>
  <v-card class="mx-auto my-12" max-width="374">
    <v-img
      v-if="product?.product?.photoUrl"
      contain
      height="200"
      :src="product?.product?.photoUrl"
    ></v-img>
    <v-img
      v-else
      cover
      height="200"
      src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
    ></v-img>

    <v-card-item>
      <v-card-title>{{ product.product.name }}</v-card-title>
    </v-card-item>

    <v-card-text>
      <div>Price: {{ product.productPrice }}$</div>
      <div>Quantity: {{ product.quantity }}</div>
      <div>
        Total price: {{ (product.productPrice * product.quantity).toFixed(2) }}$
      </div>
    </v-card-text>

    <v-divider v-if="!isSummary" class="mx-4 mb-1"></v-divider>

    <v-card-actions v-if="!isSummary">
      <v-form @submit.prevent="updateProductQuantityInCart">
        <v-row align="center">
          <v-col cols="12" md="4">
            <v-text-field
              v-model.number="quantity"
              label="Quantity"
              type="number"
              variant="underlined"
              min="1"
            ></v-text-field>
          </v-col>

          <v-col cols="12" md="8" class="text-center">
            <v-btn
              color="deep-purple-lighten-2"
              variant="text"
              :disabled="quantity <= 0"
              type="submit"
              prepend-icon="mdi-refresh"
            >
              Update Quantity
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card-actions>

    <v-card-actions v-if="!isSummary">
      <v-form @submit.prevent="removeProductFromCart">
        <v-row align="center">
          <v-col cols="12" md="12" class="text-center">
            <v-btn
              color="warning"
              variant="tonal"
              type="submit"
              prepend-icon="mdi-trash-can-outline"
            >
              Remove
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card-actions>
  </v-card>
</template>

<script>
import cartService from "../services/cartService";

export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
    updateCartFromResponse: Function,
    isSummary: Boolean,
  },
  mounted() {
    this.quantity = this.product.quantity;
  },
  data() {
    return {
      quantity: 1,
    };
  },
  methods: {
    async updateProductQuantityInCart() {
      try {
        const res = await cartService.updateProductQuantityInCart(
          this.product.id,
          this.quantity,
        );
        this.updateCartFromResponse(res?.data);
        this.$toast.success("Successfully updated product quantity in cart.");
      } catch (error) {
        console.error(
          "updateProductQuantityInCart() CartOrderProduct.vue: ",
          error,
        );
        const errorMessage =
          error.response?.data.message || "Updating product quantity failed.";
        this.$toast.error(errorMessage);
      }
    },
    async removeProductFromCart() {
      try {
        const res = await cartService.removeProductFromCart(this.product.id);
        this.updateCartFromResponse(res?.data);
        this.$toast.success("Successfully deleted product from cart.");
      } catch (error) {
        console.error("removeProductFromCart() CartOrderProduct.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Removing product from cart failed.";
        this.$toast.error(errorMessage);
      }
    },
  },
};
</script>
