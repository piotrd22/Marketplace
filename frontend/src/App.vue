<template>
  <v-app>
    <v-main>
      <Navbar />
      <div class="home-container">
        <router-view />
      </div>
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
.home-container {
  height: 70vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
