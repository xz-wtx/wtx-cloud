//统一更改状态(禁用，启用，删除)
import message from "@/plugins/use/useMessage";

export default function upState(obj, but, tableData, api) {
    let data;
    let title;
    if (but.b_v==='OFF')
    {
        data={id:obj.id,status: 1};
        title=(but.b_t===undefined?"确定禁用？":but.b_t);
    }
    if (but.b_v==='ON')
    {
        data={id:obj.id,status: 0};
        title=(but.b_t===undefined?"确定启用？":but.b_t);
    }
    if (but.b_v==='Y')
    {
        data={id:obj.id,deleteFlag: 1};
        title=(but.b_t===undefined?"确定删除？":but.b_t);
    }

    message.confirmBox({title:title}).then((res) => {
        api(data).then(res=>{
            message.hint(res,tableData)
        })
    })
}


