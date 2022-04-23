<template>
  <el-menu
      class="el-menu-vertical-demo"
      :unique-opened="true"
      :default-active="this.$route.path"
      :router="false"
      :collapse="menuData.isCollapse"
      active-text-color="#2a6fbc"
      background-color="#2a394c"
      text-color="#bfcbd9"
  >

    <x_menu_item :menu-list="menuData.menuItems"></x_menu_item>
  </el-menu>
</template>

<script lang="ts" >
import x_menu_item from "@/components/menu/x_menu_item.vue";
export default {
  name:"x_menu",

  props:{
    menuData:{
      menuItems:{
        type:Array,
        default:[]
      },
      isCollapse:{
        type:Boolean,
        default:false
      },
    }

  },
  components:{
    x_menu_item
  },

setup(props, { attrs, slots, emit }) {
  return{

    };

  }
}

</script>

<style scoped>

</style>