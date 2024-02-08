<template>
  <v-card class="mx-auto my-12" max-width="374">
    <v-img
      v-if="product?.product?.photoUrl"
      cover
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
      <div>Total price: {{ product.productPrice * product.quantity }}$</div>
    </v-card-text>

    <v-divider class="mx-4 mb-1"></v-divider>

    <v-card-actions>
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

    <v-card-actions>
      <v-form @submit.prevent="removeProductFromCart">
        <v-row align="center">
          <v-col cols="12" md="12" class="text-center">
            <v-btn
              color="warning"
              variant="text"
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
      console.log("heheh");
    },
    async removeProductFromCart() {
      console.log("removed");
    },
  },
};
</script>
