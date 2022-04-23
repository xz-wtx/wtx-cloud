import {User} from "@/store/module/User";

const userConst = "user";
const currentMenuConst = "currentMenu";


export default {

    //持久化用户信息
    setUser(user: User) {
        sessionStorage.setItem(userConst, JSON.stringify(user))
    },
    getUser() {
        return sessionStorage.getItem(userConst);
    },

    //持久化当前菜单
    setCurrentMenu(menu) {
        sessionStorage.setItem(currentMenuConst, JSON.stringify(menu))
    },
    delCurrentMenu() {
        sessionStorage.removeItem(currentMenuConst);
    },
    getCurrentMenu() {
        return sessionStorage.getItem(currentMenuConst);
    },

    //清空数据
    clear() {
        sessionStorage.clear();
    }

}

