<template>
  <div>
    <!-- 功能+table -->
    <z-table :tableData="tableData" :funcs="[]" :tableColumns="tableColumns" :page="page" @handleCurrentChange="handleCurrentChange" @handleSelectionChange="handleSelectionChange"></z-table>
  </div>

</template>

<script>
import ZTable from "@/components/z-table/z-table";
import { tableColumns } from "./data.devlist";
import { getAllDevs } from "./api";
export default {
  components: { ZTable },
  created() {
    this.getList();
  },
  data() {
    return {
      tableData: [],
      tableColumns,
      page: {
        total: 0,
        currentPage: 1
      }
    };
  },
  methods: {
    getList() {
      const params = {
        page: this.page.currentPage,
        id: Number(localStorage.getItem("userId")),
        faultFlag: 0
      };
      getAllDevs(params).then(res => {
        if (res.data.ret === 0) {
          this.tableData = res.data.datas.list;
          this.page.total = res.data.datas.itemCounts;
        }
      });
    },
    handleCurrentChange(val) {
      this.getList();
    },
    handleSelectionChange(val){
        let ids = val.map(item => item.device_id);
        this.$emit('selectionChange', ids);
    }
  }
};
</script>

<style>
</style>
