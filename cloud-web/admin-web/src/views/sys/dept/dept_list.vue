<template>
  <x_init>
    <x_table
        ref="tableRef"
        :table-data="tableData"
        @load-data="loadData"></x_table>
    <el-dialog
        v-model="tableData.dialog.visible_1"
        title="添加/修改部门"
        width="50%"
        draggable
        :destroy-on-close="true"
        :close-on-click-modal="false"
    >
      <dept_edit :table-data="tableData"></dept_edit>
    </el-dialog>
  </x_init>
</template>

<script lang="ts">

import {reactive} from "vue";
import Dept_edit from "@/views/sys/dept/dept_edit.vue";
import userCommon from "@/plugins/useCommon";


export default {
  name: "dept_list",
  components: {Dept_edit},
  setup(){

    const { proxy,upState,createTable,message }=userCommon();
    const data=reactive<any>({});
    let xTableData = createTable();

    xTableData.search.fields={
      name: {placeholder: '部门名称', type: "input", title: '部门名称'},
      level: {placeholder: '部门等级', type: "input", title: '部门等级'}
    };
    xTableData.search.but=[
      {
        title:"添加",//TODO 按钮名称
        func:add,//TODO 事件
        auth:"role_edit"//TODO 按钮权限（可写可不写）
        //icon:'',//TODO 图片（可写可不写）
      },
    ]
    xTableData.column=[
      {prop: 'name',label: '部门名称'},
      {prop: 'level',label: '部门等级'},
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
      width: 150,
      but: [
        {name: "修改",func: edit,authType:[1],auth:"role_edit"},
        {name: "禁用",func: upStatus,b_v:'OFF',b_t:'确定删除当前部门及子级部门？',authType:[1,3],field:"status",value:[0],auth:"role_edit"},
        {name: "启用",func: upStatus,b_v:'ON',b_t:'确定删除当前部门及子级部门？',authType:[1,3],field:"status",value:[1],auth:"role_edit"},
        {name: "删除",func: upStatus,b_v:'Y',b_t:'确定删除当前部门及子级部门？',authType:[1],auth:"role_edit"},
      ]
    };
    xTableData.openPage=false;
    xTableData.defaultExpandAll=true;
    let tableData = reactive<any>(xTableData);

    let methods={
      loadData(obj){
        proxy.$api.admin.dept_api.getTreeList(obj).then(res=>{
          tableData.load(res)
        })
      },
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
    //禁用，启用，删除
    function upStatus(obj,but) {
      upState(obj,but,tableData,proxy.$api.admin.dept_api.edit);
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