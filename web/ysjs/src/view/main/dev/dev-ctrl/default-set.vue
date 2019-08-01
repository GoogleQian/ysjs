<template>
    <div class="default-set">
        <p style="margin-bottom: 10px;">用户所有参数将恢复到默认设置</p>
        <el-button @click="ok">恢复到默认设置</el-button>
    </div>
</template>

<script>
import { getModel, reset } from "@/service/api/set";
export default {
  data() {
    return {
      state: null
    };
  },
  created() {
    this.getModel();
  },
  methods: {
    getModel() {
      getModel({ devId: this.$route.query.id }).then(res => {
        if (res.data.ret === 0) {
          this.state = res.data.datas.type;
          //   this.$store.commit("setDevModel", this.type);
        } else {
          this.$alert(res.data.msg, "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        }
      });
    },
    ok() {
      const that = this;
      this.$confirm("确认恢复默认设置?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.reset();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    reset() {
      reset({ devId: this.$route.query.id, state: this.state })
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

<style scoped lang="less">
.default-set {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
}
</style>
