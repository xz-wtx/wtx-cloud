<template>

  <span v-for="(but,index) in tableData.columnBut.but">
      <el-button
          :style="{marginLeft:index===0?'0px':'10px'}"
          v-if="auth(but,scope)"
          type="text"
          @click="clickHandle(but,scope.row,but)"
          size="small">{{but.name}}</el-button>
  </span>

</template>

<script lang="ts">

import store from "@/store";
export default {
  name: "x_auth",
  props:{
    tableData:{
      type: Object,
      default: () => {
        return {}
      }
    },
    scope:{
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  setup(){

    //按钮权限认证
    function ButAuth(auth){
      return auth===undefined?true: store.getters.getUser.buttonList.indexOf(auth)>-1
    }
    //登录人权限认证
    function  AccountAuth(account){
      return account!==undefined?account.indexOf(store.getters.getUser.account)>-1:true
    }

    function auth(but,scope){
      if(but.authType===undefined){
        return true;
      }

      let bool=true;
      if(but.authType.indexOf(1)>-1){
        //只判断是否有按钮权限
        bool =ButAuth(but.auth)
        if(!bool){
          return false;
        }
      }
      if (but.authType.indexOf(2)>-1){
        //只判断登录人和指定人是否相等权限
        bool =AccountAuth(scope.row[but.account])
        if(!bool){
          return false;
        }
      }
      if (but.authType.indexOf(3)>-1){
        //只判断指定值是包含指定类型字段的值
        bool= but.value.indexOf(scope.row[but.field])>-1
        if(!bool){
          return false;
        }
      }
      if (but.authType.indexOf(4)>-1){
        //判断指定值不包含指定类型字段的值
        bool= but.value.indexOf(scope.row[but.field])===-1
        if(!bool){
          return false;
        }
      }

      return bool;
    };
    /**
     * 行操作事件
     * @param data
     * @param row
     * @param but
     */
   function clickHandle(data, row,but){
      if(data.func!==undefined) {
        if (typeof data.func === "function") {
          data.func(row, but)
          //data.func.call(this,row,but)
        } else {
          console.log("非函数")
        }
      }
    }
    return{
      clickHandle,
      auth
    }
  }
}
</script>

<style scoped>

</style>