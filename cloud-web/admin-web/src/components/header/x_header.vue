<template>

        <div class="toolbar" style="display: flex">
          <div @click="foldUp(!this.isCollapse)" style="flex: 1;">
            <el-icon :size="20" >
              <fold v-if="!this.isCollapse" />
              <expand v-if="this.isCollapse" />
            </el-icon>
          </div>

          <el-dropdown  @command="handleCommand">
            <el-icon style="margin-right: 8px; margin-top: 1px;color: white">
              <setting/>
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="personal">个人信息</el-dropdown-item>
                <el-dropdown-item command="quit">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Tom</span>
        </div>



</template>

<script lang="ts" >
import {onBeforeMount, onMounted, ref} from 'vue'
import store from "@/store";
import userCommon from "@/plugins/useCommon";
export default {

  setup(props,context) {
    const { body } = document
    const { proxy }=userCommon();

    const isCollapse = ref(false);

     const methods={
        foldUp(bool: any){
         isCollapse.value = bool;
         context.emit("zoomMenu", bool)
       },

        handleCommand (command: string){
          if (command==='quit'){
            store.commit("clearSave")
            proxy.$router.push({ path: '/' });
          } else if (command==='personal'){
            window["addTab"]({path: '/home/personal'})
            //proxy.$router.push({ path: '/home/personal' });
          }

       },
       //动态调整页面大小
       resizeHandler(){
          const rect = body.getBoundingClientRect();
          const b = rect.width - 1 < 998;
          methods.foldUp(b)
      }
    }
    //在挂载之前
    onBeforeMount(()=>{
      window.addEventListener('resize', methods.resizeHandler)
    })
    //首次加载
    onMounted(()=>{
      methods.resizeHandler();
    })

    return{
      isCollapse,
      ...methods,
    }
  }

}
</script>

<style scoped>


</style>