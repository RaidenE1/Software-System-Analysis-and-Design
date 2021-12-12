import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import VueAxios from 'vue-axios'
import utils from './javascript/utils.js'
import md5 from 'js-md5';
import VueClipboard from 'vue-clipboard2';
import VueParticles from 'vue-particles'  
import api from './http'

Vue.use(utils);
Vue.use(api);
Vue.use(utils);
Vue.use(VueParticles)
Vue.use(VueAxios,axios);
Vue.prototype.$axios = axios;
Vue.use(ElementUI);
Vue.prototype.$md5 = md5;
Vue.use(VueClipboard)

Vue.prototype.requestUrl = "http://rap2api.taobao.org/app/mock/271178";
/*Vue.config.productionTip = false;
Vue.prototype.requestUrl = "http://localhost:8081";
Vue.prototype.baseUrl = "http://39.97.126.242:8081/index.html#/";
Vue.prototype.recentDoc = "";*/

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  render: h => h(App)
}).$mount('#app');
