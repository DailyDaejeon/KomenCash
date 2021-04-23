import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);

const router =  new Router({
  mode: 'history',
  routes: [
    {
      path: '/member/join',
      name: 'Signup',
      component: () => import('@/views/user/Signup.vue'),
    },
    {
      path: '/member/login',
      name: 'Login',
      component: () => import('@/views/user/Login.vue'),
      children: [
        {
          path: 'findid',
          name: 'FindId',
          component: () => import('@/components/user/FindId.vue'),
        },
        {
          path: 'findpw',
          name: "FindPw",
          component: () => import('@/components/user/FindPw.vue')
        },
      ]
    },
    {
      path: '/group/list',
      name: 'GroupList',
      component: () => import('@/views/group/GroupList.vue'),
    },
  ],
});

export default router;