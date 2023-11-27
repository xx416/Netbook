import api from "@/net/axios"

const _URL = '/news'

export const getList = () => api.get(_URL+"/getList")
export const save = (data) => api.post(_URL+"/save",data)