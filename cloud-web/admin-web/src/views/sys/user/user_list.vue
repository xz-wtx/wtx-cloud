<template>

    <x_init>
      <x_table
              ref="tableRef"
              :table-data="tableData"
               @load-data="loadData">
        <template #left>
          <div style="width: 180px;padding: 10px">
            <el-tree
                @node-click="nodeDeptClick"
                :data="data.treeDept"
                :expand-on-click-node="false"
                default-expand-all
                :props="{value: 'id',label: 'name',children: 'children',}"
                node-key="id"
            />
          </div>
        </template>
      </x_table>
      <el-dialog
          v-model="tableData.dialog.visible_1"
          title="添加/修改用户"
          width="50%"
          draggable
          :destroy-on-close="true"
          :close-on-click-modal="false"
      >
      <user_edit :table-data="tableData"></user_edit>
      </el-dialog>
    </x_init>

</template>

<script lang="ts">

import {onMounted, reactive, ref} from "vue";
import User_edit from "@/views/sys/user/user_edit.vue";
import userCommon from "@/plugins/useCommon"


export default {
  name: "user_list",
  components: {User_edit},
  setup(props,context){

    const { proxy,upState,createTable,message }=userCommon();

    const data=reactive<any>({
      treeDept:[]
    });



    let xTableData = createTable();

    xTableData.search.fields={
      userName: {placeholder: '用户名', type: "input", title: '用户名'},
      phone: {placeholder: '手机号', type: "input", title: '手机号'},
      account: {placeholder: '账号', type: "input", title: '账号'},
      status: {
        type: "select",title: '状态',value:'',
        options: {value: "v", label: "l", data: [{v: '', l: '所有'}, {v: '0', l: "启用"}, {v: '1', l: "禁用"}]}
      },
    };
    xTableData.search.but=[
      {
        title:"添加",//TODO 按钮名称
        func:add,//TODO 事件
        auth:"user_edit"//TODO 按钮权限（可写可不写）
        //icon:'',//TODO 图片（可写可不写）
      },
      {
        title:"重置密码",//TODO 按钮名称
        func:resetPassword,//TODO 事件
        auth:"user_edit"//TODO 按钮权限（可写可不写）
        //icon:'',//TODO 图片（可写可不写）
      }
    ]
    xTableData.columnBox.checkBox=true
    xTableData.column=[
      {prop: 'img',label: '图像',width: 70,
        render: function (row) {
          return '<img width="24" height="24" src='+row.img+'/>'
        }},
      {prop: 'token',label: 'Token',showOverflowTooltip: true},
      {prop: 'phone',label: '手机号',width: 130},
      {prop: 'userName',label: '用户名',width: 130},
      {prop: 'account',label: '账号',width: 130},
      {prop: 'mail',label: '邮箱',width: 170},
      {prop: 'mail',label: '邮箱',width: 170},
      {prop: 'mail',label: '邮箱',width: 170},
      {prop: 'mail',label: '邮箱',width: 170},
      {prop: 'mail',label: '邮箱',width: 170},
      {prop: 'sex',label: '性别',width: 90,
        render: function (row) {
          let sex="未知";
        if (row.sex==1){
              sex="女"
          }
          if (row.sex==0){
            sex="男"
          }
          return '<div>'+sex+'</div>'
        }},
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
        {name: "修改",func: edit,authType:[1],auth:"user_edit"},
        {name: "禁用",func: upStatus,b_v:'OFF',authType:[1,3],field:"status",value:[0],auth:"user_edit"},
        {name: "启用",func: upStatus,b_v:'ON',authType:[1,3],field:"status",value:[1],auth:"user_edit"},
        {name: "删除",func: upStatus,b_v:'Y',authType:[1],auth:"user_edit"},
      ]
    };

    let tableData = reactive<any>(xTableData);

    let methods={
      loadData(obj){
        proxy.$api.admin.user_api.getPageUserList(obj).then(res=>{
          tableData.pageLoad(res)
        })
      },

      getTreeDept(){
        proxy.$api.admin.dept_api.getTreeList({}).then(res=>{
          data.treeDept=res.data.data
        })
      },
      //
      nodeDeptClick(treeNode ){
        console.log(treeNode.id)
        methods.loadData({deptId:treeNode.id})
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
    //禁用启用
    function upStatus(obj,but) {
      upState(obj,but,tableData,proxy.$api.admin.user_api.editUser);
    }
    //重置密码
    function resetPassword() {
      let selectData = tableData.columnBox.selectData;
      if (selectData.length==0){
        proxy.$message.error("请选择数据")
        return
      }
      let ids = Array.from(selectData,({id})=>id);
      let str_ids=ids.join(",");
      message.confirmBox({title: "确定重置该用户密码？"}).then(()=>{
        proxy.$api.admin.user_api.resetPassword({ids:str_ids}).then(res=>{
          message.hint(res,tableData);
        })
      })
    }

    onMounted(()=>{
      methods.getTreeDept();
    })

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