import request from '@/utils/request'

// 查询队伍列表
export function listTeams(query) {
  return request({
    url: '/activity/teams/list',
    method: 'get',
    params: query
  })
}

// 获取队伍详情
export function getTeam(teamId) {
  return request({
    url: '/activity/teams/' + teamId,
    method: 'get'
  })
}

// 新增队伍
export function addTeam(data) {
  return request({
    url: '/activity/teams',
    method: 'post',
    data: data
  })
}

// 修改队伍
export function updateTeam(data) {
  return request({
    url: '/activity/teams',
    method: 'put',
    data: data
  })
}

// 删除队伍
export function delTeam(teamIds) {
  return request({
    url: '/activity/teams/' + teamIds,
    method: 'delete'
  })
}