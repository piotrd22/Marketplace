<template>
  <v-card class="mx-auto my-12" max-width="374">
    <v-img
      v-if="product.photoUrl"
      contain
      height="250"
      :src="product.photoUrl"
    ></v-img>
    <v-img
      v-else
      cover
      height="250"
      src="../assets/default-product.jpg"
    ></v-img>

    <v-card-item>
      <v-card-title>{{ product.name }}</v-card-title>

      <v-card-subtitle>
        <span class="me-1">Local Favorite</span>

        <v-icon color="error" icon="mdi-fire-circle" size="small"></v-icon>
      </v-card-subtitle>
    </v-card-item>

    <v-card-text>
      <v-row class="mx-0">
        <v-rating
          :model-value="4.5"
          color="amber"
          density="compact"
          half-increments
          readonly
          size="small"
        ></v-rating>

        <div class="text-grey ms-4">4.5 (413)</div>
      </v-row>

      <div class="mt-4 text-subtitle-1">$ • {{ product.price }}</div>

      <div class="text-center">
        <v-btn
          class="mt-4"
          color="primary"
          variant="outlined"
          @click="navigateToProductInfo"
        >
          MORE
        </v-btn>
      </div>
    </v-card-text>

    <v-divider class="mx-4 mb-1"></v-divider>

    <v-card-actions class="d-flex justify-center">
      <v-form @submit.prevent="addToCart">
        <v-row align="center">
          <v-col cols="12" md="4">
            <v-text-field
              v-model.number="quantity"
              label=" Quantity"
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
            >
              Add To Cart
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card-actions>
  </v-card>
</template>

<script>
import cartService from "../services/cartService";
import { useCartStore } from "../store";

export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      quantity: 1,
      cartStore: useCartStore(),
    };
  },
  methods: {
    async addToCart() {
      try {
        const body = {
          quantity: this.quantity,
          productId: this.product.id,
        };

        const res = await cartService.addProductToCart(body);
        this.cartStore.setCartSize(res?.data?.cartProducts?.length);
        this.quantity = 1;
        this.$toast.success("Successfully added product to cart!");
      } catch (error) {
        console.error("addToCart() Product.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Adding product to cart failed.";
        this.$toast.error(errorMessage);
      }
      this.quantity = 1;
    },
    navigateToProductInfo() {
      this.$router.push({
        name: "ProductInfo",
        params: { id: this.product.id },
      });
    },
  },
};
</script>
