import api from "@/net/axios"

const _URL = '/news'
export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)
export const getBookByName = (data) => api.get(_URL+"/getBookByName",data)
export const removeById = (data) => api.get(_URL+"/removeById",data)
export const save = (data) => api.post(_URL+"/save",data)
export const updateById = (data) => api.post(_URL+"/update",data)