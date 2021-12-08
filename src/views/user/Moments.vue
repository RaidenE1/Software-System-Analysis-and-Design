<template>
<div id="moments">
    <navbar>
    </navbar>
    <el-container>
        <myaside>
        </myaside>
        <div>
            <div v-for="i in moments_to_show" v-bind:key="i.index">
                <moment :moment=i>
                </moment>
            </div>
        </div>
    </el-container>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import myaside from "@/components/UserAside.vue"
export default {
    components: {
        navbar,
        myaside,
    },
    data() {
        return{
            count: 0,
            moments:[
                {},
            ],
            moments_to_show:[],

        }
    },
    methods: {
      load () {
        this.count++;
        this.moments.push({count:this.count})
      }
    }

}
</script>

<style lang="scss">
body {
    // margin-right: 0;
}
// #momentmain {
//     border: 1px solid #e0e0e0;
//     border-radius: 10px;
//     width: 1400px;
//     margin-left: 5%;
//     margin-top: 50px;
//     padding-bottom: 50px;
//     background: #fafafa;

//     .el-divider.el-divider--horizontal {
//         width: 96%;
//         align-self: center;
//         margin-left: 2%;
//     }
// }
ul {
    height: 800px;
    width: 98%;
}
</style>
