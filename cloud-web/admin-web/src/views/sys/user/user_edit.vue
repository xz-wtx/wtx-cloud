<template>
  <el-form
      ref="formRef"
      :rules="rules"
      :model="fromData"
      class="demo-ruleForm"
  >
    <div class="table_1">
      <el-form-item label="用户名：" prop="userName" >
        <el-input type="text" v-model="fromData.userName"/>
      </el-form-item>
      <el-form-item label="账号：" prop="account" >
        <el-input type="text" v-model="fromData.account" />
      </el-form-item>
      <el-form-item label="手机号：" prop="phone" >
        <el-input type="text" v-model="fromData.phone" />
      </el-form-item>
      <el-form-item label="部门：" prop="deptId" >
        <el-tree-select v-model="fromData.deptId"
                        :props="data.tree"
                        :check-strictly=true :data="data.deptList" placeholder="选择部门" />
      </el-form-item>
      <el-form-item label="角色："  >
        <el-select
            v-model="fromData.roleIds" multiple collapse-tags placeholder="可以选择角色" >
          <el-option
              v-for="item in data.roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱：" prop="mail" >
        <el-input type="text" v-model="fromData.mail" />
      </el-form-item>
      <el-form-item label="性别：" prop="sex" >
        <el-select v-model="fromData.sex"  placeholder="性别" >
          <el-option :key="0" label="男" :value="0"/>
          <el-option :key="1" label="女" :value="1"/>
          <el-option :key="2" label="未知" :value="2"/>
        </el-select>
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
  name: "user_edit",

  props:{
    tableData:{}
  },

  setup(props,context){

    const { proxy,message,elSubmitForm }=userCommon();

    const formRef = ref<FormInstance>();
    const fromData = ref<any>({});

    const data =reactive({
      deptList:[],
      roleList:[],
       tree:{
        value: 'id',
        label: 'label',
        children: 'children',
      }
    })


    const rules = reactive<FormRules>({
      userName: [{ required: true, message: '不能为空', trigger: 'blur' }],
      account: [{ required: true, message: '不能为空', trigger: 'blur' }],
      phone: [{ required: true, message: '不能为空', trigger: 'blur' }],
      deptId: [{ required: true, message: '不能为空', trigger: 'blur' }],
    })

    let methods={
      //提交
       submitForm (formEl: FormInstance | undefined){
        let api=fromData.value.id!==undefined?proxy.$api.admin.user_api.editUser:proxy.$api.admin.user_api.insert;

        elSubmitForm(formEl,fromData.value,api,function (res) {
           methods.cancelForm();
         });
      },
      //取消
      cancelForm(){
        props.tableData.dialog.visible_1=false
        props.tableData.reload=true;
      },

      //初始加载数据
      loadMounted(){
        const selectData = props.tableData.dialog.selectData;
        if (selectData.length>0){
          proxy.$api.admin.user_api.getUser({id:selectData[0].id}).then(res=>{
            fromData.value=res.data.data;
          })
        }
        proxy.$api.admin.dept_api.getTree({}).then(res=>{
          if (res.data.status===200){
            data.deptList=res.data.data
          }
        })
        proxy.$api.admin.role_api.getList({}).then(res=>{
          if (res.data.status===200){
            data.roleList=res.data.data
          }
        })
      }
    }

    onMounted(()=>{
      methods.loadMounted()
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