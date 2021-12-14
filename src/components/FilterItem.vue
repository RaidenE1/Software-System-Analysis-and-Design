<template>
<el-card class="card-body">
    <!-- title属性 汉字名 -->
    <div class="nav-title" @click="changeHide">
        <i ref="icon" class="el-icon-arrow-up" style="float: right" id="icon"></i>
        {{ this.filter_item.title }}
    </div>
    <!-- filter_itemList -->
    <div class="nav-list" v-if="isVisible">
        <div class="nav-list-item is-hover" v-for="(item, index) in this.showList" :key="index" @click="clickFilter(item.content)">
            <span>{{ item.content }}</span>
            <span id="item-nums">( {{ item.count }} )</span>
        </div>
    </div>
    <!--选中某一项-->
    <div class="nav-select" v-if="isClicked">
        <span class="nav-select-item">{{ this.select_item }}</span>
        <i class="el-icon-close is-hover" @click="clickCancel"></i>
    </div>
    <!--+-按键-->
    <div ref="show-more" class="list-show  is-hover" v-if="isVisible && (listLength > 3)" @click="changeShowNums">{{ showMore === false ? "+" : "-" }}</div>
</el-card>
</template>

<script>
export default {
    name: "FilterItem",
    props: {
        filter_item: {
            type: Object,
            default: null
        },
        c_filter_words: {}
    },
    data() {
        return {
            isClicked: false,
            select_item: '',
            listLength: 0,
            showList: [],
            showMore: false,
            isVisible: true
        }
    },
    methods: {
        clickFilter(val) {
            console.log("点击了" + val);
            this.$emit("getFilter", val, this.filter_item.filter_name);
        },
        clickCancel() {
            console.log("取消了筛选")
            this.$emit("cancelSelect", this.filter_item.filter_name);
        },
        changeHide() {
            if (this.isVisible === true) {
                this.isVisible = false;
                this.$refs.icon.className = "el-icon-arrow-down";
            } else {
                if (this.isClicked !== true) {
                    this.isVisible = true;
                    this.$refs.icon.className = "el-icon-arrow-up";
                }
            }
        },
        changeShowNums() {
            if (this.showMore === false) {
                this.showMore = true;
                this.showList = this.filter_item.filter_itemList.slice(0, Math.min(10, this.listLength));
            } else {
                this.showMore = false;
                this.showList = this.filter_item.filter_itemList.slice(0, Math.min(3, this.listLength));
            }
            console.log(this.filter_item.filter_itemList.length);
        },
        handleSelect() {
            // 获取该filter的种类
            let name = this.filter_item.filter_name;
            if (this.c_filter_words[name] === '') {
                // 表示该filter还未被选中
                this.isClicked = false
                this.isVisible = true
                this.$refs.icon.className = "el-icon-arrow-up";
                console.log('暂时无')
            } else {
                this.isClicked = true;
                this.isVisible = false;
                // 获取选中的值
                this.select_item = this.c_filter_words[name]
                this.$refs.icon.className = "el-icon-arrow-down";
                console.log(this.c_filter_words[name])
            }
        }
    },
    watch: {
        c_filter_words: {
            handler() {
                console.log("子组件监听到了filter_words的变化：")
                console.log(this.c_filter_words)
                this.handleSelect()
            },
            deep: true
        }
    },
    mounted() {
        // 判断该条目是否被选中
        this.handleSelect();
        // 默认只显示三条
        this.showMore = false;
        this.listLength = this.filter_item.filter_itemList.length;
        // 默认只显示三条
        this.showList = this.filter_item.filter_itemList.slice(0, Math.min(3, this.listLength));
    }
}
</script>

<style lang="scss" scoped>
.el-card {
    font-size: 13px;
    border: none;
    box-shadow: none;
    margin-right: 50px;
}

.el-card {
    .el-card__body {
        padding: 10px 0 0 0;
    }
}

.nav-title {
    color: #9fa19f;
    margin-bottom: 10px;
}

.nav-title:hover {
    cursor: pointer;
}

.nav-list {
    line-height: 20px;
}

.list-show {
    line-height: 20px;
}

.nav-list-item:hover {
    background-color: #f1f1f1;
}

.list-show:hover {
    background-color: #f1f1f1;
}

#item-nums {
    float: right;
    color: #9fa19f;
}

.nav-select {
    height: 33px;
    line-height: 33px;
    padding: 0 4px 0 5px;
    color: #666;
    background: #fafafa;
}

.el-icon-close {
    padding-top: 10px;
    float: right;
}

.is-hover:hover {
    cursor: pointer;
}
</style>
