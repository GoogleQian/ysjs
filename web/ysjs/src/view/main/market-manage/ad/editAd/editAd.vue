<template>
  <div class="market-manage top-border">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="图片">
        <input ref="imgField" hidden type="file" @change="imgChange($event)">
        <div>
          <img @click="handleclick" :src="form.img_url" alt="" style="cursor: pointer;max-width: 100%;max-height: 100%;border: 1px solid #ccc;padding:10px;">
        </div>
      </el-form-item>
      <el-form-item label="是否使用外链">
        <el-switch v-model="form.use_hyper_link" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#eee">
        </el-switch>
      </el-form-item>
      <el-form-item label="内容详情" v-if="!form.use_hyper_link">
        <tinymce :height="300" v-model="form.content" action="/api/adv/uploadRichTextImage" :headers="headers" />
      </el-form-item>
      <el-form-item label="URL" v-if="form.use_hyper_link">
        <el-input v-model="form.url" placeholder=""></el-input>
      </el-form-item>
      <el-form-item style="margin-top: 40px;">
        <el-button type="success" @click="submitUrl()">提交</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Tinymce from "@/components/Tinymce/";
import { uploadAd, editAd, getAdDetail } from "@/service/api/ad";
import addIcon from "./add.png";
export default {
  data() {
    return {
      headers: {
        token: null
      },
      form: {
        img_url: addIcon
      },
      fileList: [],
      useHyperLink: false,
      formData: new FormData()
    };
  },
  mounted() {
    this.headers.token = localStorage.getItem("token");
    if (this.$route.query.row !== undefined) {
      const row = JSON.parse(this.$route.query.row);
      this.getAdDetail(row);
    } 
  },
  methods: {
    handleclick() {
      this.$refs.imgField.click();
    },
    getAdDetail(row) {
      getAdDetail({ adId: row.id }).then(res => {
        if (res.data.ret === 0) {
          this.form = { ...res.data.datas };
          console.log(this.form);
        }
      });
    },
    imgChange($event) {
      this.form.img_url = window.URL.createObjectURL($event.target.files[0]);
      this.formData.append("file", $event.target.files[0]);
    },
    submitUrl() {
      console.log(this.form);
      // return;
      this.formData.append("use_hyper_link", this.form.use_hyper_link);
      this.formData.append("user_id", localStorage.getItem("userId"));
      if (this.form.url) {
        this.formData.append("url", this.form.url);
      }
      if (this.form.name) {
        this.formData.append("name", this.form.name);
      }
      if (this.form.content) {
        this.formData.append("content", this.form.content);
      }
      if (this.form.id) {
        this.formData.append("id", this.form.id);
      }
      if (this.form.use_hyper_link) {
        this.formData.append("use_hyper_link", this.form.use_hyper_link);
      }
      if (this.$route.query.row !== undefined) {
        // 修改
        // this.formData.append("id", this.form.id);
        editAd({ id: this.form.id, data: this.formData })
          .then(res => {
            if (res.data.ret === 0) {
              this.$message({
                message: "上传成功",
                type: "success"
              });
              this.$router.push("/content/ad");
            } else {
              this.$message({
                message: "上传失败",
                type: "error"
              });
              // this.formData = new FormData();
            }
          })
          .catch(error => {
            // this.formData = new FormData();
          });
      } else {
        // 新增
        uploadAd(this.formData)
          .then(res => {
            if (res.data.ret === 0) {
              this.$message({
                message: "上传成功",
                type: "success"
              });
              this.$router.push("/content/ad");
            } else {
              this.$message({
                message: "上传失败",
                type: "error"
              });
              // this.formData = new FormData();
            }
          })
          .catch(error => {
            // this.formData = new FormData();
          });
      }
    },
    cancel(){
      this.$router.go(-1)
    }
  },
  components: { Tinymce }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
