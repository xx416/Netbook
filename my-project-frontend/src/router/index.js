import { createRouter, createWebHistory } from 'vue-router'
import {unauthorized} from "@/net";
import {ElMessage} from "element-plus";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/welcome',
            name: 'welcome',
            component: () => import('@/views/welcome/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                }, {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                }, {
                    path: 'forget',
                    name: 'welcome-forget',
                    component: () => import('@/views/welcome/ForgetPage.vue')
                }
            ]
        }, {
            path: '/',
            name: 'indexView',
            component: () => import('@/views/index/IndexView.vue'),
            children: [
                {
                    path: '',
                    name: 'index',
                    component: () => import('@/views/index/Index.vue')
                },
                {
                    path: 'bookInfo/:id',
                    name: 'bookInfo',
                    component: () => import('@/views/book/bookInfo.vue')
                },
                {
                    path: 'person',
                    name: 'person',
                    component: () => import('@/views/person/person.vue'),
                    beforeEnter:[isLogin]
                },
                {
                    path: 'order',
                    name: 'order',
                    component: () => import('@/views/order/order.vue'),
                    beforeEnter:[isLogin]
                },
                {
                    path: 'success',
                    name: 'success',
                    component: () => import('@/views/Result/success.vue'),
                    beforeEnter:[isLogin]
                }
            ]
        }
    ]
})

function isLogin(){
    if (unauthorized()){
        ElMessage.warning('请登录再试')
        router.push('/welcome')
    }
}

// 路由守卫
// router.beforeEach((to, from, next) => {
//     const isUnauthorized = unauthorized()
//     if(to.name.startsWith('welcome') && !isUnauthorized) {
//         next('/index')
//     } else if(to.fullPath.startsWith('/index') && isUnauthorized) {
//         next('/')
//     } else {
//         next()
//     }
// })

export default router
