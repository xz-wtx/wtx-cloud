<template>
  <el-form
      ref="formRef"
      :rules="rules"
      :model="fromData"
      class="demo-ruleForm"
  >
    <div class="table_1">
      <el-form-item label="菜单名称：" prop="title" >
        <el-input type="text" v-model="fromData.title" placeholder="名称"/>
      </el-form-item>
      <el-form-item label="权限编码：" prop="authCode" >
        <el-input type="text" v-model="fromData.authCode"
                  placeholder="权限唯一标识,不可更改"
                  :disabled="fromData.id!==undefined&&fromData.authCode.indexOf('_copy')===-1"/>
      </el-form-item>
      <el-form-item label="菜单类型：" prop="type" >
        <el-radio-group v-model="fromData.type"  :disabled="fromData.id!==undefined&&fromData.authCode.indexOf('_copy')===-1" >
          <el-radio :label="0">目录</el-radio>
          <el-radio :label="1">菜单</el-radio>
          <el-radio :label="2">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单图标：" prop="icon" >
        <el-input type="text" v-model="fromData.icon" placeholder="图标" v-if="fromData.type!==2"/>
      </el-form-item>
      <el-form-item label="路由地址：" prop="path" v-if="fromData.type!==2">
        <el-input type="text" v-model="fromData.path" placeholder="目录可以给空"/>
      </el-form-item>
      <el-form-item label="文件路径：" prop="filePath" v-if="fromData.type!==2">
        <el-input type="text" v-model="fromData.filePath" placeholder="目录需要填layout"/>
      </el-form-item>
      <el-form-item label="组件名称：" prop="name" v-if="fromData.type!==2">
        <el-input type="text" v-model="fromData.name" placeholder="vue组件名称"/>
      </el-form-item>
      <el-form-item label="菜单可见："  v-if="fromData.type!==2">
        <el-radio-group v-model="fromData.visible">
          <el-radio :label="0">可见</el-radio>
          <el-radio :label="1">隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="上级菜单：" prop="parentId"  >
        <el-tree-select v-model="fromData.parentId"
                        :props="data.tree"
                        :disabled="fromData.id!==undefined&&fromData.authCode.indexOf('_copy')===-1"
                        :check-strictly=true :data="data.menuList" placeholder="选择部门" />
      </el-form-item>
      <el-form-item label="排序："  >
        <el-input-number
            v-model="fromData.sort"
            class="mx-4"
            :min="1"
            :max="1000"
            controls-position="right"
        />
      </el-form-item>
    </div>
    <el-form-item>
      <el-button type="primary" @click="submitForm(formRef)">Submit</el-button>
      <el-button type="primary" @click="cancelForm">cancel</el-button>
    </el-form-item>
  </el-form>
</template>


<script lang="ts">
  import {onMounted, reactive, ref} from "vue";
  import {FormInstance, FormRules} from "element-plus";
  import userCommon from "@/plugins/useCommon";


  export default {
  name: "menu_edit",

  props:{
  tableData:{}
},

  setup(props,context){

    const { proxy,message,elSubmitForm }=userCommon();

  const formRef = ref<FormInstance>();
  const fromData = ref<any>({type:0,sort:1,visible:0});
  const data =reactive({
    menuList:[],
    tree:{
      value: 'id',
      label: 'label',
      children: 'children',
    }
  })


  const rules = reactive<FormRules>({
    title: [{ required: true, message: '不能为空', trigger: 'blur' }],
    authCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
    type: [{ required: true, message: '不能为空', trigger: 'blur' }],
})

  let methods={
    submitForm(formEl: FormInstance | undefined) {
      delete fromData.value.children
      let api=fromData.value.id===undefined?proxy.$api.admin.menu_api.insert:proxy.$api.admin.menu_api.edit;
      elSubmitForm(formEl,fromData.value,api,function (res) {
        methods.cancelForm();
      });
    },

  cancelForm(){
  props.tableData.dialog.visible_1=false
  props.tableData.reload=true;
}
}

  onMounted(()=>{
      const selectData = props.tableData.dialog.selectData;
      if (selectData.length>0){
        fromData.value=JSON.parse(JSON.stringify(selectData[0]))
    }
    proxy.$api.admin.menu_api.getTree({types:[0,1]}).then(res=>{
      if (res.data.status===200){
        data.menuList=res.data.data
      }
    })
})
  return{
    rules,
    fromData,
    data,
    formRef,
    ...methods,
}
}
}
</script>

<style scoped>
@import "../../../assets/styles/table_edit.scss";
</style>