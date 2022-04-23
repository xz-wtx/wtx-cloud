import { createStore } from 'vuex'
import {User, UserFunc} from "@/store/module/User";
import storage from  "@/store/storage/storage";
//定义一个state的接口
export interface State {
    user: User,
}
const store = createStore<State>({
    state() {
        return {
            user:<User>{},
        }
    },
    mutations: {
        //保存用户信息
        setUser(state,user:User) {
            storage.setUser(user)
            state.user=user
        },
        //清空数据
        clearSave(state) {
            storage.clear()
            state.user=UserFunc({});
        }
    },
    getters:{
        getUser(state){
            if (state.user.token!==undefined){
                return state.user;
            }else{
                let user=storage.getUser();
                if (user != null){
                    let parseUser = JSON.parse(user);
                    store.commit("setUser",parseUser)
                    return parseUser;
                }else{
                    return <User>{};
                }

            }
        }
    }
})
export default store