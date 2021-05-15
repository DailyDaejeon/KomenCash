import Vue from 'vue';
import Router from 'vue-router';
import store from '@/stores/index';

Vue.use(Router);

const router =  new Router({
  mode: 'history',
  scrollBehavior(to, from) {
    // to, from, savedPosition
    if (to.path !== from.path) {
      return { x: 0, y: 0 }
    }
  },
  routes: [
    {
      path: '/',
      redirect: { name: 'Login' },
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
      meta: { auth: true },
      props: true
    },
    {
      path: '/group/list',
      name: 'GroupList',
      meta: { auth: true },
      component: () => import('@/views/group/GroupList.vue'),
    },
    {
      path: '/group/job',
      meta: { auth: true },
      name: 'JobPage',
      component: () => import('@/views/group/job/JobListPage.vue'),
    },
    {
      path: '/group/job/detail',
      name: 'JobDetailPage',
      meta: { auth: true },
      component: () => import('@/views/group/job/JobDetailPage.vue'),
      props: true,
      children : [
        {
          path: ':dataName',
          name: 'JobDetail',
          meta: { auth: true },
          component: () => import('@/components/group/job/JobDetail.vue'),
          props: true
        },
      ]
    },
    {
      path: '/group/job/resume',
      name: 'JobResumeRequest',
      meta: { auth: true },
      component: () => import('@/components/group/job/JobRequestList.vue'),
    },
    {
      path: '/group/job/resume/detail',
      name: 'JobResumeDetail',
      props:true,
      meta: { auth: true },
      component: () => import('@/components/group/job/Resume.vue')
    },
    {
      path: '/group/law',
      name: 'LawPage',
      meta: { auth: true },
      component: () => import('@/views/group/law/LawPage.vue'),
      props: true,
      children:[
        // {
        //   path: 'list',
        //   name: 'LawList',
        //   component: () => import('@/components/group/law/LawList.vue'),
        //   props: true
        // },
        {
          path: ':id',
          name: 'LawType',
          meta: { auth: true },
          component: () => import('@/components/group/law/LawType.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/case',
      name: 'CasePage',
      component: () => import('@/views/group/law/CasePage.vue'),
      meta: { auth: true },
      props: true
    },
    {
      path: '/group/vote/:id',
      name: 'VoteDetail',
      component: () => import('@/components/group/vote/VoteDetail.vue'),
      meta: { auth: true },
      props: true
    },
    {
      path: '/group/tax',
      name: 'TaxPage',
      meta: { auth: true },
      component: () => import('@/views/group/tax/TaxPage.vue'),
    },
    {
      path: '/group/bank/account',
      name: 'BankAccountPage',
      meta: { auth: true },
      component: () => import('@/views/group/bank/BankAccountPage.vue'),
      children: [
        { 
          path: ':id',
          name: 'BankMemberDetail',
          meta: { auth: true },
          component: () => import('@/components/group/bank/BankMemberDetail.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/bank/financial',
      name: 'BankFinancialPage',
      meta: { auth: true },
      component: () => import('@/views/group/bank/BankFinancialList.vue'),
      children: [
        {
          path: ':id',
          name: 'BankFinancialDetail',
          meta: { auth: true },
          component: () => import('@/components/group/bank/BankFinancialItem.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/bank/stock',
      name: 'StockPage',
      meta: { auth: true },
      component: () => import('@/views/group/bank/StockPage.vue'),
    },
    {
      path: '/group/bank/stock/detail',
      name: 'StockDetailPage',
      meta: { auth: true },
      component: () => import('@/views/group/bank/StockDetailPage.vue'),
      props: true
    },
    {
      path: '/group/credit/chart',
      name: 'CreditPage',
      meta: { auth: true },
      component: () => import('@/views/group/credit/CreditPage.vue'),
    },
    {
      path: '/group/credit/statistic',
      name: 'StatisticPage',
      meta: { auth: true },
      component: () => import('@/views/group/credit/StatisticPage.vue'),
      children: [{
        path: ':id',
        name: 'StatisticDetail',
        meta: { auth: true },
        component: () => import('@/components/group/credit/GroupStatisticDetail.vue'),
        props: true
      }]
    },
    {
      path: '/group/store/products',
      name: 'StoreListPage',
      meta: { auth: true },
      component: () => import('@/views/group/store/StoreListPage.vue'),
      props: true
    },
    {
      path: '/group/store/history',
      name: 'StoreHistoryPage',
      meta: { auth: true },
      component: () => import('@/views/group/store/StoreHistoryPage.vue'),
      props: true
    },
    {
      path: '/group/setting',
      name: 'GroupSettingPage',
      meta: { auth: true },
      component: () => import('@/views/group/GroupSettingPage.vue'),
      redirect: '/group/setting/groupInfo',
      children: [
        {
          path: 'groupInfo',
          name: 'GroupInfo',
          meta: { auth: true },
          component: () => import('@/components/group/settings/GroupInfoForm.vue'),
          props: true
        }, {
          path: 'userInfo',
          name: 'UserInfoPage',
          meta: { auth: true },
          component: () => import('@/components/group/settings/UserInfoPage.vue'),
          props: true
        },
        {
          path: 'certificate',
          name: 'GroupCertificate',
          meta: { auth: true },
          component: () => import('@/components/group/settings/GroupCertificate.vue'),
          props: true
        },
        {
          path: 'groupMemberList',
          name: 'GroupMemberList',
          meta: { auth: true },
          component: () => import('@/components/group/settings/GroupMemberListForm.vue'),
          props: true
        },
        {
          path: 'request',
          name: 'AddMemberRequest',
          meta: { auth: true },
          component: () => import('@/components/group/settings/AddMemberRequestForm.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/member/',
      name: 'GroupMemberDetail',
      meta: { auth: true },
      component: () => import('@/views/group/GroupMemberDetail.vue'),
      // redirect: '/group/member/groupInfo',
      children: [
        {
          path: ':id',
          name: 'GroupMemberInfo',
          meta: { auth: true },
          component: () => import('@/components/group/settings/GroupMemberInfo.vue'),
          props: true
        }
      ]
    },
    {
      path: '*',
      name: 'NotFound',
      component: () => import('@/views/NotFoundPage.vue'),
      redirect: '/group/main'
    },
  ],
});

  // console.log('로그인확인', store.state.isLogin)


router.beforeEach((to, from, next) => {
  // console.log('로그인확인', store.state.isLogin)
  if (to.meta.auth && !store.state.user.isLogin) {
    next('/member/login');
    return;
  }
  next();
});


export default router;