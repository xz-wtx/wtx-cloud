<template>
  <x_table_template>
    <template #one>
      <x_search :table-data="tableData" @realTime="realTime" @reLoadData="reLoadData" ></x_search>
    </template>
    <template #two>
      <slot name="left"></slot>
    </template>
    <template #three>
      <el-table
          :data="tableData.data"
          stripe
          style="width: 100%"
          row-key="id"
          key="id"
          @selection-change="handleSelectionChange"
          :header-cell-style="cellStyle"
          :cell-style="{padding:'5px 0'}"
          :default-expand-all="tableData.defaultExpandAll"
      >
        <el-table-column type="selection" width="55" v-if="tableData.columnBox.checkBox"/>

        <el-table-column
            v-for="(column) in tableData.column"
            :property="column.prop"
            :label="column.label"
            :width="column.width===undefined?0:column.width"
            :min-width="column.width===undefined?0:column.width"
        >
          <template #default="scope">
            <span v-if="column.edit===undefined||!column.edit" style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">
              <el-tooltip class="item" effect="dark" :content="htmlRender(column,scope.row)+''" :show-after="100" placement="top-start"  :disabled="!column.showOverflowTooltip" >
                <span
                    v-html="htmlRender(column,scope.row)"
                    @click="clickHandle(column,scope.row,scope.row[column.prop])">
                </span>
              </el-tooltip>
            </span>

            <span v-else style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">
              <el-tooltip class="item" effect="dark" :content="htmlRender(column,scope.row)+''" :show-after="100" placement="top-start"   :disabled="!column.showOverflowTooltip" >
              <span style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">
                <el-input v-model="scope.row[column.prop]" :placeholder="column.placeholder" v-if="column.type==='input'"/>
                <el-switch v-model="scope.row[column.prop]" :active-color="column.activeColor" :inactive-color="column.nactiveColor" v-if="column.type==='switch'"/>
              </span>
              </el-tooltip>
            </span>
          </template>
        </el-table-column>

        <el-table-column fixed="right"
                         label="操作"
                         :width="tableData.columnBut.width"
                         v-if="tableData.columnBut.but.length>0">
          <template #default="scope">
            <x_auth :scope="scope" :table-data="tableData"></x_auth>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <div style="display: flex;justify-content: center;margin-top: 20px;" v-if="tableData.openPage">
        <el-pagination
            background
            v-model:currentPage="tableData.page.currentPage"
            v-model:page-size="tableData.page.pageSize"
            :page-sizes="tableData.page.sizes"
            layout="total, sizes, prev, pager, next, jumper"
            :total="tableData.page.total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </div>
    </template>
  </x_table_template>

</template>

<script lang="ts">


import x_search from "@/components/table/x_search.vue";
import X_table_template from "@/components/table/x_table_template.vue";
import x_auth from "@/components/table/x_auth.vue";
import {nextTick, onMounted, ref, watch} from "vue";
import Sortable from 'sortablejs'

export default {
  name: "x_table",
  components: {x_auth, X_table_template, x_search},
  props:{
    tableData:{
      search:{},
      data:[],
      column:[],
      columnBut:[],
      columnBox:{},
      page:{},

    },
  },

  emits:["load-data","real-time"],

  setup(props,context){


    const queryData =ref<any>(null);

    watch(() =>props.tableData.reload,(newValue,oldValue)=> {
      if (props.tableData.reload){
        methods.loadData();
        props.tableData.reload=false;
      }
    })
    const methods={

      /**
       * 选中数据
       * @param val
       */
       handleSelectionChange(val){
         props.tableData.columnBox.selectData=val;
       },

      /**
       * 行操作事件
       * @param data
       * @param row
       * @param but
       */
      clickHandle(data, row,but){
        if(data.func!==undefined) {
          if (typeof data.func === "function") {
            data.func(row, but)
            //data.func.call(this,row,but)
          } else {
            console.log("非函数")
          }
        }
      },
      //字符串转html
      htmlRender(item,row){
        return item.render===undefined?row[item.prop]:item.render(row)
      },

      /**
       * 更改每页大小
       * @param val
       */
       handleSizeChange(val: number){
        console.log(`${val} items per page`)
         props.tableData.page.pageSize=val;
         methods.loadData();
      },
      /**
       * 更改当前页
       * @param val
       */
       handleCurrentChange(val: number){
         console.log(`current page: ${val}`)
         props.tableData.page.currentPage=val;
          methods.loadData();
      },

      //搜索框实时通知，通知参数有所改变（全部）
       realTime(){
          let page={pageSize:props.tableData.page.pageSize,total:props.tableData.page.total,currentPage:props.tableData.page.currentPage};
          const assignData= Object.assign(page,methods.getSearchValue());
          context.emit("real-time",assignData);
      },

      //获取当前条件值
      getSearchValue(){
        let obj={};
        let searchList=props.tableData.search;
        if(searchList===undefined){
          return obj;
        }
        let list=searchList.fields;
        if(list===undefined){
          return obj;
        }
        for (let [key, value ] of Object.entries(list)) {
         // console.log(key + ':' + value.value);
          let values: any=value;
          if(values.default!==undefined&&values.defaultValue===undefined){
            values.defaultValue=values.value
          }
          if(values.value==null){
            values.value=''
          }
          if(values.trimBlankBool===undefined||values.trimBlankBool){
            obj[key]=values.value.trim()
          }else{
            obj[key]=values.value;
          }
        }
        return obj;
      },
      //加载数据
      loadData(obj={}){
         if (queryData.value==null){
           queryData.value=Object.assign(methods.getSearchValue(), obj);
         }
        let page={pageSize:props.tableData.page.pageSize,total:props.tableData.page.total,currentPage:props.tableData.page.currentPage};
        let data=Object.assign(queryData.value, page);
        context.emit("load-data",data)
      },

        reLoadData(){
        queryData.value=null;
        methods.loadData({})
      },

      //标题样式
      cellStyle() {
        return {'background-color': '#f2f2f2', 'color': '#2f2f2f',fontSize:'13px'}
      },

      //行拖拽
       rowDrop() {
      const tbody = document.querySelector('.el-table__body-wrapper tbody')
      Sortable.create(tbody, {
        onEnd({ item,newIndex, oldIndex }) {
          let newArr=props.tableData.data;
          const currRow = newArr.splice(oldIndex, 1)[0]
          const upRow = newArr[newIndex]
          newArr.splice(newIndex, 0, currRow)
          //返回当前移动对象，被移动对象，和所有对象集合
          props.tableData.row.drag.func.call(this,[currRow,upRow,newArr])
        }
      })
    }
    };
    //首次加载
    onMounted(()=>{
      if (props.tableData.row.drag.open){
        nextTick(()=>{
          methods.rowDrop();
        })
      }

      if (props.tableData.firstLoad){
        methods.loadData();
      }
    })

    return{
        ...methods,
    }

  }

}
</script>

<style>

</style>