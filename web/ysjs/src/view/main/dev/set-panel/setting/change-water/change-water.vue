<template>
  <div class="change-water">
    <p style="margin-bottom: 10px;">更换净水机内储水</p>
    <el-button @click="ok">手动换水</el-button>
  </div>
</template>

<script>
import { changeWater } from "@/service/api/set";
export default {
  props: {
    id: [Number, String]
  },
  methods: {
     ok() {
      const that = this;
      this.$confirm("确认下发换水命令?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.changeWater();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    changeWater() {
      changeWater({ devId: this.id, state: this.$store.state.app.devModel })
        .then(res => {
          console.log(res);
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
