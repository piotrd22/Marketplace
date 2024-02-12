<template>
  <v-container>
    <v-row v-if="isLoading" class="text-center">
      <v-col cols="12" md="12">
        <v-progress-circular indeterminate></v-progress-circular>
      </v-col>
    </v-row>

    <v-row v-else-if="order">
      <v-col cols="12" mdi="12">
        <h1>
          <v-icon>mdi-bookmark-check-outline</v-icon>
          Order Info
        </h1>
      </v-col>
      <v-col cols="12" mdi="12">
        <h3>
          <v-icon>mdi-cart-outline</v-icon> Total price
          {{ order?.orderPrice.toFixed(2) }}$
        </h3>
        <h3>
          <v-icon>mdi-check-all</v-icon> Order status
          {{ order?.orderStatus }}
        </h3>
      </v-col>
      <v-col
        v-for="product in order?.orderProducts"
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
        <div class="mt-2 ml-2">
          <p>Address: {{ order?.address?.address }}</p>
          <p>City: {{ order?.address?.city }}</p>
          <p>Zip / Postal Code: {{ order?.address?.zipCode }}</p>
          <p>State/Province/Region: {{ order?.address?.state }}</p>
          <p>Country: {{ order?.address?.country }}</p>
        </div>
      </v-col>

      <v-col cols="12" mdi="12">
        <h3>
          <v-icon>mdi-cash-multiple</v-icon>
          Payment
        </h3>
        <div class="mt-2 ml-2">
          <p>Payment Method: {{ order?.payment?.paymentMethod }}</p>
        </div>
        <div class="mt-2 ml-2">
          <p>
            Payment Date:
            {{ new Date(order?.payment?.paymentDate).toLocaleString() }}
          </p>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import orderService from "../services/orderService";
import CartOrderProduct from "../components/CartOrderProduct.vue";

export default {
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      order: null,
      isLoading: false,
    };
  },
  async mounted() {
    await this.getOrderById();
  },
  methods: {
    async getOrderById() {
      try {
        const res = await orderService.getOrderById(this.id);
        this.order = res?.data;
      } catch (error) {
        console.error("getOrderById() OrderInfo.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Getting order failed.";
        this.$toast.error(errorMessage);
      }
    },
  },
  components: {
    CartOrderProduct,
  },
};
</script>
