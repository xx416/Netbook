<template>
  <div style="width: 80%;margin: 0 auto">
    <div class="common-layout">
      <el-container>
        <el-header style="padding: 0">
          <Menu/>
        </el-header>
          <router-view></router-view>
        <el-footer style="text-align: center;"></el-footer>
      </el-container>
    </div>
  </div>
</template>

<script setup>
import Menu from '@/components/menu/Menu.vue'
import {intCard} from "@/net/card";
import {unauthorized} from "@/net";
import {useGoodCarStore} from "@/store";

const store = useGoodCarStore();

if (!unauthorized() && (store.listArr.length == 0)){
  console.log("初始化购物车成功")
  intCard()
}

</script>

<style scoped>

</style>
