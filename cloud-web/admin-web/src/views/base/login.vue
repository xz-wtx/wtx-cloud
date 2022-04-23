<template>
  <div class="login" style="background-image: url('public/login.png')">
    <div class="loginPart">
      <h2>用户登录</h2>
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules">
        <div class="inputElement">
          <el-form-item prop="account">
          <el-input v-model="ruleForm.account" placeholder="请输入用户名/手机号 ">
            <template #prepend><el-icon><user-filled /></el-icon></template>
          </el-input>
          </el-form-item>
        </div>
        <div class="inputElement">
          <el-form-item prop="password">
          <el-input v-model="ruleForm.password" type="password" placeholder="请输入密码 ">
            <template #prepend><el-icon><stamp /></el-icon></template>
          </el-input>
          </el-form-item>
        </div>
        <div class="inputElement">
          <el-form-item prop="captchaCode">
            <div style="display:flex;width: 100%;">
              <div style="flex: 1">
                <el-input v-model="ruleForm.captchaCode"  placeholder="请输验证码">
                </el-input>
              </div>
              <div style="margin-left: 10px">
                <img height="30" width="100" :src="captcha" @click="queryCaptcha">
              </div>
            </div>
          </el-form-item>
        </div>
        <div>
          <el-button type="primary" style="width: 100%;"  @click="submitForm(ruleFormRef)">登录</el-button>
        </div>
        <div style="text-align: right;color: white;">
          <el-link type="warning">没有账号？去注册</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script  lang="ts">
import useCommon from "@/plugins/useCommon";
import {defineComponent, onMounted, reactive, ref} from 'vue'
import { useStore } from 'vuex'
import {UserFunc} from "@/store/module/User";
import md5 from 'js-md5';
import router from "@/routes";
import {FormInstance, FormRules} from "element-plus";
import {captcha} from "@/http/api/login/login_api";
export default defineComponent({
  name:"login",
  props:{},
  setup(){
    const store = useStore();
    const ruleFormRef = ref<FormInstance>()
    const ruleForm = reactive({account:'',password:'',captchaKey:'',captchaCode:''})
    const captcha = ref()
    const rules = reactive<FormRules>({
      account: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 10, message: '用户名长度 be 3 to 10', trigger: 'blur' },
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 10, message: '密码长度 be 5 to 10', trigger: 'blur' },
      ],
      captchaCode: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
      ],
    });

    const {proxy}=useCommon();

    //函数集合
    const methods={
       submitForm(formEl: FormInstance | undefined){
        if (!formEl) return
         formEl.validate((valid, fields) => {
          if (valid) {
            methods.submit();
          }
        })
      },

      queryCaptcha(){
        proxy.$api.admin.login_api.captcha({}).then(res=>{
         ruleForm.captchaKey=res.data.key;
         captcha.value=res.data.image;
        })
      },
      //提交登录
      submit() {
        //清空历史数据
        store.commit("clearSave");
        const data = {...ruleForm}
        data.password=md5(data.password)
        proxy.$api.admin.login_api.login(data).then(res=>{
          if(res.data.status===200){
            let data=res.data.data;
            let user= UserFunc(
                {
                    id:data.id,
                    token:data.token,
                    username:data.userName,
                    account:data.account,
                    signStr:data.signStr
                  })
            store.commit("setUser", user);
            methods.loadMenu();
          }else{
            proxy.$message.error(res.data.message)
          }
        });
      },
      //获取菜单
      loadMenu(){
        let user=store.getters.getUser;
        proxy.$api.admin.login_api.getUserMenu({}).then(rs=>{
          if(rs.data.status===200) {
            const data = rs.data.data;
            //写入新数据
            user.menuList=data.menuList;
            user.buttonList=data.buttonList;
            user.roleList=data.roleList
            store.commit("setUser",user);
            router.push("/home")
          }else {
            proxy.$message.error(rs.data.message)
          }
        }).catch(err =>{
          proxy.$message.error(err.response.data.message)
        })
      }
    }
    onMounted(()=>{
      methods.queryCaptcha()
    })
    return {
      ruleFormRef,
      captcha,
      ruleForm,
      rules,
      ...methods
    };
  },


})
</script>

<style scoped>

.loginPart{
  position:absolute;
  /*定位方式绝对定位absolute*/
  top:50%;
  left:50%;
  /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
  transform:translate(-50%,-50%);
  /*实现块元素百分比下居中*/
  width:420px;
  padding:50px;
  background: rgba(0,0,0,.5);
  /*背景颜色为黑色，透明度为0.8*/
  box-sizing:border-box;
  /*box-sizing设置盒子模型的解析模式为怪异盒模型，
  将border和padding划归到width范围内*/
  box-shadow: 0px 15px 25px rgba(0,0,0,.5);
  /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
  border-radius:15px;
  /*边框圆角，四个角均为15px*/
}
.loginPart h2{
  margin:0 0 30px;
  padding:0;
  color: #fff;
  text-align:center;
  /*文字居中*/
}

.inputElement{
  margin: 15px 0px;
}

.login{
  width:100%;
  height:100%;
}
</style>