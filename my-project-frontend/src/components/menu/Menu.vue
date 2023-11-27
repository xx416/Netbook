<template>
  <div>
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
    >
      <el-menu-item index="0">网上书城 (开发中)</el-menu-item>
      <div class="flex-grow"/>
      <el-menu-item index="1" @click="router.push('/')">商城</el-menu-item>
      <el-menu-item index="2" type="primary" style="margin-left: 16px" @click="verifyJwt()">购物车</el-menu-item>
      <div v-if="unauthorized()">
        <el-menu-item index="3" @click="router.push('/welcome')">亲，请登录</el-menu-item>
      </div>
      <div v-else-if="!unauthorized()">
        <el-menu-item index="3" @click="router.push('/person')">个人中心</el-menu-item>
      </div>
      <div v-if="!unauthorized()">
        <el-menu-item index="4" @click="out()">退出登录</el-menu-item>
      </div>
      <el-drawer v-model="drawer"
                 title="I am the title"
                 :with-header="false"
                 :before-close="handleClose"
                 size="50%"
      >
        <Cart/>
      </el-drawer>
    </el-menu>
  </div>
</template>

<script setup>
import router from "@/router"
import { ref, computed } from 'vue'
import Cart from "@/components/cart/Cart.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {unauthorized,logout} from "@/net";
import {useGoodCarStore} from "@/store";

const store = useGoodCarStore();
const activeIndex = ref('1')
const drawer = computed(() => store.switch)
function verifyJwt(){
  const isUnauthorized = unauthorized()
  if (!isUnauthorized){
    store.updateSwitch()
  }else{
    ElMessage.warning("亲，请登录");
  }
}

const handleClose = (() => {
  store.updateSwitch()
})

function out(){
  logout()
  router.push('/welcome')
}

</script>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

</style>
