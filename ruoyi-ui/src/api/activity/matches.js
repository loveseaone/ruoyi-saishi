import request from '@/utils/request'

// 查询场次列表
export function listMatches(query) {
  return request({
    url: '/activity/matches/list',
    method: 'get',
    params: query
  })
}

// 获取场次详情
export function getMatch(matchId) {
  return request({
    url: '/activity/matches/' + matchId,
    method: 'get'
  })
}

// 新增场次
export function addMatch(data) {
  return request({
    url: '/activity/matches',
    method: 'post',
    data: data
  })
}

// 修改场次
export function updateMatch(data) {
  return request({
    url: '/activity/matches',
    method: 'put',
    data: data
  })
}

// 删除场次
export function delMatch(matchIds) {
  return request({
    url: '/activity/matches/' + matchIds,
    method: 'delete'
  })
}