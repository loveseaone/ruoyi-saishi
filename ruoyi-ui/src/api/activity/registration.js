import request from '@/utils/request'

// 查询报名列表
export function listRegistration(query) {
  return request({
    url: '/activity/registration/list',
    method: 'get',
    params: query
  })
}

// 获取报名详情
export function getRegistration(regId) {
  return request({
    url: '/activity/registration/' + regId,
    method: 'get'
  })
}

// 新增报名
export function addRegistration(data) {
  return request({
    url: '/activity/registration',
    method: 'post',
    data: data
  })
}

// 修改报名
export function updateRegistration(data) {
  return request({
    url: '/activity/registration',
    method: 'put',
    data: data
  })
}

// 删除报名
export function delRegistration(regIds) {
  return request({
    url: '/activity/registration/' + regIds,
    method: 'delete'
  })
}