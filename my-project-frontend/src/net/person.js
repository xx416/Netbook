import api from "@/net/axios"

const _URL = '/file'

export const postUploadFile = (data) => api.postMultipart(_URL+"/upload",data)