import {createRouter, createWebHistory} from "vue-router";
import {unauthorized} from "@/net";

const routes = [
    {
        path: '/',
        name: 'welcome',
        component: () => import('@/views/welcome/WelcomeView.vue'),
        children: [
            {
                path: '',
                name: 'welcome-login',
                component: () => import('@/views/welcome/LoginPage.vue')
            },
            {
                path: 'register',
                name: 'welcome-register',
                component: () => import('@/views/welcome/RegisterPage.vue')
            },
            {
                path: 'personalcenter',
                name: 'personalcenter',
                component: () => import('@/views/person/person.vue')
            },
        ]
    },
    {
        path: '/index',
        name: 'index',
        component: () => import('@/views/index/IndexView.vue'),
    }
]

const router = createRouter(
    {
        history: createWebHistory(),
        routes
    }
)

router.beforeEach((to,from,next)=>{
    const isUnauthorized = unauthorized()
    if (to.name.startsWith('welcome-') && !isUnauthorized){
        next('/index')
    }else if (to.fullPath.startsWith('/index') && isUnauthorized){
        next('/')
    }else{
        next()
    }
})

export default router