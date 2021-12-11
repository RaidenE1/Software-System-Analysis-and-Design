<template>
<div id="achievement" @click="view_details">
    <el-row id="titlecontainer">
        <span>{{ show_info.title }}</span>
    </el-row>
    <el-row id="otherinfo">
        <el-col :span='6' class="singleinfo"> 发表日期: {{ show_info.date }} </el-col>
        <el-col :span='6' class="singleinfo"> 类型: {{ show_info.type }} </el-col>
        <el-col :span='6' class="singleinfo"> 被引用量: {{ show_info.cites }} </el-col>
        <el-col :span='6' class="singleinfo"> 领域: {{ show_info.field }} </el-col>
    </el-row>
</div>
</template>

<script>
export default {
    props: {
        show_info: Object,
    },
    methods:{
        view_details(){
            console.log("view_details")
        }
    }
};
</script>

<style lang="scss" scoped>
#achievement {
    border: 1px solid #99CCFF;
    margin-top:2px;
    margin-bottom:2px;
    padding: 5px;
    background: #ffffff;
}
#achievement:hover{
    box-shadow: 1px 2px 3px rgba(48,48,48,0.6);
    transition:box-shadow .2s;
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
