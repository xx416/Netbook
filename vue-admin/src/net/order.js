import api from "@/net/axios"

const _URL = '/order'
export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)
export const getOrderByUser = (data) => api.get(_URL+"/getOrderByUser",data)
export const removeById = (data) => api.get(_URL+"/removeById",data)
export const save = (data) => api.post(_URL+"/save",data)
export const updateById = (data) => api.post(_URL+"/update",data)