<template>
  <v-container>
    <v-row v-if="isLoading" class="text-center">
      <v-col cols="12" md="12">
        <v-progress-circular indeterminate></v-progress-circular>
      </v-col>
    </v-row>

    <v-row v-else-if="product">
      <v-col cols="12" md="6">
        <v-img
          v-if="product?.photoUrl"
          contain
          max-height="500"
          max-width="500"
          :src="product?.photoUrl"
        ></v-img>
        <v-img
          v-else
          cover
          max-height="500"
          max-width="500"
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
        ></v-img>
      </v-col>
      <v-col cols="12" md="6">
        <v-card class="mx-auto my-12" variant="outlined">
          <v-card-item>
            <v-card-title>{{ product.name }}</v-card-title>

            <v-card-subtitle>
              <span class="me-1">Local Favorite</span>

              <v-icon
                color="error"
                icon="mdi-fire-circle"
                size="small"
              ></v-icon>
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

            <div v-if="product?.description" class="mt-4 text-subtitle-2">
              {{ product?.description }}
            </div>
          </v-card-text>

          <v-divider class="mx-4 mb-1"></v-divider>
          <v-card-actions>
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
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import cartService from "../services/cartService";
import productService from "../services/productService";

export default {
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      product: null,
      isLoading: false,
      quantity: 1,
    };
  },
  async mounted() {
    await this.getProductById();
  },
  methods: {
    async getProductById() {
      try {
        this.isLoading = true;
        const res = await productService.getProductById(this.id);
        this.product = res?.data;
        this.isLoading = false;
      } catch (error) {
        console.error("getProductById() ProductInfo.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Getting product failed.";
        this.$toast.error(errorMessage);
      }
    },
    async addToCart() {
      try {
        const body = {
          quantity: this.quantity,
          productId: this.product.id,
        };

        const res = await cartService.addProductToCart(body);
        console.log(res);
        this.quantity = 1;
        this.$toast.success("Successfully added product to cart!");
      } catch (error) {
        console.error("addToCart() ProductInfo.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Adding product to cart failed.";
        this.$toast.error(errorMessage);
      }
      this.quantity = 1;
    },
  },
};
</script>
