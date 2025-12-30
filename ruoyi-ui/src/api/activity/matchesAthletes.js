import request from '@/utils/request'

// 查询场次成员关联列表
export function listMatchesAthletes(query) {
  return request({
    url: '/activity/matches/athletes/list',
    method: 'get',
    params: query
  })
}

// 获取场次成员关联详情
export function getMatchesAthletes(id) {
  return request({
    url: '/activity/matches/athletes/' + id,
    method: 'get'
  })
}

// 新增场次成员关联
export function addMatchesAthletes(data) {
  return request({
    url: '/activity/matches/athletes',
    method: 'post',
    data: data
  })
}

// 修改场次成员关联
export function updateMatchesAthletes(data) {
  return request({
    url: '/activity/matches/athletes',
    method: 'put',
    data: data
  })
}

// 删除场次成员关联
export function delMatchesAthletes(ids) {
  return request({
    url: '/activity/matches/athletes/' + ids,
    method: 'delete'
  })
}