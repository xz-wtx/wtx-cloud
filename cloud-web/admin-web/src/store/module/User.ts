

export interface User{
    id?: number;
    token?:  string,
    username?: string,
    account?:string,
    signStr?:string,
    roleList?:any,
    menuList?: any;
    buttonList?:any;
}

export let UserFunc = (p: User) => {
    p.id??= 0;
    p.token??= "";
    p.username??="";
    p.account??="";
    p.signStr??="";
    p.roleList??=[];
    p.menuList??=[];
    p.buttonList??=[];
    return p;
};


//let square = <UserStore> {token:res.data.data.token,username:res.data.data.userName};
//
// export class User implements UserStore{
//
//     token?: string ;
//     username?: string;
//     account?:string;
//     role?: any;
//     menuList?: any;
//     buttonList?:any;
//
//
//     constructor(token?:string,username?:string,account?:string,role?:any,menuList?:any,buttonList?:any) {
//         this.token =token;
//         this.username =username??"";
//         this.account =account??"";
//         this.role =role??[];
//         this.menuList =menuList??[];
//         this.buttonList =buttonList??[];
//     }
//}