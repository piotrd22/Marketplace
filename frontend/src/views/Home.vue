<template>
  <v-container>
    <v-form @submit.prevent="search">
      <v-row>
        <v-col cols="12" md="2">
          <v-text-field
            v-model="searchName"
            label="Name"
            outlined
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="3">
          <v-select
            v-model="selectedCategories"
            :items="categories"
            item-title="name"
            item-value="id"
            label="Categories"
            multiple
            outlined
          >
            <template v-slot:selection="{ item, index }">
              <v-chip v-if="index < 1">
                <span>{{ item.title }}</span>
              </v-chip>
              <span
                v-if="index === 1"
                class="text-grey text-caption align-self-center"
              >
                (+{{ selectedCategories.length - 1 }} others)
              </span>
            </template></v-select
          >
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

        <v-btn type="submit" append-icon="mdi-magnify" class="ml-2"
          >Search</v-btn
        >
        <v-btn @click="handleReset" append-icon="mdi-close" class="ml-2"
          >Clear</v-btn
        >
      </v-row>
    </v-form>

    <div class="my-12">
      <v-row justify="end">
        <v-progress-circular v-if="loading" indeterminate></v-progress-circular>
        <v-col v-else cols="12" md="2">
          <v-select
            v-model="size"
            :items="pageSizeOptions"
            label="Items per page"
            outlined
          ></v-select>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-for="product in products" :key="product.id" cols="12" md="4">
          <product :product="product"></product>
        </v-col>
      </v-row>
    </div>
    <v-pagination
      v-model="page"
      class="my-4"
      :length="totalPages"
      :total-visible="6"
    ></v-pagination>
  </v-container>
</template>

<script>
import categoryService from "../services/categoryService";
import productService from "../services/productService";
import Product from "../components/Product.vue";

export default {
  data() {
    const sortItems = [
      { key: "By creation date ascending", value: "createdAt,asc" },
      { key: "By creation date descending", value: "createdAt,desc" },
      { key: "By price ascending", value: "price,asc" },
      { key: "By price descending", value: "price,desc" },
    ];
    const pageSizeOptions = [10, 15, 25, 50];

    return {
      searchName: "",
      selectedCategories: [],
      minPrice: null,
      maxPrice: null,
      categories: [],
      page: 1,
      size: pageSizeOptions[0],
      pageSizeOptions: pageSizeOptions,
      sortItems: sortItems,
      sort: sortItems[0].value,
      totalPages: 0,
      products: [],
      loading: false,
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
        this.loading = true;
        const searchParams = this.createSearchParams();
        const res = await productService.searchProducts(
          searchParams,
          this.size,
          this.page - 1,
          this.sort,
        );
        this.totalPages = res.data.totalPages;
        this.products = res.data.content;
        this.loading = false;
      } catch (error) {
        this.loading = false;
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
      this.sort = this.sortItems[0].value;
      this.search();
    },
  },
  watch: {
    size() {
      this.search();
    },
    page() {
      this.search();
    },
  },
  components: {
    Product,
  },
};
</script>
