<template>
    <!--简单搜索-->
    <div class="List wrap">
      <!-- 自定义搜索框-->
      <slot name="search_front"></slot>
      <div v-for="(obj,index) in tableData.search.fields" :key="index" class="div1">

        <span class="div1_title"> {{ obj.title }}：</span>
        <span class="div1_content">
                        <el-input
                                   @input="realTime($event,obj)"
                                  v-if="obj.type==='input'"
                                   class="width_1"
                                  :placeholder="obj.placeholder"
                                  :disabled="obj.disabled"
                                  v-model="obj.value"
                                  :clearable="obj.clearable===undefined?true:obj.clearable">
                        </el-input>


                        <el-date-picker
                            @change="realTime($event,obj)"
                            v-if="obj.type==='date'"
                            :disabled="obj.disabled"
                            class="width_1"
                            v-model="obj.value"
                            value-format="YYYY-MM-DD"
                            type="date"
                            :placeholder="obj.placeholder"
                            :clearable="obj.clearable===undefined?true:obj.clearable">
                        </el-date-picker>
                          <el-date-picker
                              @change="realTime($event,obj)"
                              v-if="obj.type==='datetime'"
                              :disabled="obj.disabled"
                              class="width_1"
                              v-model="obj.value"
                              value-format="YYYY-MM-DD HH:mm:ss"
                              type="datetime"
                              :placeholder="obj.placeholder"
                              :clearable="obj.clearable===undefined?true:obj.clearable">
                            </el-date-picker>

                            <!-- ADD::month select -->
                            <el-date-picker
                                @change="realTime($event,obj)"
                                v-if="obj.type==='month'"
                                :disabled="obj.disabled"
                                class="width_1"
                                v-model="obj.value"
                                value-format="YYYY-MM"
                                type="month"
                                :placeholder="obj.placeholder"
                                :clearable="obj.clearable===undefined?true:obj.clearable">
                              </el-date-picker>

                          <el-select
                              v-if="obj.type==='select'"
                              :disabled="obj.disabled"
                              filterable
                              class="width_1"
                              @change="realTime($event,obj)"
                              v-model="obj.value"
                              :clearable="obj.clearable===undefined?true:obj.clearable"
                              :placeholder="obj.placeholder">
                             <el-option
                                 v-for="item in obj.options===undefined?[]:obj.options.data"
                                 :key="item[obj.options.value]"
                                 :label="item[obj.options.label]"
                                 :value="item[obj.options.value]">
                                </el-option>
                        </el-select>
        </span>
      </div>

      <!-- 自定义搜索框-->
      <slot name="search_later"></slot>

      <div style="width: 100%;" v-if="tableData.search.newline"></div>

      <div class="div1" style="margin-bottom: 0" >
        <div >
          <el-button type="primary" size="small" class="button_text"  color="#5678a2" style="outline-width: 10px" @click="onSearch(1)">
            <el-icon> <component is="Search"></component></el-icon>
            搜索
          </el-button>
          <el-button type="primary" size="small"  class="button_text" color="#5678a2" @click="onSearch(0)">
            <el-icon> <component is="RefreshRight"></component></el-icon>
            清空
          </el-button>
          <span v-if="tableData.search.but.length>0" >
            <span v-for="(but,index) in tableData.search.but" style="margin-left: 10px;" :key="index">
                 <el-button
                     type="primary"
                     size="small"
                     :class="{'button_text':but.title.length<3}"
                     v-if="but.auth===undefined||$store.getters.getUser.buttonList.indexOf(but.auth)>-1"
                     :color="but.color===undefined?'#5678a2':but.color"
                     @click="funClick(but)"
                 >
                    <el-icon v-if="but.icon!==undefined"> <component :is="but.icon"></component></el-icon>
                    {{but.title}}
                  </el-button>
            </span>
          </span>
        </div>

      </div>

    </div>



</template>

<script>

export default {

  name: "x_search",
  props: {
    type: Array,
    tableData: {
      search: {
        fields: [],
        but: [],
      },
    },
  },
  setup(props,context) {
    let methods= {
      //输入值改变时通知(实时)
      realTime(value, obj) {
        // //通知参数有所改变（全部）
        context.emit("realTime")
        //单个通知
        if (value === undefined) {
          obj.value ='';
        }
        if (obj.valueFun !== undefined) {
          if (typeof obj.valueFun === "function") {

            obj.valueFun.apply(this,[obj])
          }
        }
      },

      /**
       * 查询，和清空
       * @param i
       */
      onSearch(i){
        props.tableData.page.currentPage=1
        if (i===0){
          let obj=props.tableData.search.fields;
          for (let [key, value] of Object.entries(obj)) {
            if(value.disabled===undefined){
              value.value=value.defaultValue!==undefined?value.defaultValue:'';
            }
            value.filterName=''
          }
        }
          context.emit("reLoadData")
      },


      funClick(row){
        if (row.func !== undefined) {
          if (typeof row.func === "function") {
            row.func.apply(this,[row])
          }
        }
      }

    }
    return{
      ...methods
    }
  }
}
</script>

<style scoped>
.wrap{
  display: flex;
  flex-wrap: wrap;
  padding-bottom: 10px;
  padding-top: 10px;
}

.div1{
  margin: 8px;
}
.div1 .div1_title{
  flex: 1;
  align-self: center;
  font-size: 14px
}
.div1 .div1_content{
  flex: 1;
}
::v-deep(.width_1) {
  width: 200px;
}

.el-button--small {
  --el-button-size: 27px;
}
::v-deep(input){
  height: 27px;
}

.button_text{
  letter-spacing: 6px;
}
::v-deep(.el-button>span) {
  padding-left: 4px;
}
</style>