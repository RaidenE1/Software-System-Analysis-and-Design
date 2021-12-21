import axios from "@/http/axios";

// 提交登录表单
export const postLoginForm = params => {
  return axios({
    url: '/user/login',
    method: 'post',
    params
  })
}

// 提交注册表单
export const postRegisterForm = params => {
  return axios({
    url: '/user/register',
    method: 'post',
    params
  })
}

// 邮箱验证
export const sendEmail = params => {
  return axios({
    url: '/user/sendEmail',
    method: 'get',
    params
  })
}

// 获得个人信息
export const getPerInfo = params => {
  return axios({
    url: '/user/getPerInfo',
    method: 'get',
    params
  })
}

// 修改个人头像
export const changeImg = params => {
    return axios({
        url: '/user/changeImg',
        method: 'post',
        params
    })
}

// 修改个人信息
export const changeInfo = params => {
    return axios({
        url: '/user/changeInfo',
        method: 'post',
        params
    })
}

// 修改密码
export const changePasswd = params => {
    return axios({
        url: '/user/changePasswd',
        method: 'post',
        params
    })
}

// 获取收藏列表
export const getCollectionList = params => {
  return axios({
      url: '/user/getCollectionList',
      method: 'get',
      params
  })
}

// 获取关注列表
export const getFollowList = params => {
  return axios({
      url: '/user/getFollowList',
      method: 'get',
      params
  })
}

// 查询是不是管理员
export const isadmin = params => {
  return axios({
    url: '/user/isadmin',
    method: 'get',
    params
  })
}

//获取学术成果
export const getAchievement = params =>{
  return axios({
    url: '/user/myAchievement',
    method :'get',
    params
  })
}