<template>
  USER PROFILE
  <div v-if="userStore?.user">
    {{ userStore.user.user.username }}
    {{ userStore.user.user.email }}
    {{ userStore.user.user.createdAt }}
    {{ userStore.user.user.updatedAtAt }}
  </div>
</template>

<script>
import { useUserStore } from "../store";

export default {
  data() {
    return {
      userStore: useUserStore(),
    };
  },
};
</script>
