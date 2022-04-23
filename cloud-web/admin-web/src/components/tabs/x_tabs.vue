<template>
  <div>
    <el-tabs
        v-model="editableTabsValue"
        type="card"
        class="demo-tabs"
        closable
        @tab-remove="removeTab"
        @tab-change="tabChange"
    >
      <el-tab-pane
          v-for="item in editableTabs"
          :key="item.path"
          :label="item.title"
          :name="item.path"
      >
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="ts">
import {onMounted, ref, watch} from "vue";
import router from "@/routes";
import storage from "@/store/storage/storage";



export default {
  name: "x_tabs",



  setup(){


    const editableTabsValue = ref('')
    const editableTabs = ref<any>([])
    const routes=ref<any>([]);


    const removeTab = (targetName: string) => {
      const tabs = editableTabs.value
      let activeName = editableTabsValue.value
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.path === targetName) {
            const nextTab = tabs[index + 1] || tabs[index - 1]
            if (nextTab) {
              activeName = nextTab.path
            }
          }
        })
      }

      editableTabsValue.value = activeName
      editableTabs.value = tabs.filter((tab) => tab.path !== targetName)
      if (editableTabs.value.length ==0) {
        storage.delCurrentMenu();
        router.push("/home")
        routes.value=router.getRoutes();
      }
    }

    function tabChange(targetName){
      router.push(targetName)
    }

    function addTab({title="",path=""}) {
      console.log(path)
      routes.value.forEach((route) => {
        if (path!==''&&route.path === path) {
          storage.setCurrentMenu({path:route.path,title:route.meta.title})
          router.push(route.path);
          return;
        }
        if (title!==''&&route.meta.title === title) {
          storage.setCurrentMenu({path:route.path,title:route.meta.title})
          router.push(route.path);
          return;
        }
      })
    }

    watch(() =>router.currentRoute.value.path,(newValue,oldValue)=> {
      //查询是否存在打开
      const isObj = editableTabs.value.filter((tab) => tab.path === newValue);
      if (isObj.length>0){
        storage.setCurrentMenu(isObj[0])
        editableTabsValue.value = isObj[0].path
        return;
      }
      //获取当前菜单（从菜单列表中点击）
      const item =storage.getCurrentMenu();
       if (item===null){
         return;
       }
      const parse = JSON.parse(item);
      const filter = editableTabs.value.filter((tab) => tab.path === parse.path);
      if (filter.length==0){
        editableTabs.value.push(parse)
      }
       editableTabsValue.value = parse.path
    },{ immediate: true })

    onMounted(()=>{
      window["addTab"]=addTab
    })
    return{
      removeTab,
      tabChange,
      editableTabsValue,
      editableTabs,
    }
  },


}


</script>

<style scoped>

</style>