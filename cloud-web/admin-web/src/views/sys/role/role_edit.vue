<template>
  <el-form
      ref="formRef"
      :rules="rules"
      :model="fromData"
      class="demo-ruleForm"
  >
    <div class="table_1">
      <el-form-item label="角色名称：" prop="name">
        <el-input type="text" v-model="fromData.name"/>
      </el-form-item>
      <el-form-item label="角色编号：" prop="code">
        <el-input type="text" v-model="fromData.code" :disabled="fromData.id!==undefined"/>
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
import useCurrentInstance from "@/plugins/use/useCurrentInstance";
import userCommon from "@/plugins/useCommon";


export default {
  name: "role_edit",

  props: {
    tableData: {}
  },

  setup(props, context) {
    const {proxy, message, elSubmitForm} = userCommon();
    const formRef = ref<FormInstance>();
    const fromData = ref<any>({});
    const data = reactive({})


    const rules = reactive<FormRules>({
      name: [{required: true, message: '不能为空', trigger: 'blur'}],
      code: [{required: true, message: '不能为空', trigger: 'blur'}]
    })

    let methods = {
      submitForm(formEl: FormInstance | undefined) {
        let api=fromData.value.id===undefined?proxy.$api.admin.role_api.insert:proxy.$api.admin.role_api.edit;

        elSubmitForm(formEl,fromData.value,api,function (res) {
          methods.cancelForm();
        });
      },


      cancelForm() {
        props.tableData.dialog.visible_1 = false
        props.tableData.reload = true;
      }
    }

    onMounted(() => {
      const selectData = props.tableData.dialog.selectData;
      if (selectData.length > 0) {
        fromData.value = selectData[0]
      }

    })
    return {
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