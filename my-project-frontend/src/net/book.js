import api from "@/net/axios"

const _URL = '/book'

export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getListById = (id) => api.get(_URL+"/getBookById",id)
export const getBookByName = (data) => api.get(_URL+"/getBookByName",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)