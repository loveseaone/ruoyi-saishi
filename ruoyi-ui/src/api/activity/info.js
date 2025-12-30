import request from '@/utils/request'

// 查询活动列表
export function listActivity(query) {
  return request({
    url: '/activity/info/list',
    method: 'get',
    params: query
  })
}

// 获取活动详情
export function getActivity(activityId) {
  return request({
    url: '/activity/info/' + activityId,
    method: 'get'
  })
}

// 新增活动
export function addActivity(data) {
  return request({
    url: '/activity/info',
    method: 'post',
    data: data
  })
}

// 修改活动
export function updateActivity(data) {
  return request({
    url: '/activity/info',
    method: 'put',
    data: data
  })
}

// 删除活动
export function delActivity(activityIds) {
  return request({
    url: '/activity/info/' + activityIds,
    method: 'delete'
  })
}