//动态加载路由
import useStore from "@/store";
import store from "@/store";
import index from "@/views/base/index.vue";
import layout from "@/components/layout/layout";



export default (router: any) => {

//通过oneRun变量控制 避免陷入死循环
let oneRun = true;

router.beforeEach((to, from, next) => {

    if (to.path === "/"||to.path==="/index") {
        oneRun = true;
        next();
    } else {
        //判断是否登录
        if (store.getters.getUser.token===undefined){
            next("/")
            return;
        }

        if (oneRun) {
            oneRun = false;//必须在creatNewRouter() 执行
            const modules = import.meta.glob("@/views/**/**.vue")
            const item=useStore.getters.getUser.menuList;

            if (item != null) {
                const copyItem = JSON.parse(JSON.stringify(item));
                let list = createNewRouter(modules, copyItem);
                let obj = {
                    path: `/newIndex`,
                    name: "newIndex",
                    component: index,
                    children: list
                }
                router.addRoute(obj)
            }
            next(to.path);
        }else{
            next();
        }
    }
})

//创建动态路径
function createNewRouter(modules, menuList) {
    return menuList.filter(router => {

        if (router.filePath === "layout") {
            // Layout组件特殊处理
            router.component = layout;
        } else {
            //处理组件---重点
            const module = modules["../views" + router.filePath+".vue"];
            if (module==undefined){
                throw new Error("找到不对应的vue文件=》"+router.filePath);
            }
            router.component = module;
            router.meta={
                title:router.title,
            }
        }
        //存在子集
        if (router.children && router.children.length) {
            router.children = createNewRouter(modules, router.children);
        }
        return true;
    });
}

}