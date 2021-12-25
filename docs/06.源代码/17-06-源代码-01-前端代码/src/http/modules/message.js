import axios from "@/http/axios";

// 获取消息
export const getMessage = params => {
    return axios({
        url: '/message/getMessage',
        method: 'get',
        params
    })
}

// 删除消息
export const deleteMessage = params => {
    return axios({
        url: '/message/deleteMessage',
        method: 'get',
        params
    })
}

// 修改已读未读
export const setMessageStatus = params => {
    return axios({
        url: '/message/setMessageStatus',
        method: 'get',
        params
    })
}