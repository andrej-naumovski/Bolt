import {Interest} from "./interest";
import {User} from "./user";

export interface ChatGroup {
  name: string,
  description: string,
  interest: Interest,
  users: Array<User>
}
