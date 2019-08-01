<template>
    <div class="timing-set">
        <p style="margin-bottom: 10px;">模式设置</p>
        <!-- <p>系统运行在定时模式下，按下“切换到连续运行”按钮，将设备设置为连续运行模式</p> -->
        <!-- <el-button @click="setTimeModel">切换到{{type === 0 ? 1 : 0 | renderType}}模式</el-button> -->
        <el-radio-group v-model="state">
            <el-radio :label="1">定时模式</el-radio>
            <el-radio :label="0">连续运行模式</el-radio>
        </el-radio-group>
        <p style="margin-top: 10px;">
            <el-button @click="setTimeModel">下发</el-button>
        </p>
    </div>
</template>

<script>
import { getModel, setTimeModel } from "@/service/api/set";
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
    setTimeModel() {
      setTimeModel({
        devId: this.$route.query.id,
        type: this.state,
        state: this.state
      })
        .then(res => {
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
          } else {
            this.state = this.state === 0 ? 1 : 0; // 切换状态
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(() => {
          this.state = this.state === 0 ? 1 : 0; // 切换状态
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
.timing-set {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
}
</style>
