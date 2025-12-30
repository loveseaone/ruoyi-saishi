import request from '@/utils/request'

// 查询积分排名列表
export function listStandings(query) {
  return request({
    url: '/activity/standings/list',
    method: 'get',
    params: query
  })
}

// 获取积分排名详情
export function getStanding(standingId) {
  return request({
    url: '/activity/standings/' + standingId,
    method: 'get'
  })
}

// 新增积分排名
export function addStanding(data) {
  return request({
    url: '/activity/standings',
    method: 'post',
    data: data
  })
}

// 修改积分排名
export function updateStanding(data) {
  return request({
    url: '/activity/standings',
    method: 'put',
    data: data
  })
}

// 删除积分排名
export function delStanding(standingIds) {
  return request({
    url: '/activity/standings/' + standingIds,
    method: 'delete'
  })
}