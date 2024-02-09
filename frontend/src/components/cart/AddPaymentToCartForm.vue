<template>
  <v-form @submit.prevent="addPaymentToCart">
    <v-select
      v-model="paymentMethod"
      :items="paymentMethods"
      label="Select"
      variant="outlined"
    ></v-select>
    <v-btn type="submit" color="secondary"> save </v-btn>
    <v-btn class="ml-2" color="warning" @click="handleReset"> clear </v-btn>
  </v-form>
</template>

<script>
import cartService from "../../services/cartService";
import enumService from "../../services/enumService";

export default {
  props: {
    cart: {
      type: Object,
      required: true,
    },
    updateCartFromResponse: Function,
  },
  async mounted() {
    await this.getPaymentMethods();
    this.handleReset();
  },
  data() {
    return {
      paymentMethod: null,
      paymentMethods: [],
    };
  },
  methods: {
    async getPaymentMethods() {
      try {
        const res = await enumService.getPaumentMethods();
        this.paymentMethods = res?.data;
      } catch (error) {
        console.error("getPaymentMethods() AddPaymentToCartForm.vue: ", error);
      }
    },
    async addPaymentToCart() {
      try {
        const body = { paymentMethod: this.paymentMethod };
        const res = await cartService.addPaymentToCart(body);
        this.updateCartFromResponse(res?.data);
        this.$toast.success("Successfully saved address.");
      } catch (error) {
        console.error("addPaymentToCart() AddPaymentToCartForm.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Saving payment method failed.";
        this.$toast.error(errorMessage);
      }
    },
    handleReset() {
      this.paymentMethod = this.cart?.payment?.paymentMethod;
    },
  },
};
</script>
