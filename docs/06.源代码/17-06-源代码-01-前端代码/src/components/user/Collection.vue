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
                    发表日期:{{show_info.time}}
                </span>
            </el-row>
        </el-col>
        <el-col :span=4>
            <div class="delbutton">
                <i class="el-icon-star-on" style="font-size:25px;color:orange;cursor:pointer" @click="delCollection(show_info.id,$event)"></i>
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
            this.$message({
                message: '删除成功',
                type: 'success',
                offset: 100,
            });

            this.$parent.$parent.delHistory(h_id);
            event.stopPropagation();
        },
        delCollection(id,event) {
            this.$parent.$parent.delCollection(id);

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

<style lang="scss">
.singlehistory {
    /* border: 1px solid #99CCFF; */
    margin-top: 2px;
    margin-bottom: 2px;
    padding: 5px;
    background-color: #ffffff;
    width: 920px;
    margin-left: 20px;
    height: 74px;
    padding-left: 0px;
    padding-top: 3px;
    padding-bottom: 3px;
    padding-right: 0px;

    .singletitle {
        text-align: left;
        height: 54px;

        span {
            line-height: 36px;
            font-size: 16px;
        }
    }

    .singletime {
        text-align: left;
        height: 20px;

        span {
            font: 14px;
            line-height: 20px;
            color: #aeaeae;

        }
    }

    .delbutton {
        height: 80px;
        line-height: 80px;

        button {
            width: 80%;
        }
    }

}

.singlehistory:hover {
    background-color: #e0e0e0;
    box-shadow: 1px 2px 3px rgba(48, 48, 48, 0.6);
    transition: box-shadow .2s, background-color .2s;
}
</style>
