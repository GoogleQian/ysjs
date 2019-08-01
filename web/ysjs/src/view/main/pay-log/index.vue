<template>
  <div class="pay-log top-border">
    <!-- 搜索 -->
    <z-search :searchItems="searchItems" @search="search"></z-search>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
import { tableColumns, searchItems } from "./data";
import { getOrderList } from "@/service/api/order";
export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1,
        page_size: 10
      },
      tableColumns,
      searchItems,
      tableLoading: false,
      searchForm: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.tableLoading = true;
      const params = { page: this.page.currentPage, page_size: this.page.page_size, ...this.searchForm };
      getOrderList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.datas.list;
        this.page.total = res.data.datas.itemCounts;
      });
    },
    search(searchForm) {
      console.log(searchForm);
      this.page.currentPage = 1;
      this.searchForm = searchForm;
      this.getList();
    },
    func({ opera, row }) {
      console.log(opera);
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    }
  },
  computed: {
    funcs() {
      return this.$store.state.user.funcs;
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
