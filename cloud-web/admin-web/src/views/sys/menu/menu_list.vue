<template>
  <x_init>
    <x_table
        ref="tableRef"
        :table-data="tableData"
        @load-data="loadData">
      <template #left>
        <div style="width: 180px;padding: 10px">
         <div style="margin-bottom: 10px;">
           拖动排序：
         </div>

          <el-tree
              @node-drop="handleDrop"
              :data="tableData.data"
              draggable
              :props="{value: 'id',label: 'title',children: 'children',}"
              node-key="id"
          />
        </div>
      </template>
    </x_table>
    <el-dialog
        v-model="tableData.dialog.visible_1"
        title="添加/修改菜单"
        width="50%"
        draggable
        :destroy-on-close="true"
        :close-on-click-modal="false"
    >
      <menu_edit :table-data="tableData"></menu_edit>
    </el-dialog>
  </x_init>
</template>

<script lang="ts">

import {nextTick, onMounted, reactive} from "vue";
import Menu_edit from "@/views/sys/menu/menu_edit.vue";
import userCommon from "@/plugins/useCommon";

export default {
  name: "dept_list",
  components: {Menu_edit},
  setup(){
    const { proxy,upState,createTable,message }=userCommon();

    const data=reactive<any>({});
    let xTableData = createTable();

    xTableData.search.fields={
      title: {placeholder: '菜单名称', type: "input", title: '菜单名称'},
      authCode: {placeholder: '权限编码', type: "input", title: '权限编码'},
      path: {placeholder: '路由地址', type: "input", title: '路由地址'}
    };
    xTableData.search.but=[
      {
        title:"添加",//TODO 按钮名称
        func:add,//TODO 事件
        auth:"menu_edit"//TODO 按钮权限（可写可不写）
        //icon:'',//TODO 图片（可写可不写）
      },
    ]
    xTableData.column=[
      {prop: 'title',label: '菜单名称',width: 190},
      {prop: 'authCode',label: '权限编码',width: 110},
      {prop: 'path',label: '路由地址'},
      {prop: 'filePath',label: '文件路径'},
      {prop: 'name',label: '组件名称',width: 130},
      {prop: 'type',label: '菜单类型',width: 80},
      {prop: 'visible',label: '菜单可见',width: 80},
      {prop: 'sort',label: '排序',width: 50},
      {prop: 'status',label: '状态',width: 90,
        render: function (row) {
          if (row.status==0){
            return '<div style="color:#39b45f;">可用</div>';
          }
          if (row.status==1){
            return '<div style="color:#dc0920;">不可用</div>';
          }
          return "";
        }}
    ]
    xTableData.columnBut={
      width: 180,
      but: [
        {name: "修改",func: edit,authType:[1],auth:"menu_edit"},
        {name: "复制",func: copy,authType:[1],auth:"menu_edit"},
        {name: "禁用",func: upStatus,b_v:'OFF',b_t:'确定删除当前菜单及子级菜单？',authType:[1,3],field:"status",value:[0],auth:"menu_edit"},
        {name: "启用",func: upStatus,b_v:'ON',b_t:'确定删除当前菜单及子级菜单？',authType:[1,3],field:"status",value:[1],auth:"menu_edit"},
        {name: "删除",func: upStatus,b_v:'Y',b_t:'确定删除当前菜单及子级菜单？',authType:[1],auth:"menu_edit"},
      ]
    };
    xTableData.openPage=false;
    let tableData = reactive<any>(xTableData);

    let methods={
      loadData(obj){
        proxy.$api.admin.menu_api.getTreeList(obj).then(res=>{
          tableData.load(res)
        })
      },
      //拖拽排序
      handleDrop(before,after,inner,ev){
        //before、after、inner
        let bid=before.data.id
        let aId=after.data.id
        if (inner==='after'){
           bid=after.data.id
           aId=before.data.id
        }
        proxy.$api.admin.menu_api.changeSort({bId:bid,aId:aId,type:inner}).then(res=>{
          message.hint(res,tableData);
        })
      }
    }

    //新增
    function add(){
      tableData.dialog.selectData=[];
      tableData.dialog.visible_1=true;
    }
    //修改
    function edit(obj) {
      add();
      tableData.dialog.selectData.push(obj);

    }
    //复制
    function copy(obj){
      message.confirmBox({title:"确定复制改目录及子目录"}).then(res=>{
        proxy.$api.admin.menu_api.copyMenu(obj).then(res=>{
          message.hint(res,tableData);

        })
      })
    }

    //禁用，启用，删除
    function upStatus(obj,but) {
      upState(obj,but,tableData,proxy.$api.admin.menu_api.edit);
    }

    return{
      tableData,
      data,
      ...methods,
    }
  }
}
</script>

<style scoped>

</style>