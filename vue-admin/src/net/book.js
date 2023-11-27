import api from "@/net/axios"

const _URL = '/book'

export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getListById = (id) => api.get(_URL+"/getBookById",id)
export const getBookByName = (data) => api.get(_URL+"/getBookByName",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)
export const removeById = (id) => api.get(_URL+"/removeById",id)
export const save = (data) => api.post(_URL+"/save",data)
export const updateById = (data) => api.post(_URL+"/update",data)
export const updateByIdInt = (data) => api.get(_URL+"/updateByIdInt",data)
export const getBookNameList = (data) => api.get(_URL+"/getBookNameList",data)