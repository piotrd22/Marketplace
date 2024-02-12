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

    <v-menu>
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props" text>
          @randomuser
          <v-avatar size="36" class="avatar ml-2">
            <img
              src="../../assets/default-avatar.jpg"
              alt="User Avatar"
              class="avatar-image"
            />
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
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { title: "My Profile", route: "Profile" },
        { title: "My Orders", route: "Orders" },
      ],
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
    navigateHome() {
      this.navigate("Home");
    },
    navigateCart() {
      this.navigate("Cart");
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
