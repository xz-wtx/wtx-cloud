import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//全局导入element-icons
import * as ElIcons from '@element-plus/icons-vue'

export default (app: any) => {

    app.use(ElementPlus)

    //全局导入element-icons
    for (const name in ElIcons) {
        app.component(name, ElIcons[name])
    }
}