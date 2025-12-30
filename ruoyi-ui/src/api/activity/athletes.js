import request from '@/utils/request'

// 查询待审核运动员名单
export function listLineups(query) {
  return request({
    url: '/activity/athletes/lineups/list',
    method: 'get',
    params: query
  })
}

// 确认运动员名单
export function confirmLineup(athleteId) {
  return request({
    url: '/activity/athletes/lineups/confirm',
    method: 'post',
    params: { athleteId: athleteId }
  })
}

// 驳回运动员名单
export function rejectLineup(data) {
  return request({
    url: '/activity/athletes/lineups/reject',
    method: 'post',
    data: data
  })
}

// 查询已绑定的微信用户
export function listWxUsers(query) {
  return request({
    url: '/activity/athletes/wx-users/list',
    method: 'get',
    params: query
  })
}

// 解绑微信用户
export function unbindWxUser(athleteId) {
  return request({
    url: '/activity/athletes/wx-users/' + athleteId,
    method: 'post'
  })
}

// 查询人员列表
export function listAthletes(query) {
  return request({
    url: '/activity/athletes/list',
    method: 'get',
    params: query
  })
}

// 获取人员详情
export function getAthlete(athleteId) {
  return request({
    url: '/activity/athletes/' + athleteId,
    method: 'get'
  })
}

// 新增人员
export function addAthlete(data) {
  return request({
    url: '/activity/athletes',
    method: 'post',
    data: data
  })
}

// 修改人员
export function updateAthlete(data) {
  return request({
    url: '/activity/athletes',
    method: 'put',
    data: data
  })
}

// 删除人员
export function delAthlete(athleteIds) {
  return request({
    url: '/activity/athletes/' + athleteIds,
    method: 'delete'
  })
}