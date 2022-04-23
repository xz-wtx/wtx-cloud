//提交
import {FormInstance} from "element-plus";
import message from "@/plugins/use/useMessage";

/**
 * el表单提交
 * @param formEl from的ref
 * @param form  data提交值
 * @param api   api
 * @param callback  成功回调
 */
function elSubmitForm (formEl: FormInstance | undefined,form:{},api,callback:Function){
        if (!formEl) return
        formEl.validate((valid) => {
            if (valid) {
                api(form).then(res => {
                    if (res.data.status === 200) {
                        message.success(res.data.message)
                        callback.call(callback,res)
                        return;
                    }
                    message.error(res.data.message)
                })
            } else {
                return false
            }
        }).then(r => {})
}
export default elSubmitForm