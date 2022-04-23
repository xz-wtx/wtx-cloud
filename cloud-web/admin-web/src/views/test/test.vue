<template>

  <div>
    <el-button type="primary">el-button</el-button>

    <h1>{{ name }}</h1>
    <h1>{{ state.count }}</h1>
    <h2>{{counts}}</h2>
    <h2>{{data}}</h2>
    <button @click="handleClickIncrement">累加</button>
    <button @click="updateName">更改名称</button>
  </div>

</template>


<script  lang="ts">
import {ref, computed, defineComponent, reactive} from 'vue'
import { useStore } from 'vuex'
import useCurrentInstance from "@/plugins/use/useCurrentInstance";

export default defineComponent({
  name:"login",
  components:{},
  mounted() {
    this.updateName();
  },
  //第一个参数props中可以获取父组件传入的参数，
  //第二个参数中有很多内容，context.attrs可以获取父组件的属性（没有被子组件接收的那部分，即props中没有声明的部分）
  //context.slots可以获取组件中slot部分的内容
  //context.parent可以获取父组件对象
  //context.root可以获取根组件对象
  //context.emit用于调用父组件的方法
  //context.refs获取组件上面的元素对象
  setup(props,context){

      // as ComponetInternalInstance表示类型断言，ts时使用。否则报错，proxy为null
      // proxy.$parent
      // proxy.$refs
      // proxy.$nextTick
      // proxy.$attrs
      // proxy.$data
      // proxy.$el
      // proxy.$emit
      // proxy.$forceUpdate
      // proxy.$options
      // proxy.$props
      // proxy.$root
      // proxy.$slots
      // proxy.$watch


    const store = useStore()
    // 为基本数据类型添加响应式状态
    const name = ref('Neo');
    // 为复杂数据类型添加响应式状态，赋值使用:state.count.value=1111
    const state = ref({ count: 1})
    //reactive和ref类似；区别是不需要.value
    const data = reactive<any>({
      tables: [],
      ss:"",
    });

    //store在计算属性中使用
    let counts = computed(()=>{
      return store.state.count
    })

    //函数集合
    const methods={
      //store函数
       handleClickIncrement() {
        store.commit("increment");
      },
      //函数
       updateName() {
        name.value="更改："+new Date();
         let obj={"name":1}
         data.tables.push(obj);
         data.ss="1231"
      }
    }

    return {
      counts,
      name,
      state,
      data,
      ...methods
    };
  },

})




</script>

<style scoped>

</style>