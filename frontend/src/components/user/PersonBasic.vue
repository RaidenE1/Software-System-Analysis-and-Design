<template>
<div id='person'>
    <el-row id="personcontent">
        <el-col :span=5 id="head">
            <el-avatar :size="50" :src="circleUrl"></el-avatar>
        </el-col>
        <el-col :span=12 id='name'>
            <span>{{showed_name}}</span>
        </el-col>
        <el-col :span=7 id="operatesub">
            <el-button v-if="is_subscribe" type="info" @click="cancelSubscribe"><i class="el-icon-finished"></i>取消关注</el-button>
            <el-button v-else type="primary" @click="subscribe"><i class="el-icon-plus"></i>&#8194;关&#12288;注&#8194;</el-button>
        </el-col>
    </el-row>
</div>
</template>

<script>
export default {
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            is_subscribe:true,
        }
    },
    props: {
        basic_info: {
            head_img: '',
            username: "",
            real_name: '',
        }
    },
    computed: {
        showed_name: {
            get: function () {
                if (this.basic_info.real_name != '') {
                    if (this.basic_info.username != '') {
                        return this.basic_info.real_name + '(' + this.basic_info.username + ')';
                    } else {
                        return this.basic_info.real_name
                    }
                } else {
                    return this.basic_info.username;
                }
            }
        }
    },
    methods:{
        cancelSubscribe(){
            this.is_subscribe=false;
        },
        subscribe(){
            this.is_subscribe=true;
        }
    }
}
</script>

<style lang="scss" scoped>
#person {
    border: 1px solid blue;
    &content{
        margin-top: 10px;
    }
}
</style>
