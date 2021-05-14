import Vue from 'vue';
import Router from 'vue-router';
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
      name: 'JobDetailPage',
      component: () => import('@/views/group/job/JobDetailPage.vue'),
      props: true,
      children : [
        {
          path: ':dataName',
          name: 'JobDetail',
          component: () => import('@/components/group/job/JobDetail.vue'),
          props: true
        },
      ]
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
      component: () => import('@/components/group/job/Resume.vue')
    },
    {
      path: '/group/law',
      name: 'LawPage',
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
          component: () => import('@/components/group/law/LawType.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/case',
      name: 'CasePage',
      component: () => import('@/views/group/law/CasePage.vue'),
      props: true
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
      path: '/group/credit/chart',
      name: 'CreditPage',
      component: () => import('@/views/group/credit/CreditPage.vue'),
    },
    {
      path: '/group/credit/statistic',
      name: 'StatisticPage',
      component: () => import('@/views/group/credit/StatisticPage.vue'),
      children: [{
        path: ':id',
        name: 'StatisticDetail',
        component: () => import('@/components/group/credit/GroupStatisticDetail.vue'),
        props: true
      }]
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
          name: 'GroupInfo',
          component: () => import('@/components/group/settings/GroupInfoForm.vue'),
          props: true
        }, {
          path: 'userInfo',
          name: 'UserInfoPage',
          component: () => import('@/components/group/settings/UserInfoPage.vue'),
          props: true
        },
        {
          path: 'certificate',
          name: 'GroupCertificate',
          component: () => import('@/components/group/settings/GroupCertificate.vue'),
          props: true
        },
        {
          path: 'groupMemberList',
          name: 'GroupMemberList',
          component: () => import('@/components/group/settings/GroupMemberListForm.vue'),
          props: true
        },
        {
          path: 'request',
          name: 'AddMemberRequest',
          component: () => import('@/components/group/settings/AddMemberRequestForm.vue'),
          props: true
        }
      ]
    },
    {
      path: '/group/member/',
      name: 'GroupMemberDetail',
      component: () => import('@/views/group/GroupMemberDetail.vue'),
      // redirect: '/group/member/groupInfo',
      children: [
        {
          path: ':id',
          name: 'GroupMemberInfo',
          component: () => import('@/components/group/settings/GroupMemberInfo.vue'),
          props: true
        }
      ]
    },
  ],
});

export default router;