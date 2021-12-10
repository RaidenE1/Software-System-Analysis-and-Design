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

Vue.use(utils);

import api from './http'
Vue.use(utils);
Vue.use(api);

Vue.use(VueAxios,axios);
Vue.prototype.$axios = axios;
Vue.use(ElementUI);
Vue.prototype.$md5 = md5;
Vue.use(VueClipboard)

// Vue.prototype.requestUrl = "http://localhost:8082";

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  render: h => h(App)
}).$mount('#app');
