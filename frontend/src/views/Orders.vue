<template>
  <h1>
    <v-icon>mdi-cart-plus</v-icon>
    My Orders
  </h1>

  <v-data-table-server
    class="mt-4"
    v-model:items-per-page="itemsPerPage"
    :headers="headers"
    :items-length="totalItems"
    :items="serverItems"
    :loading="loading"
    @update:options="loadItems"
    @click:row="navigateToOrder"
  >
    <template v-slot:item.orderStatus="{ item }">
      <div class="text-end">
        <v-chip
          :color="orderStatusChipColor(item.orderStatus)"
          :text="item.orderStatus"
          class="text-uppercase"
          label
          size="small"
        ></v-chip>
      </div>
    </template>

    <template v-slot:item.payment.paymentMethod="{ item }">
      <div class="text-end">
        <v-chip
          :color="paymentMethodChipColor(item.payment.paymentMethod)"
          :text="item.payment.paymentMethod"
          class="text-uppercase"
          label
          size="small"
        ></v-chip>
      </div>
    </template>
  </v-data-table-server>
</template>

<script>
import orderService from "../services/orderService";

export default {
  data() {
    return {
      itemsPerPage: 10,
      headers: [
        { title: "ID", key: "id", align: "start" },
        { title: "Price ($)", key: "orderPrice", align: "end" },
        { title: "Order status", key: "orderStatus", align: "end" },
        { title: "Payment Method", key: "payment.paymentMethod", align: "end" },
        {
          title: "Payment Date",
          key: "paymentDate",
          align: "end",
          sortable: false,
        },
      ],
      serverItems: [],
      loading: false,
      totalItems: 0,
    };
  },
  methods: {
    async loadItems({ page, itemsPerPage, sortBy }) {
      try {
        this.loading = true;

        let sort = "id,desc";
        if (sortBy.length > 0) {
          sort = "";
          sortBy.forEach((element) => {
            sort = sort + `${element.key},${element.order}`;
          });
        }

        const res = await orderService.getOdersByUserId(
          itemsPerPage,
          page - 1,
          sort,
        );

        this.serverItems = res.data.content.map((order) => ({
          ...order,
          orderPrice: order.orderPrice.toFixed(2),
          paymentDate: order.payment.paymentDate
            ? new Date(order.payment.paymentDate).toLocaleString()
            : "NOT PAID",
        }));

        this.totalItems = res.data.totalElements;
        this.loading = false;
      } catch (error) {
        console.error("loadItems() Orders.vue: ", error);
        const errorMessage =
          error.response?.data.message || "Loading orders failed.";
        this.$toast.error(errorMessage);
      }
    },
    navigateToOrder(_, row) {
      this.$router.push({
        name: "OrderInfo",
        params: { id: row.item.id },
      });
    },
    orderStatusChipColor(orderStatus) {
      switch (orderStatus) {
        case "PAID":
          return "pink";
        case "SHIPPED":
          return "blue";
        default:
          return "green";
      }
    },
    paymentMethodChipColor(paymentMethod) {
      switch (paymentMethod) {
        case "CARD":
          return "cyan";
        case "TRANSFER":
          return "purple";
        default:
          return "orange";
      }
    },
  },
};
</script>
