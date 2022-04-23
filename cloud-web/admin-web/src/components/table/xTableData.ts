import message from "@/plugins/use/useMessage";
export function create(){

    return {
        search:<any>{//查询
            fields:{},//查询字段
            newline:true,//查询按钮换行
            but:[],//事件
        },
        column:<any> [],//列
        columnBut:<any>{//列操作
            width:0,//宽度
            but:[]//事件
        },
        defaultExpandAll:false,//树形默认展开全部
        data:<any>[],
        columnBox:<any>{//复选框
            checkBox:false,
            selectData:[]//选中数据
        },
        firstLoad:true,//首次加载
        openPage:true,//开启分页
        page:{//分页
            currentPage:1,
            pageSize:10,
            total:0,
            sizes:[20, 50, 100, 300,500,1000]
        },
        row:{
            drag:{open:false,func:function (){}}//行拖拽
        },
        reload:false,//监听，如果改变true，重新加载数据
        load,//封装返回数据（非分页）
        pageLoad,//封装返回数据(分页)
        dialog:{//对话框
            selectData:<any>[],
            visible_1:false,
            visible_2:false,
            visible_3:false,
            visible_4:false,
        }

    };
}

//加载非分页数据
function load(this:any,res){
    this.data=res.data.data
}
//加载分页数据
function pageLoad(this:any,res){
    if (res.data.status==200){
        this.data=res.data.data.records
        this.page.total=res.data.data.total
    }else{
        message.error(res.data.message)
    }

}
