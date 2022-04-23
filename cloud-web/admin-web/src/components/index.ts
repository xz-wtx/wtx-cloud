import x_table from "@/components/table/x_table.vue";
import x_init from '@/components/init/x_init.vue'
const load= {
    install:function (Vue) {
        Vue.component("x_table",x_table);
        Vue.component("x_init",x_init);
    }
}

export default load;