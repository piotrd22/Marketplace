<template>Produkt nr: {{ product.id }}</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {};
  },
};
</script>
