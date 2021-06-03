import request from '@/utils/request'

export function login(data) {
  return request({
    url: 'http://192.168.101.8:7879/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: 'http://192.168.101.8:7879/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout(token) {
  return request({
    url: 'http://192.168.101.8:7879/user/logout',
    method: 'post',
    params: { token }
  })
}
