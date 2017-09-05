import {User} from "./user";

export interface GroupChatMessage {
  id: number,
  message: string,
  timestamp: Date,
  senderUser: User
}
