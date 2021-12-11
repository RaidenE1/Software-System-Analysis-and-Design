<template>
<div id="moments">
    <navbar>
    </navbar>
    <el-container>
        <myaside>
        </myaside>
        <div>
            <div v-for="i in moments_to_show" v-bind:key="i.index" class="momentsingle">
                <moment :moment=i>
                </moment>
            </div>
        </div>
    </el-container>
</div>
</template>

<script>
import navbar from "@/components/header.vue"
import myaside from "@/components/UserHeader.vue"
import moment from "@/components/user/SingleMoment.vue"
export default {
    components: {
        navbar,
        myaside,
        moment
    },
    data() {
        return {
            count: 0,
            moments: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, ],
            moments_to_show: [],
            no_more_moments: false,
        }
    },
    mounted() {
        this.load();
        window.addEventListener('scroll', this.handleScroll, true)
    },
    methods: {
        handleScroll() {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
            var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
            if (scrollTop + windowHeight >= scrollHeight) {
                this.load()
            }

        },
        load() {
            if(this.no_more_moments){
                return;
            }
            this.count += 10;
            if (this.count >= this.moments.length) {
                this.no_more_moments = true;
                this.count = this.moments.length;
            }
            for (var i = 0; i < this.count; i++) {
                this.moments_to_show.push(this.moments[i]);
            }
        }
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll)
    },
}
</script>

<style lang="scss">
body {
    overflow: visible;
}

//  momentmain {
//     border: 1px solid #e0e0e0;
//     border-radius: 10px;
//     width: 1400px;
//     margin-left: 5%;
//     margin-top: 50px;
//     padding-bottom: 50px;
//     background: #fafafa;

//     .el-divider.el-divider--horizontal {
//         width: 96%;
//         align-self: center;
//         margin-left: 2%;
//     }
//  }
ul {
    height: 800px;
    width: 98%;
}
.momentsingle {
    border: 1px solid black;
    height:100px;
    width: 500px;
}
</style>
