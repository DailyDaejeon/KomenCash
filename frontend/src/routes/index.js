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
    {
      path: '/group/job',
      name: 'JobPage',
      component: () => import('@/views/group/job/JobListPage.vue'),
    },
    {
      path: '/group/law',
      name: 'LawPage',
      component: () => import('@/views/group/law/LawPage.vue'),
    },
    {
      path: '/group/tax',
      name: 'TaxPage',
      component: () => import('@/views/group/tax/TaxPage.vue'),
    },
    {
      path: '/group/bank',
      name: 'BankPage',
      component: () => import('@/views/group/bank/BankPage.vue'),
    },
    {
      path: '/group/bank/stock',
      name: 'StockPage',
      component: () => import('@/views/group/bank/StockPage.vue'),
    },
    {
      path: '/group/credit',
      name: 'CreditPage',
      component: () => import('@/views/group/credit/CreditPage.vue'),
    },
    {
      path: '/group/store',
      name: 'StorePage',
      component: () => import('@/views/group/store/StorePage.vue'),
    },
    {
      path: '/group/setting',
      name: 'GroupSettingPage',
      component: () => import('@/views/group/GroupSettingPage.vue'),
    },
  ],
});

export default router;