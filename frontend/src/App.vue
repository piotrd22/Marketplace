<template>
  <v-app>
    <v-main>
      <Navbar />
      <v-container class="custom-container">
        <router-view />
      </v-container>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Footer from "./components/layout/Footer.vue";
import Navbar from "./components/layout/Navbar.vue";

export default {
  components: {
    Footer,
    Navbar,
  },
};
</script>

<style scoped>
.custom-container {
  max-width: 80%;
}
</style>
