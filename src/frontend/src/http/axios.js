import axios from 'axios';
import config from "@/http/config";

export default function $axios(options){
  return new Promise((resolve, reject) => {
    const instance = axios.create({
      baseURL: config.baseUrl,
      headers: config.header,
      withCredentials: config.withCredentials
    })
    // request 请求拦截器
    instance.interceptors.request.use(
      config => {
        // let token = this.$store.state.user.username
        // if (token) {
        //   config.headers.token = token
        // } else {
        //   // router.push('/login')
        // }
        return config
      },
      error => {
        // console.log('request', error)
        // router.push('/404')
        return Promise.reject(error)
      }
    )

    // response 响应拦截器
    instance.interceptors.response.use(
      response => {
        return response.data
      },
      error => {
        // console.error(err)
        // if (error) {
          // store.commit('logout')
          // router.replace('/login')
        // }
        return Promise.reject(error)
      }
    )
    // 请求处理
    instance(options).then(res => {
      resolve(res)
      return false
    }).catch(error => {
      reject(error)
    })
  })
}
