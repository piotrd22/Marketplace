<template>
  <v-form @submit.prevent="search">
    <v-row>
      <v-col cols="12" md="3">
        <v-text-field v-model="searchName" label="Name" outlined></v-text-field>
      </v-col>

      <v-col cols="12" md="2">
        <v-select
          v-model="selectedCategories"
          :items="categories"
          item-title="name"
          item-value="id"
          label="Categories"
          multiple
          outlined
        ></v-select>
      </v-col>

      <v-col cols="12" md="2">
        <v-text-field
          v-model.number="minPrice"
          label=" Min price"
          prefix="$"
          type="number"
          outlined
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="2">
        <v-text-field
          v-model.number="maxPrice"
          label="Max price"
          prefix="$"
          type="number"
          outlined
        ></v-text-field>
      </v-col>

      <v-col cols="12" md="3">
        <v-select
          v-model="sort"
          :items="sortItems"
          item-title="key"
          item-value="value"
          label="Sort by"
          outlined
        ></v-select>
      </v-col>

      <v-btn type="submit" append-icon="mdi-magnify" class="ml-2">Search</v-btn>
      <v-btn @click="handleReset" append-icon="mdi-close" class="ml-2"
        >Clear</v-btn
      >
    </v-row>
  </v-form>
</template>

<script>
import categoryService from "../services/categoryService";
import productService from "../services/productService";

export default {
  data() {
    const sortItems = [
      { key: "By creation date ascending", value: "createdAt,asc" },
      { key: "By creation date descending", value: "createdAt,desc" },
      { key: "By price ascending", value: "price,asc" },
      { key: "By price descending", value: "price,desc" },
    ];

    return {
      searchName: "",
      selectedCategories: [],
      minPrice: null,
      maxPrice: null,
      categories: [],
      page: 0,
      size: 10,
      sortItems: sortItems,
      sort: sortItems[0].value,
    };
  },
  mounted() {
    this.getCategories();
    this.search();
  },
  methods: {
    async getCategories() {
      try {
        const res = await categoryService.getCategories();
        this.categories = res.data;
      } catch (error) {
        console.error(error);
      }
    },
    async search() {
      try {
        const searchParams = this.createSearchParams();
        const res = await productService.searchProducts(
          searchParams,
          this.size,
          this.page,
          this.sort,
        );
        console.log(res);
      } catch (error) {
        console.error(error);
      }
    },
    createSearchParams() {
      const searchParams = {};

      searchParams.name = this.searchName;

      if (this.selectedCategories.length > 0) {
        searchParams.categoryIds = this.selectedCategories;
      }

      if (this.minPrice !== null) {
        searchParams.minPrice = this.minPrice;
      }

      if (this.maxPrice !== null) {
        searchParams.maxPrice = this.maxPrice;
      }

      return searchParams;
    },
    handleReset() {
      this.searchName = "";
      this.selectedCategories = [];
      this.minPrice = null;
      this.maxPrice = null;
      this.sort = this.sortItems[0];
    },
  },
};
</script>

<style scoped></style>
