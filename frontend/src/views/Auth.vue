<template>
  <div class="home-container">
    <v-row justify="center" align="center">
      <v-col cols="12" md="4">
        <div class="svg-container">
          <v-img src="../assets/auth.svg" alt="SVG" class="svg-image" />
        </div>
      </v-col>

      <v-col cols="12" md="7" lg="6">
        <v-card class="mx-auto vertical-card" :variant="'outlined'">
          <div class="card-background"></div>

          <v-card-title class="headline larger-text"
            >Unbeatable Deals, Unforgettable Shopping!</v-card-title
          >
          <v-card-subtitle class="mb-4"
            >Explore our wide range of products</v-card-subtitle
          >

          <v-card-actions>
            <v-btn large color="primary" @click="openRegisterDialog"
              >Register</v-btn
            >
            <v-btn large color="secondary" @click="openLoginDialog"
              >Log in</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="registerDialog" persistent max-width="500">
      <v-card>
        <v-card-title class="text-h5 larger-text">Registration</v-card-title>
        <v-card-text>
          <v-form ref="registerForm" @submit.prevent="register">
            <v-text-field
              v-model="registerUsername"
              label="Username"
              :rules="[(v) => !!v || 'This field is required']"
            ></v-text-field>
            <v-text-field
              v-model="registerEmail"
              label="Email"
              type="email"
              :rules="[(v) => !!v || 'This field is required']"
            ></v-text-field>
            <v-text-field
              v-model="registerPassword"
              label="Password"
              type="password"
              :rules="[(v) => !!v || 'This field is required']"
            ></v-text-field>
            <v-btn type="submit" color="primary">Register</v-btn>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue-darken-1"
            variant="text"
            @click="closeRegisterDialog"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="loginDialog" persistent max-width="500">
      <v-card>
        <v-card-title class="text-h5 larger-text">Log in</v-card-title>
        <v-card-text>
          <v-form ref="loginForm" @submit.prevent="login">
            <v-text-field
              v-model="loginUsername"
              label="Username"
              :rules="[(v) => !!v || 'This field is required']"
            ></v-text-field>
            <v-text-field
              v-model="loginPassword"
              label="Password"
              type="password"
              :rules="[(v) => !!v || 'This field is required']"
            ></v-text-field>
            <v-btn type="submit" color="primary">Log in</v-btn>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="text" @click="closeLoginDialog">
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import cartService from "../services/cartService";
import authService from "../services/authService";
import { useCartStore, useUserStore } from "../store";

export default {
  data() {
    return {
      userStore: useUserStore(),
      cartStore: useCartStore(),
      loginDialog: false,
      registerDialog: false,
      loginUsername: "",
      loginPassword: "",
      registerUsername: "",
      registerPassword: "",
      registerEmail: "",
    };
  },
  methods: {
    openLoginDialog() {
      this.loginDialog = true;
    },
    openRegisterDialog() {
      this.registerDialog = true;
    },
    closeLoginDialog() {
      this.loginDialog = false;
      this.loginUsername = "";
      this.loginPassword = "";
    },
    closeRegisterDialog() {
      this.registerDialog = false;
      this.registerUsername = "";
      this.registerPassword = "";
      this.registerEmail = "";
    },
    async register() {
      try {
        const validationResult = await this.$refs.registerForm.validate();
        if (validationResult.valid) {
          await authService.signUp({
            username: this.registerUsername,
            email: this.registerEmail,
            password: this.registerPassword,
          });
          this.closeRegisterDialog();
          this.$toast.success("Registered successfully. Please log in.");
        }
      } catch (error) {
        console.error("Registration error:", error);
        const errorMessage =
          error.response.data.message || "Registration failed.";
        this.$toast.error(errorMessage);
      }
    },
    async login() {
      try {
        const validationResult = await this.$refs.loginForm.validate();
        if (validationResult.valid) {
          const res = await authService.signIn({
            username: this.loginUsername,
            password: this.loginPassword,
          });
          localStorage.setItem("user", JSON.stringify(res?.data));
          this.userStore.setUser(res?.data);
          this.$router.push({ name: "Home" });
          this.getCartSize();
        }
      } catch (error) {
        console.error("Login error:", error);
        const errorMessage =
          error?.response?.data.message ||
          "Login failed. Please check your credentials and try again.";
        this.$toast.error(errorMessage);
      }
    },
    async getCartSize() {
      try {
        const res = await cartService.getCartProductLengthByUserId();
        this.cartStore.setCartSize(res.data);
      } catch (error) {
        if (error?.response?.data?.statusCode === 404) {
          this.cartStore.setCartSize(0);
        } else {
          console.error(error);
        }
      }
    },
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

.svg-container {
  display: flex;
  justify-content: center;
}

.svg-image {
  max-width: 100%;
  height: auto;
  max-height: 400px;
}

.larger-text {
  font-size: 1.2em;
}
</style>
