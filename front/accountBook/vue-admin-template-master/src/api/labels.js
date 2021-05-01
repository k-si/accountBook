import request from '@/utils/request'

const api_name = '/accountbook/account-label'
export default {
    // 查找所有标签
    all() {
        return request({
            url: `${api_name}/all`,
            method: 'get'
        })
    },
    // 添加标签
    add(addObj) {
        return request({
            url: `${api_name}/add`,
            method: 'post',
            data: addObj
        })
    },
    // 根据id删除标签
    deleteById(id) {
        return request({
            url: `${api_name}/delete/${id}`,
            method: 'delete'
        })
    }
}