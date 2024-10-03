import axios from 'axios'
import profileImg from '@/assets/Images/ProfilePictureBase.png'

export default function profilePicImageResolve(url: string) {
  if (url === null || url === undefined || url === '') {
    return profileImg
  } else return axios.defaults.baseURL + 'public/files/media/' + url
}
