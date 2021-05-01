import request from '@/utils/request'

const api_name = '/accountbook/account-record'
export default {
    // 带条件的分页查询
    findByCondition(page, limit, searchObj) {
        return request({
            url: `${api_name}/findByCondition/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    // 根据id查找流水记录
    findById(id) {
        return request({
            url: `${api_name}/find/${id}`,
            method: 'get'
        })
    },
    // 根据id修改流水记录
    updateById(id, updateObj) {
        return request({
            url: `${api_name}/update/${id}`,
            method: 'post',
            data: updateObj
        })
    },
    // 添加流水记录
    add(addObj) {
        return request({
            url: `${api_name}/add`,
            method: 'post',
            data: addObj
        })
    },
    // 根据id删除流水记录
    deleteById(id) {
        return request({
            url: `${api_name}/delete/${id}`,
            method: 'delete'
        })
    },
    // 获取用户组总资产
    getTotalAssets() {
        return request({
            url: `${api_name}/getTotalAssets`,
            method: 'get'
        })
    }
}