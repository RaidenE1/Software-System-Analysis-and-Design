import axios from "@/http/axios";

// 获取科研人员基本信息
export const getInfo = params => {
  return axios({
    url: '/scholar/getInfo',
    method: 'get',
    params
  })
}

// 获取科研人员合作作者列表
export const getCoAuthors = params => {
  return axios({
    url: '/scholar/getCoAuthors',
    method: 'get',
    params
  })
}

// 获得科研人员合作机构列表
export const getCoAffiliate = params => {
  return axios({
    url: '/scholar/getCoAffiliate',
    method: 'get',
    params
  })
}

// 关注科研门户
export const focusScholar = params => {
  return axios({
    url: '/scholar/focusScholar',
    method: 'get',
    params
  })
}


export const getInfoByName = params => {
  return axios({
    url: '/scholar/getInfoByName',
    method: 'get',
    params
  })
}
