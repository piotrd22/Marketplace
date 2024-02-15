import { createApp } from "vue";
import { createPinia } from "pinia";
import "./styles.css";
import App from "./App.vue";
import router from "./router";
import ToastPlugin from "vue-toast-notification";
import "vue-toast-notification/dist/theme-bootstrap.css";
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "@mdi/font/css/materialdesignicons.min.css";

const vuetify = createVuetify({
  components,
  directives,
});

const pinia = createPinia();
createApp(App)
  .use(pinia)
  .use(vuetify)
  .use(ToastPlugin)
  .use(router)
  .mount("#app");
