import api from "@/net/axios"

const _URL = '/order'
export const confirmOrder = (data) => api.post(_URL+"/confirmOrder",data)
export const getListByUid = () => api.get(_URL+"/getListByUid")