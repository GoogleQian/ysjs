<template>
  <div class="user-set">
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="130px" class="demo-dataForm" style="overflow: hidden;">
      <el-row>
        <el-col :xs="24" :md="8">
          <el-form-item label="最近一次下发时间：" prop="sendTime" label-width="200px">
            {{dataForm.sendTime | dateFormat}}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="最近一次上报时间：" prop="reportTime" label-width="200px">
            {{dataForm.reportTime | dateFormat}}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :xs="24" :md="24">
          <el-form-item label="当前时间">
            <el-select v-model.number="dataForm.day" placeholder="当前日" style="width:100px;">
              <el-option label="星期一" :value="1"></el-option>
              <el-option label="星期二" :value="2"></el-option>
              <el-option label="星期三" :value="3"></el-option>
              <el-option label="星期四" :value="4"></el-option>
              <el-option label="星期五" :value="5"></el-option>
              <el-option label="星期六" :value="6"></el-option>
              <el-option label="星期日" :value="7"></el-option>
            </el-select>
            <el-select v-model.number="dataForm.hours" placeholder="时" style="width:100px;">
              <el-option :label="item-1" :value="item-1" v-for="item in 24" :key="item"></el-option>
            </el-select>
            <el-select v-model.number="dataForm.minutes" placeholder="分" style="width:100px;">
              <el-option :label="item-1" :value="item-1" v-for="item in 60" :key="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="开机日">
            <el-select v-model="dataForm.startDay" placeholder="开机日">
              <el-option label="星期一" :value="1"></el-option>
              <el-option label="星期二" :value="2"></el-option>
              <el-option label="星期三" :value="3"></el-option>
              <el-option label="星期四" :value="4"></el-option>
              <el-option label="星期五" :value="5"></el-option>
              <el-option label="星期六" :value="6"></el-option>
              <el-option label="星期日" :value="7"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="关机日">
            <el-select v-model="dataForm.stopDay" placeholder="关机日">
              <el-option label="星期一" :value="1"></el-option>
              <el-option label="星期二" :value="2"></el-option>
              <el-option label="星期三" :value="3"></el-option>
              <el-option label="星期四" :value="4"></el-option>
              <el-option label="星期五" :value="5"></el-option>
              <el-option label="星期六" :value="6"></el-option>
              <el-option label="星期日" :value="7"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时开机一：">
            <el-time-picker v-model="dataForm.startOne" placeholder="定时开机一" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时关机一：">
            <el-time-picker v-model="dataForm.stopOne" placeholder="定时关机一" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时开机二：">
            <el-time-picker v-model="dataForm.startTwo" placeholder="定时开机二" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时关机二：">
            <el-time-picker v-model="dataForm.stopTwo" placeholder="定时关机二" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时开机三：">
            <el-time-picker v-model="dataForm.startThr" placeholder="定时开机三" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-form-item label="定时关机三：">
            <el-time-picker v-model="dataForm.stopThr" placeholder="定时关机三" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时消毒一：">
            <el-time-picker v-model="dataForm.cleanOne" placeholder="定时消毒一" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时消毒二：">
            <el-time-picker v-model="dataForm.cleanTwo" placeholder="定时消毒二" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时消毒三：">
            <el-time-picker v-model="dataForm.cleanThr" placeholder="定时消毒三" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时换水时间：">
            <el-time-picker v-model="dataForm.changeTime" placeholder="定时换水时间：" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="换水时长">
            <el-input v-model.number="dataForm.hangeTime" placeholder="换水时长"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="换水周期">
            <el-input v-model.number="dataForm.changeCycle" placeholder="换水周期"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="滤芯寿命">
            <el-input v-model.number="dataForm.filterLife" placeholder="换水周期"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="显示温度">
            <el-input v-model.number="dataForm.showTemp" placeholder="显示温度"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="实际温度">
            <el-input v-model.number="dataForm.actualTemp" placeholder="实际温度"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时冲洗时间">
            <el-time-picker v-model="dataForm.flushTime" placeholder="定时换水时间：" value-format="HH:mm" format="HH:mm" :picker-options="{
      selectableRange: '00:00:00 - 23:50:00'
    }"></el-time-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="冲洗时长">
            <el-input v-model.number="dataForm.flushDuration" placeholder="冲洗时长"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :md="8">
          <el-form-item label="定时消毒时长">
            <el-input v-model.number="dataForm.cleanDuration" placeholder="定时消毒时长"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button @click="submitForm('dataForm')" :loading="submiting">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserParams, setUserParams } from "@/service/api/set";
import { rules } from "./data";
export default {
  props: {
    id: [Number, String]
  },
  data() {
    return {
      sysParams: {},
      dataForm: {},
      rules,
      edit: false,
      submiting: false,
      resetData: {}
    };
  },
  mounted() {
    this.getUserParams();
  },
  methods: {
    getUserParams() {
      // 获取系统参数
      getUserParams({
        devId: this.id,
        state: this.$store.state.app.devModel
      }).then(res => {
        if (res.data.ret === 0) {
          this.dataForm = res.data.datas;
          this.resetData = { ...this.dataForm };
          console.log(this.dataForm);
        } else {
          this.$message({
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    setUserParams() {
      // 设置系统参数
      this.submiting = true;
      setUserParams({
        ...this.dataForm,
        state: this.$store.state.app.devModel,
        type: this.getChangeType().join(",")
      })
        .then(res => {
          this.submiting = false;
          if (res.data.ret === 0) {
            this.$alert("下发成功", "提示", {
              confirmButtonText: "确定",
              type: "success"
            });
            this.edit = false;
          } else if (res.data.ret === 1115) {
            this.dataForm = { ...this.resetData };
            this.$alert(res.data.msg, "提示", {
              confirmButtonText: "确定",
              type: "error"
            });
          }
        })
        .catch(() => {
          this.submiting = false;
          this.$alert("请求出错", "提示", {
            confirmButtonText: "确定",
            type: "error"
          });
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.setUserParams();
          this.resetData = { ...this.dataForm };
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    getChangeType() {
      let arr = [];
      for (let key in this.resetData) {
        if (this.dataForm[key] !== this.resetData[key]) {
          arr.push(key);
        }
      }
      return arr;
    }
  },
  filters: {
    renderDay(value) {
      switch (value) {
        case 1:
          return "一";
          break;
        case 2:
          return "二";
          break;
        case 3:
          return "三";
          break;
        case 4:
          return "四";
          break;
        case 5:
          return "五";
          break;
        case 6:
          return "六";
          break;
        case 7:
          return "日";
          break;
        default:
          return;
      }
    }
  }
};
</script>

<style>
</style>
