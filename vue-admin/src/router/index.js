import { createRouter, createWebHistory } from 'vue-router'
import {unauthorized} from "@/net";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/welcome/login.vue')
        },
        {
            path: '/',
            name: 'index',
            component: () => import('@/views/index/index.vue'),
            children:[
                {
                    path: '/basic',
                    name: 'basic',
                    component: () => import('@/views/basic/basic.vue')
                },
                {
                    path: '/book',
                    name: 'book',
                    component: () => import('@/views/book/book.vue')
                },
                {
                    path: '/order',
                    name: 'order',
                    component: () => import('@/views/order/order.vue')
                },
                {
                    path: '/tag',
                    name: 'tag',
                    component: () => import('@/views/tag/tag.vue')
                },
                {
                    path: '/press',
                    name: 'press',
                    component: () => import('@/views/press/press.vue')
                },
                {
                    path: '/news',
                    name: 'news',
                    component: () => import('@/views/news/news.vue')
                },
                {
                    path: '/userinfo',
                    name: 'userinfo',
                    component: () => import('@/views/user/userinfo.vue')
                },
                {
                    path: '/log',
                    name: 'log',
                    component: () => import('@/views/syslog/log.vue')
                }
            ]
        }
    ]
})

// function isLogin(){
//     if (unauthorized()){
//         ElMessage.warning('请登录再试')
//         router.push('/welcome')
//     }
// }

// 路由守卫
router.beforeEach((to, from) => {
    const isUnauthorized = unauthorized()
    if (isUnauthorized && to.name !== 'login'){
        return { name: 'login' }
    } else if (!isUnauthorized && to.name === 'login') {
        return { name: ''}
    }else{
        return null;
    }
})

export default router
