<template>

  <template v-for="menu in menuList" :key="menu.id+''">

      <el-sub-menu :index="menu.id+''" v-if="menu.type===0&&menu.visible===0" :key="menu.id+''">
        <template #title>
          <el-icon > <component v-if="menu.icon!==''" :is="menu.icon"></component></el-icon>
          <span>{{ menu.title }}</span>
        </template>
        <x_menu_item :menuList="menu.children" />
      </el-sub-menu>
      <el-menu-item :index="menu.path" :key="menu.id+''" v-if="menu.type===1&&menu.visible===0" @click="onMenu(menu)">
        <template #title>
          <el-icon ><component  v-if="menu.icon!==''" :is="menu.icon"></component></el-icon>
          <span>{{ menu.title }}</span>
        </template>
      </el-menu-item>

  </template>
</template>
<script lang="ts" setup>
import {defineProps, onMounted} from 'vue'
import router from "@/routes";
import storage from "@/store/storage/storage";
import store from "@/store";
defineProps({
  menuList: {
    type: Array,
    default: () => []
  }
})
function onMenu(obj){
  const path=obj.path;
  if (path.indexOf("http")<0) {
    storage.setCurrentMenu({path:obj.path,title:obj.title});
    router.push(path)
  }else{
    window.open(path)
    router.go(0)
  }
}


</script>