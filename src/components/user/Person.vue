<template>
<div id='person' @click="goScholar">
    <el-row id="personcontent">
        <el-col :span=5 id="head">
            <el-avatar :size="50" :src="show_info.avatar_url"></el-avatar>
        </el-col>
        <el-col :span=12 id='name'>
            <span>{{show_info.name}}</span>
        </el-col>
        <el-col :span=7 id="operatesub">
            <el-button v-if="is_subscribe" type="info" @click="cancelFollow"><i class="el-icon-finished"></i>取消关注</el-button>
            <!-- <el-button v-else type="primary" @click="subscribe"><i class="el-icon-plus"></i>&#8194;关&#12288;注&#8194;</el-button> -->
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
        show_info: Object,

    },
    // computed: {
    //     showed_name: {
    //         get: function () {
    //             if (this.basic_info.real_name != '') {
    //                 if (this.basic_info.username != '') {
    //                     return this.basic_info.real_name + '(' + this.basic_info.username + ')';
    //                 } else {
    //                     return this.basic_info.real_name
    //                 }
    //             } else {
    //                 return this.basic_info.username;
    //             }
    //         }
    //     }
    // },
    methods:{
        cancelFollow() {
            var _this = this
            this.$api.scholar.focusScholar({
                scholar_id: _this.show_info.expertId,
                user_id: sessionStorage.getItem("userID")
            }).then(res => {
                if (Number(res.code) === 200) {
                    _this.$message("取消关注成功");
                    // _this.follow_list.splice(index, 1);
                }
            })
        },
        goScholar() {
            this.$router.push({
                name: "ScholarPage",
                params: {
                    expertid: this.show_info.expertId
                }
            })
        }

    }
}
</script>

<style lang="scss" scoped>
#person {
    margin-top:2px;
    margin-bottom: 2px;
    border: 1px solid #99CCFF;
    padding-bottom: 5px;
    &content{
        margin-top: 10px;
    }
}
#person:hover {
    box-shadow: 1px 2px 3px rgba(48,48,48,0.6);
    transition:box-shadow .2s;
}
</style>
