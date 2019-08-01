// 可用于新增表单和登录表单
// 接受props：rules(表单验证规则)、form(表单数据)、btnText(表单保存按钮文本)、formItems(表单项)
// 向上传递表单验证成功事件submit
<template>
  <el-form :model="form" status-icon :rules="rules" ref="form" label-width="100px" class="demo-ruleForm" auto-complete="off">
    <template v-for="item in formItems">
      <el-form-item :label="item.label" :prop="item.value" :key="item.value">
        <el-input v-if="item.type === 'pwd'" type="password" v-model="form[item.value]" auto-complete="off"></el-input>
        <el-input v-if="item.type === 'number'" v-model.number="form[item.value]" auto-complete="off"></el-input>
        <el-input v-if="item.type === 'text'" v-model="form[item.value]" auto-complete="off"></el-input>
        <el-date-picker value-format="timestamp" v-if="item.type==='date'" v-model="form[item.value]" type="date" placeholder="选择日期" auto-complete="off"></el-date-picker>
      </el-form-item>
    </template>
    <el-form-item>
      <el-button  @click="submitForm('form')" :loading="btn.loading">{{btn.text}}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "myForm",
  data() {
    return {};
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$store.commit("toggleSubmitBtnLoading", true);
          // 前端规则验证完成后，向上触发保存函数
          this.$emit("submit");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  },
  computed: {
    submitBtnLoading() {
      return this.$store.state.app.submitBtnLoading;
    }
  },
  props: {
    rules: {
      type: Object,
      default: function() {
        return {};
      }
    },
    form: {
      type: Object,
      default: function() {
        return {};
      }
    },
    formItems: {
      type: Array,
      default: function() {
        return [];
      }
    },
    btn: {
      type: Object,
      default: function() {
        return {
          text: "保存",
          loading: false
        };
      }
    }
  }
};
</script>

<style lang="less" scoped>
@import url("./form.less");
</style>
