import httpClient from "./httpClient";

export default {
  signIn(body) {
    return httpClient.post("/auth/signin", body);
  },
  signUp(body) {
    return httpClient.post("/auth/signup", body);
  },
  logout() {
    localStorage.removeItem("user");
  },
};
