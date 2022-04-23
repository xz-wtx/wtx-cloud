<template>
  <x_init>
    <x_table
        ref="tableRef"
        :table-data="tableData"
        @load-data="loadData"></x_table>
    <el-dialog
        v-model="tableData.dialog.visible_1"
        title="添加/修改角色"
        width="50%"
        draggable
        :destroy-on-close="true"
        :close-on-click-modal="false"
    >
      <role_edit :table-data="tableData"></role_edit>
    </el-dialog>
    <el-dialog
        v-model="tableData.dialog.visible_2"
        title="分配角色"
        width="30%"
        draggable
        :destroy-on-close="true"
        :close-on-click-modal="false"
    >
      <el-tree
          ref="treeRef"
          :data="data.menuList"
          show-checkbox
          node-key="id"
          highlight-current
      />
      <div>
        <el-button type="primary" @click="submitAllotMenu">Submit</el-button>
        <el-button type="primary" @click="cancelAllotMenu">cancel</el-button>
      </div>
    </el-dialog>
  </x_init>
</template>

<script lang="ts">

import {nextTick, onMounted, reactive, ref} from "vue";
import Role_edit from "@/views/sys/role/role_edit.vue";
import type { ElTree } from 'element-plus'
import userCommon from "@/plugins/useCommon";


export default {
  name: "role_list",
  components: {Role_edit},
  setup(){
    const { proxy,upState,createTable,message }=userCommon();

    const data=reactive<any>({
      menuList:[],
      objMenu:{}
    });
    let xTableData = createTable();
    const treeRef = ref<InstanceType<typeof ElTree>>()

    xTableData.search.fields={
      name: {placeholder: '角色名称', type: "input", title: '角色名称'},
      code: {placeholder: '角色编号', type: "input", title: '角色编号'}
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
      {prop: 'name',label: '角色名称'},
      {prop: 'code',label: '角色编号'},
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
      width: 200,
      but: [
        {name: "分配菜单",func: allotMenu,authType:[1],auth:"role_edit"},
        {name: "修改",func: edit,authType:[1],auth:"role_edit"},
        {name: "禁用",func: upStatus,b_v:'OFF',authType:[1,3],field:"status",value:[0],auth:"role_edit"},
        {name: "启用",func: upStatus,b_v:'ON',authType:[1,3],field:"status",value:[1],auth:"role_edit"},
        {name: "删除",func: upStatus,b_v:'Y',authType:[1],auth:"role_edit"},
      ]
    };

    let tableData = reactive<any>(xTableData);

    let methods={
      //加载数据
      loadData(obj){
        proxy.$api.admin.role_api.getPageList(obj).then(res=>{
          tableData.pageLoad(res)
        })
      },
      //获取树形菜单
      getMenuTree(){
        proxy.$api.admin.menu_api.getTree({}).then(res=>{
          data.menuList=res.data.data
        })
      },
      //提交菜单申请
      submitAllotMenu(){
        const nodeData = treeRef.value!.getCheckedNodes(false, false);
        const keys = treeRef.value!.getHalfCheckedKeys();
        let nodes  = nodeData.map(function (item) { return item.value; });
        keys.forEach(p=>{
          if (nodes.indexOf(p)==-1){
            nodes.push(p)
          }
        })
        proxy.$api.admin.role_api.allotMenu({id: data.objMenu.id,menuIds:nodes}).then(res=>{
          if (message.hint(res)) {
            methods.cancelAllotMenu();
          }
        })
      },
      //取消菜单申请
      cancelAllotMenu(){
        tableData.dialog.visible_2=false;
        tableData.reload=true;
      }
    }

    //打开申请菜单
   function allotMenu(row){
     tableData.dialog.visible_2=true;
     data.objMenu=row;
     nextTick(()=>{
       treeRef.value!.setCheckedNodes(row.listMap,false)
     })
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
      upState(obj,but,tableData,proxy.$api.admin.role_api.edit);
    }

    onMounted(()=>{
       methods.getMenuTree();
    })

    return{
      treeRef,
      tableData,
      data,
      ...methods,
    }
  }
}
</script>

<style scoped>

</style>