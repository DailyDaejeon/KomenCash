import Vue from 'vue'
import App from './App.vue'
import router from '@/routes/index';
import store from '@/stores/index';
import Vuetify from 'vuetify'
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faEye, faEyeSlash, faEnvelope, faCheckCircle as farCheckC, faStar as farStar, faQuestionCircle } from "@fortawesome/free-regular-svg-icons";
import { faCheckCircle as fasCheckC, faCheck, faTimes, faThumbsUp, faStar, faAngleRight, faAngleLeft, faUserCog, faUpload, faTv} from "@fortawesome/free-solid-svg-icons";
import '@/css/index.css'
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';

Vue.config.productionTip = false


Vue.use(VueSweetalert2);
Vue.use(BootstrapVue);
Vue.use(Vuetify);


library.add(fasCheckC, faCheck, faTimes, faThumbsUp, faStar, faAngleLeft, faAngleRight, faUserCog, faUpload, faTv) //fas lib
library.add(faEye, faEyeSlash, faEnvelope, farCheckC, farStar, faQuestionCircle) //far lib
Vue.component('font-awesome-icon', FontAwesomeIcon)


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
