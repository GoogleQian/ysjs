<template>
  <div class="sale-plan top-border">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="type" label="售水类型">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { saleTypeList } from "@/service/api/saleType";
import { addForm, searchData, tableItems } from "./data";
export default {
  name: "dev",
  data() {
    return {
      tableData: [],
      page: {
        total: 0,
        currentPage: 1
      },
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
      saleTypeList(params).then(res => {
        this.tableLoading = false;
        this.tableData = res.data.datas.list;
        this.page.total = res.data.datas.itemCounts;
      });
    }
  }
};
</script>

<style scoped lang="less">
.sale-plan {
  padding: 10px;
  margin: 10px;
}
</style>
