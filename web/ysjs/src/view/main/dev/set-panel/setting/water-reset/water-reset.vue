<template>
  <div class="water-reset">
    <p style="margin-bottom: 10px;">手动复位净水量</p>
    <el-button @click="ok">复位净水量</el-button>
  </div>
</template>

<script>
import { resetWater } from "@/service/api/set";
export default {
  props: {
    id: [Number, String]
  },
  methods: {
    ok() {
      const that = this;
      this.$confirm("确认复位净水量?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.resetWater();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    resetWater() {
      resetWater({ devId: this.id, state: this.$store.state.app.devModel })
        .then(res => {
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
          } else {
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(() => {
          this.$alert("请求出错", "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        });
    }
  }
};
</script>

<style>
</style>
