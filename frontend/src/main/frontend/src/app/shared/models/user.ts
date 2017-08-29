import {Interest} from "./interest";
export interface User {
  id: number,
  username: string,
  firstName: string,
  lastName: string,
  photoUrl?: string,
  contact: {
    country?: string,
    city?: string,
    email: string,
    phone?: string,
    addressName?: string
  },
  interests: Array<Interest>
}
