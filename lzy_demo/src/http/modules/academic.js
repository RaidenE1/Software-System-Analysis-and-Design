import axios from "@/http/axios";

// 根据检索词获取检索结果列表
export const getSearchResult = data => {
  return axios({
    url: '/academic/getSearchResult',
    method: 'post',
    data
  })
}

// 根据id获取一篇文章
export const getById = params =>{
  return axios({
    url:'/academic/getById',
    method:'get',
    params
  })
}

// 获取搜索结果左侧导航栏的信息
// export const getNavResult = params => {
//   return axios({
//     url: '/academic/getNavResult',
//     method: 'get',
//     params
//   })
// }

// 收藏文章
export const favorSc = params => {
  return axios({
    url: '/academic/favorSc',
    method: 'get',
    params
  })
}

// 引用文章
// export const quoteSc = params => {
//   return axios({
//     url: '/academic/quoteSc',
//     method: 'get',
//     params
//   })
// }

// 批量引用文章
// export const BQuoteSc = params => {
//   return axios({
//     url: '/academic/BQuoteSc',
//     method: 'get',
//     params
//   })
// }

// 获取关键字
export const getHotKeywords = params => {
  return axios({
    url: '/academic/getHotKeywords',
    method: 'get',
    params
  })
}
