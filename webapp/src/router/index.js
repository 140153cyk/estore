import { createRouter, createWebHashHistory } from "vue-router";
import Login from "../pages/LoginPage.vue";
import Main from "../pages/MainPage";
import Register from "../pages/RegisterPage";
import ProductPage from "../pages/ProductPage";
import UserOrderPage from "../pages/UserOrderPage";
import ShoppingCartPage from "../pages/ShoppingCartPage";
import MyStorePage from "../pages/MyStorePage";
import DetailedProductPage from "../pages/DetailedProductPage";
import StoreOrderPage from "../pages/StoreOrderPage";
import MinePage from "../pages/MinePage";



const routes = [
    {
        path: "/",
        redirect: "/LoginPage"
    },
    {
        path: "/LoginPage",
        name: "LoginPage",
        component: Login
    },
    {
        path: "/MainPage/:username",
        name: "MainPage",
        component: Main,
        children: [
            {
                path: "ProductPage",
                name: "ProductPage",
                component: ProductPage
            },
            {
                path: "UserOrderPage",
                name: "UserOrderPage",
                component: UserOrderPage
            },
            {
                path: "ShoppingCartPage",
                name: "ShoppingCartPage",
                component: ShoppingCartPage
            },
            {
                path: "MyStorePage",
                name: "MyStorePage",
                component: MyStorePage
            },
            {
                path: "StoreOrderPage",
                name: "StoreOrderPage",
                component: StoreOrderPage
            },
            {
                path: "MinePage",
                name: "MinePage",
                component: MinePage
            },
            {
                path: "DetailedProduct",
                name: "DetailedProductPage",
                component: DetailedProductPage
            }
        ]
    },
    {
        path: "/RegisterPage",
        name: "RegisterPage",
        component: Register
    }

];

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes
});

export default router;