import api from "@/net/axios"

const _URL = '/user'

export const getListById = (data) => api.get(_URL+"/getListById",data)
export const save = (data) => api.get(_URL+"/save",data)
export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)
export const removeById = (data) => api.get(_URL+"/removeById",data)
export const updateById = (data) => api.post(_URL+"/update",data)