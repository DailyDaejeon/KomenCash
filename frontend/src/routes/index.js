import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);

const router =  new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect:{ name: 'MainPage' },
    },
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
      path: '/group/main',
      name: 'MainPage',
      component: () => import('@/views/group/MainPage.vue'),
      props:true
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
      path: '/group/job/detail',
      name: 'JobDetail',
      component: () => import('@/components/group/job/JobDetail.vue'),
    },
    {
      path: '/group/job/resume',
      name: 'JobResumeRequest',
      component: () => import('@/components/group/job/JobRequestList.vue'),
    },
    {
      path: '/group/job/resume/detail',
      name: 'JobResumeDetail',
      props:true,
      component: () => import('@/components/group/job/Resume.vue'),
    },
    {
      path: '/group/law',
      name: 'LawPage',
      component: () => import('@/views/group/law/LawPage.vue'),
    },
    {
      path: '/group/vote/:id',
      name: 'VoteDetail',
      component: () => import('@/components/group/vote/VoteDetail.vue'),
      props: true
    },
    {
      path: '/group/tax',
      name: 'TaxPage',
      component: () => import('@/views/group/tax/TaxPage.vue'),
    },
    {
      path: '/group/bank/account',
      name: 'BankAccountPage',
      component: () => import('@/views/group/bank/BankAccountPage.vue'),
      children: [
        { 
        path: ':id',
        name: 'BankMemberDetail',
        component: () => import('@/components/group/bank/BankMemberDetail.vue'),
        props: true
        }
      ]
    },
    {
      path: '/group/bank/financial',
      name: 'BankFinancialPage',
      component: () => import('@/views/group/bank/BankFinancialList.vue'),
      children: [
        {
          path: ':id',
          name: 'BankFinancialDetail',
          component: () => import('@/components/group/bank/BankFinancialItem.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/bank/stock',
      name: 'StockPage',
      component: () => import('@/views/group/bank/StockPage.vue'),
    },
    {
      path: '/group/bank/stock/detail',
      name: 'StockDetailPage',
      component: () => import('@/views/group/bank/StockDetailPage.vue'),
      props: true
    },
    {
      path: '/group/credit',
      name: 'CreditPage',
      component: () => import('@/views/group/credit/CreditPage.vue'),
    },
    {
      path: '/group/store/products',
      name: 'StoreListPage',
      component: () => import('@/views/group/store/StoreListPage.vue'),
      props: true
    },
    {
      path: '/group/store/history',
      name: 'StoreHistoryPage',
      component: () => import('@/views/group/store/StoreHistoryPage.vue'),
      props: true
    },
    {
      path: '/group/setting',
      name: 'GroupSettingPage',
      component: () => import('@/views/group/GroupSettingPage.vue'),
      redirect: '/group/setting/groupInfo',
      children: [
        {
          path: 'groupInfo',
          name: 'groupInfo',
          component: () => import('@/components/group/settings/GroupInfoForm.vue'),
          props: true
        },
        {
          path: 'groupMemberList',
          name: 'groupMemberList',
          component: () => import('@/components/group/settings/GroupMemberListForm.vue'),
          props: true
        },
        {
          path: 'addMemberRequest',
          name: 'addMemberRequest',
          component: () => import('@/components/group/settings/AddMemberRequestForm.vue'),
          props: true
        }
      ]
    },
  ],
});

export default router;