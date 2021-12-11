import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

  const routes = [
    {
      path: '/login',
      name:'Login',
      component: () => import('../views/user/Login.vue')
    },
    {
      path: '/register',
      name:'Register',
      component: () => import('../views/user/Register.vue')
    },
    {
      path: '/changeInfo',
      name:'ChangeInfo',
      component: () => import('../views/user/ChangeInfo.vue')
    },
    {
      path: '/myCollection',
      name:'MyCollection',
      component: () => import('../views/user/MyCollection.vue')
    },
    {
      path: '/myFollow',
      name:'MyFollow',
      component: () => import('../views/user/MyFollow.vue')
    },
    {
      path: '/myHistory',
      name:'MyHistory',
      component: () => import('../views/user/MyHistory.vue')
    },
    {
      path: '/test',
      name: 'Test',
      component: () => import('../views/test.vue')
    },
    {
      path: '/',
      name: 'Home',
      component: () => import('../views/homePage.vue')
    },

    {
      path: '/academicSearch/:search_words',
      name: 'AcademicSearch',
      component: () => import('../views/academic/AcademicSearch')
    },
    {
      path:'/academicShow/:academicID',
      name: 'AcademicShow',
      component: () => import('../views/academic/AcademicShow')
    },
    {
      path: '/scholarPage/:expertid',
      name: 'ScholarPage',
      component: () => import('../views/scholar/ScholarPage')
    },
    {
      path:'/admin',
      name:'admin',
      component: () => import('../views/admin/admin')
    },
    {
      path: '/404',
      name: 'NotFound',
      component: () => import('../views/404')
    },
    {
      path: '/myAchievement',
      name :"myAchievement",
      component: ()=> import('../views/user/MyAchievement.vue')
    },
    {
      path:'/moments',
      name : 'Moments',
      component: ()=>import('../views/user/Moments.vue')
    },
    {
      path:"*",
      redirect:{
        name:'NotFound'
      }
    }
]

const router = new VueRouter({
  //mode: 'history',
  routes: routes
})

export default router
