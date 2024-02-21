import { createRouter, createWebHistory } from "vue-router";
import Auth from "../views/Auth.vue";
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
    path: "/auth",
    name: "Auth",
    component: Auth,
  },
  {
    path: "/cart",
    name: "Cart",
    component: Cart,
    meta: { requiresAuth: true },
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    meta: { requiresAuth: true },
  },
  {
    path: "/orders",
    name: "Orders",
    component: Orders,
    meta: { requiresAuth: true },
  },
  {
    path: "/order/:id",
    name: "OrderInfo",
    component: OrderInfo,
    props: true,
    meta: { requiresAuth: true },
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

const isAuthenticated = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  return !!user;
};

router.beforeEach((to, _, next) => {
  if (to.meta.requiresAuth) {
    const authenticated = isAuthenticated();
    if (authenticated) {
      next();
    } else {
      next({ name: "Auth" });
    }
  } else if (to.name === "Auth" && isAuthenticated()) {
    next({ name: "Home" });
  } else {
    next();
  }
});

export default router;
