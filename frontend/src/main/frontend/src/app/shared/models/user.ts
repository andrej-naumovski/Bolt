export interface RegisterUser {
  username: string,
  password: string,
  firstName: string,
  lastName: string,
  contact: {
    email: string
  }
}
