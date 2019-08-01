<template>
  <div class="login">
    <div class="login-box">
      <h3>水质管理系统</h3>
      <MyForm class="form" @submit="submit" :form="loginForm.form" :rules="loginForm.rules" :formItems="loginForm.formItems" :btn="loginForm.btn" ref="myform" />
    </div>
  </div>
</template>

<script>
// import Three from 'Three'
import {
  Scene,
  WebGLRenderer,
  PerspectiveCamera,
  BoxGeometry,
  MeshBasicMaterial,
  Mesh
} from "three";
import MyForm from "@/components/form/form";
import { login } from "@/service/api/login";
import md5 from "md5";
import { loginForm } from "./data";
import { _routes } from "@/router/routes";
export default {
  name: "login",
  data() {
    return {
      loginForm
    };
  },
  methods: {
    submit() {
      this.loginForm.btn.loading = true;
      // 保存
      login({
        username: this.loginForm.form.username,
        password: md5(this.loginForm.form.password),
        brandname: this.loginForm.form.brandname || "小荷"
      }).then(
        res => {
          this.loginForm.btn.loading = false;
          if (res.status === 200 && res.data.ret === 0) {
            localStorage.setItem("loginState", true);
            console.log(_routes)
            var menus = _routes;
            localStorage.setItem("menu", JSON.stringify(menus));
            sessionStorage.setItem("username", this.loginForm.form.username);
            sessionStorage.setItem("user_id", res.data.datas.user_id);
            sessionStorage.setItem("token", res.data.datas.token);
            sessionStorage.setItem("parent_id", res.data.datas.parent_id);
            sessionStorage.setItem("sub_id", res.data.datas.sub_id);
            this.$router.push({ path: "/content/bdmap" }, () => {
              this.$message({
                message: "登录成功！",
                type: "success"
              });
            });
          } else {
            this.loginForm.btn.loading = false;
            this.$message({
              message: "用户名或密码有误！",
              type: "error"
            });
          }
        },
        error => {
          this.loginForm.btn.loading = false;
        }
      );
    }
  },
  mounted() {
    if (isIE()) {
      return;
    }
    var SEPARATION = 100,
      AMOUNTX = 100,
      AMOUNTY = 70;

    var container;
    var camera, scene, renderer;

    var particles,
      particle,
      count = 0;

    var mouseX = 85,
      mouseY = -342;

    var windowHalfX = window.innerWidth / 2;
    var windowHalfY = window.innerHeight / 2;

    init();
    animate();

    function init() {
      // container = document.createElement('div');
      container = document.getElementsByClassName("login")[0];
      // document.body.appendChild(container);

      camera = new THREE.PerspectiveCamera(
        120,
        window.innerWidth / window.innerHeight,
        1,
        10000
      );
      camera.position.z = 1000;

      scene = new THREE.Scene();

      particles = new Array();

      var PI2 = Math.PI * 2;
      var material = new THREE.ParticleCanvasMaterial({
        color: 0xe1e1e1,
        program: function(context) {
          context.beginPath();
          context.arc(0, 0, 0.6, 0, PI2, true);
          context.fill();
        }
      });

      var i = 0;

      for (var ix = 0; ix < AMOUNTX; ix++) {
        for (var iy = 0; iy < AMOUNTY; iy++) {
          particle = particles[i++] = new THREE.Particle(material);
          particle.position.x = ix * SEPARATION - AMOUNTX * SEPARATION / 2;
          particle.position.z = iy * SEPARATION - AMOUNTY * SEPARATION / 2;
          scene.add(particle);
        }
      }

      renderer = new THREE.CanvasRenderer();
      renderer.setSize(window.innerWidth, window.innerHeight);
      container.appendChild(renderer.domElement);
      document.addEventListener("mousemove", onDocumentMouseMove, false);
      document.addEventListener("touchstart", onDocumentTouchStart, false);
      document.addEventListener("touchmove", onDocumentTouchMove, false);
      window.addEventListener("resize", onWindowResize, false);
    }

    function onWindowResize() {
      windowHalfX = window.innerWidth / 2;
      windowHalfY = window.innerHeight / 2;

      camera.aspect = window.innerWidth / window.innerHeight;
      camera.updateProjectionMatrix();

      renderer.setSize(window.innerWidth, window.innerHeight);
    }

    function onDocumentMouseMove(event) {
      mouseX = event.clientX - windowHalfX;
      mouseY = event.clientY - windowHalfY;
    }

    function onDocumentTouchStart(event) {
      if (event.touches.length === 1) {
        event.preventDefault();

        mouseX = event.touches[0].pageX - windowHalfX;
        mouseY = event.touches[0].pageY - windowHalfY;
      }
    }

    function onDocumentTouchMove(event) {
      if (event.touches.length === 1) {
        event.preventDefault();

        mouseX = event.touches[0].pageX - windowHalfX;
        mouseY = event.touches[0].pageY - windowHalfY;
      }
    }

    function animate() {
      requestAnimationFrame(animate);

      render();
    }

    function render() {
      camera.position.x += (mouseX - camera.position.x) * 0.05;
      camera.position.y += (-mouseY - camera.position.y) * 0.05;
      camera.lookAt(scene.position);

      var i = 0;

      for (var ix = 0; ix < AMOUNTX; ix++) {
        for (var iy = 0; iy < AMOUNTY; iy++) {
          particle = particles[i++];
          particle.position.y =
            Math.sin((ix + count) * 0.3) * 50 +
            Math.sin((iy + count) * 0.5) * 50;
          particle.scale.x = particle.scale.y =
            (Math.sin((ix + count) * 0.3) + 1) * 2 +
            (Math.sin((iy + count) * 0.5) + 1) * 2;
        }
      }

      renderer.render(scene, camera, container);

      count += 0.1;
    }
  },
  components: { MyForm }
};

function isIE() {
  if (window.navigator.userAgent.indexOf("MSIE") >= 1) return true;
  else return false;
}
</script>

<style lang="less" scoped>
@import url("./login.less");
</style>
