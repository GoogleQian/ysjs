<template>
  <div class="manage">
    <p style="margin-bottom: 10px;">
      设备编号：{{$route.query.device_id}}
    </p>
    <p style="margin: 10px 0;padding-top: 10px;border-top: 1px solid #eee;">
    </p>
    <el-form class="demo-form-inline" label-width="90px">
      <el-form-item label="售水方案：">
        <el-select v-model="pipsData.schema_id" placeholder="请选择">
          <el-option v-for="item in sellTypes" :key="item.id" :label="item.pro_name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <div style="overflow: hidden">
        <el-form-item label="通道设置：">
        </el-form-item>
      </div>
      <el-row v-for="(pip, index) in pipsData.passList" :key="pip.passage_no">
        <el-col :span="22">
          <el-col :xs="24" :md="4">
            <el-form-item label="通道号：">
              <el-select v-model="pip.passage_no" style="width: 100px">
                <el-option label="1" :value="1"></el-option>
                <el-option label="2" :value="2"></el-option>
                <el-option label="3" :value="3"></el-option>
                <el-option label="4" :value="4"></el-option>
                <el-option label="5" :value="5"></el-option>
                <el-option label="6" :value="6"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="4">
            <el-form-item label="出水类型：">
              <el-select v-model="pip.passage_type" style="width: 100px">
                <el-option v-for="item in saleType" :key="item.id" :value="item.id" :label="item.type"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="7">
            <el-form-item label="脉冲数：">
              <el-input v-model="pip.pulse_num" placeholder="脉冲数" style="width: 100px"></el-input> / 100ML
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="9">
            <el-form-item label="售水价格：">
              <el-input v-model.number="pip.str_price" placeholder="价格(元)" style="width: 100px"></el-input> 元 / 100ML
            </el-form-item>
          </el-col>
        </el-col>
        <el-col :span="2">
          <el-col :xs="24" :md="24">
            <el-button type="danger" @click="removePip(index)" style="margin: 0; padding-left: 2px;padding-right: 2px" icon="el-icon-delete">删除</el-button>
          </el-col>
        </el-col>
      </el-row>
      <div style="text-align: center;">
        <el-button @click="addPip()" icon="el-icon-circle-plus-outline">增加通道</el-button>
      </div>
      <div style="text-align: center;margin-top: 20px;">
        <el-button type="primary" @click="setPips" style="width: 200px;">保存</el-button>
      </div>
      <el-form-item>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  userlist,
  bindUser,
  setPips,
  setPips1,
  getPips,
  getPrice
} from "@/service/api/manage";
import { saleTypeList } from "@/service/api/saleType";
import { getList } from "@/service/api/sale-plan.js";
export default {
  data() {
    return {
      options: [],
      agent: null,
      pipsData: {
        passList: []
      },
      saleType: [],
      priceTypeDialog: false,
      priceType: null,
      priceTypes: [],
      sellTypes: [],
      sellType: null,
      index: null,
      flag: 0 // 0新增，1修改
    };
  },
  created() {
    this.getPips();
    this.getTypeList();
    this.getSaleType();
  },
  methods: {
    //   添加一项
    addPip(index) {
      this.pipsData.passList.push({});
    },
    removePip(index) {
      this.pipsData.passList.splice(index, 1);
    },
    handleClickSelect(event, pip) {
      getPrice({ id: pip.passage_type }).then(res => {
        // this.priceTypeDialog = true;
        this.priceTypes = res.data.datas;
      });
    },
    // 出水类型
    getTypeList() {
      saleTypeList().then(res => {
        this.saleType = res.data.datas.list;
      });
    },
    getSaleType() {
      getList().then(res => {
        this.sellTypes = res.data.datas.list;
      });
    },
    setType(pip, index) {
      this.index = index; // 获取数据索引
      // 根据出水类型获取该类型的售水方案
      getPrice({ id: pip.passage_type }).then(res => {
        this.priceTypeDialog = true;
        this.priceTypes = res.data.datas;
      });
    },
    // 将售水方案赋给item
    bindPriceType() {
      pipsData[this.index].str_price = this.priceType;
    },
    getPips() {
      getPips({ id: this.$route.query.id }).then(res => {
        if (res.data.ret === 0) {
          this.pipsData = res.data.datas;
          if (this.pipsData.passList.length === 0) {
            this.flag = 0;
            this.pipsData.passList.push({});
          } else {
            this.flag = 1;
          }
        }
      });
    },
    // 给设备绑定供应商
    binduser() {
      bindUser({
        id: this.$route.query.id,
        params: { userId: this.agent }
      }).then(res => {
        if (res.data.ret === 0) {
          this.$message({
            message: "绑定成功",
            type: "success"
          });
        }
      });
    },
    setPips() {
      const params = {
        id: this.$route.query.id,
        data: { ...this.pipsData }
      };
      if (this.flag === 0) {
        setPips(params).then(res => {
          if (res.data.ret === 0) {
            this.$message({
              message: "设置成功",
              type: "success"
            });
          } else {
            this.$message({
              message: res.data.msg,
              type: "error"
            });
          }
        });
      } else if (this.flag === 1) {
        setPips1(params).then(res => {
          if (res.data.ret === 0) {
            this.$message({
              message: "设置成功",
              type: "success"
            });
          } else {
            this.$message({
              message: res.data.msg,
              type: "error"
            });
          }
        });
      }
    }
  }
};
</script>

<style lang="less" scoped>
.manage {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
  .el-row {
    margin-bottom: 30px;
    border-bottom: 1px solid #eee;
  }
}
</style>
