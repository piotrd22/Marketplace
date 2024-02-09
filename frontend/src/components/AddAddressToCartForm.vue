<template>
  <v-form @submit.prevent="addAddressToCart">
    <v-text-field
      v-model="address"
      variant="outlined"
      label="Address"
    ></v-text-field>
    <v-text-field v-model="city" variant="outlined" label="City"></v-text-field>
    <v-text-field
      v-model="state"
      variant="outlined"
      label="State/Province/Region"
    ></v-text-field>
    <v-text-field
      v-model="zipCode"
      variant="outlined"
      label="ZIP / Postal Code"
    ></v-text-field>
    <v-autocomplete
      ref="country"
      v-model="country"
      :items="countries"
      label="Country"
      placeholder="Select..."
      variant="outlined"
    ></v-autocomplete>
    <v-btn type="submit" color="secondary"> save </v-btn>
    <v-btn class="ml-2" color="warning" @click="handleReset"> clear </v-btn>
  </v-form>
</template>

<script>
import countries from "../assets/countries";
import cartService from "../services/cartService";

export default {
  props: {
    cart: {
      type: Object,
      required: true,
    },
    updateCartFromResponse: Function,
  },
  mounted() {
    this.handleReset();
  },
  data() {
    return {
      address: null,
      city: null,
      state: null,
      zipCode: null,
      country: null,
      countries: countries,
    };
  },
  methods: {
    async addAddressToCart() {
      try {
        const body = {
          address: this.address,
          city: this.city,
          state: this.state,
          zipCode: this.zipCode,
          country: this.country,
        };
        const res = await cartService.addAddressToCart(body);
        this.updateCartFromResponse(res?.data);
        this.$toast.info("Successfully saved address.");
      } catch (error) {
        console.error("addAddressToCart() AddAddressToCartForm.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Saving address failed.";
        this.$toast.error(errorMessage);
      }
    },
    handleReset() {
      this.address = this.cart.address?.address;
      this.city = this.cart.address?.city;
      this.state = this.cart.address?.state;
      this.zipCode = this.cart.address?.zipCode;
      this.country = this.cart.address?.country;
    },
  },
};
</script>
