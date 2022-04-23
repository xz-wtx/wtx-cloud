import useCurrentInstance from "@/plugins/use/useCurrentInstance";
import {create} from "@/components/table/xTableData";
import message from "@/plugins/use/useMessage";
import upState from "@/plugins/use/useUpState";
import elSubmitForm from "@/plugins/use/useSubmitForm";
import useMousePosition from "@/plugins/use/useMousePosition";


// 1. 定义一个函数,抽离逻辑，命名使用 useXXX
function useCommon() {


  return{
    //获取当前实例
    proxy:useCurrentInstance(),
    //创建表格
    createTable:create,
    //消息
    message,
    //统一更改状态(禁用，启用，删除)
    upState,
    //element表单提交
    elSubmitForm,
    //鼠标划动事件
    useMousePosition,
  }
}




// 导出这个函数
export default useCommon
