<template>
    <div class="water_status top-border">
        <!-- 搜索 -->
        <z-search :searchItems="searchItems" @search="search"></z-search>
        <!-- 功能+table -->
        <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>

    </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import ZSearch from "@/components/z-search/z-search";
// import { getList } from "@/service/api/water_status";
import { getList } from "./api.js";
import { tableColumns, searchItems, funcs } from "./data";
export default {
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
      funcs,
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
      const params = { page: this.page.currentPage, ...this.searchForm };
      getList(params).then(res => {
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
      switch (opera) {
        case "修改":
          this.edit(row);
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del(row);
          break;
      }
    },
    edit(row){
        console.log(row)
    },
    add(row){
        console.log(row)
    },
    del(row){
        console.log(row)
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    }
  },
  components: { ZTable, ZSearch }
};
</script>

<style scoped lang="less">
@import "./index.less";
</style>
