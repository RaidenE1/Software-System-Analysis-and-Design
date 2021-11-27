<template>
<div id="achievement">
    <el-row id="titlecontainer">
        <span>{{ achievement.title }}</span>
    </el-row>
    <el-row id="otherinfo">
        <el-col :span='6' class="singleinfo"> 发表日期: {{ achievement.date }} </el-col>
        <el-col :span='4' class="singleinfo"> 类型: {{ achievement.type }} </el-col>
        <el-col :span='4' class="singleinfo"> 被引用量: {{ achievement.cites }} </el-col>
        <el-col :span='7' class="singleinfo"> 领域: {{ achievement.field }} </el-col>
        <el-col :span='3' class="operate"><el-button size="small" type="text" @click="view_details">查看</el-button></el-col>
    </el-row>
</div>
</template>

<script>
export default {
    props: {
        achievement: {
            title: "",
            date: "",
            type: "",
            cites: "",
            field: "",
        },
    },
    methods: {
        view_details(){

        }
    }
};
</script>

<style lang="scss" scoped>
#achievement {
    border: 1px solid red;
}

#titlecontainer{
    margin-top: 10px;
    span{
        font-size: 20px;
        display: flex;
        justify-self: left;
    }
}

#otherinfo{
    line-height: 32px;
    .singleinfo{
        border: 1px solid green;
        display: flex;
        justify-self: left;
        font-size:10px;
        color: gray;
    }
    .operate{
        border: 1px solid green;
    }
}

</style>
