<template>
  <div class="timeset-model">
    <p style="margin-bottom: 10px;">模式设置</p>
    <!-- <p>系统运行在定时模式下，按下“切换到连续运行”按钮，将设备设置为连续运行模式</p> -->
    <!-- <el-button @click="setTimeModel">切换到{{type === 0 ? 1 : 0 | renderType}}模式</el-button> -->
    <el-radio-group v-model="type">
      <el-radio :label="1">定时模式</el-radio>
      <el-radio :label="0">连续运行模式</el-radio>
    </el-radio-group>
    <p style="margin-top: 10px;">
      <el-button @click="setTimeModel">下发</el-button>
    </p>
  </div>
</template>

<script>
import { getTimeModel, setTimeModel } from "@/service/api/set";
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
    this.getTimeModel();
  },
  methods: {
    getTimeModel() {
      getTimeModel({
        devId: this.id,
        state: this.$store.state.app.devModel
      }).then(res => {
        if (res.data.ret === 0) {
          this.type = res.data.datas.type;
        }
      });
    },
    setTimeModel() {
      setTimeModel({
        devId: this.id,
        type: this.type,
        state: this.$store.state.app.devModel
      })
        .then(res => {
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
          } else {
            this.type = this.type === 0 ? 1 : 0; // 切换状态
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(() => {
          this.type = this.type === 0 ? 1 : 0; // 切换状态
          this.$alert("请求出错", "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        });
    }
  },
  filters: {
    renderType(value) {
      return value === 0 ? "定时" : "连续运行";
    }
  }
};
</script>

<style>
</style>
