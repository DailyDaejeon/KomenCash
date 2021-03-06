import axios from 'axios';
import store from '@/stores/index.js';
import router from '@/routes/index.js';


export function setInterceptors() {
  let instance = axios.create({
    baseURL: 'https://k4b203.p.ssafy.io/api',
    // CORS 방지코드
    // headers: {
    //   Authorization: store.state.user.token
      // 'Access-Control-Allow-Origin': '*',
      // 'Content-Type': 'application/json; charset = utf-8'
    // }
  });
  instance.interceptors.request.use(
    (config) => {
      // console.log('request Config',config)
      let token = store.state.user.token;
      if (token) {
        var expire_date =store.state.user.userInfo['token_expired'];
        if(expire_date < Date.now()){
          store.commit('logout')
          router.push("{name:'Login'}");
        }
        else{
        config.headers['auth-token'] = token;
        }
      }
      return config;
    },
    (error) => Promise.reject(error.response)
  );
  instance.interceptors.response.use(
    (config) => {
      // console.log('response Config',config)
      return config;
    },
    (error) => Promise.reject(error.response)
  );
  return instance;
}



