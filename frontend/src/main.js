import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index';
import store from '@/stores/index';
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faEye, faEyeSlash, faEnvelope, faCheckCircle as farCheckC, faStar as farStar, faQuestionCircle } from "@fortawesome/free-regular-svg-icons";
import { faCheckCircle as fasCheckC, faCheck, faTimes, faThumbsUp, faStar, faAngleRight, faAngleLeft, faUserCog, faUpload, faTv, faEllipsisV} from "@fortawesome/free-solid-svg-icons";
import '@/css/index.css'
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';

import ChartPlugin from '@/plugins/ChartPlugin';
Vue.use(ChartPlugin);

Vue.config.productionTip = false

// sweetalert2 옵션
const options = {
  confirmButtonColor: '#e7ab3c',
  cancelButtonColor: '#757575',
};

Vue.use(VueSweetalert2,options);

Vue.use(BootstrapVue);


library.add(fasCheckC, faCheck, faTimes, faThumbsUp, faStar, faAngleLeft, faAngleRight, faUserCog, faUpload, faTv, faEllipsisV) //fas lib
library.add(faEye, faEyeSlash, faEnvelope, farCheckC, farStar, faQuestionCircle) //far lib
Vue.component('font-awesome-icon', FontAwesomeIcon)


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
