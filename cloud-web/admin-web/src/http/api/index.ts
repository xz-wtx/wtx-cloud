import login_api from './login/login_api'
import user_api from "@/http/api/sys/user_api";
import dept_api from "@/http/api/sys/dept_api";
import role_api from "@/http/api/sys/role_api";
import menu_api from "@/http/api/sys/menu_api";
import dict_api from "@/http/api/sys/dict_api";
import path_api from "@/http/api/sys/white_path_api";
import ip_api  from "@/http/api/sys/white_ip_api";
export default {
    admin:{
        login_api,
        user_api,
        dept_api,
        role_api,
        menu_api,
        dict_api,
        ip_api,
        path_api,

    }
};