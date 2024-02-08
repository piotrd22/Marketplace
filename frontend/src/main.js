import { createApp } from "vue";
import "./styles.css";
import App from "./App.vue";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.min.css";

const vuetify = createVuetify({
  components,
  directives,
});

import ToastPlugin from "vue-toast-notification";
import "vue-toast-notification/dist/theme-bootstrap.css";

import router from "./router";

createApp(App).use(vuetify).use(ToastPlugin).use(router).mount("#app");
