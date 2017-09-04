import {Interest} from "./interest";

export interface ChatGroup {
  name: string,
  description: string,
  interest: Interest,
  users: Array<Interest>
}
