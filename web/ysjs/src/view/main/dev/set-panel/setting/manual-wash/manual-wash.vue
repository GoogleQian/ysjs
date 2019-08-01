<template>
  <div class="manual-wash">
    <p style="margin-bottom: 10px;">手动冲洗</p>
    <el-button @click="ok">手动冲洗</el-button>
  </div>
</template>

<script>
import { wash } from "@/service/api/set";
export default {
  props: {
    id: [Number, String]
  },
  methods: {
    ok() {
      const that = this;
      this.$confirm("确认下发手动冲洗命令?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.wash();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    wash() {
      wash({ devId: this.id, state: this.$store.state.app.devModel })
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
