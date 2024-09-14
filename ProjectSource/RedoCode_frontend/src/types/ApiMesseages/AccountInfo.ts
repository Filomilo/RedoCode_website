import USER_TYPE from "./UserType";

export default interface AccountInfo{
    nickname: string
    profilePicture: string
    mail: string;
    type: USER_TYPE;
}