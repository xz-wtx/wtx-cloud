<template>
  <x_init>
    <x_table
        ref="tableRef"
        :table-data="tableData"
        @load-data="loadData"></x_table>
    <el-dialog
        v-model="tableData.dialog.visible_1"
        title="添加/修改白名单IP"
        width="40%"
        draggable
        :destroy-on-close="true"
        :close-on-click-modal="false"
    >
      <ip_edit :table-data="tableData"></ip_edit>
    </el-dialog>
  </x_init>
</template>

<script lang="ts">
import { reactive} from "vue";
import Ip_edit from "@/views/sys/white/ip_edit.vue";
import userCommon from "@/plugins/useCommon";


export default {
  name: "ip_list",
  components: {Ip_edit},
  setup(){
    const { proxy,upState,createTable,message }=userCommon();
    const data=reactive<any>({});
    let xTableData = createTable();


    xTableData.search.fields={
      ip: {placeholder: 'IP', type: "input", title: 'IP'},
      remark: {placeholder: '备注', type: "input", title: '备注'},
    };
    xTableData.search.but=[
      {
        title:"添加",//TODO 按钮名称
        func:add,//TODO 事件
        auth:"white_ip_add"//TODO 按钮权限（可写可不写）
        //icon:'',//TODO 图片（可写可不写）
      },
    ]
    xTableData.column=[
      {prop: 'ip',label: 'ip'},
      {prop: 'remark',label: 'remark'}
    ]
    xTableData.columnBut={
      width: 200,
      but: [
        {name: "修改",func: edit,authType:[1],auth:"white_ip_edit"},
        {name: "删除",func: upStatus,b_v:'Y',authType:[1],auth:"white_ip_edit"},
      ]
    };

    let tableData = reactive<any>(xTableData);
    // tableData.row.drag.open=true;
    // tableData.row.drag.func=function (currRow,upRow,dataList) {
    //   //返回排序结果
    // }
    let methods={
      //加载数据
      loadData(obj){
        proxy.$api.admin.ip_api.getPageList(obj).then(res=>{
          tableData.pageLoad(res)
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

    //删除
    function upStatus(obj,but) {
      upState(obj,but,tableData,proxy.$api.admin.ip_api.del);
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