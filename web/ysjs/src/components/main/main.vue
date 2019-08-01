<template>
  <div class="main" style="padding: 10px 0">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "Main"
};
</script>

<style>
.main {
  background: #ecf0f5;
}

/*滚动条样式*/
.main::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 8px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 5px;
}
.main::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 5px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(120, 231, 166, 0.2);
}
.main::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: rgba(168, 166, 166, 0.2);
}
</style>

