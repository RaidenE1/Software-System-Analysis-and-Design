<template>
<div id="achievement" @click="view_details">
    <el-row id="titlecontainer">
        <span>{{ show_info.title }}</span>
    </el-row>
    <el-row id="otherinfo">
        <el-col :span='20' class="singleinfotime"> 发表日期: {{ show_info.time }} </el-col>
        <el-col :span='4' class="singleinfonum"> 被引用量: {{ show_info.num }} </el-col>
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
            this.$router.push({
                path:'/academicShow/'+this.show_info.documentId
                })
            console.log("view_details")
        }
    }
};
</script>

<style lang="scss" scoped>
#achievement {
    margin-top:2px;
    margin-bottom:2px;
    padding: 5px;
    background: #ffffff;
    width:920px;
    margin-left:20px;
    height: 74px;
    padding-left: 0px;
    padding-top: 3px;
    padding-bottom: 3px;
    padding-right:0px;
}
#achievement:hover{
    background-color:#e0e0e0;
    box-shadow: 1px 2px 3px rgba(48,48,48,0.6);
    transition:box-shadow .2s,background-color .2s;
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
    margin-left: 10px;
    line-height: 36px;
    .singleinfotime{
        display: flex;
        justify-self: left;
        font-size:16px;
        
        color: gray;
    }
    .singleinfonum{
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
