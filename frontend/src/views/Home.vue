<template>
  <v-container>
    <v-form @submit.prevent="searchWithFirstPage">
      <v-row>
        <v-col cols="12" md="2">
          <v-text-field
            v-model="searchName"
            label="Name"
            variant="outlined"
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
            variant="outlined"
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
            variant="outlined"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="2">
          <v-text-field
            v-model.number="maxPrice"
            label="Max price"
            prefix="$"
            type="number"
            variant="outlined"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="3">
          <v-select
            v-model="sort"
            :items="sortItems"
            item-title="key"
            item-value="value"
            label="Sort by"
            variant="outlined"
          ></v-select>
        </v-col>

        <v-btn
          type="submit"
          color="secondary"
          append-icon="mdi-magnify"
          class="ml-2"
          >Search</v-btn
        >
        <v-btn
          @click="handleReset"
          color="warning"
          append-icon="mdi-close"
          class="ml-2"
          >Clear</v-btn
        >
      </v-row>
    </v-form>

    <div class="my-12">
      <v-row justify="end">
        <v-col cols="12" md="2">
          <v-select
            v-model="size"
            :items="pageSizeOptions"
            label="Items per page"
            variant="outlined"
          ></v-select>
        </v-col>
      </v-row>
      <v-row v-if="loading">
        <v-col class="text-center">
          <v-progress-circular indeterminate></v-progress-circular>
        </v-col>
      </v-row>
      <v-row v-else>
        <v-col v-for="product in products" :key="product.id" cols="12" md="4">
          <product :product="product"></product>
        </v-col>
        <v-col cols="12" md="12" class="text-center">
          <v-pagination
            v-model="page"
            class="my-4"
            :length="totalPages"
            :total-visible="6"
          ></v-pagination>
        </v-col>
      </v-row>
    </div>
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
    const pageSizeOptions = [9, 15, 30, 60];

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
  async mounted() {
    await this.getCategories();
    this.readQueryParams();
    await this.search();
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
    async searchWithFirstPage() {
      this.page = 1;
      await this.search();
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
    },
    scrollToTop() {
      window.scrollTo({ top: 0, behavior: "auto" });
    },
    readQueryParams() {
      const { query } = this.$route;

      if (query.page) {
        this.page = parseInt(query.page);
      }

      if (query.size) {
        this.size = parseInt(query.size);
      }

      if (query.searchName) {
        this.searchName = query.searchName;
      }

      if (query.minPrice) {
        this.minPrice = parseFloat(query.minPrice);
      }

      if (query.maxPrice) {
        this.maxPrice = parseFloat(query.maxPrice);
      }

      if (query.sort) {
        const validSort = this.sortItems.some(
          (item) => item.value === query.sort,
        );
        if (validSort) {
          this.sort = query.sort;
        } else {
          this.sort = this.sortItems[0].value;
        }
      }

      if (query.selectedCategories) {
        // I'm checking if anyone has added a category to the url that doesn't exist
        const selectedCategories = query.selectedCategories
          .split(",")
          .map((id) => parseInt(id));
        const validCategories = selectedCategories.every((categoryId) =>
          this.categories.some((category) => category.id === categoryId),
        );
        if (validCategories) {
          this.selectedCategories = selectedCategories;
        } else {
          const validSelectedCategories = selectedCategories.filter(
            (categoryId) =>
              this.categories.some((category) => category.id === categoryId),
          );
          this.selectedCategories = validSelectedCategories;
        }
      }
    },
    writeQuery(newVal, queryName) {
      const query = { ...this.$route.query };
      if (!newVal) {
        delete query[`${queryName}`];
      } else {
        query[`${queryName}`] = newVal;
      }
      this.$router.push({ path: this.$route.path, query });
    },
  },
  watch: {
    size(newVal) {
      if (newVal === this.pageSizeOptions[0]) {
        this.writeQuery(null, "size");
      } else {
        this.writeQuery(newVal, "size");
      }
      this.page = 1;
      this.search();
    },
    page(newVal) {
      if (newVal === 1) {
        this.writeQuery(null, "page");
      } else {
        this.writeQuery(newVal, "page");
      }
      this.scrollToTop();
      this.search();
    },
    searchName(newVal) {
      this.writeQuery(newVal, "searchName");
    },
    minPrice(newVal) {
      this.writeQuery(newVal, "minPrice");
    },
    maxPrice(newVal) {
      this.writeQuery(newVal, "maxPrice");
    },
    sort(newVal) {
      if (newVal === this.sortItems[0].value) {
        this.writeQuery(null, "sort");
      } else {
        this.writeQuery(newVal, "sort");
      }
    },
    // selectedCategories: {
    //   deep: true,
    //   handle(newVal) {
    //     if (newVal.length === 0) {
    //       this.writeQuery(null, "selectedCategories");
    //     } else {
    //       this.writeQuery(newVal.join(","), "selectedCategories");
    //     }
    //   },
    // },
  },
  components: {
    Product,
  },
};
</script>
