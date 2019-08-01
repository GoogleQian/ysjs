<template>
    <div class="water-standard top-border">
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="TDS" label="TDS">
            </el-table-column>
            <el-table-column prop="standard" label="水质等级">
                <template slot-scope="scope">
                    <span :style="`color: ${scope.row.color}`">{{scope.row.standard}}</span>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        {
          TDS: "0~50",
          standard: "水质：优",
          color: "#67C23A",
        },
        {
          TDS: "50~100",
          standard: "水质：良",
          color: "#409EFF",
        },
        {
          TDS: "100~300",
          standard: "水质：中",
          color: "#E6A23C",
        },
        {
          TDS: "大于300",
          standard: "水质：差",
          color: "#F56C6C",
        }
      ]
    };
  }
};
</script>

<style lang="less" scoped>
.water-standard {
  padding: 10px;
  margin: 10px;
}
</style>
