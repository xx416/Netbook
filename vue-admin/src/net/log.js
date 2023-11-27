import api from "@/net/axios"

const _URL = '/log'
export const getPageList = (data) => api.get(_URL+"/getPageList",data)
export const getPagination = (data) => api.get(_URL+"/getPagination",data)