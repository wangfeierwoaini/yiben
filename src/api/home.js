import request from '@/utils/request'
import { getToken } from '@/utils/auth'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()
const home = {
  findAllVideo({ state }) {
    return request.get('http://127.0.0.1:7879/video/saveVideo', state.token)
  }
}
export default {
  namespaced: true,
  home,
  state
}
