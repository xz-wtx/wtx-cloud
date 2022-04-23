<template>
  <el-form
      ref="formRef"
      :rules="rules"
      :model="fromData"
      class="demo-ruleForm"
  >
    <div class="table_1">
      <el-form-item label="路径：" prop="path" >
        <el-input type="text" v-model="fromData.path"/>
      </el-form-item>
      <el-form-item label="备注：" prop="remark" >
        <el-input type="text" v-model="fromData.remark"/>
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
  name: "path_edit",

  props:{
    tableData:{}
  },

  setup(props,context){
    const {proxy, message, elSubmitForm} = userCommon();
    const formRef = ref<FormInstance>();
    const fromData = ref<any>({});
    const data =reactive({})


    const rules = reactive<FormRules>({
      path: [{ required: true, message: '不能为空', trigger: 'blur' }],
    })

    let methods={
      submitForm(formEl: FormInstance | undefined) {
        let api=fromData.value.id===undefined?proxy.$api.admin.path_api.insert:proxy.$api.admin.path_api.edit
        elSubmitForm(formEl, fromData.value,api, function (res) {
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
        fromData.value=selectData[0]
      }

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