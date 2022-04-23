<template>

  <div style="width: 100%">
     <x_table :tableData="tableData" @load-data="loadData"></x_table>
  </div>
</template>


<script lang="ts" >

import xTableData from "@/components/table/xTableData";
export default {

  setup(){

    xTableData.search.fields={
      taskName: {placeholder: '任务名称', type: "input", title: '任务名称',valueFun:edit},
      taskStatus: {
        placeholder: '是否启用',
        type: "select",
        title: '是否启用',
        value:'',
        options: {value: "v", label: "l", data: [{v: '', l: '所有'}, {v: '1', l: "启用"}, {v: '0', l: "禁用"}]}
      },
    };
    const columns=[
      {
        prop: 'date',
        label: '时间',
        showOverflowTooltip: true,
        render:function (row){
          return '<div>1</div>'
        },
        func:edit
      },
      {
        prop: 'address',
        label: '地址',
        showOverflowTooltip: true,
        width:100
      },
      {
        prop: 'name',
        label: '名称',
        edit:true,
        type:'switch',
        showOverflowTooltip: true,
        width:800
      },

    ];
    xTableData.column=columns;
    const columnBut={
      width:200,
          but:[
        {
          name: "修改",
          func:edit,
          authType:[1],
          auth:"userAdd"
        },
        {
          name: "删除",
          func: edit,
          authType:[1,2],//几种权限
          auth:"userDel",//权限标识
          account:"account"//指定字段
        },
      ]
    };
    xTableData.columnBut=columnBut;


    xTableData.data=[
      {
        date: '2016-05-03',
        name: false,
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-02',
        name: false,
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-04',
        name: false,
        address: 'No. 189, Grove St, Los Angeles',
      },
      {
        date: '2016-05-01',
        name: true,
        address: 'No. 189, Grove St, Los Angeles',
      },
    ];


    let methods={
      loadData(obj){
        console.log(obj)
      }
    }

    function edit(obj) {
      console.log(obj)
    }

    return{
      tableData:xTableData,
      ...methods,

    }
  },

}



</script>