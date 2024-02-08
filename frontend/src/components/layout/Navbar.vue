<template>
  <v-app-bar app>
    <v-btn v-if="isGoBackPage" @click="goBack">
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
    <v-toolbar-title>
      <v-btn @click="navigateHome" text>
        <span class="portal-text">eCommerce</span>
      </v-btn>
    </v-toolbar-title>

    <v-btn @click="navigateCart" prepend-icon="mdi-cart-outline"> Cart </v-btn>

    <v-btn @click="navigateProfile" text>
      @randomuser
      <v-avatar size="36" class="avatar ml-2">
        <img
          src="../../assets/default-avatar.jpg"
          alt="User Avatar"
          class="avatar-image"
        />
      </v-avatar>
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  computed: {
    isGoBackPage() {
      return this.$route.name !== "Home";
    },
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    navigateHome() {
      this.navigate("Home");
    },
    navigateCart() {
      this.navigate("Cart");
    },
    navigateProfile() {
      this.navigate("Profile");
    },
    navigate(routeName) {
      this.$router.push({ name: routeName });
    },
  },
};
</script>

<style scoped>
.portal-text {
  font-weight: bold;
  font-size: 1.2em;
}

.narrow-text-field {
  max-width: 300px;
}
</style>
