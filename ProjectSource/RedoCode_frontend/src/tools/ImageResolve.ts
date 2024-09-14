import axios from 'axios'

export default function profilePicImageResolve(url: string)
{
if(url=== null || url=== undefined || url==="")
{
    return "/src/assets/Images/ProfilePictureBase.png"
}
else
return axios.defaults.baseURL+"public/files/media/"+url;
}