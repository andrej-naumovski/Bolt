import {User} from "./user";
export interface Message {
  id: number,
  message: string,
  senderUser: User,
  receiverUser: User,
  timestamp: Date
}
