// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'lib-flexible/flexible'
import hljs from 'highlight.js'
import 'highlight.js/styles/googlecode.css' // 样式文件
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'


Vue.config.productionTip = false
Vue.prototype.$http=axios;
Vue.use(ElementUI);
// use
Vue.use(mavonEditor);


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
