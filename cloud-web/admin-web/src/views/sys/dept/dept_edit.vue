<template>
  <el-form
      ref="formRef"
      :rules="rules"
      :model="fromData"
      class="demo-ruleForm"
  >
    <div class="table_1">
      <el-form-item label="部门名称：" prop="name">
        <el-input type="text" v-model="fromData.name"/>
      </el-form-item>
      <el-form-item label="上级部门：" prop="parentId">
        <el-tree-select v-model="fromData.parentId"
                        :props="data.tree"
                        :check-strictly=true :data="data.deptList" placeholder="选择部门"/>
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
  name: "dept_edit",

  props: {
    tableData: {}
  },

  setup(props, context) {

    const { proxy,message,elSubmitForm }=userCommon();
    const formRef = ref<FormInstance>();
    const fromData = ref<any>({});
    const data = reactive({
      deptList: [],
      tree: {
        value: 'id',
        label: 'label',
        children: 'children',
      }
    })


    const rules = reactive<FormRules>({
      name: [{required: true, message: '不能为空', trigger: 'blur'}]
    })

    let methods = {
      submitForm(formEl: FormInstance | undefined) {
        let api=fromData.value.id===undefined?proxy.$api.admin.dept_api.insert:proxy.$api.admin.dept_api.edit;

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
      proxy.$api.admin.dept_api.getTree({}).then(res => {
        if (res.data.status === 200) {
          data.deptList = res.data.data
        }
      })
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