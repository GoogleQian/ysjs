<template>
  <div class="manage">
    <p style="margin-bottom: 10px;">设备编号：{{$route.query.device_id}}</p>
    所属经销商：<el-select v-model="userId" placeholder="请选择">
      <el-option v-for="item in options" :key="item.userId" :label="item.mchName+ ' —— ' +item.username" :value="item.userId">
      </el-option>
    </el-select>
    <el-button @click="binduser">确定</el-button>
  </div>
</template>

<script>
import { userlist, bindUser } from "@/service/api/manage";
export default {
  data() {
    return {
      options: [],
      userId: null
    };
  },
  created() {
    this.getuserlist();
    this.userId = Number(this.$route.query.user_id);
  },
  methods: {
    // 获取供应商列表
    getuserlist() {
      userlist().then(res => {
        if (res.data.ret === 0) {
          this.options = res.data.datas;
        }
      });
    },
    // 给设备绑定供应商
    binduser() {
      bindUser({
        id: this.$route.query.id,
        params: { userId: this.userId }
      }).then(res => {
        if (res.data.ret === 0) {
          this.$message({
            message: "绑定成功",
            type: "success"
          });
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.manage {
  background: #fff;
  padding: 20px;
  margin: 0 10px;
}
</style>
