<template>
  <div class="dev-ctrl">
    <p style="margin-bottom: 10px;">
      设备编号：{{$route.query.device_id}}
    </p>
    <p style="margin: 10px 0;padding-top: 10px;border-top: 1px solid #eee;">
    </p>
    <div class="btnBox">
      <div>
        <el-button
          @click="command('手动换水')"
          style="width: 116px;"
        >手动换水</el-button>
      </div>
      <div>
        <el-button
          @click="command('手动消毒')"
          style="width: 116px;"
        >手动消毒</el-button>
      </div>
      <div>
        <el-button
          @click="command('手动冲洗')"
          style="width: 116px;"
        >手动冲洗</el-button>
      </div>
      <div>
        <el-button
          @click="command('手动复位净水量')"
          style="width: 116px;"
        >手动复位净水量</el-button>
      </div>
      <div>
        手动开关机：
        <el-switch
          v-model="machine_status"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="0"
          :inactive-value="1"
          @change="machine_statusChange"
        >
        </el-switch>
      </div>
    </div>
  </div>
</template>

<script>
import {
  manualClean,
  changeWater,
  wash,
  resetWater,
  getModel,
  ctlMachineStatus
} from "@/service/api/set";
export default {
  data() {
    return {
      state: null,
      machine_status: null
    };
  },
  created() {
    this.getModel();
  },
  mounted() {
    this.machine_status = this.$route.query.machine_status;
  },
  methods: {
    getModel() {
      getModel({ devId: this.$route.query.id }).then(res => {
        if (res.data.ret === 0) {
          this.type = res.data.datas.type;
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
    machine_statusChange(val) {
      ctlMachineStatus({ machineStatus: val, devId: this.$route.query.id })
        .then(res => {
          if (res.data.ret === 0) {
            this.$message({
              message: "下发成功",
              type: "success"
            });
          } else {
            this.$message({
              message: res.data.msg,
              type: "error"
            });
          }
        })
        .catch(() => {
          this.$message({
            message: "请求出错",
            type: "error"
          });
          // this.machine_status =
        });
    },
    command(type) {
      if (this.state == undefined) {
        this.$alert("未获取到设备机型无法下发命令", "提示", {
          confirmButtonText: "确定"
        });
        return;
      }
      this.$confirm(`确定下发${type}命令？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          switch (type) {
            case "手动换水":
              this.changeWater();
              break;
            case "手动消毒":
              this.manualClean();
              break;
            case "手动冲洗":
              this.wash();
              break;
            case "手动复位净水量":
              this.resetWater();
              break;
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消"
          });
        });
    },
    changeWater() {
      changeWater({ devId: this.$route.query.id, state: this.state })
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
    },
    manualClean() {
      manualClean({ devId: this.$route.query.id, state: this.state })
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
    },
    wash() {
      wash({ devId: this.$route.query.id, state: this.state })
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
    },
    resetWater() {
      resetWater({ devId: this.$route.query.id, state: this.state })
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

<style scoped lang="less">
.dev-ctrl {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
  .btnBox {
    display: flex;
    justify-content: space-around;
    flex-direction: column;
    align-items: center;
    height: 200px;
  }
}
</style>
