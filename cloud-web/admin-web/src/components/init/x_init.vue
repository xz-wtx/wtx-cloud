<template>
  <div style="width: 100%">
    <slot></slot>
  </div>
</template>

<script>
import useCurrentInstance from "@/plugins/use/useCurrentInstance";

export default {
  name: "init",
  setup(){
    const proxy=useCurrentInstance();
    //统一更改状态（禁用，启用，删除）
    function upStatus(obj,but,tableData,http) {
      let data;
      let title;
      if (but.b_v==='OFF')
      {
        data={id:obj.id,status: 1};
        title=(but.b_t===undefined?"确定禁用？":but.b_t);
      }
      if (but.b_v==='ON')
      {
        data={id:obj.id,status: 0};
        title=(but.b_t===undefined?"确定启用？":but.b_t);
      }
      if (but.b_v==='Y')
      {
        data={id:obj.id,deleteFlag: 1};
        title=(but.b_t===undefined?"确定删除？":but.b_t);
      }

      proxy.$messageBox.confirm(title,
          {confirmButtonText: '确定',cancelButtonText: '取消',type: 'warning'}
      ).then(() => {
        http(data).then(res=>{
          tableData.hint(res);
        })
      })
    }
    return{
      upStatus
    }
  }
}
</script>

<style scoped>

</style>