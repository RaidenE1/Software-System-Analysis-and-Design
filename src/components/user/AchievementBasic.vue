<template>
<div id="achievement">
    <el-row id="titlecontainer">
        <span>{{ achievement.title }}</span>
    </el-row>
    <el-row id="otherinfo">
        <el-col :span='5' class="singleinfo"> 发表日期: {{ achievement.date }} </el-col>
        <el-col :span='5' class="singleinfo"> 类型: {{ achievement.type }} </el-col>
        <el-col :span='4' class="singleinfo"> 被引用量: {{ achievement.cites }} </el-col>
        <el-col :span='6' class="singleinfo"> 领域: {{ achievement.field }} </el-col>
        <el-col :span='3' class="operate"><el-button size="medium" @click="view_details">查看</el-button></el-col>
    </el-row>
</div>
</template>

<script>
export default {
    props: {
        achievement: Object,
    },
    methods:{
        view_details(){
            
        }
    }
};
</script>

<style lang="scss" scoped>
#achievement {
    border: 1px solid #99CCFF;
    border-radius: 10px;
    margin-top:2px;
    margin-bottom:2px;
    padding: 5px;
    background: #ffffff;
}

#titlecontainer{
    margin-left: 10px;
    margin-top: 10px;
    span{
        font-size: 20px;
        display: flex;
        justify-self: left;
    }
}

#otherinfo{
    margin-bottom: 5px;
    margin-left: 5px;
    line-height: 36px;
    .singleinfo{
        display: flex;
        justify-self: left;
        margin-left: 5px;
        font-size:16px;
        
        color: gray;
    }
    .operate{
        margin-left: 5px;
        .el-button{
            width:100%;
        }
    }

}

</style>
