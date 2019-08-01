<template>
  <div class="devList">
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" max-height="300" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="Id" width="120">
      </el-table-column>
      <el-table-column prop="device_id" label="设备编号" show-overflow-tooltip>
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top: 10px;" @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="50" layout="prev, pager, next, jumper" :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    tableData: {
      type: Array,
      default: function() {
        return [{}];
      }
    },
    total: {
      default: 0,
      type: Number
    },
    selectedIds: {
      type: Array,
      default: function() {
        return [];
      }
    }
  },
  data() {
    return {
      currentPage: 1
    };
  },
  updated() {
    console.log("updated");
    // 勾选当前用户已选设备
    this.toggleSelection();
  },
  computed: {
  },
  watch: {
    // 监听tableData和selectIDS的变化及时勾选已分配设备
    tableData(_new, _old) {
      this.toggleSelection();
    },
    selectedIds(_new, _old) {
      this.toggleSelection();
    }
  },
  methods: {
    toggleSelection() {
      let rows = [];
      rows = this.tableData.filter(item => {
        return this.selectedIds.includes(item.id);
      });
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      const ids = this.multipleSelection.map(item => item.id);
      this.$emit("selectionChange", ids);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.$emit("currentChange", val);
    }
  },
  beforeDestroy(){
    console.log('destory')
    this.$refs.multipleTable.clearSelection();
  }
};
</script>

<style>
</style>
