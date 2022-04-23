import {ElMessage, ElMessageBox,Action} from "element-plus";

//消息1
 let message={
    success(message) {
        ElMessage.success(message)
    },
    error(message){
        ElMessage.error(message)
    },
    //统请求返回提示
    hint(this:any,res :any,tableData?: any){
        if (res.data.status===200){
            ElMessage.success(res.data.message)
            if (tableData!==undefined){
                tableData.reload=true;
            }
            return true;
        }else{
            ElMessage.error(res.data.message)
            return false;
        }
    },
    //统一确认框
    confirmBox({title='是否确认该操作？',confirmText='确定',cancelText='取消'}){
        return new Promise((resolve, reject) => {
               ElMessageBox.confirm(title,
                    {confirmButtonText: confirmText,
                    cancelButtonText: cancelText,
                    type: 'warning'}
            ).then(() => {
                   resolve(true);
            }).catch(() => {
                   reject(false)
            })
        })
    },
     alertBox({header="提示",title='是否确认该操作？',confirmText='确定'}){
         return new Promise((resolve, reject) => {
             ElMessageBox.alert(title, header, {
                 confirmButtonText: confirmText,
                 callback: (action: Action) => {
                     resolve(true);
                 },
             }).then(r => {}).catch(err => reject(false))
         })
     },

}
export default message;