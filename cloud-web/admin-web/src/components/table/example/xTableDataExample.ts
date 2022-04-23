/**
 * 使用示例
 */
// @ts-ignore
const tableData ={
    //查询字段
    search:{
        fields:{//查询字段
            taskName1: {
                placeholder: '任务名称',
                type: "input",
                title: '任务名称',
                //valueFun:taskNameFun// 事件函数
            },
            taskStatus: {
                placeholder: '是否启用', type: "select", title: '是否启用',value:'',
                options: {value: "v", label: "l", data: [{v: '', l: '所有'}, {v: '1', l: "启用"}, {v: '0', l: "禁用"}]}
            },
        },
        newline:true,//换行
        but:[//按钮
            {
            title: "添加",
            //func: edit,// 事件函数
            color:"#5678a2",
            auth:"userAdd"//权限标识
             }
        ],
    },
    //列字段
    column:[
        {
            prop: 'date',
            label: '时间',
            showOverflowTooltip: true,//提示
            render:function (row){ //html渲染
                return '<div>1</div>'
            },
            //func:edit,//事件
            width:100//指定宽度
        },
        {
            prop: 'address',
            label: '地址',
            showOverflowTooltip: true,

        },
        {
            prop: 'name',
            label: '名称',
            edit:true,//可编辑
            type:'switch',//编辑类型
            showOverflowTooltip: true,
            width:800
        },

    ],
    //列操作
    columnBut:{
        width:200,
        but:[
            {
                name: "修改",
                //func:edit,//函数
                authType:[1],//权限类型
                auth:"userAdd"//权限标识
            },
            {
                name: "删除",
                //func: edit,//函数
                authType:[1,2],//几种权限
                auth:"userDel",//权限标识
                account:"account"//指定字段
            },
        ]
    },
};

// function taskNameFun(row){
//
// }
// function edit(row){
//
// }