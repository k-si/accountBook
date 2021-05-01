import request from '@/utils/request'

const api_name = '/accountbook/account-user'
export default {
    // 带条件的分页查询
    findByCondition(page, limit, searchObj) {
        return request({
            url: `${api_name}/findByCondition/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    // 根据id查找使用人
    findById(id) {
        return request({
            url: `${api_name}/find/${id}`,
            method: 'get'
        })
    },
    // 根据id修改使用人
    updateById(id, updateObj) {
        return request({
            url: `${api_name}/update/${id}`,
            method: 'post',
            data: updateObj
        })
    },
    // 添加使用人
    add(addObj) {
        return request({
            url: `${api_name}/add`,
            method: 'post',
            data: addObj
        })
    },
    // 根据id删除使用人
    deleteById(id) {
        return request({
            url: `${api_name}/delete/${id}`,
            method: 'delete'
        })
    },
    // 查询所有使用人
    find() {
        return request({
            url: `${api_name}/find`,
            method: 'get'
        })
    }
}