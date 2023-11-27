<template>
  <div>
    <el-card class="box-card" shadow="always">
      <template #header>
        <div class="card-header">
          <span>分类</span>
        </div>
      </template>
      <div v-for="item in tag" class="text item">
        <el-link :underline="true" @click="updateList(item.name)">{{ item.name }}</el-link>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {reactive,ref} from "vue";
import {useBookStore} from "@/store";
import {getPageList} from "@/net/book";

const store = useBookStore()
const tag = ref([
      {name: '小说'},
      {name: '文学'},
      {name: '教育'},
      {name: '计算机科学'},
      {name: '散文'},
      {name: '管理学'},
      {name: '网络'},
    ])

function updateList(value){
  store.list = []
  store.selectValue = value
  const pageSize = store.countPage.pageSize;
  const currentPage = store.countPage.currentPage;
  const tagName = store.selectValue;
  getPageList({pageSize,currentPage,tagName}).then(res =>{
    store.list = res.data.data
  })
}

</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: auto;
}
</style>
