<template>
    <div class="sys-set">
        <div style="padding-bottom: 10px;;border-bottom: 1px solid #eaeaea;margin-bottom: 20px;">
            设备类型：{{dataForm.state === 0 ? '步进' : '节能'}}
            <!-- <el-button @click="edit=true" style="float:right" size="mini" >修改</el-button> -->
        </div>
        <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="130px" class="demo-dataForm">
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
                <el-col :xs="24" :md="6">
                    <el-form-item label="停止加热温度：" prop="stopTemp">
                        <el-input v-model.number="dataForm.stopTemp" @blur="doCommand({type: '停止加热温度'})"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :md="6">
                    <el-form-item label="自动清洗：" prop="flushType">
                        <el-switch v-model="dataForm.flushType" :active-value="1" :inactive-value="0" @change="doCommand({type: '自动清洗'})"></el-switch>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :md="6">
                    <el-form-item label="滤芯管理功能：" prop="filterType">
                        <el-switch v-model="dataForm.filterType" :active-value="1" :inactive-value="0" @change="doCommand({type: '滤芯管理功能'})"></el-switch>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :md="6">
                    <el-form-item label="LOGO展示：" prop="logoShows" @change="doCommand({type: 'LOGO展示'})">
                        <el-switch v-model="dataForm.logoShows" :active-value="1" :inactive-value="0"></el-switch>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :md="6">
                    <el-form-item label="进水温差：" prop="inTemp" @blur="doCommand({type: '进水温差'})">
                        <el-input v-model.number="dataForm.inTemp"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :md="6">
                    <el-form-item label="水位检测灵敏度：" prop="delicacy">
                        <el-input v-model.number="dataForm.delicacy" @blur="doCommand({type: '水位检测灵敏度'})"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <template v-if="dataForm.state == 0">
                <!-- 步进型 -->
                <el-row>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="排空功能：" prop="emptyType">
                            <el-switch v-model="dataForm.emptyType" :active-value="1" :inactive-value="0" @change="doCommand({type: '排空功能'})"></el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="保温温差：" prop="keepTemp">
                            <el-input v-model.number="dataForm.keepTemp" @blur="doCommand({type: '保温温差'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="保温回差：" prop="keepHeight">
                            <el-input v-model.number="dataForm.keepHeight" @blur="doCommand({type: '保温回差'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="进水速度：" prop="speed">
                            <el-input v-model.number="dataForm.speed" @blur="doCommand({type: '进水速度'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="补水温差：" prop="addTemp">
                            <el-input v-model.number="dataForm.addTemp" @blur="doCommand({type: '补水温差'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="补水量：" prop="addAmount">
                            <el-input v-model.number="dataForm.addAmount" @blur="doCommand({type: '补水量'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="显示温度：" prop="showTemp">
                            <el-input v-model.number="dataForm.showTemp" @blur="doCommand({type: '显示温度'})"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
            <template v-if="dataForm.state == 1">
                <!-- 节能型 -->
                <el-row>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="出水方式：" prop="outType">
                            <el-switch v-model="dataForm.outType" :active-value="1" :inactive-value="0" @change="doCommand({type: '出水方式'})"></el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="换水功能：" prop="changeType">
                            <el-switch v-model="dataForm.changeType" :active-value="1" :inactive-value="0" @change="doCommand({type: '换水功能'})"></el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="消毒功能：" prop="disinfectType">
                            <el-switch v-model="dataForm.disinfectType" :active-value="1" :inactive-value="0" @change="doCommand({type: '消毒功能'})"></el-switch>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="进水回差：" prop="inHeight">
                            <el-input v-model="dataForm.inHeight" @blur="doCommand({type: '进水回差'})"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="换水方式：" prop="changeMethod">
                            <el-select v-model="dataForm.changeMethod" placeholder="换水方式" @change="doCommand({type: '换水方式'})">
                                <el-option label="开进水阀和排水阀，从排水管排水" :value="1"></el-option>
                                <el-option label="开进水阀和所有触摸开关，从龙头排水" :value="2"></el-option>
                                <el-option label="排空，打开排空阀和所有触摸开关，利用空气压力慢速排水" :value="3"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="24" :md="6">
                        <el-form-item label="水位监测：" prop="checkWater" @change="doCommand({type: '水位监测'})">
                            <el-switch v-model.number="dataForm.checkWater" :active-value="1" :inactive-value="0"></el-switch>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
            <el-form-item>
                <el-button @click="submitForm('dataForm')" :loading="submiting">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { getSysParams, getModel, setSysParams } from "@/service/api/set";
import { DataForm, stepRules, energyRules } from "./sys-set.data";
import Moment from "moment";
export default {
  data() {
    return {
      sysParams: {},
      state: null,
      dataForm: new DataForm(),
      rules: {},
      edit: false,
      submiting: false,
      resetData: {},
      type: { type: null }
    };
  },
  created() {
    this.getModel();
    setTimeout(() => {
      this.getSysParams();
    }, 1000);
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
    getSysParams() {
      // 获取系统参数
      getSysParams({
        devId: this.$route.query.id,
        state: this.state
      }).then(res => {
        console.log(res);
        if (res.data.ret === 0) {
          this.dataForm = res.data.datas;
          this.resetData = { ...this.dataForm };
          if (res.data.datas.state === 0) {
            this.rules = stepRules;
          } else {
            this.rules = energyRules;
          }
        } else {
          this.$message({
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
    setSysParams() {
      // 设置系统参数
      this.submiting = true;
      setSysParams({ ...this.dataForm, type: this.getChangeType().join(",") })
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
          } else {
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
    doCommand(type) {
      console.log(type);
      //   this.type = type;
      //   this.setSysParams();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        console.log(this.dataForm);
        if (valid) {
          this.setSysParams();
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
  }
};
</script>

<style scoped lang="less">
.sys-set {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
}
</style>

<style>
</style>
