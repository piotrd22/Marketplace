import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import NotFound from "../views/NotFound.vue";
import Cart from "../views/Cart.vue";
import Profile from "../views/Profile.vue";
import Orders from "../views/Orders.vue";
import OrderInfo from "../views/OrderInfo.vue";
import ProductInfo from "../views/ProductInfo.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/cart",
    name: "Cart",
    component: Cart,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/orders",
    name: "Orders",
    component: Orders,
  },
  {
    path: "/order/:id",
    name: "OrderInfo",
    component: OrderInfo,
    props: true,
  },
  {
    path: "/product/:id",
    name: "ProductInfo",
    component: ProductInfo,
    props: true,
  },
  { path: "/:pathMatch(.*)*", name: "NotFound", component: NotFound },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
