<template>
  <v-app-bar app>
    <v-btn v-if="isGoBackPage" @click="goBack">
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
    <v-toolbar-title>
      <v-btn @click="navigateHome" text>
        <span class="portal-text">e-commerce</span>
      </v-btn>
    </v-toolbar-title>

    <div v-if="userStore.user">
      <v-btn @click="navigateCart" prepend-icon="mdi-cart-outline">
        Cart
        <v-badge
          v-if="cartStore.cartSize > 0"
          :content="cartStore.cartSize"
          color="primary"
          inline
        ></v-badge>
      </v-btn>

      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn v-bind="props" text>
            @{{ userStore.user.user.username }}
            <v-avatar size="36" class="avatar ml-2">
              <v-img
                src="../../assets/default-avatar.jpg"
                alt="User Avatar"
                class="avatar-image"
              ></v-img>
            </v-avatar>
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            :value="i"
            @click="navigate(item.route)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
          <v-list-item @click="logout">
            <v-list-item-title>
              <v-icon>mdi-logout</v-icon>
              Logout
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>

    <div v-else>
      <v-btn @click="navigateAuth" prepend-icon="mdi-login"> login </v-btn>
    </div>
  </v-app-bar>
</template>

<script>
import authService from "../../services/authService";
import { useCartStore, useUserStore } from "../../store";

export default {
  data() {
    return {
      items: [
        { title: "My Profile", route: "Profile" },
        { title: "My Orders", route: "Orders" },
      ],
      cartStore: useCartStore(),
      userStore: useUserStore(),
    };
  },
  computed: {
    isGoBackPage() {
      return this.$route.name !== "Home";
    },
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    navigateAuth() {
      this.navigate("Auth");
    },
    navigateHome() {
      this.navigate("Home");
    },
    navigateCart() {
      this.navigate("Cart");
    },
    navigate(routeName) {
      this.$router.push({ name: routeName });
    },
    logout() {
      authService.logout();
      this.userStore.setUser(null);
      this.cartStore.setCartSize(0);
      this.navigate("Home");
      this.$toast.success("Successfully logout!");
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
