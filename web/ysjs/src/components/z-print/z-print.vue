<template>
  <div class="print" :style="`width: 100%`">
    <el-table :data="tableData" border :fit="true">
      <el-table-column v-for="column in tableColumns" :key="column.value" :label="column.label">
        <!-- 单行表头， 可接收html格式文本 -->
        <template slot-scope="scope" v-if="!column.mul">
          <div v-html="column.formatter(scope.row)" v-if="column.formatter"></div>
          <div v-else>
            {{scope.row[column.value]}}
          </div>
        </template>
        <!-- 多行表头， 可接收html格式文本 -->
        <el-table-column v-if="column.mul" v-for="subColumn in column.subColumns" :label="subColumn.label" :key="subColumn.value" :width="subColumn.width">
          <template slot-scope="scope">
            <div v-html="subColumn.formatter(scope.row)" v-if="subColumn.formatter"></div>
            <div v-else>
              {{scope.row[subColumn.value]}}
            </div>
          </template>
        </el-table-column>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      tableColumns: [],
      componentWidth: 1000
    };
  },
  mounted() {
    const _this = this;
    // 打印完成后返回上页
    window.onafterprint = res => {
      _this.$router.go(-1);
    };
    // 赋值数据
    console.log(this.$route)
    this.tableData = this.$route.params.tableData;
    this.tableColumns = this.$route.params.tableColumns;
    console.log(this.tableData)
    console.log(this.tableColumns)
    this.componentWidth = this.$route.params.componentWidth || 2000;
    // 宽度求和赋给组件
    // this.componentWidth = this.tableColumns
    //   .map(item => item.width || 100)
    //   .reduce((total, num) => {
    //     return total + num;
    //   });
    //   执行打印
    setTimeout(() => {
      window.print();
    }, 100);
  }
};
</script>

<style scoped lang="less">
</style>
