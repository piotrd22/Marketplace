<template>
  <v-app-bar app>
    <v-btn v-if="isGoBackPage" @click="goBack">
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
    <v-toolbar-title>
      <v-btn @click="navigateHome" text>
        <span class="portal-text">ECommerce</span>
      </v-btn>
    </v-toolbar-title>
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
    navigateHome() {
      const routeName = "Home";
      this.$router.push({ name: routeName });
    },
    goBack() {
      this.$router.go(-1);
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
