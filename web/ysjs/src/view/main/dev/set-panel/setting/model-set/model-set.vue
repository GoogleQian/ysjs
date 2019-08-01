<template>
  <div class="model-set">
    <p style="margin-bottom: 10px;">模式设置</p>
    <el-radio-group v-model="type">
      <el-radio :label="1">JN模式</el-radio>
      <el-radio :label="0">BJ模式</el-radio>
    </el-radio-group>
    <p style="margin-top: 10px;">
      <el-button  @click="setModel">下发</el-button>
    </p>
  </div>
</template>

<script>
import { getModel, setModel } from "@/service/api/set";
export default {
  props: {
    id: {
      type: [String, Number]
    }
  },
  data() {
    return {
      type: null
    };
  },
  created() {
    this.getModel();
  },
  methods: {
    getModel() {
      getModel({ devId: this.id }).then(res => {
        if (res.data.ret === 0) {
          this.type = res.data.datas.type;
          this.$store.commit("setDevModel", this.type);
        } else {
          this.$alert(res.data.msg, "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        }
      });
    },
    setModel() {
      setModel({ devId: this.id, state: this.type })
        .then(res => {
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
            this.$store.commit("setDevModel", this.type);
          } else {
            this.type = this.type === 0 ? 1 : 0;
            this.$store.commit("setDevModel", this.type);
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(error => {
          this.type = this.type === 0 ? 1 : 0;
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

<style>
</style>
