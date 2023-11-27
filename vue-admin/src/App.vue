<script setup>
import {provide, nextTick, ref} from 'vue'
const isRouterActive = ref(true)
provide('reload', () => {
  isRouterActive.value = false
  nextTick(() => {
    isRouterActive.value = true
  })
})
</script>

<template>
  <div>
    <router-view/>
  </div>
</template>

<style scopd>
</style>
