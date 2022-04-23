<template>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="1">
      <el-scrollbar >
        <x_menu :menu-data="menuData" ></x_menu>
      </el-scrollbar>
    </el-aside>

    <el-container>

      <el-header style=" font-size: 12px;height: 40px">
        <x_header @zoomMenu="zoomMenu"></x_header>
        <x_tabs></x_tabs>
      </el-header>
      <div style="margin-top:45px" v-if="this.$route.path!=='/home'"></div>
      <el-main style="background-color: #f1f1f1;">
        <el-scrollbar>

          <home></home>

        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>


<script lang="ts">
import {onMounted, reactive} from 'vue'
import x_header from "@/components/header/x_header.vue";
import x_menu from "@/components/menu/x_menu.vue";
import Home from "@/components/layout/layout.vue";
import X_tabs from "@/components/tabs/x_tabs.vue";
import store from "@/store";


export default {
  name:"index",
  components:{X_tabs, x_header,x_menu,Home},
  setup(){


    const menuData=reactive<any>({
      menuItems:[],
      isCollapse:false
    });

      const zoomMenu=(bool: any)=>{
        menuData.isCollapse=bool;
    };

      onMounted(()=>{
        methods.loadMenu();
      })

    const methods={
        //获取菜单
        loadMenu(){
           let user=store.getters.getUser;
          menuData.menuItems=user.menuList;
        }
    }
    return{
      menuData,
      zoomMenu,
      ...methods
    }
  }

}

</script>

<style scoped>
@import "../../assets/styles/route-transition.scss";

.layout-container-demo .el-header {
  position: relative;
  background-color: #2a394c;
  color: #fff;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;

}
::v-deep(.el-scrollbar__view) {
  height: 100%;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  height: 100%;
}
.el-menu--collapse {
  height: 100%;
}

/**子级菜单背景颜色**/
::v-deep(.el-sub-menu .el-menu) {
  border: none;
  background-color: #000000;
}


.el-main {
 --el-main-padding: 0px;

}
</style>