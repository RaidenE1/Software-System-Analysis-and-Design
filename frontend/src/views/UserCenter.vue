<template>
    <div id='usercenter'>
        <div id="left">
            <div id="basicinfo">
                <div id="head">
                </div>
                <div id="info">
                    <span>用户名:{{user_info.username}}</span><br>
                    <span>邮箱:{{user_info.email}}</span><br>
                    <span>隶属单位:{{user_info.organazition}}</span><br>
                </div>
            </div>
            <div id="achievements">
                <div v-for="achievement,i in user_info.achievement" v-bind:key="i">
                <achievement :achievement="achievement"></achievement>
                </div>
            </div>
        </div>
        <div id='right'>
            <div id="subscribe">
                <div v-for="person,i in subscribe_person" v-bind:key="i">
                    <person :basic_info="person"></person>
                </div>
            </div>
            <div id="recent">
                <div v-for="achievement,i in recent_achievement" v-bind:key="i">
                    <achievment :achievment="achievement"></achievment>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default{
    data(){
        return{
            user_info:{
                username:'',
                email:'',
                organazition:'',
                real_name:'',
                education:'',
                field:'',
                introduction:'',
                achievement:[],
            },
            subscribe_person:[],
            recent_achievement:[],
        }

    }
}
</script>
<style>

</style>