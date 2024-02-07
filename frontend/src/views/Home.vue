<template>
  <v-form @submit.prevent="search">
    <v-row>
      <v-col cols="12" md="3">
        <v-text-field v-model="searchName" label="Name" outlined></v-text-field>
      </v-col>

      <v-col cols="12" md="3">
        <v-select
          v-model="selectedCategories"
          :items="categories"
          label="Categories"
          multiple
          outlined
        ></v-select>
      </v-col>

      <v-col cols="12" md="3">
        <v-text-field
          v-model.number="minPrice"
          label=" Min price"
          prefix="$"
          outlined
        ></v-text-field>
      </v-col>
      <v-col cols="12" md="3">
        <v-text-field
          v-model.number="maxPrice"
          label="Max price"
          prefix="$"
          outlined
        ></v-text-field>
      </v-col>
      <v-col cols="12" md="2">
        <v-btn type="submit" append-icon="mdi-magnify">Search</v-btn>
        <v-btn @click="handleReset">Clear</v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import categoryService from "../services/categoryService.js";

export default {
  data() {
    return {
      searchName: "",
      selectedCategories: [],
      minPrice: null,
      maxPrice: null,
      categories: [],
    };
  },
  methods: {
    async search() {
        const response = await categoryService.getCategories();
        console.log(response.data)
      const searchParams = {
        name: this.searchName,
        description: this.searchName,
        categoryIds: this.selectedCategories,
        minPrice: this.minPrice,
        maxPrice: this.maxPrice,
      };
    },
  },
};
</script>

<style scoped></style>
