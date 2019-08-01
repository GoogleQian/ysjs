<template>
    <div class="model-set">
        <p style="margin-bottom: 10px;">模式设置</p>
        <el-radio-group v-model="state">
            <el-radio :label="1">JN模式</el-radio>
            <el-radio :label="0">BJ模式</el-radio>
        </el-radio-group>
        <p style="margin-top: 10px;">
            <el-button @click="setModel">下发</el-button>
        </p>
    </div>
</template>

<script>
import { getModel, setModel } from "@/service/api/set";
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
        } else {
          this.$alert(res.data.msg, "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        }
      });
    },
    setModel() {
      setModel({ devId: this.$route.query.id, state: this.state })
        .then(res => {
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
            this.$store.commit("setDevModel", this.state);
          } else {
            this.type = this.type === 0 ? 1 : 0;
            this.$store.commit("setDevModel", this.state);
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(error => {
          this.state = this.state === 0 ? 1 : 0;
          console.log(error);
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
.model-set {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
}
</style>
