<template>
  <div>
    <el-form>
      <el-form-item>
        <el-row :gutter="10" style="width: 100%;justify-content: flex-end;">
          <el-col :span="8">
            <el-input placeholder="请输入书名" v-model="value"></el-input>
          </el-col>
          <el-drawer v-model="drawer" title="I am the title" :with-header="false" size="60%">
            <Cart/>
          </el-drawer>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ref, watch} from "vue";
import {getBookByName} from "@/net/book";
import {useBookStore} from "@/store";

const store = useBookStore()
const drawer = ref(false)
const value = ref('')

watch(value,() =>{
  const bookName = value.value
  getBookByName({bookName}).then(res =>{
    store.list = []
    store.list = res.data.data
  })
})

</script>

<style scoped>


</style>