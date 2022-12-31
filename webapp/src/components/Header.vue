<template>
    <el-header>
        <div class="container">
            <el-icon>
                <Goods />
            </el-icon>
            <span>珞珈优选</span>
        </div>
        <el-button type="info" @click="logout">退出</el-button>
    </el-header>
</template>
<script>
import { Goods } from '@element-plus/icons-vue';

export default {
    name: "PageHeader",
    methods: {
        logout() {
            window.sessionStorage.clear();
            this.$router.push({
                name: "LoginPage"
            });
        }
    },
    components: { Goods }
}
</script>
<style scoped>
.el-header {
    background-color: #313743;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;
}

.container {
    display: flex;
    align-items: center;
}

span {
    margin-left: 15px;
}
</style>