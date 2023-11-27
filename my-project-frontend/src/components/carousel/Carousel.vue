<template>
  <el-carousel :interval="4000" type="card" height="200px" >
    <el-carousel-item v-for="item in form" :key="item" @click="goInfo(item.bookName)">
      <el-image :src="`${baseURL}${item.bannerSrc}`" fit="scale-down" style="width: 100%;height: 100%"></el-image>
    </el-carousel-item>
  </el-carousel>
</template>

<script setup>
import {reactive, onMounted, ref} from "vue"
import {getList} from "@/net/news";
import router from "@/router";
import api from "@/net/axios";
import {getBookByName} from "@/net/book";
const form = ref()
const baseURL = "http://192.168.0.124:8080"

onMounted(() =>{
  getList().then(res => {
    form.value = res.data.data
  })
})

const goInfo = (bookName) => {
  getBookByName({bookName}).then(res => {
    router.push('bookInfo/'+res.data.data[0].bookId)
  })
}
</script>

<style scoped>

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}


</style>
