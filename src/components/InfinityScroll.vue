<template>
<div>
    <div v-for="i in to_show" v-bind:key="i.index">
        <component :is="used_component" :show_info="i"></component>
    </div>
    <div v-if="no_more" class="nomore">
        <span>没有更多了</span>
    </div>
</div>
</template>

<script>
export default {
    props: {
        list: Array,
        component: String,
    },
    data() {
        return {
            count: 0,
            no_more: false,
            to_show: [],
            used_component: null,
        }
    },
    mounted() {
        this.chooseComponent();
        this.load();
        window.addEventListener('scroll', this.handleScroll, true);
        console.log(this.list);

    },
    methods: {
        chooseComponent() {
            this.used_component = () => import("./user/" + this.component + ".vue");
            //this.used_component=()=>import('./user/Achievement.vue')
        },
        handleScroll() {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
            var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
            if (scrollTop + windowHeight >= scrollHeight) {
                this.load()
            }
        },
        load() {
            if (this.no_more) {
                return;
            }
            this.count += 10;
            if (this.count >= this.list.length) {
                this.no_more = true;
                this.count = this.list.length;
            }
            for (var i = 0; i < this.count; i++) {
                this.to_show.push(this.list[i]);
            }
        }
    },

    destroyed() {
        window.removeEventListener('scroll', this.handleScroll)
    },
}
</script>

<style lang="scss">
.nomore {
    margin-top:30px;
    margin-bottom:20px;
    color:#3b3b3b;
    span{
        line-height:30px;
        font-size:24px;
        font-weight:700;
    }
}
</style>
