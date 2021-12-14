<template>
<div class="singlehistory" @click="goArticle(show_info.id)">
    <el-row>
        <el-col :span=20>
            <el-row class="singletitle">
                <span>
                    {{show_info.title}}
                </span>
            </el-row>
            <el-row class="singletime">
                <span>
                    上次浏览:{{show_info.time}}
                </span>
            </el-row>
        </el-col>
        <el-col :span=4>
            <div class="delbutton">
                <el-button @click="delHistory(show_info.h_id,$event)">删除</el-button>
            </div>
        </el-col>
    </el-row>
</div>
</template>

<script>
export default {
    props: {
        show_info: Object,
    },
    methods: {
        delHistory(h_id, event) {
            console.log(h_id);
            this.$message('删除成功');
            
            this.$parent.$parent.delHistory(h_id);
            event.stopPropagation();
        },
        goArticle(id) {
            this.$router.push({
                path: "/academicShow/" + id,
            })
        },
    }

}
</script>

<style>

</style>
