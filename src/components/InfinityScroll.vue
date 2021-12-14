<template>
<!-- 组件需要传入两个值，
list:想要实现无限滚动的数据的Array，其内数据为对象Object
component:一个String，为需要无限滚动的组件的url,!!!注意，无法识别@,默认路径为InfinityScroll.vue-->
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
            this.used_component = () => import('' + this.component);
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
            console.log("now load");
            if (this.no_more) {
                return;
            }
            this.count += 10;
            if (this.count >= this.list.length) {
                this.no_more = true;
                this.count = this.list.length;
            }
        },
    },

    computed: {
        to_show() {

            return this.list.slice(0, this.count);
        }
    },
    destroyed() {
        window.removeEventListener('scroll', this.handleScroll)
    },
}
</script>

<style lang="scss">
.nomore {
    margin-top: 30px;
    margin-bottom: 20px;
    color: #3b3b3b;

    span {
        line-height: 30px;
        font-size: 24px;
        font-weight: 700;
    }
}
</style>
