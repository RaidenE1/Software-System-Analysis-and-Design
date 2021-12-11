<template>
<div class="singlemoment">
    <el-row>
        <el-col :span=2>
            <el-avatar :size="50" :src="circleUrl"></el-avatar>
        </el-col>
        <el-col :span=22>
            <el-row>
                <span>{{moment.from_name}}</span>
            </el-row>
            <el-row>
                <span>{{moment.content}}</span>
            </el-row>
            <el-row>
                <el-col :span=4>{{moment.time}}</el-col>
                <el-col :span=4><el-button><font-awesome-icon v-if="thumbed_up" icon="fa-solid fa-heart" /><font-awesome-icon v-else icon="fa-regular fa-heart" /></el-button></el-col>
                <el-col :span=16><el-button>评论</el-button></el-col>
            </el-row>
            <el-row>
                <div v-for="i in moment.submoment" v-bind:key=i.index>
                    <submoment :submoment=i>
                    </submoment>
                </div>
            </el-row>
        </el-col>
    </el-row>
</div>
</template>

<script>
import submoment from "@/components/user/SubMoment.vue"
export default {
    components:{
        submoment,
    },
    props: {
        moment:Object,
    }

}
</script>

<style>

</style>
