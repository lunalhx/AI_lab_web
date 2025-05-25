import request from '@/utils/request'

// 查询通知管理列表
export function listNotifications(query) {
  return request({
    url: '/lab_web_sys/notifications/list',
    method: 'get',
    params: query
  })
}

// 查询通知管理详细
export function getNotifications(id) {
  return request({
    url: '/lab_web_sys/notifications/' + id,
    method: 'get'
  })
}

// 新增通知管理
export function addNotifications(data) {
  return request({
    url: '/lab_web_sys/notifications',
    method: 'post',
    data: data
  })
}

// 修改通知管理
export function updateNotifications(data) {
  return request({
    url: '/lab_web_sys/notifications',
    method: 'put',
    data: data
  })
}

// 删除通知管理
export function delNotifications(id) {
  return request({
    url: '/lab_web_sys/notifications/' + id,
    method: 'delete'
  })
}
